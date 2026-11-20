# AssetFlow – IT Asset Management System

AssetFlow is a web-based IT Asset Management System developed using Java and Spring Boot. It helps organizations manage IT assets, employees, asset assignments, returns, maintenance activities, and asset status tracking through a centralized platform.

## 🚀 Features

- **Dashboard:** Overview of asset management activities.
- **Employee Management:** Add, view, update, and delete employee records.
- **Asset Category Management:** Organize assets into categories.
- **Asset Management:** Manage IT equipment and asset details.
- **Asset Assignment:** Assign assets to employees and track assignments.
- **Asset Return Management:** Record and manage returned assets.
- **Maintenance Management:** Track maintenance and repair records.
- **Asset Status History:** Maintain asset status records.
- **Authentication and Authorization:** Secure login with role-based access.
- **Role-Based Access Control:** Separate permissions for Admin and Staff.

## 👥 User Roles

### Admin

- Full access to all modules.
- Manage employees, assets, categories, assignments, returns, and maintenance.
- Manage system administration activities.
- Restricted from deleting asset return records.

### Staff

- Access permitted operational modules.
- View employees, assets, categories, and status history.
- Create and manage assignments, returns, and maintenance records.
- Restricted from administrative delete operations.

## 🛠️ Technologies Used

| Technology              | Purpose                          |
| ----------------------- | -------------------------------- |
| Java 17                 | Backend programming              |
| Spring Boot             | Application framework            |
| Spring MVC              | Web application development      |
| Spring Data JPA         | Database operations              |
| Spring Security         | Authentication and authorization |
| Hibernate               | ORM                              |
| Thymeleaf               | Frontend templating              |
| HTML5, CSS3, JavaScript | User interface                   |
| MySQL                   | Database                         |
| Maven                   | Dependency management            |
| Git and GitHub          | Version control                  |

## 🗂️ Project Structure

```text
AssetFlow/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/demo/
│   │   │       ├── Controller/
│   │   │       ├── config/
│   │   │       ├── entity/
│   │   │       ├── repository/
│   │   │       └── service/
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       ├── templates/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## ⚙️ Getting Started

### Prerequisites

- JDK 17
- MySQL 8
- Maven
- Spring Tool Suite (STS) or an equivalent Java IDE

### 1. Clone the repository

```bash
git clone https://github.com/Greeshma09876/AssetFlow.git
```

### 2. Create the MySQL database

```sql
CREATE DATABASE it_asset_management;
```

### 3. Configure environment variables

Configure the following environment variables in your IDE or operating system:

```text
DB_PASSWORD=your_mysql_password
ADMIN_PASSWORD=your_admin_password
STAFF_PASSWORD=your_staff_password
```

Do not commit real passwords or credentials to GitHub.

### 4. Run the application

Activate the `dev` profile and run:

```bash
mvn spring-boot:run
```

Alternatively, run the main application class from STS:

`ItAssetManagementApplication.java`

### 5. Open the application

```text
http://localhost:8080
```

## 🔐 Security

- Spring Security authentication.
- BCrypt password encryption.
- Role-based authorization.
- CSRF protection.
- Environment-based credential configuration.
- Restricted administrative operations.

## 🎯 Project Objective

The objective of AssetFlow is to simplify IT asset tracking and management by providing a centralized application with secure access, organized asset records, assignment tracking, and maintenance management.

## 👩‍💻 Developer

**Greeshma Haridas**

- GitHub: [https://github.com/Greeshma09876](https://github.com/Greeshma09876)

---

*Developed as a Java Spring Boot portfolio project.*
