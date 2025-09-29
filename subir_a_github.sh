#!/bin/bash
set -e
if [ -z "$*" ]; then
  COMMIT_MSG="Añado ficheros"
  echo "No se proporcionó mensaje de commit. Usando mensaje por defecto: '$COMMIT_MSG'"
else
  COMMIT_MSG="$*"
fi
git status

git add .

git commit -m "$COMMIT_MSG" || echo "No hay cambios para añadir."

git pull || {echo "Error en el git pull."; exit 1;}

git push || {echo "Error en el git push."; exit 2;}

echo "Operacion completada con éxito"
