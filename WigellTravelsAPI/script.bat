@echo off
echo Stopping microservice-a
docker stop microservice-a
echo Deleting container microservice-a
docker rm microservice-a
echo Deleting image microservice-a
docker rmi microservice-a
echo Running mvn package
call mvn package
echo Creating image microservice-a
docker build -t microservice-a .
echo Creating and running container microservice-a
docker run -d -p 9900:9900 --name microservice-a --network services-network microservice-a
echo Done!