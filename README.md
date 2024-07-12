# TPK-app-inclusion

## Init the app
tanzu app init

## Configure the app 
```
Configure to use Java17 (needed for Spring Boot v3)
export ZAPP=inclusion
tanzu app config build non-secret-env set --app=$ZAPP BP_JVM_VERSION=17
```

## Add yaml file to expose the app with a http route
```
cp yaml/httproute_inclusion.yaml .tanzu/config
```

## Deploy the app 
```
First, make sure we are targeting the right project and space, and then go!
tanzu project use
tanzu space use
tanzu deploy -y
```



