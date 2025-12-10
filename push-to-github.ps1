# Script para subir el código a GitHub
Write-Host "=== Iniciando proceso de subida a GitHub ===" -ForegroundColor Green

# Configurar el repositorio si no existe
Write-Host "`nVerificando repositorio Git..." -ForegroundColor Yellow
if (-not (Test-Path .git)) {
    Write-Host "Inicializando repositorio Git..." -ForegroundColor Cyan
    git init
}

# Agregar todos los archivos
Write-Host "`nAgregando archivos al repositorio..." -ForegroundColor Yellow
git add -A

# Hacer commit
Write-Host "`nCreando commit..." -ForegroundColor Yellow
git commit -m "Initial commit: Backend Chaski - Sistema de delivery completo"

# Cambiar a rama main
Write-Host "`nCambiando a rama main..." -ForegroundColor Yellow
git branch -M main

# Verificar si existe el remoto origin
$remotes = git remote
if ($remotes -notcontains "origin") {
    Write-Host "`nAgregando repositorio remoto..." -ForegroundColor Yellow
    git remote add origin https://github.com/lestrada57/ChaskiBackend.git
} else {
    Write-Host "`nRemoto 'origin' ya existe" -ForegroundColor Cyan
}

# Hacer push
Write-Host "`nSubiendo archivos a GitHub..." -ForegroundColor Yellow
Write-Host "IMPORTANTE: Se te pedirá autenticación de GitHub" -ForegroundColor Red
Write-Host "Si tienes autenticación de 2 factores, necesitarás un Personal Access Token" -ForegroundColor Red
git push -u origin main

Write-Host "`n=== Proceso completado ===" -ForegroundColor Green
Write-Host "Revisa tu repositorio en: https://github.com/lestrada57/ChaskiBackend" -ForegroundColor Cyan

