-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: ta_management_system_db
-- ------------------------------------------------------
-- Server version	8.0.41


create database ta_management_system_db;
use ta_management_system_db;

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class_proctoring`
--

DROP TABLE IF EXISTS `class_proctoring`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_proctoring` (
  `class_proctoring_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NOT NULL,
  `task_type_id` int DEFAULT NULL,
  `instructor_id` int NOT NULL,
  `section_no` int DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `ta_count` int DEFAULT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`class_proctoring_id`),
  KEY `course_id` (`course_id`),
  KEY `task_type_id` (`task_type_id`),
  KEY `instructor_id` (`instructor_id`),
  KEY `date_time_idx` (`start_date`,`end_date`),
  CONSTRAINT `class_proctoring_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `class_proctoring_ibfk_2` FOREIGN KEY (`task_type_id`) REFERENCES `task_type` (`task_type_id`),
  CONSTRAINT `class_proctoring_ibfk_3` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_proctoring`
--

LOCK TABLES `class_proctoring` WRITE;
/*!40000 ALTER TABLE `class_proctoring` DISABLE KEYS */;
INSERT INTO `class_proctoring` VALUES (1,1,1,1,1,'2025-04-20 10:00:00','2025-04-20 12:00:00',2,0),(2,2,2,7,1,'2025-04-21 13:00:00','2025-04-21 15:00:00',1,1),(3,3,3,10,1,'2025-04-22 09:00:00','2025-04-22 11:00:00',3,0),(4,4,4,1,2,'2025-04-23 14:00:00','2025-04-23 16:00:00',2,0),(5,5,5,7,1,'2025-04-24 10:00:00','2025-04-24 12:00:00',1,1);
/*!40000 ALTER TABLE `class_proctoring` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_proctoring_classroom`
--

DROP TABLE IF EXISTS `class_proctoring_classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_proctoring_classroom` (
  `class_proctoring_id` int NOT NULL,
  `classroom` varchar(10) NOT NULL,
  PRIMARY KEY (`class_proctoring_id`,`classroom`),
  CONSTRAINT `class_proctoring_classroom_ibfk_1` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_proctoring_classroom`
--

LOCK TABLES `class_proctoring_classroom` WRITE;
/*!40000 ALTER TABLE `class_proctoring_classroom` DISABLE KEYS */;
INSERT INTO `class_proctoring_classroom` VALUES (1,'EE01'),(2,'CS102'),(3,'MATH201'),(4,'PHYS301'),(5,'ECON105');
/*!40000 ALTER TABLE `class_proctoring_classroom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `class_proctoring_ta_relation`
--

DROP TABLE IF EXISTS `class_proctoring_ta_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_proctoring_ta_relation` (
  `class_proctoring_id` int NOT NULL,
  `ta_id` int NOT NULL,
  `is_paid` tinyint(1) DEFAULT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  `is_open_to_swap` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`class_proctoring_id`,`ta_id`),
  KEY `ta_id` (`ta_id`),
  CONSTRAINT `class_proctoring_ta_relation_ibfk_1` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`),
  CONSTRAINT `class_proctoring_ta_relation_ibfk_2` FOREIGN KEY (`ta_id`) REFERENCES `ta` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class_proctoring_ta_relation`
--

LOCK TABLES `class_proctoring_ta_relation` WRITE;
/*!40000 ALTER TABLE `class_proctoring_ta_relation` DISABLE KEYS */;
INSERT INTO `class_proctoring_ta_relation` VALUES (1,2,0,0,1),(1,3,0,0,0),(2,8,1,1,0),(3,2,0,0,1),(3,3,0,0,1);
/*!40000 ALTER TABLE `class_proctoring_ta_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `department_id` int NOT NULL,
  `course_code` int NOT NULL,
  `course_name` varchar(100) NOT NULL,
  `coordinator_id` int DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_code` (`course_code`),
  KEY `department_id` (`department_id`),
  KEY `coordinator_id` (`coordinator_id`),
  KEY `course_name_idx` (`course_name`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`coordinator_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course`
--

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` VALUES (1,1,101,'Intro to Programming',1),(2,2,102,'Circuits I',7),(3,3,103,'Linear Algebra',10),(4,4,104,'Classical Mechanics',1),(5,5,105,'Microeconomics',7),(6,6,106,'Business Ethics',1),(7,1,107,'Data Structures',1),(8,2,108,'Electronics',7),(9,3,109,'Calculus I',10),(10,4,110,'Quantum Physics',1);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_instructor_relation`
--

DROP TABLE IF EXISTS `course_instructor_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_instructor_relation` (
  `offered_course_id` int NOT NULL,
  `instructor_id` int NOT NULL,
  PRIMARY KEY (`offered_course_id`,`instructor_id`),
  KEY `instructor_id` (`instructor_id`),
  CONSTRAINT `course_instructor_relation_ibfk_1` FOREIGN KEY (`offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `course_instructor_relation_ibfk_2` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_instructor_relation`
--

LOCK TABLES `course_instructor_relation` WRITE;
/*!40000 ALTER TABLE `course_instructor_relation` DISABLE KEYS */;
INSERT INTO `course_instructor_relation` VALUES (1,1),(4,1),(6,1),(7,1),(10,1),(2,7),(5,7),(8,7),(3,10),(9,10);
/*!40000 ALTER TABLE `course_instructor_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_student_relation`
--

DROP TABLE IF EXISTS `course_student_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_student_relation` (
  `offered_course_id` int NOT NULL,
  `student_id` int NOT NULL,
  PRIMARY KEY (`offered_course_id`,`student_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `course_student_relation_ibfk_1` FOREIGN KEY (`offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `course_student_relation_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_student_relation`
--

LOCK TABLES `course_student_relation` WRITE;
/*!40000 ALTER TABLE `course_student_relation` DISABLE KEYS */;
INSERT INTO `course_student_relation` VALUES (1,1),(1,2),(1,3),(2,4),(2,5),(3,6),(3,7),(4,8),(4,9),(5,10);
/*!40000 ALTER TABLE `course_student_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deans_office`
--

DROP TABLE IF EXISTS `deans_office`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deans_office` (
  `user_id` int NOT NULL,
  `faculty_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `deans_office_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `deans_office_ibfk_2` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deans_office`
--

LOCK TABLES `deans_office` WRITE;
/*!40000 ALTER TABLE `deans_office` DISABLE KEYS */;
INSERT INTO `deans_office` VALUES (5,1),(6,2);
/*!40000 ALTER TABLE `deans_office` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(50) NOT NULL,
  `department_code` varchar(10) NOT NULL,
  `faculty_id` int DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `department_code` (`department_code`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Computer Engineering','CS',1),(2,'Electrical Engineering','EE',1),(3,'Mathematics','MATH',2),(4,'Physics','PHYS',2),(5,'Management','MAN',4),(6,'Economics','ECON',3),(7,'Law','LAW',5),(8,'Education','EDU',6),(9,'Architecture','ARCH',7),(10,'Tourism','TRM',10);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_secretary`
--

DROP TABLE IF EXISTS `department_secretary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department_secretary` (
  `user_id` int NOT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `department_secretary_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `department_secretary_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_secretary`
--

LOCK TABLES `department_secretary` WRITE;
/*!40000 ALTER TABLE `department_secretary` DISABLE KEYS */;
INSERT INTO `department_secretary` VALUES (6,1),(7,2);
/*!40000 ALTER TABLE `department_secretary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `faculty_id` int NOT NULL AUTO_INCREMENT,
  `faculty_name` varchar(50) NOT NULL,
  PRIMARY KEY (`faculty_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `faculty`
--

LOCK TABLES `faculty` WRITE;
/*!40000 ALTER TABLE `faculty` DISABLE KEYS */;
INSERT INTO `faculty` VALUES (1,'Engineering'),(2,'Arts and Sciences'),(3,'Economics'),(4,'Business Administration'),(5,'Law'),(6,'Education'),(7,'Architecture'),(8,'Philosophy'),(9,'Music'),(10,'Tourism');
/*!40000 ALTER TABLE `faculty` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `general_variable`
--

DROP TABLE IF EXISTS `general_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_variable` (
  `semester_id` int DEFAULT NULL,
  `ta_proctoring_cap_time` int DEFAULT NULL,
  KEY `semester_id` (`semester_id`),
  CONSTRAINT `general_variable_ibfk_1` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `general_variable`
--

LOCK TABLES `general_variable` WRITE;
/*!40000 ALTER TABLE `general_variable` DISABLE KEYS */;
INSERT INTO `general_variable` VALUES (1,20),(2,25),(3,18),(4,22),(5,20),(6,19),(7,23),(8,17),(9,26),(10,21);
/*!40000 ALTER TABLE `general_variable` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor` (
  `user_id` int NOT NULL,
  `department_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `department_id` (`department_id`),
  CONSTRAINT `instructor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `instructor_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor`
--

LOCK TABLES `instructor` WRITE;
/*!40000 ALTER TABLE `instructor` DISABLE KEYS */;
INSERT INTO `instructor` VALUES (1,1),(7,2),(10,3);
/*!40000 ALTER TABLE `instructor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `instructor_ta_proctoring_request`
--

DROP TABLE IF EXISTS `instructor_ta_proctoring_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `instructor_ta_proctoring_request` (
  `request_id` int NOT NULL,
  `class_proctoring_id` int NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `class_proctoring_id` (`class_proctoring_id`),
  CONSTRAINT `instructor_ta_proctoring_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`),
  CONSTRAINT `instructor_ta_proctoring_request_ibfk_2` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `instructor_ta_proctoring_request`
--

LOCK TABLES `instructor_ta_proctoring_request` WRITE;
/*!40000 ALTER TABLE `instructor_ta_proctoring_request` DISABLE KEYS */;
INSERT INTO `instructor_ta_proctoring_request` VALUES (5,5);
/*!40000 ALTER TABLE `instructor_ta_proctoring_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `login_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `password` varchar(50) NOT NULL,
  `user_type_id` int NOT NULL,
  PRIMARY KEY (`login_id`),
  KEY `user_id` (`user_id`),
  KEY `password_idx` (`password`),
  KEY `user_type_id_idx` (`user_type_id`),
  CONSTRAINT `login_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `login_ibfk_2` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (1,1,'passAlice',2),(2,2,'passBob',3),(3,3,'passCharlie',3),(4,4,'passDiana',4),(5,5,'passEdward',5),(6,6,'passFiona',6),(7,7,'passGeorge',2),(8,8,'passHannah',3),(9,9,'passIsaac',4),(10,10,'passJulia',2);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offered_course`
--

DROP TABLE IF EXISTS `offered_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offered_course` (
  `offered_course_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int NOT NULL,
  `section_no` int DEFAULT NULL,
  `semester_id` int DEFAULT NULL,
  PRIMARY KEY (`offered_course_id`),
  KEY `course_id` (`course_id`),
  KEY `semester_id` (`semester_id`),
  CONSTRAINT `offered_course_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `offered_course_ibfk_2` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offered_course`
--

LOCK TABLES `offered_course` WRITE;
/*!40000 ALTER TABLE `offered_course` DISABLE KEYS */;
INSERT INTO `offered_course` VALUES (1,1,1,1),(2,2,1,1),(3,3,1,2),(4,4,2,2),(5,5,1,3),(6,6,1,4),(7,7,2,5),(8,8,2,6),(9,9,1,7),(10,10,1,8);
/*!40000 ALTER TABLE `offered_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `offered_course_schedule_relation`
--

DROP TABLE IF EXISTS `offered_course_schedule_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offered_course_schedule_relation` (
  `offered_course_id` int NOT NULL,
  `time_interval_id` int NOT NULL,
  PRIMARY KEY (`offered_course_id`,`time_interval_id`),
  KEY `time_interval_id` (`time_interval_id`),
  CONSTRAINT `offered_course_schedule_relation_ibfk_1` FOREIGN KEY (`offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `offered_course_schedule_relation_ibfk_2` FOREIGN KEY (`time_interval_id`) REFERENCES `time_interval` (`time_interval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offered_course_schedule_relation`
--

LOCK TABLES `offered_course_schedule_relation` WRITE;
/*!40000 ALTER TABLE `offered_course_schedule_relation` DISABLE KEYS */;
INSERT INTO `offered_course_schedule_relation` VALUES (1,1),(2,2),(3,3),(4,4),(5,5),(6,6),(7,7),(8,8),(9,9),(10,10);
/*!40000 ALTER TABLE `offered_course_schedule_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `sender_user_id` int NOT NULL,
  `reciever_user_id` int NOT NULL,
  `sent_date` datetime NOT NULL,
  `is_approved` tinyint(1) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `sender_user_id` (`sender_user_id`),
  KEY `reciever_user_id` (`reciever_user_id`),
  KEY `sent_date_idx` (`sent_date`),
  CONSTRAINT `request_ibfk_1` FOREIGN KEY (`sender_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `request_ibfk_2` FOREIGN KEY (`reciever_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (1,2,1,'2025-04-15 09:00:00',1,'Requesting TA reassignment.'),(2,3,1,'2025-04-16 14:00:00',0,'Availability update.'),(3,8,7,'2025-04-17 16:00:00',1,'Swap request for proctoring.'),(4,1,2,'2025-04-17 11:30:00',NULL,'Additional workload details.'),(5,7,3,'2025-04-17 13:00:00',1,'TA shortage notification.');
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `semester`
--

DROP TABLE IF EXISTS `semester`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `semester` (
  `semester_id` int NOT NULL AUTO_INCREMENT,
  `year` int NOT NULL,
  `term` int NOT NULL,
  PRIMARY KEY (`semester_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `semester`
--

LOCK TABLES `semester` WRITE;
/*!40000 ALTER TABLE `semester` DISABLE KEYS */;
INSERT INTO `semester` VALUES (1,2024,1),(2,2024,2),(3,2025,1),(4,2025,2),(5,2023,2),(6,2023,1),(7,2022,2),(8,2022,1),(9,2021,2),(10,2021,1);
/*!40000 ALTER TABLE `semester` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `bilkent_id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `class` int DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `bilkent_id` (`bilkent_id`),
  KEY `department_id` (`department_id`),
  KEY `surname_name_idx` (`surname`,`name`),
  KEY `name_surname_idx` (`name`,`surname`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (1,'22100001','Liam','Moore','liam.moore@bilkent.edu.tr','05001119900',1,1,2),(2,'22100002','Noah','Taylor','noah.taylor@bilkent.edu.tr','05001118800',1,2,3),(3,'22100003','Olivia','Anderson','olivia.anderson@bilkent.edu.tr','05001117700',1,3,1),(4,'22100004','Emma','Thomas','emma.thomas@bilkent.edu.tr','05001116600',1,4,4),(5,'22100005','Ava','Jackson','ava.jackson@bilkent.edu.tr','05001115500',1,5,2),(6,'22100006','Mason','White','mason.white@bilkent.edu.tr','05001114400',1,1,2),(7,'22100007','Ella','Harris','ella.harris@bilkent.edu.tr','05001113300',1,2,1),(8,'22100008','Lucas','Martin','lucas.martin@bilkent.edu.tr','05001112200',1,3,3),(9,'22100009','Mia','Thompson','mia.thompson@bilkent.edu.tr','05001111100',1,1,4),(10,'22100010','Ethan','Garcia','ethan.garcia@bilkent.edu.tr','05001110000',1,1,1);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swap_request`
--

DROP TABLE IF EXISTS `swap_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swap_request` (
  `request_id` int NOT NULL,
  `class_proctoring_id` int DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `class_proctoring_id` (`class_proctoring_id`),
  CONSTRAINT `swap_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`),
  CONSTRAINT `swap_request_ibfk_2` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swap_request`
--

LOCK TABLES `swap_request` WRITE;
/*!40000 ALTER TABLE `swap_request` DISABLE KEYS */;
INSERT INTO `swap_request` VALUES (3,1);
/*!40000 ALTER TABLE `swap_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ta`
--

DROP TABLE IF EXISTS `ta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta` (
  `user_id` int NOT NULL,
  `department_id` int DEFAULT NULL,
  `course_id` int DEFAULT NULL,
  `class` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `department_id` (`department_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `ta_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `ta_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `ta_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ta`
--

LOCK TABLES `ta` WRITE;
/*!40000 ALTER TABLE `ta` DISABLE KEYS */;
INSERT INTO `ta` VALUES (2,1,NULL,2),(3,1,NULL,3),(8,2,NULL,2);
/*!40000 ALTER TABLE `ta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ta_availability_request`
--

DROP TABLE IF EXISTS `ta_availability_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta_availability_request` (
  `request_id` int NOT NULL,
  `is_urgent` tinyint(1) DEFAULT NULL,
  `unavailability_start_date` datetime NOT NULL,
  `unavailability_end_date` datetime NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `start_date_end_date_idx` (`unavailability_start_date`,`unavailability_end_date`),
  CONSTRAINT `ta_availability_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ta_availability_request`
--

LOCK TABLES `ta_availability_request` WRITE;
/*!40000 ALTER TABLE `ta_availability_request` DISABLE KEYS */;
INSERT INTO `ta_availability_request` VALUES (2,1,'2025-04-20 08:00:00','2025-04-21 20:00:00');
/*!40000 ALTER TABLE `ta_availability_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ta_from_dean_request`
--

DROP TABLE IF EXISTS `ta_from_dean_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta_from_dean_request` (
  `request_id` int NOT NULL,
  `ta_count` int DEFAULT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  `class_proctoring_id` int NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `class_proctoring_id` (`class_proctoring_id`),
  CONSTRAINT `ta_from_dean_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`),
  CONSTRAINT `ta_from_dean_request_ibfk_2` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ta_from_dean_request`
--

LOCK TABLES `ta_from_dean_request` WRITE;
/*!40000 ALTER TABLE `ta_from_dean_request` DISABLE KEYS */;
INSERT INTO `ta_from_dean_request` VALUES (1,2,0,1);
/*!40000 ALTER TABLE `ta_from_dean_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ta_schedule_relation`
--

DROP TABLE IF EXISTS `ta_schedule_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta_schedule_relation` (
  `ta_id` int NOT NULL,
  `time_interval_id` int NOT NULL,
  PRIMARY KEY (`ta_id`,`time_interval_id`),
  KEY `time_interval_id` (`time_interval_id`),
  CONSTRAINT `ta_schedule_relation_ibfk_1` FOREIGN KEY (`ta_id`) REFERENCES `ta` (`user_id`),
  CONSTRAINT `ta_schedule_relation_ibfk_2` FOREIGN KEY (`time_interval_id`) REFERENCES `time_interval` (`time_interval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ta_schedule_relation`
--

LOCK TABLES `ta_schedule_relation` WRITE;
/*!40000 ALTER TABLE `ta_schedule_relation` DISABLE KEYS */;
INSERT INTO `ta_schedule_relation` VALUES (2,1),(3,2),(8,3),(2,4),(3,5),(8,6);
/*!40000 ALTER TABLE `ta_schedule_relation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task_type`
--

DROP TABLE IF EXISTS `task_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_type` (
  `task_type_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int DEFAULT NULL,
  `task_type_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`task_type_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `task_type_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task_type`
--

LOCK TABLES `task_type` WRITE;
/*!40000 ALTER TABLE `task_type` DISABLE KEYS */;
INSERT INTO `task_type` VALUES (1,1,'Midterm Proctoring'),(2,2,'Lab Supervision'),(3,3,'Final Exam Proctoring'),(4,4,'Office Hours'),(5,5,'Homework Review'),(6,6,'Discussion Session'),(7,7,'Midterm Proctoring'),(8,8,'Grading Assistance'),(9,9,'Lab Setup'),(10,10,'Project Guidance');
/*!40000 ALTER TABLE `task_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `time_interval`
--

DROP TABLE IF EXISTS `time_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `time_interval` (
  `time_interval_id` int NOT NULL AUTO_INCREMENT,
  `day` varchar(20) NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  PRIMARY KEY (`time_interval_id`),
  KEY `day_idx` (`day`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `time_interval`
--

LOCK TABLES `time_interval` WRITE;
/*!40000 ALTER TABLE `time_interval` DISABLE KEYS */;
INSERT INTO `time_interval` VALUES (1,'Monday','08:40:00','10:30:00'),(2,'Tuesday','10:40:00','12:30:00'),(3,'Wednesday','13:40:00','15:30:00'),(4,'Thursday','15:40:00','17:30:00'),(5,'Friday','08:40:00','10:30:00'),(6,'Monday','10:40:00','12:30:00'),(7,'Tuesday','13:40:00','15:30:00'),(8,'Wednesday','15:40:00','17:30:00'),(9,'Thursday','08:40:00','10:30:00'),(10,'Friday','10:40:00','12:30:00');
/*!40000 ALTER TABLE `time_interval` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `bilkent_id` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `surname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `bilkent_id` (`bilkent_id`),
  UNIQUE KEY `email` (`email`),
  KEY `surname_name_idx` (`surname`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'21900001','Alice','Smith','alice.smith@bilkent.edu.tr','05001112233',1),(2,'21900002','Bob','Johnson','bob.johnson@bilkent.edu.tr','05002223344',1),(3,'21900003','Charlie','Williams','charlie.williams@bilkent.edu.tr','05003334455',1),(4,'21900004','Diana','Brown','diana.brown@bilkent.edu.tr','05004445566',1),(5,'21900005','Edward','Jones','edward.jones@bilkent.edu.tr','05005556677',1),(6,'21900006','Fiona','Garcia','fiona.garcia@bilkent.edu.tr','05006667788',1),(7,'21900007','George','Miller','george.miller@bilkent.edu.tr','05007778899',1),(8,'21900008','Hannah','Davis','hannah.davis@bilkent.edu.tr','05008889900',1),(9,'21900009','Isaac','Martinez','isaac.martinez@bilkent.edu.tr','05009990011',1),(10,'21900010','Julia','Lopez','julia.lopez@bilkent.edu.tr','05001001001',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `user_type_id` int NOT NULL AUTO_INCREMENT,
  `user_type_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES (1,'Admin'),(2,'Instructor'),(3,'TA'),(4,'Student'),(5,'Dean'),(6,'Secretary'),(7,'Coordinator'),(8,'Guest'),(9,'Staff'),(10,'Observer');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workload_request`
--

DROP TABLE IF EXISTS `workload_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workload_request` (
  `request_id` int NOT NULL,
  `task_type_id` int DEFAULT NULL,
  `time_spent` int NOT NULL,
  `course_id` int DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `task_type_id` (`task_type_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `workload_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`),
  CONSTRAINT `workload_request_ibfk_2` FOREIGN KEY (`task_type_id`) REFERENCES `task_type` (`task_type_id`),
  CONSTRAINT `workload_request_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workload_request`
--

LOCK TABLES `workload_request` WRITE;
/*!40000 ALTER TABLE `workload_request` DISABLE KEYS */;
INSERT INTO `workload_request` VALUES (4,1,120,1);
/*!40000 ALTER TABLE `workload_request` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-18  0:11:08
