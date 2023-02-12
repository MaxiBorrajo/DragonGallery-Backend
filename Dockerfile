FROM amazoncorretto:17-alpine-jdk

MAINTAINER MaxiBorrajo

COPY target/DragonGallery.jar DragonGallery.jar

ENTRYPOINT ["java","-jar","/DragonGallery.jar"]
