@echo off

echo Stopping wigell-travels
docker stop wigell-travels
echo Deleting container wigell-travels
docker rm wigell-travels
echo Deleting image wigell-travels
docker rmi wigell-travels

echo Ensuring wigelldb exists inside mysql-service
docker exec mysql-service mysql -u myuser -pmypassword -e "CREATE DATABASE IF NOT EXISTS wigelldb;"

echo Running mvn package
call mvnw.cmd package -DskipTests
echo Creating image wigell-travels
docker builder prune --all --force
docker build --no-cache -t wigell-travels .
echo Creating and running container wigell-travels
docker run -d -p 8585:8585 --name wigell-travels --network wigell-network wigell-travels --spring.profiles.active=prod



echo Checking container status...
docker ps -a --filter "name=wigell-travels"

echo Showing logs if container failed to start...
docker logs wigell-travels

echo Done!