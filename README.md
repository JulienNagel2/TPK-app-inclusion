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
export ZAPPNAME=inclusionapp1
tanzu app init $ZAPPNAME --build-path . --build-type buildpacks
```

## Configure the app $ZAPPNAME for Tanzu Platform
Configure the buildpacks to use Java v17 (needed for Spring Boot v3)
```
tanzu app config build non-secret-env set --app=$ZAPPNAME BP_JVM_VERSION=17
```

## Add a yaml file to expose the app (using a http route definition)
```
cp yaml/httproute_inclusion.yaml .tanzu/config
```

## Deploy the app through the Tanzu Platform
First, make sure we are targeting the right project and space in the Tanzu Platform, and then go!
```
tanzu project use
tanzu space use
tanzu deploy -y
```



