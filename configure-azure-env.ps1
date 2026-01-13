# Script para configurar variáveis de ambiente
$RESOURCE_GROUP = "gr-avidata"
$APP_NAME = "api-avidata"

Write-Host "Configurando variáveis de ambiente no Azure Container App..." -ForegroundColor Yellow

$dbUrl = "jdbc:mysql://cerfc.com.br:3306/rafa2370_data_avidata?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true"
$dbPassword = "QXZpRGF0YUAyMDI2"
$jwtSecret = "q9m2X4p7T1uJ8sF0bC3rV9yQ6wN1eH5kL2tA7zP4xM8="

az containerapp update `
    --name $APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --replace-env-vars `
        "AVIDATA_DB_URL=$dbUrl" `
        "AVIDATA_DB_USERNAME=rafa2370_user_avidata" `
        "AVIDATA_DB_PASSWORD=$dbPassword" `
        "AVIDATA_JWT_SECRET=$jwtSecret"

Write-Host "`nVariáveis de ambiente configuradas com sucesso!" -ForegroundColor Green
Write-Host "O Container App será reiniciado automaticamente." -ForegroundColor Cyan
