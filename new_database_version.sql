-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: ta_management_system_db
-- ------------------------------------------------------
-- Server version	8.0.41

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
  `isComplete` tinyint(1) DEFAULT NULL,
  `tacount` int NOT NULL,
  `is_complete` bit(1) NOT NULL,
  PRIMARY KEY (`class_proctoring_id`),
  KEY `course_id` (`course_id`),
  KEY `task_type_id` (`task_type_id`),
  KEY `instructor_id` (`instructor_id`),
  KEY `date_time_idx` (`start_date`,`end_date`),
  CONSTRAINT `class_proctoring_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `class_proctoring_ibfk_2` FOREIGN KEY (`task_type_id`) REFERENCES `task_type` (`task_type_id`),
  CONSTRAINT `class_proctoring_ibfk_3` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `class_proctoring_classroom`
--

DROP TABLE IF EXISTS `class_proctoring_classroom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `class_proctoring_classroom` (
  `class_proctoring_id` int NOT NULL,
  `classroom` varchar(255) NOT NULL,
  `class_proctoring_class_proctoring_id` int NOT NULL,
  PRIMARY KEY (`class_proctoring_id`,`classroom`),
  KEY `FKt16i44uunu4tny3e0tsijjery` (`class_proctoring_class_proctoring_id`),
  CONSTRAINT `class_proctoring_classroom_ibfk_1` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`),
  CONSTRAINT `FKt16i44uunu4tny3e0tsijjery` FOREIGN KEY (`class_proctoring_class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `ta_user_id` int NOT NULL,
  `class_proctoring_class_proctoring_id` int NOT NULL,
  PRIMARY KEY (`class_proctoring_id`,`ta_id`),
  KEY `ta_id` (`ta_id`),
  KEY `FKog8vw83ic8sjdisowt9r0hve0` (`ta_user_id`),
  KEY `FKjuixtpopikxccja1deo2gpqwm` (`class_proctoring_class_proctoring_id`),
  CONSTRAINT `class_proctoring_ta_relation_ibfk_1` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`),
  CONSTRAINT `class_proctoring_ta_relation_ibfk_2` FOREIGN KEY (`ta_id`) REFERENCES `ta` (`user_id`),
  CONSTRAINT `FKjuixtpopikxccja1deo2gpqwm` FOREIGN KEY (`class_proctoring_class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`),
  CONSTRAINT `FKog8vw83ic8sjdisowt9r0hve0` FOREIGN KEY (`ta_user_id`) REFERENCES `ta` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `course_name` varchar(255) DEFAULT NULL,
  `coordinator_id` int DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  UNIQUE KEY `course_code` (`course_code`),
  KEY `department_id` (`department_id`),
  KEY `course_name_idx` (`course_name`),
  KEY `FKmpwq9xkvt5nupip50f4y2fgi7` (`coordinator_id`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `course_ibfk_2` FOREIGN KEY (`coordinator_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKmpwq9xkvt5nupip50f4y2fgi7` FOREIGN KEY (`coordinator_id`) REFERENCES `instructor` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course_instructor_relation`
--

DROP TABLE IF EXISTS `course_instructor_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_instructor_relation` (
  `offered_course_id` int NOT NULL,
  `instructor_id` int NOT NULL,
  `instructor_user_id` int NOT NULL,
  `course_offered_course_id` int NOT NULL,
  PRIMARY KEY (`offered_course_id`,`instructor_id`),
  KEY `instructor_id` (`instructor_id`),
  KEY `FK38uxgk2xm7q0mra8poxfa1umv` (`course_offered_course_id`),
  KEY `FKbm5r9c48cqytedx0k7yqo23md` (`instructor_user_id`),
  CONSTRAINT `course_instructor_relation_ibfk_1` FOREIGN KEY (`offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `course_instructor_relation_ibfk_2` FOREIGN KEY (`instructor_id`) REFERENCES `instructor` (`user_id`),
  CONSTRAINT `FK38uxgk2xm7q0mra8poxfa1umv` FOREIGN KEY (`course_offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `FKbm5r9c48cqytedx0k7yqo23md` FOREIGN KEY (`instructor_user_id`) REFERENCES `instructor` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `course_student_relation`
--

DROP TABLE IF EXISTS `course_student_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `course_student_relation` (
  `offered_course_id` int NOT NULL,
  `student_id` int NOT NULL,
  `student_student_id` int NOT NULL,
  `course_offered_course_id` int NOT NULL,
  PRIMARY KEY (`offered_course_id`,`student_id`),
  KEY `student_id` (`student_id`),
  KEY `FKabab9mevdq37q578imr0hip67` (`course_offered_course_id`),
  KEY `FKrb2n8o4wagnod41rxm27t9vu5` (`student_student_id`),
  CONSTRAINT `course_student_relation_ibfk_1` FOREIGN KEY (`offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `course_student_relation_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`),
  CONSTRAINT `FKabab9mevdq37q578imr0hip67` FOREIGN KEY (`course_offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `FKrb2n8o4wagnod41rxm27t9vu5` FOREIGN KEY (`student_student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `department` (
  `department_id` int NOT NULL AUTO_INCREMENT,
  `department_name` varchar(255) DEFAULT NULL,
  `department_code` varchar(255) DEFAULT NULL,
  `faculty_id` int DEFAULT NULL,
  PRIMARY KEY (`department_id`),
  UNIQUE KEY `department_code` (`department_code`),
  KEY `faculty_id` (`faculty_id`),
  CONSTRAINT `department_ibfk_1` FOREIGN KEY (`faculty_id`) REFERENCES `faculty` (`faculty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `faculty`
--

DROP TABLE IF EXISTS `faculty`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `faculty` (
  `faculty_id` int NOT NULL AUTO_INCREMENT,
  `faculty_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`faculty_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `general_variable`
--

DROP TABLE IF EXISTS `general_variable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `general_variable` (
  `general_variable_id` int NOT NULL AUTO_INCREMENT,
  `semester_id` int DEFAULT NULL,
  `ta_proctoring_cap_time` int DEFAULT NULL,
  `taproctoring_cap_time` int NOT NULL,
  PRIMARY KEY (`general_variable_id`),
  KEY `semester_id` (`semester_id`),
  CONSTRAINT `general_variable_ibfk_1` FOREIGN KEY (`semester_id`) REFERENCES `semester` (`semester_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `login` (
  `login_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `user_type_id` int NOT NULL,
  PRIMARY KEY (`login_id`),
  KEY `user_id` (`user_id`),
  KEY `password_idx` (`password`),
  KEY `user_type_id_idx` (`user_type_id`),
  CONSTRAINT `FKaq7lksyv994hdpaewcmgvaq5o` FOREIGN KEY (`user_type_id`) REFERENCES `user_type` (`user_type_id`),
  CONSTRAINT `login_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `offered_course_schedule_relation`
--

DROP TABLE IF EXISTS `offered_course_schedule_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offered_course_schedule_relation` (
  `offered_course_id` int NOT NULL,
  `time_interval_id` int NOT NULL,
  `time_interval_intervalid` int NOT NULL,
  `course_offered_course_id` int NOT NULL,
  `time_interval_interval_id` int NOT NULL,
  `time_interval_time_interval_id` int NOT NULL,
  PRIMARY KEY (`offered_course_id`,`time_interval_id`),
  KEY `time_interval_id` (`time_interval_id`),
  KEY `FKryudvexbi69nshr8406y2j5s7` (`course_offered_course_id`),
  KEY `FKid8l3xlba2ko9g83n1h337vkg` (`time_interval_time_interval_id`),
  CONSTRAINT `FKid8l3xlba2ko9g83n1h337vkg` FOREIGN KEY (`time_interval_time_interval_id`) REFERENCES `time_interval` (`time_interval_id`),
  CONSTRAINT `FKryudvexbi69nshr8406y2j5s7` FOREIGN KEY (`course_offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `offered_course_schedule_relation_ibfk_1` FOREIGN KEY (`offered_course_id`) REFERENCES `offered_course` (`offered_course_id`),
  CONSTRAINT `offered_course_schedule_relation_ibfk_2` FOREIGN KEY (`time_interval_id`) REFERENCES `time_interval` (`time_interval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `request` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `sender_user_id` int NOT NULL,
  `receiver_user_id` int NOT NULL,
  `sent_date` datetime NOT NULL,
  `is_approved` tinyint(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `sender_user_id` (`sender_user_id`),
  KEY `receiver_user_id` (`receiver_user_id`),
  KEY `sent_date_idx` (`sent_date`),
  CONSTRAINT `request_ibfk_1` FOREIGN KEY (`sender_user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `request_ibfk_2` FOREIGN KEY (`receiver_user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `bilkent_id` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `department_id` int DEFAULT NULL,
  `class` int DEFAULT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `bilkent_id` (`bilkent_id`),
  KEY `department_id` (`department_id`),
  KEY `surname_name_idx` (`surname`,`name`),
  KEY `name_surname_idx` (`name`,`surname`),
  CONSTRAINT `student_ibfk_1` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `is_open_to_swap` bit(1) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `department_id` (`department_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `ta_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `ta_ibfk_2` FOREIGN KEY (`department_id`) REFERENCES `department` (`department_id`),
  CONSTRAINT `ta_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ta_availability_request`
--

DROP TABLE IF EXISTS `ta_availability_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta_availability_request` (
  `request_id` int NOT NULL,
  `unavailability_start_date` datetime NOT NULL,
  `unavailability_end_date` datetime NOT NULL,
  `is_urgent` bit(1) NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `start_date_end_date_idx` (`unavailability_start_date`,`unavailability_end_date`),
  CONSTRAINT `ta_availability_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ta_from_dean_request`
--

DROP TABLE IF EXISTS `ta_from_dean_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta_from_dean_request` (
  `request_id` int NOT NULL,
  `is_complete` tinyint(1) DEFAULT NULL,
  `class_proctoring_id` int NOT NULL,
  `tacount` int NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `class_proctoring_id` (`class_proctoring_id`),
  CONSTRAINT `ta_from_dean_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`),
  CONSTRAINT `ta_from_dean_request_ibfk_2` FOREIGN KEY (`class_proctoring_id`) REFERENCES `class_proctoring` (`class_proctoring_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `ta_schedule_relation`
--

DROP TABLE IF EXISTS `ta_schedule_relation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ta_schedule_relation` (
  `ta_id` int NOT NULL,
  `time_interval_id` int NOT NULL,
  `time_interval_intervalid` int NOT NULL,
  `ta_user_id` int NOT NULL,
  `time_interval_interval_id` int NOT NULL,
  `time_interval_time_interval_id` int NOT NULL,
  PRIMARY KEY (`ta_id`,`time_interval_id`),
  KEY `time_interval_id` (`time_interval_id`),
  KEY `FKcsa9g596jjyqgtforibpuhuyr` (`ta_user_id`),
  KEY `FKrp5yhabhip5gel5c0kbgfcu6y` (`time_interval_time_interval_id`),
  CONSTRAINT `FKcsa9g596jjyqgtforibpuhuyr` FOREIGN KEY (`ta_user_id`) REFERENCES `ta` (`user_id`),
  CONSTRAINT `FKrp5yhabhip5gel5c0kbgfcu6y` FOREIGN KEY (`time_interval_time_interval_id`) REFERENCES `time_interval` (`time_interval_id`),
  CONSTRAINT `ta_schedule_relation_ibfk_1` FOREIGN KEY (`ta_id`) REFERENCES `ta` (`user_id`),
  CONSTRAINT `ta_schedule_relation_ibfk_2` FOREIGN KEY (`time_interval_id`) REFERENCES `time_interval` (`time_interval_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `task_type`
--

DROP TABLE IF EXISTS `task_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task_type` (
  `task_type_id` int NOT NULL AUTO_INCREMENT,
  `course_id` int DEFAULT NULL,
  `task_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`task_type_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `task_type_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `time_interval`
--

DROP TABLE IF EXISTS `time_interval`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `time_interval` (
  `time_interval_id` int NOT NULL AUTO_INCREMENT,
  `day` varchar(255) DEFAULT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  PRIMARY KEY (`time_interval_id`),
  KEY `day_idx` (`day`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `bilkent_id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `bilkent_id` (`bilkent_id`),
  UNIQUE KEY `email` (`email`),
  KEY `surname_name_idx` (`surname`,`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_type` (
  `user_type_id` int NOT NULL AUTO_INCREMENT,
  `user_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `tacount` int NOT NULL,
  PRIMARY KEY (`request_id`),
  KEY `task_type_id` (`task_type_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `workload_request_ibfk_1` FOREIGN KEY (`request_id`) REFERENCES `request` (`request_id`),
  CONSTRAINT `workload_request_ibfk_2` FOREIGN KEY (`task_type_id`) REFERENCES `task_type` (`task_type_id`),
  CONSTRAINT `workload_request_ibfk_3` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-04-16 15:45:32
