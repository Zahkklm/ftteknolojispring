## 1.
Open a command prompt at the root location of project:

## 2. 
Run 
``` 
docker-compose up --build
```
## 3. Visit Swagger UI for each microservice


| Mikroservis          | Swagger Linki                         |
|----------------------|---------------------------------------|
| Payment-Service      | http://localhost:8086/swagger-ui.html |
| Notification-Service | http://localhost:8089/swagger-ui.html |
| Booking-Service      | http://localhost:8095/swagger-ui.html |
| User-Auth-Service    | http://localhost:9088/swagger-ui.html |

## 4.  (Optional)

Run Tests using IntelliJ or Maven `mvn test`

## Base Project Structure

![Diagram](BiletAppDiagram.svg)

##

```
biletuygulamasi/
│
├── gateway/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── patika/
│   │   │   │           └── gateway/
│   │   │   │               └── GatewayApplication.java
│   │   │   ├── resources/
│   │   │   │   ├── application.yaml
│   ├── Dockerfile
│
├── user-auth-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── patika/
│   │   │   │           └── userauth/
│   │   │   │               ├── UserAuthApplication.java
│   │   │   │               ├── controller/
│   │   │   │               │   └── UserController.java
│   │   │   │               ├── service/
│   │   │   │               │   └── UserService.java
│   │   │   │               └── repository/
│   │   │   │                   └── UserRepository.java
│   │   │   ├── resources/
│   │   │   │   ├── application.properties
│   ├── Dockerfile
│
├── booking-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── patika/
│   │   │   │           └── booking/
│   │   │   │               ├── BookingApplication.java
│   │   │   │               ├── controller/
│   │   │   │               │   └── BookingController.java
│   │   │   │               │   └── TravelController.java
│   │   │   │               ├── service/
│   │   │   │               │   └── BookingService.java
│   │   │   │               │   └── TravelService.java
│   │   │   │               ├── repository/
│   │   │   │               │   └── BookingRepository.java
│   │   │   │               │   └── TravelRepository.java
│   │   │   │               └── model/
│   │   │   │                   ├── Booking.java
│   │   │   │                   └── Travel.java
│   │   │   ├── resources/
│   │   │   │   ├── application.properties
│   ├── Dockerfile
│
├── payment-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── patika/
│   │   │   │           └── payment/
│   │   │   │               ├── PaymentApplication.java
│   │   │   │               ├── controller/
│   │   │   │               │   └── PaymentController.java
│   │   │   │               ├── service/
│   │   │   │               │   └── PaymentService.java
│   │   │   │               └── repository/
│   │   │   │                   └── PaymentRepository.java
│   │   │   ├── resources/
│   │   │   │   ├── application.properties
│   ├── Dockerfile
│
├── notification-service/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── patika/
│   │   │   │           └── notification/
│   │   │   │               ├── NotificationApplication.java
│   │   │   │               ├── controller/
│   │   │   │               │   └── NotificationController.java
│   │   │   │               ├── service/
│   │   │   │               │   └── NotificationService.java
│   │   │   │               └── model/
│   │   │   │                   └── Notification.java
│   │   │   ├── resources/
│   │   │   │   ├── application.yml
│   ├── Dockerfile
│   └── application.properties
│
└── docker-compose.yml
```



