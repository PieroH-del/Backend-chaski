# 🚀 Guía Rápida de Despliegue en Azure (GRATIS 24/7)

## 📋 Requisitos Previos

### 1. Cuenta de Azure
- Regístrate en [azure.microsoft.com/free](https://azure.microsoft.com/free)
- Obtienes $200 de crédito + servicios gratuitos por 12 meses
- No se cobra hasta que actualices manualmente

### 2. Instalar Azure CLI
```powershell
# Windows
winget install -e --id Microsoft.AzureCLI

# O descarga desde: https://aka.ms/installazurecli
```

### 3. Docker Desktop (opcional para pruebas locales)
```powershell
winget install Docker.DockerDesktop
```

## 🎯 Despliegue Automático

### Opción Simple: Ejecutar el script
```powershell
.\deploy-azure.ps1
```

El script te guiará paso a paso y:
- ✅ Creará todos los recursos necesarios
- ✅ Construirá y subirá tu aplicación
- ✅ Te dará la URL de tu backend funcionando
- ✅ Todo configurado para estar dentro del tier gratuito

## 💰 Costos y Límites Gratuitos

### Azure Container Apps (Plan de Consumo)
- **180,000** vCPU-segundos/mes GRATIS
- **360,000** GiB-segundos de memoria/mes GRATIS
- **2 millones** de peticiones HTTP/mes GRATIS

**Para tu aplicación (0.25 vCPU, 0.5 GB RAM):**
- Puede funcionar **~8.3 días 24/7** gratis por mes con configuración mínima
- Si optimizas peticiones, puedes mantenerlo gratis todo el mes

### Azure SQL Database (Free Tier)
- **32 GB** de almacenamiento GRATIS
- **5 DTU** de rendimiento
- Permanentemente gratuito (no expira después de 12 meses)

## 📊 Monitoreo de Uso

Revisa tu consumo en:
- Portal Azure: https://portal.azure.com
- Sección: Cost Management + Billing

## 🔄 Actualizar tu Aplicación

Después de hacer cambios en el código:

```powershell
# 1. Configurar variables
$CONTAINER_REGISTRY = "chaskiregistry"  # El nombre que usaste
$RESOURCE_GROUP = "chaski-backend-rg"
$CONTAINER_APP_NAME = "chaski-backend"

# 2. Login y configurar JAVA_HOME
$env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.7.6-hotspot"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# 3. Compilar proyecto
mvn clean package -DskipTests

# 4. Login en Azure y ACR
az login
az acr login --name $CONTAINER_REGISTRY

# 5. Obtener login server
$ACR_LOGIN_SERVER = az acr show --name $CONTAINER_REGISTRY --query "loginServer" -o tsv

# 6. Construir y subir imagen
docker build -t "${ACR_LOGIN_SERVER}/chaski-backend:latest" .
docker push "${ACR_LOGIN_SERVER}/chaski-backend:latest"

# 7. Actualizar Container App
az containerapp update `
    --name $CONTAINER_APP_NAME `
    --resource-group $RESOURCE_GROUP `
    --image "${ACR_LOGIN_SERVER}/chaski-backend:latest"
```

## 🧪 Probar Localmente con Docker

Antes de desplegar, puedes probar la imagen Docker localmente:

```powershell
# 1. Construir imagen
docker build -t chaski-backend:local .

# 2. Ejecutar con variables de entorno
docker run -p 8080:8080 `
    -e DB_URL="jdbc:sqlserver://tu-servidor:1433;databaseName=chaski_db;encrypt=true;trustServerCertificate=true" `
    -e DB_USERNAME="tu-usuario" `
    -e DB_PASSWORD="tu-password" `
    chaski-backend:local

# 3. Probar en http://localhost:8080/api/usuarios
```

## 🗃️ Opciones de Base de Datos

### Opción 1: Azure SQL Database (Recomendada)
✅ **32 GB gratis permanentemente**
```powershell
# El script deploy-azure.ps1 puede crear esto automáticamente
```

### Opción 2: Servicios Externos Gratuitos

#### A) Railway.app
- 500 horas gratis/mes
- SQL Server no disponible, pero puedes usar PostgreSQL
- Registro: https://railway.app

#### B) Clever Cloud
- MySQL/PostgreSQL gratuito con límites
- Registro: https://www.clever-cloud.com

#### C) Azure Database for MySQL/PostgreSQL (Flexible Server)
- No tiene tier completamente gratuito
- ~$15-20/mes para servidor mínimo

### Cambiar de SQL Server a PostgreSQL (si usas servicio externo)

Si eliges PostgreSQL, necesitas cambiar en `pom.xml`:

```xml
<!-- Reemplazar -->
<dependency>
    <groupId>com.microsoft.sqlserver</groupId>
    <artifactId>mssql-jdbc</artifactId>
    <scope>runtime</scope>
</dependency>

<!-- Por -->
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <scope>runtime</scope>
</dependency>
```

Y en `application.properties`:
```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/chaski_db}
spring.datasource.driverClassName=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

## 📝 Ver Logs de la Aplicación

```powershell
# Ver logs en tiempo real
az containerapp logs show `
    --name chaski-backend `
    --resource-group chaski-backend-rg `
    --follow

# Ver logs recientes
az containerapp logs show `
    --name chaski-backend `
    --resource-group chaski-backend-rg `
    --tail 100
```

## 🔒 Seguridad

### Variables de Entorno Seguras
Nunca incluyas credenciales en el código. Usa:

```powershell
# Actualizar variables de entorno sin redesplegar
az containerapp update `
    --name chaski-backend `
    --resource-group chaski-backend-rg `
    --set-env-vars `
        "DB_PASSWORD=nueva-password"
```

### Firewall de SQL
```powershell
# Permitir tu IP para gestión
az sql server firewall-rule create `
    --resource-group chaski-backend-rg `
    --server tu-servidor-sql `
    --name MiIP `
    --start-ip-address TU.IP.PUB.LICA `
    --end-ip-address TU.IP.PUB.LICA
```

## 🛑 Eliminar Todos los Recursos

Para evitar cargos futuros:

```powershell
az group delete --name chaski-backend-rg --yes --no-wait
```

## ❓ Solución de Problemas

### Error: "No se puede conectar a la base de datos"
1. Verifica que el firewall de SQL permita Azure Services
2. Comprueba las variables de entorno con:
   ```powershell
   az containerapp show --name chaski-backend --resource-group chaski-backend-rg
   ```

### Error: "Out of memory"
Aumenta la memoria del contenedor:
```powershell
az containerapp update `
    --name chaski-backend `
    --resource-group chaski-backend-rg `
    --memory 1.0Gi
```

### La aplicación no responde
```powershell
# Reiniciar la aplicación
az containerapp revision restart `
    --name chaski-backend `
    --resource-group chaski-backend-rg
```

## 🎓 Recursos Adicionales

- [Documentación Azure Container Apps](https://learn.microsoft.com/azure/container-apps/)
- [Pricing Calculator](https://azure.microsoft.com/pricing/calculator/)
- [Azure Free Account FAQ](https://azure.microsoft.com/free/free-account-faq/)

## 📞 Soporte

Si tienes problemas:
1. Revisa los logs con `az containerapp logs show`
2. Verifica el portal de Azure
3. Consulta la documentación oficial de Microsoft

---

**¡Tu backend estará funcionando 24/7 en la nube! 🎉**
