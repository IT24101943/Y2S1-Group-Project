# Blood Donation System - Senior Doctor Module

A comprehensive backend system for managing donor health reports and donation appointments, built with Java, Spring Boot, and MySQL.

## Overview

This module provides RESTful API endpoints for Senior Doctors to:
- Approve or reject blood donations
- Add medical notes to donor reports
- Retrieve donor medical reports
- Manage appointment schedules
- View statistics and reports

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**
- **Jakarta Validation**

## Project Structure

```
src/main/java/core/
├── entity/
│   ├── Doctor.java
│   └── DonorMedicalReport.java
├── repository/
│   └── DonorMedicalReportRepository.java
├── service/
│   └── DoctorService.java
├── controller/
│   └── DoctorController.java
├── dto/
│   └── MedicalNotesRequest.java
├── exception/
│   ├── ResourceNotFoundException.java
│   └── GlobalExceptionHandler.java
└── Y2S1GroupProjectApplication.java
```

## Database Schema

### Doctors Table
- `id` (BIGINT, Primary Key)
- `name` (VARCHAR(100), NOT NULL)
- `specialization` (VARCHAR(50), NOT NULL)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

### Donor Medical Reports Table
- `id` (BIGINT, Primary Key)
- `donor_id` (BIGINT, Foreign Key)
- `doctor_notes` (TEXT)
- `health_status` (VARCHAR(50))
- `is_approved` (BOOLEAN, DEFAULT FALSE)
- `appointment_date` (TIMESTAMP)
- `doctor_id` (BIGINT, Foreign Key)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## API Endpoints

### Core Doctor Operations

#### 1. Approve Donation
```http
PUT /api/doctor/reports/{reportId}/approve
```
**Description:** Approves a blood donation by setting `isApproved` to `true` and `healthStatus` to "Fit to Donate".

**Response:** 200 OK with updated medical report

#### 2. Reject Donation
```http
PUT /api/doctor/reports/{reportId}/reject
```
**Description:** Rejects a blood donation by setting `isApproved` to `false` and `healthStatus` to "Unfit".

**Response:** 200 OK with updated medical report

#### 3. Add Medical Notes
```http
PUT /api/doctor/reports/{reportId}/add-notes
```
**Description:** Adds medical notes to an existing report.

**Request Body:**
```json
{
    "notes": "Patient shows excellent health indicators. Blood pressure normal."
}
```

**Response:** 200 OK with updated medical report

#### 4. Get Medical Report by Donor ID
```http
GET /api/doctor/reports/donor/{donorId}
```
**Description:** Retrieves a specific donor's medical report.

**Response:** 200 OK with medical report or 404 Not Found

### Additional Endpoints

#### 5. Get Latest Medical Report
```http
GET /api/doctor/reports/donor/{donorId}/latest
```

#### 6. Get Reports by Doctor
```http
GET /api/doctor/reports/doctor/{doctorId}
```

#### 7. Get Approved Reports
```http
GET /api/doctor/reports/approved
```

#### 8. Get Unapproved Reports
```http
GET /api/doctor/reports/unapproved
```

#### 9. Get Reports by Health Status
```http
GET /api/doctor/reports/status/{healthStatus}
```

#### 10. Create Medical Report
```http
POST /api/doctor/reports
```

#### 11. Update Medical Report
```http
PUT /api/doctor/reports/{reportId}
```

#### 12. Delete Medical Report
```http
DELETE /api/doctor/reports/{reportId}
```

#### 13. Check Report Exists
```http
GET /api/doctor/reports/donor/{donorId}/exists
```

#### 14. Get Doctor Statistics
```http
GET /api/doctor/statistics/{doctorId}
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.6 or higher

### Database Setup
1. Create a MySQL database named `blood_donation_system`
2. Run the SQL script from `database_schema.sql`:
```sql
mysql -u root -p blood_donation_system < database_schema.sql
```

### Application Configuration
1. Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Running the Application
1. Clone the repository
2. Navigate to the project directory
3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Error Handling

The application includes comprehensive error handling:

### Custom Exceptions
- `ResourceNotFoundException`: Thrown when a requested resource is not found

### Global Exception Handler
- Handles all exceptions globally
- Returns standardized JSON error responses
- Includes validation error details

### Error Response Format
```json
{
    "timestamp": "2024-01-15T10:30:00",
    "status": 404,
    "error": "Resource Not Found",
    "message": "Medical report not found with id: 123",
    "path": "/api/doctor/reports/123/approve"
}
```

## Validation

The application includes comprehensive validation:

### Entity Validation
- `@NotNull`: Required fields
- `@NotBlank`: Non-empty strings
- `@Size`: String length limits

### Request Validation
- All request bodies are validated using `@Valid`
- Custom validation messages for better user experience

## Testing

### Unit Tests
Run unit tests with:
```bash
mvn test
```

### Integration Tests
The application includes integration tests for all endpoints.

## API Testing Examples

### Using cURL

#### Approve a donation:
```bash
curl -X PUT http://localhost:8080/api/doctor/reports/1/approve
```

#### Add medical notes:
```bash
curl -X PUT http://localhost:8080/api/doctor/reports/1/add-notes \
  -H "Content-Type: application/json" \
  -d '{"notes": "Patient is in excellent health condition."}'
```

#### Get donor report:
```bash
curl -X GET http://localhost:8080/api/doctor/reports/donor/123
```

## Monitoring

The application includes Spring Boot Actuator for monitoring:
- Health check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`

## Security Considerations

- Input validation on all endpoints
- SQL injection prevention through JPA
- CORS configuration for frontend integration
- Comprehensive error handling without exposing sensitive information

## Future Enhancements

- Authentication and authorization
- Audit logging
- Email notifications
- Report generation
- Advanced search and filtering
- Bulk operations

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is part of the Y2S1 Group Project for educational purposes.
