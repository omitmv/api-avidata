# Script para atualizar aplicação no Azure (após mudanças)
# Execute este script no PowerShell para fazer novo deploy

# Configurações - DEVEM SER AS MESMAS DO deploy-azure.ps1
$RESOURCE_GROUP = "gr-avidata"
$ACR_NAME = "acravidata"
$APP_NAME = "api-avidata"
$IMAGE_NAME = "avidata-api"
$IMAGE_TAG = "v$(Get-Date -Format 'yyyyMMdd-HHmmss')"  # Tag com timestamp

Write-Host "=== Atualizando AviData API no Azure ===" -ForegroundColor Green

# 1. Build da nova imagem
Write-Host "`n[1/4] Construindo nova imagem..." -ForegroundColor Yellow
docker build -t ${IMAGE_NAME}:${IMAGE_TAG} .
docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${IMAGE_NAME}:latest

# 2. Login no ACR
Write-Host "`n[2/4] Fazendo login no Container Registry..." -ForegroundColor Yellow
az acr login --name $ACR_NAME

# 3. Push da nova imagem
Write-Host "`n[3/4] Enviando nova versão para Azure..." -ForegroundColor Yellow
$ACR_LOGIN_SERVER = az acr show --name $ACR_NAME --query loginServer --output tsv
docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}
docker tag ${IMAGE_NAME}:${IMAGE_TAG} ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:latest
docker push ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}
docker push ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:latest

# 4. Atualizar Container App
Write-Host "`n[4/4] Atualizando Container App..." -ForegroundColor Yellow
az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --image ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}

Write-Host "`n=== Atualização Concluída! ===" -ForegroundColor Green
$APP_URL = az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.configuration.ingress.fqdn" --output tsv
Write-Host "Aplicação disponível em: https://$APP_URL" -ForegroundColor Cyan
