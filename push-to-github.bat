@echo off
echo ======================================
echo   Subiendo Backend Chaski a GitHub
echo ======================================
echo.

cd /d "%~dp0"

echo Verificando estado del repositorio...
git status
echo.

echo Agregando todos los archivos...
git add -A
echo.

echo Creando commit...
git commit -m "Initial commit: Backend Chaski - Sistema de delivery completo"
echo.

echo Cambiando a rama main...
git branch -M main
echo.

echo Verificando configuracion remota...
git remote -v
echo.

echo Intentando push a GitHub...
echo NOTA: Se te pedira autenticacion de GitHub
echo Si tienes 2FA habilitado, necesitas usar un Personal Access Token
echo.
git push -u origin main

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ======================================
    echo   EXITO! Archivos subidos a GitHub
    echo ======================================
    echo.
    echo Revisa tu repositorio en:
    echo https://github.com/lestrada57/ChaskiBackend
) else (
    echo.
    echo ======================================
    echo   ERROR al subir archivos
    echo ======================================
    echo.
    echo Posibles soluciones:
    echo 1. Usar GitHub Desktop ^(recomendado^)
    echo 2. Configurar Personal Access Token
    echo 3. Revisar GITHUB_UPLOAD_GUIDE.md
    echo.
)

echo.
pause

