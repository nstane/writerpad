# Xebia Assingment: WriterPad Article and Tags API
This app is using in-memory H2 DB. So, No extra configuration required.

All api documentation is available on http://localhost:8080/swagger-ui.html

## Build Application :
``mvn clean package``
##### Run Application
``mvn spring-boot:run`` or ``java -jar ./target/writerpad-0.0.1-SNAPSHOT.jar``

## Build Application using docker
### Build Docker Image: 
``docker build -t xebia/writerpad .``

##### Verify Docker Image : 
``docker images``

##### Run app using docker : 
`` docker run -p 8080:8080 xebia/writerpad:latest``

##### Query to check tag occurrence : 
``SELECT count(article_id) as occurrence, tags as tag_name FROM ARTICLE_TAGS  group by tags``

##### Average human reading is configurable and can be set from application.properties file
``article.human_average_read_time=200 
  // average reading speed is 200 to 250 words a minute``
