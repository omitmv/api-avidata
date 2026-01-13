# Script de Deploy para Azure Container Apps
# Execute este script no PowerShell

# Configurações - AJUSTE CONFORME NECESSÁRIO
$RESOURCE_GROUP = "gr-avidata"
$LOCATION = "East US"
$ACR_NAME = "acravidata"  # Deve ser único globalmente
$APP_NAME = "api-avidata"
$IMAGE_NAME = "avidata-api"
$IMAGE_TAG = "latest"

Write-Host "=== Deploy AviData API para Azure Container Apps ===" -ForegroundColor Green

# 1. Verificar se está logado no Azure
Write-Host "`n[1/8] Verificando login no Azure..." -ForegroundColor Yellow
$account = az account show 2>$null
if (-not $account) {
    Write-Host "Fazendo login no Azure..." -ForegroundColor Cyan
    az login
}

# 2. Criar grupo de recursos
Write-Host "`n[2/8] Criando grupo de recursos..." -ForegroundColor Yellow
az group create --name $RESOURCE_GROUP --location $LOCATION

# 3. Criar Azure Container Registry
Write-Host "`n[3/8] Criando Azure Container Registry..." -ForegroundColor Yellow
az acr create `
    --name $ACR_NAME `
    --resource-group $RESOURCE_GROUP `
    --sku Basic `
    --admin-enabled true

# 4. Build da imagem localmente
Write-Host "`n[4/8] Construindo imagem Docker..." -ForegroundColor Yellow
docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .

# 5. Login no ACR
Write-Host "`n[5/8] Fazendo login no Container Registry..." -ForegroundColor Yellow
az acr login --name $ACR_NAME

# 6. Tag e Push da imagem
Write-Host "`n[6/8] Enviando imagem para Azure..." -ForegroundColor Yellow
$ACR_LOGIN_SERVER = az acr show --name $ACR_NAME --query loginServer --output tsv
docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}
docker push ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}

# 7. Criar ambiente do Container Apps
Write-Host "`n[7/8] Criando ambiente Container Apps..." -ForegroundColor Yellow
$ENV_NAME = "avidata-env"
az containerapp env create `
    --name $ENV_NAME `
    --resource-group $RESOURCE_GROUP `
    --location $LOCATION

# 8. Criar Container App
Write-Host "`n[8/8] Criando Container App..." -ForegroundColor Yellow
$ACR_PASSWORD = az acr credential show --name $ACR_NAME --query "passwords[0].value" --output tsv

az containerapp create `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --environment $ENV_NAME `
    --image ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG} `
    --target-port 8080 `
    --ingress external `
    --registry-server $ACR_LOGIN_SERVER `
    --registry-username $ACR_NAME `
    --registry-password $ACR_PASSWORD `
    --cpu 0.5 `
    --memory 1.0Gi `
    --min-replicas 1 `
    --max-replicas 3

# Obter URL da aplicação
Write-Host "`n=== Deploy Concluído! ===" -ForegroundColor Green
$APP_URL = az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.configuration.ingress.fqdn" --output tsv
Write-Host "`nAplicação disponível em: https://$APP_URL" -ForegroundColor Cyan
Write-Host "Swagger UI: https://$APP_URL/swagger-ui.html" -ForegroundColor Cyan
