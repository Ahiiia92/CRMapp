FROM maven:3.8-jdk-11

RUN mkdir /project

COPY . /project

WORKDIR /project

RUN mvn clean package -DskipTests

EXPOSE 8088

CMD ["java", "-jar", "target/app-0.0.1-SNAPSHOT.jar"]

