# ==============================================================================
# GUÍA DE DESPLIEGUE EN AZURE CONTAINER APPS (GRATIS 24/7)
# ==============================================================================
# Este script te guía para desplegar tu backend en Azure de forma gratuita
# usando Azure Container Apps con el plan de consumo.
#
# REQUISITOS PREVIOS:
# 1. Tener una cuenta de Azure (registrarse en https://azure.microsoft.com/free)
# 2. Instalar Azure CLI: https://aka.ms/installazurecli
# 3. Tener Docker instalado (opcional para pruebas locales)
#
# LÍMITES GRATUITOS:
# - 180,000 vCPU-segundos/mes
# - 360,000 GiB-segundos de memoria/mes
# - 2 millones de peticiones HTTP/mes
# ==============================================================================

Write-Host "=== DESPLIEGUE DE CHASKI BACKEND EN AZURE ===" -ForegroundColor Cyan
Write-Host ""

# Verificar si Azure CLI está instalado
try {
    az version | Out-Null
    Write-Host "✓ Azure CLI instalado" -ForegroundColor Green
} catch {
    Write-Host "✗ Azure CLI no está instalado" -ForegroundColor Red
    Write-Host "Instálalo desde: https://aka.ms/installazurecli" -ForegroundColor Yellow
    exit 1
}

# Configuración
$RESOURCE_GROUP = "chaski-backend-rg"
$LOCATION = "eastus"  # Región más económica
$CONTAINER_APP_ENV = "chaski-env"
$CONTAINER_APP_NAME = "chaski-backend"
$CONTAINER_REGISTRY = "chaskiregistry"  # Debe ser único globalmente
$IMAGE_NAME = "chaski-backend"
$IMAGE_TAG = "latest"

Write-Host ""
Write-Host "Configuración:" -ForegroundColor Yellow
Write-Host "  Grupo de recursos: $RESOURCE_GROUP"
Write-Host "  Ubicación: $LOCATION"
Write-Host "  Aplicación: $CONTAINER_APP_NAME"
Write-Host ""

# Paso 1: Login en Azure
Write-Host "Paso 1: Iniciando sesión en Azure..." -ForegroundColor Cyan
az login

# Paso 2: Crear grupo de recursos
Write-Host ""
Write-Host "Paso 2: Creando grupo de recursos..." -ForegroundColor Cyan
az group create --name $RESOURCE_GROUP --location $LOCATION

# Paso 3: Crear Azure Container Registry (ACR)
Write-Host ""
Write-Host "Paso 3: Creando Container Registry..." -ForegroundColor Cyan
az acr create `
    --resource-group $RESOURCE_GROUP `
    --name $CONTAINER_REGISTRY `
    --sku Basic `
    --admin-enabled true

# Obtener credenciales del registry
$ACR_USERNAME = az acr credential show --name $CONTAINER_REGISTRY --query "username" -o tsv
$ACR_PASSWORD = az acr credential show --name $CONTAINER_REGISTRY --query "passwords[0].value" -o tsv
$ACR_LOGIN_SERVER = az acr show --name $CONTAINER_REGISTRY --query "loginServer" -o tsv

Write-Host "✓ Registry creado: $ACR_LOGIN_SERVER" -ForegroundColor Green

# Paso 4: Construir y subir imagen Docker
Write-Host ""
Write-Host "Paso 4: Construyendo y subiendo imagen Docker..." -ForegroundColor Cyan
Write-Host "Esto puede tomar varios minutos..." -ForegroundColor Yellow

# Login en ACR
az acr login --name $CONTAINER_REGISTRY

# Construir imagen
docker build -t "${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}" .

# Subir imagen
docker push "${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}"

Write-Host "✓ Imagen subida correctamente" -ForegroundColor Green

# Paso 5: Crear Container Apps Environment
Write-Host ""
Write-Host "Paso 5: Creando Container Apps Environment..." -ForegroundColor Cyan
az containerapp env create `
    --name $CONTAINER_APP_ENV `
    --resource-group $RESOURCE_GROUP `
    --location $LOCATION

# Paso 6: Configurar base de datos
Write-Host ""
Write-Host "======================================" -ForegroundColor Yellow
Write-Host "CONFIGURACIÓN DE BASE DE DATOS" -ForegroundColor Yellow
Write-Host "======================================" -ForegroundColor Yellow
Write-Host ""
Write-Host "OPCIONES PARA LA BASE DE DATOS:" -ForegroundColor Cyan
Write-Host ""
Write-Host "OPCIÓN 1 (GRATIS): Azure SQL Database - Free tier" -ForegroundColor Green
Write-Host "  - 32 GB de almacenamiento"
Write-Host "  - Comando para crear:"
Write-Host "    az sql server create --name chaski-sql-server --resource-group $RESOURCE_GROUP --location $LOCATION --admin-user adminuser --admin-password 'TuPassword123!'" -ForegroundColor Gray
Write-Host "    az sql db create --resource-group $RESOURCE_GROUP --server chaski-sql-server --name chaski_db --edition Free" -ForegroundColor Gray
Write-Host ""
Write-Host "OPCIÓN 2 (EXTERNA): Usar una base de datos externa" -ForegroundColor Yellow
Write-Host "  - Railway.app (ofrece plan gratuito)"
Write-Host "  - Clever Cloud"
Write-Host "  - Tu propio servidor SQL Server"
Write-Host ""

$dbOption = Read-Host "¿Deseas crear Azure SQL ahora? (s/n)"

$DB_URL = ""
$DB_USERNAME = ""
$DB_PASSWORD = ""

if ($dbOption -eq "s") {
    $SQL_SERVER = "chaski-sql-" + (Get-Random -Maximum 9999)
    $SQL_DB = "chaski_db"
    $SQL_ADMIN = "adminuser"
    $SQL_PASSWORD = Read-Host "Ingresa una contraseña segura para SQL (mínimo 8 caracteres, mayúsculas, minúsculas, números y símbolos)"
    
    Write-Host "Creando SQL Server..." -ForegroundColor Cyan
    az sql server create `
        --name $SQL_SERVER `
        --resource-group $RESOURCE_GROUP `
        --location $LOCATION `
        --admin-user $SQL_ADMIN `
        --admin-password $SQL_PASSWORD
    
    # Permitir acceso desde Azure services
    az sql server firewall-rule create `
        --resource-group $RESOURCE_GROUP `
        --server $SQL_SERVER `
        --name AllowAzureServices `
        --start-ip-address 0.0.0.0 `
        --end-ip-address 0.0.0.0
    
    Write-Host "Creando base de datos..." -ForegroundColor Cyan
    az sql db create `
        --resource-group $RESOURCE_GROUP `
        --server $SQL_SERVER `
        --name $SQL_DB `
        --edition Free
    
    $DB_URL = "jdbc:sqlserver://${SQL_SERVER}.database.windows.net:1433;databaseName=${SQL_DB};encrypt=true;trustServerCertificate=false"
    $DB_USERNAME = $SQL_ADMIN
    $DB_PASSWORD = $SQL_PASSWORD
    
    Write-Host "✓ Base de datos creada" -ForegroundColor Green
} else {
    Write-Host ""
    Write-Host "Ingresa los datos de tu base de datos externa:" -ForegroundColor Cyan
    $DB_URL = Read-Host "URL de conexión JDBC"
    $DB_USERNAME = Read-Host "Usuario"
    $DB_PASSWORD = Read-Host "Contraseña" -AsSecureString
    $DB_PASSWORD = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto([System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($DB_PASSWORD))
}

# Paso 7: Desplegar Container App
Write-Host ""
Write-Host "Paso 7: Desplegando aplicación..." -ForegroundColor Cyan
az containerapp create `
    --name $CONTAINER_APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --environment $CONTAINER_APP_ENV `
    --image "${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}" `
    --registry-server $ACR_LOGIN_SERVER `
    --registry-username $ACR_USERNAME `
    --registry-password $ACR_PASSWORD `
    --target-port 8080 `
    --ingress external `
    --min-replicas 1 `
    --max-replicas 1 `
    --cpu 0.25 `
    --memory 0.5Gi `
    --env-vars `
        "DB_URL=$DB_URL" `
        "DB_USERNAME=$DB_USERNAME" `
        "DB_PASSWORD=$DB_PASSWORD" `
        "DDL_AUTO=update" `
        "SHOW_SQL=false" `
        "LOG_LEVEL=INFO"

# Obtener URL de la aplicación
Write-Host ""
Write-Host "======================================" -ForegroundColor Green
Write-Host "✓ DESPLIEGUE COMPLETADO" -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Green
Write-Host ""

$APP_URL = az containerapp show `
    --name $CONTAINER_APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --query properties.configuration.ingress.fqdn `
    -o tsv

Write-Host "Tu aplicación está disponible en:" -ForegroundColor Cyan
Write-Host "https://$APP_URL" -ForegroundColor Yellow
Write-Host ""
Write-Host "Endpoints de ejemplo:" -ForegroundColor Cyan
Write-Host "  - https://$APP_URL/api/usuarios" -ForegroundColor Gray
Write-Host "  - https://$APP_URL/api/restaurantes" -ForegroundColor Gray
Write-Host "  - https://$APP_URL/api/productos" -ForegroundColor Gray
Write-Host ""
Write-Host "Para ver logs:" -ForegroundColor Cyan
Write-Host "  az containerapp logs show --name $CONTAINER_APP_NAME --resource-group $RESOURCE_GROUP --follow" -ForegroundColor Gray
Write-Host ""
Write-Host "Para actualizar la aplicación:" -ForegroundColor Cyan
Write-Host "  1. Construye nueva imagen: docker build -t ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG} ." -ForegroundColor Gray
Write-Host "  2. Sube imagen: docker push ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}" -ForegroundColor Gray
Write-Host "  3. Actualiza app: az containerapp update --name $CONTAINER_APP_NAME --resource-group $RESOURCE_GROUP --image ${ACR_LOGIN_SERVER}/${IMAGE_NAME}:${IMAGE_TAG}" -ForegroundColor Gray
Write-Host ""
Write-Host "IMPORTANTE: Monitorea tu uso en https://portal.azure.com para mantenerte en el tier gratuito" -ForegroundColor Yellow
