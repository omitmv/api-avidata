# Script para configurar variáveis de ambiente no Container App
# Este script usa o método mais seguro para passar variáveis com caracteres especiais

$RESOURCE_GROUP = "gr-avidata"
$APP_NAME = "api-avidata"

Write-Host "=== Configurando Variáveis de Ambiente ===" -ForegroundColor Green

# Configurar cada variável individualmente usando escape correto
Write-Host "`nConfigurando AVIDATA_DB_URL..." -ForegroundColor Yellow
$dbUrl = "jdbc:mysql://cerfc.com.br:3306/rafa2370_data_avidata?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true"
az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --replace-env-vars "AVIDATA_DB_URL=$dbUrl"

Write-Host "Configurando AVIDATA_DB_USERNAME..." -ForegroundColor Yellow
az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --set-env-vars "AVIDATA_DB_USERNAME=rafa2370_user_avidata"

Write-Host "Configurando AVIDATA_DB_PASSWORD..." -ForegroundColor Yellow
az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --set-env-vars "AVIDATA_DB_PASSWORD=QXZpRGF0YUAyMDI2"

Write-Host "Configurando AVIDATA_JWT_SECRET..." -ForegroundColor Yellow
az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --set-env-vars "AVIDATA_JWT_SECRET=q9m2X4p7T1uJ8sF0bC3rV9yQ6wN1eH5kL2tA7zP4xM8="

Write-Host "`n=== Configuração Concluída! ===" -ForegroundColor Green
Write-Host "Aguardando nova revisão iniciar..." -ForegroundColor Yellow

Start-Sleep -Seconds 10

# Verificar variáveis configuradas
Write-Host "`nVariáveis configuradas:" -ForegroundColor Cyan
az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.template.containers[0].env" --output table
