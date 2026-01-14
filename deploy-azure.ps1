# Script de Deploy para Azure Container Apps
# Execute este script no PowerShell

# Configurações - AJUSTE CONFORME NECESSÁRIO
$RESOURCE_GROUP = "gr-avidata"
$LOCATION = "eastus"
$ACR_NAME = "acravidata"  # Deve ser único globalmente
$APP_NAME = "api-avidata"
$IMAGE_NAME = "avidata-api"
$IMAGE_TAG = "latest"
$AVIDATA_DB_URL = "jdbc:mysql://cerfc.com.br:3306/rafa2370_data_avidata?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true"
$AVIDATA_DB_USERNAME = "rafa2370_user_avidata"
$AVIDATA_DB_PASSWORD = "QXZpRGF0YUAyMDI2"
$AVIDATA_JWT_SECRET = "q9m2X4p7T1uJ8sF0bC3rV9yQ6wN1eH5kL2tA7zP4xM8="

Write-Host "=== Deploy AviData API para Azure Container Apps ===" -ForegroundColor Green

# 1. Verificar se está logado no Azure
Write-Host "`n[1/9] Verificando login no Azure..." -ForegroundColor Yellow
$account = az account show 2>$null
if (-not $account) {
    Write-Host "Fazendo login no Azure..." -ForegroundColor Cyan
    az login
}

# 2. Registrar providers necessários
Write-Host "`n[2/9] Registrando providers do Azure..." -ForegroundColor Yellow
az provider register --namespace Microsoft.ContainerRegistry --wait
az provider register --namespace Microsoft.App --wait
az provider register --namespace Microsoft.OperationalInsights --wait
Write-Host "Providers registrados com sucesso!" -ForegroundColor Green

# 3. Criar grupo de recursos
Write-Host "`n[3/9] Criando grupo de recursos..." -ForegroundColor Yellow
az group create --name $RESOURCE_GROUP --location $LOCATION

# 4. Criar Azure Container Registry
Write-Host "`n[4/9] Criando Azure Container Registry..." -ForegroundColor Yellow
az acr create `
    --name $ACR_NAME `
    --resource-group $RESOURCE_GROUP `
    --sku Basic `
    --admin-enabled true

# 5. Build da imagem diretamente no Azure (sem Docker local)
Write-Host "`n[5/9] Construindo imagem no Azure Container Registry..." -ForegroundColor Yellow
Write-Host "Isso pode levar alguns minutos..." -ForegroundColor Cyan
az acr build `
    --registry $ACR_NAME `
    --image ${IMAGE_NAME}:${IMAGE_TAG} `
    --image ${IMAGE_NAME}:latest `
    --file Dockerfile `
    .

# 6. Obter informações do ACR
Write-Host "`n[6/9] Obtendo informações do Container Registry..." -ForegroundColor Yellow
$ACR_LOGIN_SERVER = az acr show --name $ACR_NAME --query loginServer --output tsv

# 7. Criar ambiente do Container Apps
Write-Host "`n[7/9] Criando ambiente Container Apps..." -ForegroundColor Yellow
$ENV_NAME = "avidata-env"
az containerapp env create `
    --name $ENV_NAME `
    --resource-group $RESOURCE_GROUP `
    --location $LOCATION

# 8. Criar Container App
Write-Host "`n[8/9] Criando Container App..." -ForegroundColor Yellow
$ACR_PASSWORD = az acr credential show --name $ACR_NAME --query "passwords[0].value" --output tsv

# Usar cmd.exe para evitar problemas com & no PowerShell
$createCommand = @"
az containerapp create --name $APP_NAME --resource-group $RESOURCE_GROUP --environment $ENV_NAME --image ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG} --target-port 8080 --ingress external --registry-server $ACR_LOGIN_SERVER --registry-username $ACR_NAME --registry-password $ACR_PASSWORD --cpu 0.5 --memory 1.0Gi --min-replicas 1 --max-replicas 3 --env-vars "AVIDATA_DB_URL=$AVIDATA_DB_URL" "AVIDATA_DB_USERNAME=$AVIDATA_DB_USERNAME" "AVIDATA_DB_PASSWORD=$AVIDATA_DB_PASSWORD" "AVIDATA_JWT_SECRET=$AVIDATA_JWT_SECRET"
"@

cmd /c $createCommand

# 9. Verificar status da aplicação
Write-Host "`n[9/9] Verificando status da aplicação..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

$latestRevision = az containerapp revision list `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --query "[0].name" `
    --output tsv

Write-Host "Revisão criada: $latestRevision" -ForegroundColor Cyan

Write-Host "`nAguardando container iniciar..." -ForegroundColor Yellow
$maxAttempts = 30
$attempt = 0
do {
    $attempt++
    Start-Sleep -Seconds 2
    $status = az containerapp revision show `
        --name $APP_NAME `
        --resource-group $RESOURCE_GROUP `
        --revision $latestRevision `
        --query "properties.runningState" `
        --output tsv
    
    Write-Host "Tentativa $attempt/$maxAttempts - Status: $status" -ForegroundColor Gray
    
    if ($status -eq "Running") {
        Write-Host "Container iniciado com sucesso!" -ForegroundColor Green
        break
    }
} while ($attempt -lt $maxAttempts)

if ($status -ne "Running") {
    Write-Host "AVISO: Container não iniciou no tempo esperado. Verifique os logs." -ForegroundColor Yellow
}

# Obter URL da aplicação
Write-Host "`n=== Deploy Concluído! ===" -ForegroundColor Green
$APP_URL = az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.configuration.ingress.fqdn" --output tsv
Write-Host "Aplicação disponível em: https://$APP_URL" -ForegroundColor Cyan
Write-Host "Swagger UI: https://$APP_URL/swagger-ui.html" -ForegroundColor Cyan
Write-Host "Versão implantada: $IMAGE_TAG" -ForegroundColor Cyan

Write-Host "`nPara ver os logs em tempo real, execute:" -ForegroundColor Yellow
Write-Host "az containerapp logs show --name $APP_NAME --resource-group $RESOURCE_GROUP --follow" -ForegroundColor White
