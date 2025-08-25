# Wonnit – Backend

### A service to revitalize local businesses by sharing idle time in spaces

Spring Boot + Kotlin based backend


[![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org/)
[![AWS S3](https://img.shields.io/badge/AWS%20S3-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)](https://aws.amazon.com/s3/)


![표지](https://github.com/user-attachments/assets/f8ba1689-3eb6-433d-b94d-875f29ad5cb8)

---

## 🚀 Overview

### Domain
- Space registration/update/deletion, search/recommendation, rental (request → approval → return → re-registration), image/3D model upload

### Infra
- AWS S3 Presigned URL, H2 (MySQL mode) test environment

### Quality
- RFC 7807-based global exception handling, structured logging, controller unit tests

<br>

---

## 📐 Architecture (ASCII)

```
[ Client (iOS) ]
|
| REST API
v
+-------------------------+
|     Controller Layer    |
+-----------+-------------+
|
v
+-------------------------+      +------------------------+
|       Service Layer     |----->|   GlobalException      |
|  - SpaceQueryService    |      |   (RFC 7807 Response)  |
|  - SpaceUpdateService   |      |   + LoggingStrategy    |
|  - SpaceRentService     |      +------------------------+
|  - SpaceImageService    |
|  - Space3DModelService  |
|  - ColumnQueryService   |
+-----------+-------------+
|
v
+-------------------------+      +------------------------+
|      Infrastructure     |      |        Database        |
|  - S3PresignService     |      |   (H2/MySQL compatible)|
|  - S3Uploader           |      +------------------------+
+-------------------------+

```

<br>

---

## 🗄️ Entity Model

```
Space
├─ id: UUID (PK, from PrimaryKeyEntity)
├─ name, description
├─ category: SpaceCategory (enum)
├─ status: SpaceStatus (enum)
├─ addressInfo: AddressInfo (Embedded)
├─ operationalInfo: OperationalInfo (Embedded)
├─ amountInfo: AmountInfo (Embedded)
└─ phoneNumber: PhoneNumber (Value Object)

Column
└─ Main screen column (Top3 display data)

Embeddables
├─ AddressInfo(city, district, detail, lat, lng ...)
├─ OperationalInfo(days/hours ...)
├─ AmountInfo(price, unit = TimeUnit{HOUR, DAY, ...})
└─ PhoneNumber

```

<br>

---

## 🔑 Key Features

### Space Management
- Register/update/delete: ownership validation and authorization check 
- Image/3D Model: Presigned URL issuance → client directly uploads to S3

### Rental Process
- Request → Approval → Return → Re-registration state transitions managed consistently by SpaceRentService 
- State-specific validation and exception handling

### Search/Query
- SpaceQueryService: keyword/my spaces/recent/nearby spaces 
- ColumnQueryService: main columns Top3

<br>

---

## 🛠️ Technical Highlights

### Service Layer Separation
- Clear responsibilities for Query / Update(Command) / Rent / Media (image/3D)

### AWS Integration
- S3Uploader: upload/delete 
- S3PresignService: generate PUT/GET Presigned URLs 
- DefaultCredentialsProvider chain (environment variables/IAM Role/Profile)

### Exception Handling (RFC 7807)
- GlobalExceptionHandler + ProblemDetailBuilder 
- ExceptionFactory/ExceptionBuilder: unify domain error codes → HTTP exceptions

### Logging
- LoggingStrategy + LogLevel for structured logs 
- Traceability across services and exception handlers

<br>

---

## ✅ Testing

### Controller Unit Tests (Spring Boot Test + JUnit5 + MockMvc)
- ColumnControllerTest, CategoryControllerTest, ProfileControllerTest 
- RecentSpaceUpdateControllerTest, SearchControllerTest 
- SpaceRentControllerTest, SpaceUpdateControllerTest

### Fixture
- SpaceFixture ensures consistency of domain test data

### Coverage Goals
- Validation of API contract (request/response), state transitions, and exception flow stability

<br>

---

## 📊 Tech Stack
- Language: Kotlin 
- Framework: Spring Boot 3.x, Spring Data JPA 
- DB: H2 (MySQL mode) (→ MySQL compatible)
- Infra: AWS S3 (Presigned URL), Spring Cloud AWS 
- Error Handling: ProblemDetail (RFC 7807)
- Logging: Custom LoggingStrategy + LogLevel 
- Testing: JUnit 5, Spring Boot Test, MockMvc

<br>

---

## 🌟 Why It Wins
- Reliability: rental logic centered on state transitions + controller tests + RFC 7807 exception handling 
- Scalability: Presigned URL-based large file traffic offloading, Service Layer separation 
- Operability: logging strategy and consistent responses for easier monitoring/debugging 
- Data Modeling: Embedded/VO/Enum usage preserves domain semantics

<br>

---

## 📚 API Documentation
- Swagger UI: complete API specification, request/response examples, and field descriptions
- Open this link -> [http://43.201.251.218:8080/swagger-ui/index.html](http://43.201.251.218:8080/swagger-ui/index.html)

<br>

---

## ⌛️ How to Run
1.	clean build
2.	docker buildx build --platform linux/amd64 -t maruhan/wonnit:latest .
3.	docker push maruhan/wonnit:latest
4.	Connect to EC2 and run docker-compose up -d
-	Requires compose.yaml and .env file

---
