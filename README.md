# TPK-app-inclusion

## Get the app on the local workstation
go to your favorite workdir and clone the repo from github (that creates a dir called 'TPK-app-inclusion')
```
git clone https://github.com/JulienNagel2/TPK-app-inclusion
cd TPK-app-inclusion
```

## Verify the app is working fine locally 
Example with maven, beware you must use Java v17+ since the app is using Spring Boot v3
```
mvn spring-boot:run
```

## Init the app for Tanzu Platform
We give a name to the app. We want to use buildpacks to build the app.
```
export ZAPPNAME=inclusionapp
tanzu app init $ZAPPNAME --build-path . --build-type buildpacks
```

## Configure the app for Tanzu Platform
Configure the buildpack to use Java v17 (needed for Spring Boot v3)
```
tanzu app config build non-secret-env set --app=$ZAPPNAME BP_JVM_VERSION=17
```

## Add a HTTPRoute definition to expose the app  
The HTTP route definition is adapted to point to the right service name (depends on app name)
```
cp yaml/httproute.yaml .tanzu/config
sed -i "s/ZPLACEHOLDER/${ZAPPNAME}/g" .tanzu/config/httproute.yaml
```

## Define the container registry for the application package
We need to have a container registry to store the app package once it is built. Example below
```
export ZREGISTRYSTRING=<YOUR REGISTRY>/<YOUR DIR>/{name}
tanzu build config --containerapp-registry ${ZREGISTRYSTRING}
```

## Docker login to your container registry 
Docker login so that the tanzu CLI can connect to the container registry and store the app package after build 
```
docker login ${ZREGISTRYSTRING}
```

## Deploy the app 
Prereq: A project, space, availability target must be configured in Tanzu Platform to host your app
First, login and make sure we are targeting the right project (YOURPROJECT) and space (YOURSPACE) in the Tanzu Platform
The tanzu deploy includes a build phase that saves the app package to the container registry
And then the actual app deployment to the K8s cluster(s) occurs
```
tanzu login
tanzu project use YOURPROJECT
tanzu space use YOURSPACE
tanzu deploy -y
```



