# TurfOff - Turf Booking System Backend

A Spring Boot application that provides backend services for a turf/sports ground booking platform.

## 🚀 Features
- User and Admin authentication with JWT
- Turf management system
- Slot-based booking system
- Double booking prevention
- Geospatial search for nearby turfs
- Multi-database architecture (MySQL + MongoDB)
- Pre-booking functionality
<!-- - Secure payment integration -->
<br>

## 🛠️ Tech Stack
- Java 21
- Spring Boot 3.2.5
- Spring Security
- JWT Authentication
- MongoDB
- MySQL
- Maven
- Docker
- JUnit 5
- ModelMapper
<br>

## 📋 Prerequisites
- JDK 21
- Maven
- Docker
- MySQL
- MongoDB
<br>

## 🏃‍♂️ Getting Started
1. Clone the repository:
```
git clone https://github.com/harsh-haria/turfoff.turfbooking.git
```

2. Create `application.properties` file in `src/main/resources` with required configurations
```
spring.application.name = turfbooking

spring.datasource.driver-class-name = com.mysql.cj.jdbc.Driver
spring.datasource.username = <db_username>
spring.datasource.password = <db_password>
spring.datasource.url = <mysql_url>

spring.data.mongodb.uri = <mongodb_uri>

spring.jpa.hibernate.ddl-auto = update
spring.jpa.show-sql = true

spring.data.mongodb.auto-index-creation = true

spring.app.jwtSecret = <super_secret_key_min_64_char>
spring.app.jwtExpirationMs = <exp_time_in_ms>
```

3. Start the databases using Docker:
```
docker-compose -f db.yaml up -d
```

4. Build the project:
```
./mvnw clean install
```

5. Run the application:
```
./mvnw spring-boot:run
```
<br>

## 🏗️ Project Structure
<t>  project<br>
  ├── src<br>
  │ ├── main<br>
  │ │ ├── java/com/turfoff/turfbooking<br>
  │ │ │ ├── config<br>
  │ │ │ ├── controllers<br>
  │ │ │ ├── domain<br>
  │ │ │ │ ├── mongo<br>
  │ │ │ │ ├── mysql<br>
  │ │ │ │ └── misc<br>
  │ │ │ ├── jwt<br>
  │ │ │ ├── mappers<br>
  │ │ │ │ ├── impl<br>
  │ │ │ │ └── Mapper.java<br>
  │ │ │ ├── repositories<br>
  │ │ │ │ ├── mongo<br>
  │ │ │ │ └── mysql<br>
  │ │ │ ├── services<br>
  │ │ │ └── utilities<br>
  │ │ ├── resources<br>
  │ │ │  └── application.properties<br>
  │ └── test<br>
  ├── db.yaml<br>
  └── pom.xml<br>
<br>

## 🔒 Security
The application uses JWT (JSON Web Tokens) for authentication.<br>
Protected endpoints require a valid JWT token in the Authorization header: ```Authorization: Bearer <your_jwt_token>```
<br><br>

## 📝 API Documentation
### Turf Management
- `GET /turfs` - Get all turfs
- `GET /turfs/{id}` - Get turf by ID
- `POST /turfs` - Create new turf (Admin only)
- `PUT /turfs/{id}` - Update turf (Admin only)

### Booking Management
- `GET /turfs/getSlots` - Get available slots for a turf
- `POST /turfs/bookSlot` - Book a slot
- `GET /bookings` - Get user bookings

### Authentication
- `POST /users/signin` - User login
- `POST /users/signup` - User registration
<br>

## 🧪 Testing
Run the tests using(Still in development):
```
./mvnw test
```
<br>

## 🤝 Contributing
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request
<br>

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
