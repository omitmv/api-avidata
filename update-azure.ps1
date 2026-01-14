# Script para atualizar aplicação no Azure (após mudanças)
# Execute este script no PowerShell para fazer novo deploy

# Configurações - DEVEM SER AS MESMAS DO deploy-azure.ps1
$RESOURCE_GROUP = "gr-avidata"
$ACR_NAME = "acravidata"
$APP_NAME = "api-avidata"
$IMAGE_NAME = "avidata-api"
$IMAGE_TAG = "v$(Get-Date -Format 'yyyyMMdd-HHmmss')"  # Tag com timestamp
# Variáveis de ambiente - DEVEM SER AS MESMAS DO configure-azure-env.ps1
$AVIDATA_DB_URL = "jdbc:mysql://cerfc.com.br:3306/rafa2370_data_avidata?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true"
$AVIDATA_DB_USERNAME = "rafa2370_user_avidata"
$AVIDATA_DB_PASSWORD = "QXZpRGF0YUAyMDI2"
$AVIDATA_JWT_SECRET = "q9m2X4p7T1uJ8sF0bC3rV9yQ6wN1eH5kL2tA7zP4xM8="

Write-Host "=== Atualizando AviData API no Azure ===" -ForegroundColor Green

# 1. Build e Push da nova imagem diretamente no Azure
Write-Host "`n[1/3] Construindo e enviando nova versão para Azure..." -ForegroundColor Yellow
Write-Host "Isso pode levar alguns minutos..." -ForegroundColor Cyan

az acr build `
    --registry $ACR_NAME `
    --image ${IMAGE_NAME}:${IMAGE_TAG} `
    --image ${IMAGE_NAME}:latest `
    --file Dockerfile `
    --no-cache `
    .

# 2. Atualizar Container App
Write-Host "`n[2/3] Atualizando Container App..." -ForegroundColor Yellow
$ACR_LOGIN_SERVER = az acr show --name $ACR_NAME --query loginServer --output tsv

az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --image ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG} `
    --set-env-vars `
        AVIDATA_DB_URL=${AVIDATA_DB_URL} `
        AVIDATA_DB_USERNAME=${AVIDATA_DB_USERNAME} `
        AVIDATA_DB_PASSWORD=${AVIDATA_DB_PASSWORD} `
        AVIDATA_JWT_SECRET=${AVIDATA_JWT_SECRET}

# 3. Aguardar nova revisão e verificar
Write-Host "`n[3/3] Verificando status da atualização..." -ForegroundColor Yellow
Start-Sleep -Seconds 5

$latestRevision = az containerapp revision list `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --query "[0].name" `
    --output tsv

Write-Host "Nova revisão criada: $latestRevision" -ForegroundColor Cyan

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

Write-Host "`n=== Atualização Concluída! ===" -ForegroundColor Green
$APP_URL = az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.configuration.ingress.fqdn" --output tsv
Write-Host "Aplicação disponível em: https://$APP_URL" -ForegroundColor Cyan
Write-Host "Swagger UI: https://$APP_URL/swagger-ui.html" -ForegroundColor Cyan
Write-Host "Versão implantada: $IMAGE_TAG" -ForegroundColor Cyan

Write-Host "`nPara ver os logs em tempo real, execute:" -ForegroundColor Yellow
Write-Host "az containerapp logs show --name $APP_NAME --resource-group $RESOURCE_GROUP --follow" -ForegroundColor White
