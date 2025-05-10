package com.example.lab9;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab9Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab9Application.class, args);
    }
}

/*{
        "emails": [
        "dgozalkhanov@mail.ru",
        "mgozalkhanov@mail.ru"
        ],
        "subject": "Тест рассылки",
        "body": "ха"
        }
        spring.application.name=lab9
server.port=8080

spring.datasource.url=jdbc\:mysql://localhost:3306/lab9?useSSL=false\&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=1234

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=[dgozalkhanov@gmail.com](mailto:dgozalkhanov@gmail.com)
spring.mail.password=oqml yrmp hpiz rklt
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

spring.mail.properties.mail.smtp.connectiontimeout=5000
spring.mail.properties.mail.smtp.timeout=5000
spring.mail.properties.mail.smtp.writetimeout=5000

docker-compose down
docker-compose up --build

        */