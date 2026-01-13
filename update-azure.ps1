# Script para atualizar aplicação no Azure (após mudanças)
# Execute este script no PowerShell para fazer novo deploy

# Configurações - DEVEM SER AS MESMAS DO deploy-azure.ps1
$RESOURCE_GROUP = "gr-avidata"
$ACR_NAME = "acravidata"
$APP_NAME = "api-avidata"
$IMAGE_NAME = "avidata-api"
$IMAGE_TAG = "v$(Get-Date -Format 'yyyyMMdd-HHmmss')"  # Tag com timestamp

Write-Host "=== Atualizando AviData API no Azure ===" -ForegroundColor Green

# 1. Build e Push da nova imagem diretamente no Azure
Write-Host "`n[1/2] Construindo e enviando nova versão para Azure..." -ForegroundColor Yellow
Write-Host "Isso pode levar alguns minutos..." -ForegroundColor Cyan

az acr build `
    --registry $ACR_NAME `
    --image ${IMAGE_NAME}:${IMAGE_TAG} `
    --image ${IMAGE_NAME}:latest `
    --file Dockerfile `
    .

# 2. Atualizar Container App
Write-Host "`n[2/2] Atualizando Container App..." -ForegroundColor Yellow
$ACR_LOGIN_SERVER = az acr show --name $ACR_NAME --query loginServer --output tsv

az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --image ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}

Write-Host "`n=== Atualização Concluída! ===" -ForegroundColor Green
$APP_URL = az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.configuration.ingress.fqdn" --output tsv
Write-Host "Aplicação disponível em: https://$APP_URL" -ForegroundColor Cyan
Write-Host "Swagger UI: https://$APP_URL/swagger-ui.html" -ForegroundColor Cyan
Write-Host "Versão implantada: $IMAGE_TAG" -ForegroundColor Cyan
