# run-local.ps1
# Script para ejecutar el backend con variables de entorno locales

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Backend Chaski - Entorno Local" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Solicitar password si no está configurado
$dbPassword = Read-Host "Ingresa el password de SQL Server (usuario 'sa')" -AsSecureString
$BSTR = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($dbPassword)
$passwordPlainText = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($BSTR)

if ([string]::IsNullOrWhiteSpace($passwordPlainText)) {
    Write-Host "ERROR: El password no puede estar vacio" -ForegroundColor Red
    Write-Host "Presiona cualquier tecla para salir..."
    $null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
    exit 1
}

# Configurar variables de entorno locales
Write-Host ""
Write-Host "Configurando variables de entorno..." -ForegroundColor Yellow

$env:DB_URL="jdbc:sqlserver://localhost:1433;databaseName=chaski_db;encrypt=true;trustServerCertificate=true"
$env:DB_USERNAME="sa"
$env:DB_PASSWORD=$passwordPlainText
$env:DDL_AUTO="update"
$env:SHOW_SQL="true"
$env:PORT="8080"
$env:LOG_SQL="DEBUG"
$env:LOG_LEVEL="DEBUG"

Write-Host "OK - Variables configuradas correctamente" -ForegroundColor Green
Write-Host ""

# Mostrar configuración (sin mostrar password completo)
Write-Host "Configuracion actual:" -ForegroundColor Cyan
Write-Host "  DB_URL: $env:DB_URL"
Write-Host "  DB_USERNAME: $env:DB_USERNAME"
Write-Host "  DB_PASSWORD: $('*' * $passwordPlainText.Length) caracteres"
Write-Host "  PORT: $env:PORT"
Write-Host "  DDL_AUTO: $env:DDL_AUTO"
Write-Host ""

# Iniciar aplicación
Write-Host "Iniciando aplicacion..." -ForegroundColor Yellow
Write-Host "Presiona Ctrl+C para detener el servidor" -ForegroundColor Yellow
Write-Host ""

.\mvnw.cmd spring-boot:run

