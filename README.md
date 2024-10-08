# TPK-app-inclusion
Procedure to deploy a simple Spring Boot app with Tanzu Platform

## Prereqs for Tanzu Platform
To deploy the app to Tanzu platform you need to:
```
Have access to a Tanzu Platform Org/Project/Space
Have installed and configured the tanzu CLI on your workstation:
  - Install tanzu CLI v1.3.0 or above on the workstation
    https://docs.vmware.com/en/VMware-Tanzu-CLI/1.3/tanzu-cli/index.html
  - Install the tanzu CLI plugins for Platform Engineer
    tanzu plugin install --group vmware-tanzu/platform-engineer
```

## Get the app on your workstation
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
Copy the definition file to the right directory
```
cp yaml/httproute.yaml .tanzu/config
```
Adapt the file to point to the right service name (depends on app name)
```
# on linux:
sed -i "s/ZPLACEHOLDER/${ZAPPNAME}/g" '.tanzu/config/httproute.yaml'
# on MacOS:
sed -i '' "s/ZPLACEHOLDER/${ZAPPNAME}/g" '.tanzu/config/httproute.yaml'
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
Login and target the right project (YOURPROJECT) and space (YOURSPACE). Then deploy
```
tanzu login
tanzu project use YOURPROJECT
tanzu space use YOURSPACE
tanzu deploy -y
```

## Check the app status
How is the app going?
```
tanzu app list
tanzu app get ${ZAPPNAME}
```

