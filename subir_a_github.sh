#!/bin/bash
set -e
if [ -z "$*" ]; then
  COMMIT_MSG="Añado ficheros"
  echo -e "\nNo se proporcionó mensaje de commit. Usando mensaje por defecto: '$COMMIT_MSG'\n"
else
  COMMIT_MSG="$*"
fi
git status

git add .

git commit -m "$COMMIT_MSG" || echo -e "\nNo hay cambios para añadir.\n"

git pull || { echo -e "\nError en el git pull.\n"; exit 1; }

git push || { echo -e "\nError en el git push.\n"; exit 2; }

echo -e "\nOperacion completada con éxito"
