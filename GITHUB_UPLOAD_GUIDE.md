# Guía para Subir el Código a GitHub

## Problema Detectado
El error `Connection was reset` indica un problema de conexión con GitHub. Esto puede deberse a:
1. Firewall o proxy bloqueando la conexión
2. Necesidad de autenticación
3. Problemas de red temporales

## Soluciones Disponibles

### Opción 1: Usar GitHub Desktop (MÁS FÁCIL)
1. Descarga e instala GitHub Desktop desde: https://desktop.github.com/
2. Abre GitHub Desktop y inicia sesión con tu cuenta de GitHub
3. Ve a File > Add Local Repository
4. Selecciona la carpeta: `C:\Users\HARRYSON\Downloads\Backend-chaski\Backend-chaski`
5. Haz clic en "Publish repository"
6. Asegúrate de que el nombre sea "ChaskiBackend"
7. Marca como público o privado según prefieras
8. Haz clic en "Publish repository"

### Opción 2: Usar Autenticación Personal Access Token (PAT)
1. Ve a GitHub.com > Settings > Developer settings > Personal access tokens > Tokens (classic)
2. Genera un nuevo token con permisos de "repo"
3. Copia el token
4. Ejecuta estos comandos:

```powershell
cd C:\Users\HARRYSON\Downloads\Backend-chaski\Backend-chaski

# Eliminar el remoto actual
git remote remove origin

# Agregar el remoto con tu usuario (reemplaza TU_USUARIO con tu usuario de GitHub)
git remote add origin https://TU_USUARIO@github.com/lestrada57/ChaskiBackend.git

# Hacer push (te pedirá contraseña, usa el token PAT)
git push -u origin main
```

### Opción 3: Configurar SSH (Más Seguro a Largo Plazo)
1. Genera una clave SSH (si no tienes una):
```powershell
ssh-keygen -t ed25519 -C "tu_email@example.com"
```

2. Copia la clave pública:
```powershell
Get-Content ~\.ssh\id_ed25519.pub | Set-Clipboard
```

3. Ve a GitHub.com > Settings > SSH and GPG keys > New SSH key
4. Pega la clave y guarda

5. Cambia el remoto a SSH:
```powershell
cd C:\Users\HARRYSON\Downloads\Backend-chaski\Backend-chaski
git remote remove origin
git remote add origin git@github.com:lestrada57/ChaskiBackend.git
git push -u origin main
```

### Opción 4: Crear el Repositorio Manualmente en GitHub y Después Hacer Push
1. Ve a https://github.com/lestrada57/ChaskiBackend
2. Si el repositorio no existe, créalo en GitHub (botón "New repository")
3. No inicialices con README, .gitignore ni licencia
4. Sigue las instrucciones que GitHub te muestre para "push an existing repository"

## Verificar el Estado Actual
Para ver qué se ha hecho hasta ahora:

```powershell
cd C:\Users\HARRYSON\Downloads\Backend-chaski\Backend-chaski
git status
git log --oneline
git remote -v
```

## Archivos Preparados para Subir
Tu repositorio local ya tiene todos los archivos listos:
- ✅ Código fuente completo (controllers, services, entities, etc.)
- ✅ Archivos de configuración (pom.xml, application.properties)
- ✅ Documentación (README.md, API_DOCUMENTATION.md, DATABASE_SETUP.md, QUICK_START.md)
- ✅ .gitignore configurado correctamente
- ✅ Scripts de utilidad

## ¿Necesitas Ayuda?
Si ninguna de estas opciones funciona, puede ser por:
- Proxy corporativo o firewall
- Configuración de red restrictiva
- Necesidad de VPN

En ese caso, considera usar la interfaz web de GitHub directamente o GitHub Desktop.

