FROM openjdk:latest
COPY ./target/CoursworkGroupWork-0.1.0.2-jar-with-dependencies.jar /tmp
WORKDIR /tmp
ENTRYPOINT ["java", "-jar", "CoursworkGroupWork-0.1.0.2-jar-with-dependencies.jar"]