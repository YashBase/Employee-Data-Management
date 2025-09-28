

CREATE DATABASE IF NOT EXISTS employee_db;

USE employee_db;

drop table employees;

CREATE TABLE IF NOT EXISTS employees (
  id  int not null AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(150) NOT NULL,
  email VARCHAR(150) NOT NULL,
  position VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  is_deleted TINYINT(1) NOT NULL DEFAULT 0,
  CONSTRAINT uq_employees_email UNIQUE (email)
);
-- Index to help name searches
CREATE INDEX idx_employees_name ON employees (name);
	
describe employees;



-- Sample seed data
INSERT INTO employees (name, email, position) VALUES
('Karan Mehta', 'karan.mehta@example.com', 'Full Stack Developer'),
('Priya Sharma', 'priya.sharma@example.com', 'UI/UX Designer'),
('Vikram Singh', 'vikram.singh@example.com', 'DevOps Engineer'),
('Anjali Rao', 'anjali.rao@example.com', 'Product Manager'),
('Rohan Kapoor', 'rohan.kapoor@example.com', 'QA Engineer'),
('Neha Joshi', 'neha.joshi@example.com', 'Data Analyst'),
('Aditya Verma', 'aditya.verma@example.com', 'Cloud Architect'),
('Snehal Patankar', 'snehal.patankar@example.com', 'Technical Lead'),
('Ishita Nair', 'ishita.nair@example.com', 'Business Analyst'),
('Manish Kulkarni', 'manish.kulkarni@example.com', 'Software Engineer');
