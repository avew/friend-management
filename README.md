# Introduction
Friend management system build using spring boots, mongodb and maven

# Usage
Use Docker to build application. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

Start a mongodb database in a docker container, run:

    docker-compose -f src/main/docker/mongodb.yml up -d

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

    ./mvnw clean test package docker:build

Then run:

    docker-compose -f src/main/docker/app.yml up

For more information i made a swagger
 
	http://localhost:8080/swagger-ui.html