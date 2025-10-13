@echo off
echo Stopping wigell-travels
docker stop wigell-travels
echo Deleting container wigell-travels
docker rm wigell-travels
echo Deleting image wigell-travels
docker rmi wigell-travels
echo Running mvn package
call mvn package
echo Creating image wigell-travels
docker build -t wigell-travels .
echo Creating and running container wigell-travels
docker run -d -p 8585:8585 --name wigell-travels --network wigell-network wigell-travels
echo Done!