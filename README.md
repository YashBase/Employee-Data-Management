# Employee Data Management System

A full-stack **CRUD application** for managing employees. Built with **Spring Boot** for the backend, **React** for the frontend, and **Redux** for state management. The UI is styled using **TailwindCSS**, with animations via **Framer Motion** and icons from **Lucide Icons**.

---

## 🚀 Project Overview

This project allows you to **Create, Read, Update, and Delete** employee records seamlessly. It includes a responsive and interactive frontend with state management, integrated with a RESTful backend API.

### Core Features

* **Backend:**

  * RESTful API endpoints under `/api/employees`
  * Employee entity: `name`, `email`, `position`
  * Persistent storage using **MySQL**
  * Full CRUD functionality: `Create`, `Read`, `Update`, `Delete`
  * Test cases for backend endpoints and core business logic

* **Frontend:**

  * List all employees in a table or card view
  * Add a new employee via a form
  * Edit and Delete employees with buttons
  * Edit via **modal** or separate page
  * State management using **Redux**
  * Animations using **Framer Motion**
  * Icons from **Lucide Icons**
  * **Bonus Features**:

    * Search/filter employees by name
    * Form validation

---

## 💻 Tech Stack

| Layer           | Technology                                                       |
| --------------- | ---------------------------------------------------------------- |
| Frontend        | React.js, Redux, TailwindCSS, Framer Motion, Lucide Icons, Axios |
| Backend         | Spring Boot, Java 21, Spring Data JPA, MySQL                     |
| Build & Dev     | Maven, npm/yarn                                                  |
| Version Control | Git, GitHub                                                      |

---

## 🛠️ Installation & Setup

### 1. Clone the repository

```bash
git clone https://github.com/yourusername/employee-data-management.git
cd employee-data-management
```

---

### 2. Backend Setup (Spring Boot)

1. Navigate to the backend folder:

```bash
cd backend
```

2. Configure database in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

3. Build & run backend:

```bash
mvn clean install
mvn spring-boot:run
```

* Backend will start at `http://localhost:8080/api/employees`

---

### 3. Frontend Setup (React + Redux + TailwindCSS)

1. Navigate to the frontend folder:

```bash
cd frontend
```

2. Install dependencies:

```bash
npm install
```

**Dependencies include:**

* `axios` – for API calls
* `redux`, `@reduxjs/toolkit`, `react-redux` – state management
* `tailwindcss` – styling
* `framer-motion` – animations
* `lucide-react` – icons

3. Start the frontend server:

```bash
npm start
```

* Frontend will run on `http://localhost:3000`

---

## 🖥️ Usage

1. Open the frontend in your browser.
2. Add new employees using the **Add Employee Form**.
3. View the list of employees.
4. Edit employee details via **Edit button** (modal or page).
5. Delete employees using the **Delete button**.
6. Use the **search bar** to filter employees by name.

---

## 📂 Project Structure

### Backend (Spring Boot)

```
backend/
├─ src/main/java/com/example/employee/
│  ├─ controller/   # REST Controllers
│  ├─ service/      # Business Logic
│  ├─ repository/   # JPA Repositories
│  ├─ entity/       # Employee Entity
│  └─ dto/          # DTOs
├─ src/main/resources/
│  └─ application.properties
└─ pom.xml
```

### Frontend (React)

```
frontend/
├─ src/
│  ├─ pages/         # Pages (EmployeeList, EmployeeForm)
│  ├─ redux/         # Redux store and slices
│  └─ App.js
├─ tailwind.config.js
├─ package.json
└─ postcss.config.js
```

---

## ✅ Key Features

* Full **CRUD operations**
* **Redux** state management for predictable state
* **Form validation** for creating/updating employees
* **Search & Filter** functionality
* Smooth **animations** using Framer Motion
* **Responsive UI** built with TailwindCSS
* **RESTful API** backend in Spring Boot
* **backend tests** for endpoints and core business logic

---

## 📷 Screenshots

<img width="1916" height="1030" alt="Screenshot 2025-09-28 220013" src="https://github.com/user-attachments/assets/b1708ca4-96f9-46b6-9372-73dfbc8fa196" />

---


## 🤝 Contact

* **Name:** Yash Patil
* **Email:** [yashpatil62002@gmail.com](mailto:yashpatil62002@gmail.com)
* **GitHub:** [https://github.com/YashBase](https://github.com/YashBase)

---

⭐ If you find this project useful, give it a **star**!
