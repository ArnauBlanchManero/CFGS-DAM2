#!/bin/bash

# Script de configuración para el proyecto Spotify
# Uso: bash setup.sh

echo "======================================"
echo "  Configurador de API Spotify"
echo "======================================"
echo ""

# Verificar que estamos en la carpeta correcta
if [ ! -f ".env.example" ]; then
    echo "❌ Error: No se encontró .env.example"
    echo "Ejecuta este script desde la raíz del proyecto"
    exit 1
fi

# Copiar .env.example a .env si no existe
if [ ! -f ".env" ]; then
    echo "📝 Creando archivo .env..."
    cp .env.example .env
    echo "✓ Archivo .env creado"
else
    echo "ℹ️  Archivo .env ya existe"
fi

# Crear carpeta assets si no existe
if [ ! -d "app/src/main/assets" ]; then
    echo "📁 Creando carpeta assets..."
    mkdir -p app/src/main/assets
    echo "✓ Carpeta assets creada"
fi

# Copiar .env a assets
echo "📋 Copiando .env a app/src/main/assets/..."
cp .env app/src/main/assets/
echo "✓ Archivo copiado"

echo ""
echo "======================================"
echo "  Próximos pasos:"
echo "======================================"
echo ""
echo "1. Abre tu editor de texto favorito"
echo "2. Edita el archivo .env"
echo "3. Reemplaza los valores por defecto con:"
echo "   - Tu Client ID (de https://developer.spotify.com/dashboard)"
echo "   - Tu Client Secret (de https://developer.spotify.com/dashboard)"
echo ""
echo "4. Guarda los cambios"
echo "5. Vuelve a ejecutar este script para copiar los cambios a la app:"
echo "   bash setup.sh"
echo ""
echo "6. Compila y ejecuta la aplicación"
echo ""
echo "✓ ¡Configuración completada!"
