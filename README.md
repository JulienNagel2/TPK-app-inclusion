# TPK-app-inclusion

## Get the SpringBoot app on the local workstation
go to your favorite workdir and clone the repo from github (that creates a dir called 'TPK-app-inclusion')
```
git clone https://github.com/JulienNagel2/TPK-app-inclusion
cd TPK-app-inclusion
```

## Verify the SpringBoot app is working fine locally 
Example with maven, beware you must use Java v17+ since the app is using SpringBoot v3
```
mvn spring-boot:run
```

## Init the app for Tanzu Platform
We give a name to the app. We want to use buildpacks to build the app.
```
export ZAPPNAME=user1-inclusionapp
tanzu app init $ZAPPNAME --build-path . --build-type buildpacks
```

## Configure the app $ZAPPNAME for Tanzu Platform
Configure the buildpacks to use Java v17 (needed for Spring Boot v3)
```
tanzu app config build non-secret-env set --app=$ZAPPNAME BP_JVM_VERSION=17
```

## Add a yaml file to expose the app through the Tanzu Platform 
Here we are using an http route definition
```
cp yaml/httproute_inclusion.yaml .tanzu/config
```

## Define the container registry where the application package will be pushed after the build
We need to have container registry available to store the app package once it is built. Example below
```
export ZREGISTRYSTRING=<YOUR REGISTRY>/<YOUR DIR>/{name}
tanzu build config --containerapp-registry ${ZREGISTRYSTRING}
```

## Docker login to your container registry 
This step is necessary so that the tanzu CLI can store the app package during the deploy process
```
docker login ${ZREGISTRYSTRING}
```

## Deploy the app through the Tanzu Platform (does the build automatically)
First, make sure we are targeting the right project (YOURPROJECT) and space (YOURSPACE) in the Tanzu Platform, and then go!
```
tanzu login
tanzu project use YOURPROJECT
tanzu space use YOURSPACE
tanzu deploy -y
```



