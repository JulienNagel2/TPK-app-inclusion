# TPK-app-inclusion

## Get the SpringBoot app on the local workstation
git clone https://github.com/JulienNagel2/TPK-app-inclusion

## Verify the SpringBoot app is working fine locally
mvn spring-boot:run

## Init the app for Tanzu Platform
tanzu app init

## Configure the app for Tanzu Platform
Configure to use Java17 (needed for Spring Boot v3)
```
export ZAPP=inclusion
tanzu app config build non-secret-env set --app=$ZAPP BP_JVM_VERSION=17
```

## Add a yaml file to expose the app with a http route
```
cp yaml/httproute_inclusion.yaml .tanzu/config
```

## Deploy the app through the Tanzu Platform
First, make sure we are targeting the right project and space, and then go!
```
tanzu project use
tanzu space use
tanzu deploy -y
```



