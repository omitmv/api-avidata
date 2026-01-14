# Script para configurar variáveis de ambiente corretamente
$RESOURCE_GROUP = "gr-avidata"
$APP_NAME = "api-avidata"

Write-Host "=== Configurando Variáveis de Ambiente ===" -ForegroundColor Green

# Usar cmd.exe para evitar problemas com & no PowerShell
$command = @"
az containerapp update --name $APP_NAME --resource-group $RESOURCE_GROUP --replace-env-vars "AVIDATA_DB_URL=jdbc:mysql://cerfc.com.br:3306/rafa2370_data_avidata?useSSL=false&serverTimezone=America/Sao_Paulo&allowPublicKeyRetrieval=true" "AVIDATA_DB_USERNAME=rafa2370_user_avidata" "AVIDATA_DB_PASSWORD=QXZpRGF0YUAyMDI2" "AVIDATA_JWT_SECRET=q9m2X4p7T1uJ8sF0bC3rV9yQ6wN1eH5kL2tA7zP4xM8="
"@

cmd /c $command

Write-Host "`n=== Verificando configuração ===" -ForegroundColor Yellow
Start-Sleep -Seconds 5

az containerapp show --name $APP_NAME --resource-group $RESOURCE_GROUP --query "properties.template.containers[0].env" --output table

Write-Host "`nAguarde cerca de 30 segundos para a nova revisão iniciar..." -ForegroundColor Cyan
