FROM amazoncorretto:21

WORKDIR /app

# Instala o tar e o gzip (necessários para o mvnw descompactar o maven)
RUN yum install -y tar gzip
 
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline
 
COPY src ./src
 
CMD ["./mvnw", "spring-boot:run"]
