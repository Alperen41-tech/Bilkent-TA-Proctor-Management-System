# Bilkent-TA-Proctor-Management-System

## Project Overview
Bilkent TA Proctor management system is a web-based application. In this product our aim is to facilitate the difficulties in distribution of TA workload for each of the duties such as grading, lab assistancy, proctoring etc. Eventually, this application will automate these assignments and help authorized staff to keep track of them.
This project is to be run especially on Linux environment and be compatible with Apache2. 

## Main Functionalities
Automatic Assignment: Assigns TAs based on their current workload while considering constraints such as:
+  Prioritizes TAs assigned to the specific course. 
+  Ensures no conflicts with leave, exams, or their schedule.
+  Enforces PhD/MS restrictions.
+  Proctor Swap: TAs can request swapping some proctor duties with other TAs under permission of authorized staff.

Manual Assignment: Assigning TAs
+  Authorized staff can add/edit/delete student, staff, course, offerings, classroom.

TA Workload Management:
+  TAs log their tasks and work hours (lab work, grading, office hours, etc.).
+  Course instructors approve/reject logs.
+  Workload is updated accordingly.

Classroom & Exam Management:
+  Faculty/staff member can print student distribution lists for exams (alphabetically/randomly).
+  Assigns proctors to specific exam rooms both automatically or manually.

Leave Of Absence Management
+  TAs request leave in case of emergency (such as medical issues, conferences, vacations etc.).
+  Approved leaves prevent automatic proctoring assignments.

Reporting & Logs
+  Logs system actions (logins, assignments, swaps).
+  Reports on total TA workload and proctoring hours per semester/year.



## Expected Technology to be Used
Technology Stack
+  Backend: Spring Boot (Java), Maven, Spring Security, JPA (Hibernate)
+  Database: MySQL
+  Frontend: PHP, React.js, Angular
+  Deployment: Apache2 on Linux server





