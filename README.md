# Simple Image Processing Prototype

## Build
`./mvnw clean compile test`

## Run
* ProtoApplication reads image files from filesystem pointed by `image.source.base.path` in `application.properties`. Please override the default path while starting the application as below:
```
./mvnw spring-boot:run -Dspring.config.additional-location="file:///[path-to-application.properties]"
```

* Alternatively, copy images to `stc\main\resources` folder and run:
```
./mvnw spring-boot:run
```

##TODO
* Externalise factory method `ProtoImageService#getImage()`, possibly as a Strategy.
* Externalise validator `ProtoController#validate()`, possibly with javax.validation.
* Make `ProtoExceptionHandler.class` more sophisticated.
* Make implementation of `ProtoImageGenerator.class` more sophisticated.
* and more...

