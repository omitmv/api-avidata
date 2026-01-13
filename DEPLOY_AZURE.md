# Deploy no Azure Container Apps

Este guia mostra como fazer deploy da API AviData no Azure usando Container Apps e Docker.

## 📋 Pré-requisitos

1. **Azure CLI** instalado
   ```powershell
   winget install Microsoft.AzureCLI
   ```

2. **Docker Desktop** instalado e rodando
   - Download: https://www.docker.com/products/docker-desktop

3. **Conta Azure** ativa
   - Crie uma conta gratuita: https://azure.microsoft.com/free/

## 🚀 Deploy Inicial

### 1. Testar localmente com Docker

```powershell
# Construir a imagem
docker build -t avidata-api .

# Executar localmente
docker run -p 8080:8080 `
  -e AZURE_DB_URL="jdbc:mysql://seu-servidor:3306/avidata" `
  -e AZURE_DB_USERNAME="seu-usuario" `
  -e AZURE_DB_PASSWORD="sua-senha" `
  -e AZURE_JWT_SECRET="seu-secret-jwt" `
  avidata-api

# Testar: http://localhost:8080/swagger-ui.html
```

### 2. Deploy no Azure

#### Opção A: Script Automatizado (Recomendado)

```powershell
# Edite deploy-azure.ps1 e ajuste as configurações no topo do arquivo:
# - RESOURCE_GROUP: nome do grupo de recursos
# - LOCATION: região (brazilsouth, eastus, etc)
# - ACR_NAME: nome do registry (único globalmente)
# - APP_NAME: nome da sua aplicação

# Execute o script
.\deploy-azure.ps1
```

#### Opção B: Comandos Manuais

```powershell
# Variáveis
$RESOURCE_GROUP = "avidata-rg"
$LOCATION = "brazilsouth"
$ACR_NAME = "avidataacr"
$APP_NAME = "avidata-api"

# Login
az login

# Criar grupo de recursos
az group create --name $RESOURCE_GROUP --location $LOCATION

# Criar Container Registry
az acr create --name $ACR_NAME --resource-group $RESOURCE_GROUP --sku Basic --admin-enabled true

# Build e push
docker build -t avidata-api .
az acr login --name $ACR_NAME
$ACR_SERVER = az acr show --name $ACR_NAME --query loginServer --output tsv
docker tag avidata-api ${ACR_SERVER}/avidata-api:latest
docker push ${ACR_SERVER}/avidata-api:latest

# Criar ambiente
az containerapp env create --name avidata-env --resource-group $RESOURCE_GROUP --location $LOCATION

# Criar app
$ACR_PASSWORD = az acr credential show --name $ACR_NAME --query "passwords[0].value" --output tsv
az containerapp create `
  --name $APP_NAME `
  --resource-group $RESOURCE_GROUP `
  --environment avidata-env `
  --image ${ACR_SERVER}/avidata-api:latest `
  --target-port 8080 `
  --ingress external `
  --registry-server $ACR_SERVER `
  --registry-username $ACR_NAME `
  --registry-password $ACR_PASSWORD `
  --cpu 0.5 `
  --memory 1.0Gi
```

## 🔄 Atualizar Aplicação

Após fazer mudanças no código:

```powershell
# Use o script de update
.\update-azure.ps1
```

## ⚙️ Configurar Variáveis de Ambiente

Configure as variáveis de ambiente no Azure:

```powershell
az containerapp update `
  --name avidata-api `
  --resource-group avidata-rg `
  --set-env-vars `
    AZURE_DB_URL="jdbc:mysql://seu-servidor.mysql.database.azure.com:3306/avidata?useSSL=true" `
    AZURE_DB_USERNAME="seu-usuario@servidor" `
    AZURE_DB_PASSWORD="sua-senha" `
    AZURE_JWT_SECRET="seu-jwt-secret-seguro"
```

Ou pelo portal Azure:
1. Acesse portal.azure.com
2. Encontre seu Container App
3. Vá em **Settings** → **Environment variables**
4. Adicione as variáveis necessárias

## 📊 Monitoramento

### Ver logs em tempo real
```powershell
az containerapp logs show --name avidata-api --resource-group avidata-rg --follow
```

### Ver informações do app
```powershell
az containerapp show --name avidata-api --resource-group avidata-rg
```

### Ver URL da aplicação
```powershell
az containerapp show --name avidata-api --resource-group avidata-rg --query "properties.configuration.ingress.fqdn" -o tsv
```

## 🗄️ Configurar Banco de Dados Azure

### Criar Azure Database for MySQL

```powershell
az mysql flexible-server create `
  --name avidata-mysql `
  --resource-group avidata-rg `
  --location brazilsouth `
  --admin-user adminuser `
  --admin-password "SuaSenha@123" `
  --sku-name Standard_B1ms `
  --tier Burstable `
  --storage-size 32

# Criar banco de dados
az mysql flexible-server db create `
  --resource-group avidata-rg `
  --server-name avidata-mysql `
  --database-name avidata

# Configurar firewall (permitir serviços Azure)
az mysql flexible-server firewall-rule create `
  --resource-group avidata-rg `
  --name avidata-mysql `
  --rule-name AllowAzureServices `
  --start-ip-address 0.0.0.0 `
  --end-ip-address 0.0.0.0
```

String de conexão:
```
jdbc:mysql://avidata-mysql.mysql.database.azure.com:3306/avidata?useSSL=true&requireSSL=true
```

## 💰 Custos Estimados

- **Container App**: ~$15-30/mês (0.5 vCPU, 1GB RAM)
- **Container Registry (Basic)**: ~$5/mês
- **MySQL Flexible Server (B1ms)**: ~$20-30/mês

**Total estimado**: ~$40-65/mês

Você pode usar o Azure Free Tier por 12 meses para começar gratuitamente.

## 🛑 Remover Recursos

Para deletar tudo e parar de ser cobrado:

```powershell
az group delete --name avidata-rg --yes
```

## 📚 Recursos Úteis

- [Azure Container Apps Documentation](https://learn.microsoft.com/azure/container-apps/)
- [Azure Container Registry](https://learn.microsoft.com/azure/container-registry/)
- [Azure Database for MySQL](https://learn.microsoft.com/azure/mysql/)

## 🔒 Segurança

- Nunca commite senhas no código
- Use Azure Key Vault para secrets sensíveis
- Configure sempre HTTPS (Container Apps já vem com SSL)
- Restrinja acesso ao banco de dados apenas para IPs necessários
