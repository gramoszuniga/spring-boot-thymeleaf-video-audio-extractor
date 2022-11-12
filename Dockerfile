FROM public.ecr.aws/docker/library/maven:3.8.6-amazoncorretto-17
COPY . ./
RUN mvn -f ./pom.xml install
ENTRYPOINT ["java","-jar","./target/spring-boot-thymeleaf-video-audio-extractor-1.0.0.jar"]