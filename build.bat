mvn -Dmaven.test.skip=true clean package install
docker-compose up --force-recreate --build -d