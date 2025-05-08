drop database if exists ta_management_system_db;
create database ta_management_system_db;
use ta_management_system_db;

create table time_interval(
	time_interval_id int primary key auto_increment,
    day varchar(20) not null,
    start_time time not null,
    end_time time not null
);

create index day_idx on time_interval(day);


create table user_type(
	user_type_id int primary key auto_increment,
    user_type_name varchar(50) not null
);


create table faculty(
	faculty_id int primary key auto_increment,
	faculty_name varchar(100) not null
);


create table department(
	department_id int primary key auto_increment,
    department_name varchar(100) not null,
	department_code varchar(10) unique not null,
    faculty_id int,
    foreign key (faculty_id) references faculty(faculty_id)
);


create table semester(
	semester_id int primary key auto_increment,
	year varchar(10) not null,
    term int not null
);


create table general_variable(
	general_variable_id int,
	semester_id int,
    ta_proctoring_cap_time int,
    foreign key (semester_id) references semester(semester_id)
);


create table user(
	user_id int primary key auto_increment,
	bilkent_id varchar(20) unique not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    email varchar(100) unique not null,
    phone_number varchar(20),
    is_active bool
);

create index surname_name_idx on user(surname, name);


create table login(
	login_id int primary key auto_increment,
	user_id int not null,
    password varchar(100) not null,
	user_type_id int not null,
    foreign key (user_id) references user(user_id),
    foreign key (user_type_id) references user_type(user_type_id)
);

create index password_idx on login(password);
create index user_type_id_idx on login(user_type_id);


create table deans_office(
    user_id int primary key,
    faculty_id int,
    foreign key (user_id) references user(user_id),
    foreign key (faculty_id) references faculty(faculty_id)
);


create table department_secretary(
    user_id int primary key,
    department_id int,
    foreign key (user_id) references user(user_id),
    foreign key (department_id) references department(department_id)
);


create table instructor(
	user_id int primary key,
    department_id int,
    foreign key (user_id) references user(user_id),
    foreign key (department_id) references department(department_id)
);


create table course(
	course_id int primary key auto_increment,
    department_id int not null,
	course_code int unique not null,
    course_name varchar(100) not null,
    coordinator_id int,
    foreign key (department_id) references department(department_id),
    foreign key (coordinator_id) references user(user_id)
);

create index course_name_idx on course(course_name);


create table ta_type(
	 ta_type_id int primary key auto_increment,
	 type_name varchar(50),
	 ta_load int
);

create index type_name_idx on ta_type(type_name);


create table ta(
	user_id int primary key,
    department_id int,
	course_id int,
    class int,
    ta_type_id int,
    workload int default 0,
    foreign key (user_id) references user(user_id),
    foreign key (department_id) references department(department_id),
    foreign key (course_id) references course(course_id),
    foreign key (ta_type_id) references ta_type(ta_type_id)
);


create table student(
	student_id int primary key auto_increment,
	bilkent_id varchar(20) unique not null,
    name varchar(50) not null,
    surname varchar(50) not null,
    email varchar(100),
    phone_number varchar(20),
    is_active bool,
	department_id int,
    class int,
    foreign key (department_id) references department(department_id)
);

create index surname_name_idx on student(surname, name);
create index name_surname_idx on student(name, surname);


create table offered_course(
	offered_course_id int primary key auto_increment,
    course_id int not null,
    section_no int,
	semester_id int,
    foreign key (course_id) references course(course_id),
    foreign key (semester_id) references semester(semester_id)
);


create table task_type(
	task_type_id int primary key auto_increment,
	course_id int,
    task_type_name varchar(100),
    time_limit int,
    foreign key (course_id) references course(course_id),
    unique key (course_id, task_type_name)
);


create table class_proctoring(
	class_proctoring_id int primary key auto_increment,
    course_id int not null,
    event_name varchar(100),
    creator_id int not null,
    section_no int,
	start_date datetime,
    end_date datetime,
    ta_count int,
    is_complete bool,
    foreign key (course_id) references course(course_id),
    foreign key (creator_id) references user(user_id)
);

create index date_time_idx on class_proctoring(start_date, end_date);


create table class_proctoring_classroom(
	class_proctoring_id int,
    classroom varchar(10),
    primary key (class_proctoring_id, classroom),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);

create table proctoring_application(
    application_id int primary key auto_increment,
    class_proctoring_id int,
    visible_department_id int,
    applicant_count_limit int,
    application_type varchar(20),
    is_complete bool not null,
    finish_date datetime not null,
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id),
    foreign key (visible_department_id) references department(department_id)
);

create table course_student_relation(
	offered_course_id int,
	student_id int,
    primary key (offered_course_id, student_id),
    foreign key (offered_course_id) references offered_course(offered_course_id),
    foreign key (student_id) references student(student_id)
);


create table offered_course_schedule_relation(
	offered_course_id int,
	time_interval_id int,
    primary key (offered_course_id, time_interval_id),
	foreign key (offered_course_id) references offered_course(offered_course_id),
    foreign key (time_interval_id) references time_interval(time_interval_id)
);


create table course_instructor_relation(
	offered_course_id int,
    instructor_id int,
    primary key (offered_course_id, instructor_id),
    foreign key (offered_course_id) references offered_course(offered_course_id),
    foreign key (instructor_id) references instructor(user_id)
);


create table class_proctoring_ta_relation(
	class_proctoring_id int,
    ta_id int,
    is_paid bool,
    is_complete bool,
    is_open_to_swap bool,
	primary key (class_proctoring_id, ta_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id),
    foreign key (ta_id) references ta(user_id)
);

create table proctoring_application_ta_relation(
	proctoring_application_ta_relation_id int primary key auto_increment,
    ta_id int,
    proctoring_application_id int,
    is_approved_by_secretary bool default 0,
    foreign key (ta_id) references ta (user_id),
    foreign key (proctoring_application_id) references proctoring_application(application_id)
);



create table request(
	request_id int primary key auto_increment,
    sender_user_id int not null,
    receiver_user_id int not null,
    sent_date datetime not null,
    is_approved bool,
    response_date datetime,
    description varchar(500),
    foreign key (sender_user_id) references user(user_id),
    foreign key (receiver_user_id) references user(user_id)
);

create index sent_date_idx on request(sent_date);
create index response_date_idx on request(response_date);


create table ta_swap_request(
	request_id int primary key auto_increment,
	class_proctoring_id int,
    foreign key (request_id) references request(request_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);


create table ta_workload_request(
	request_id int primary key,
    task_type_id int,
    time_spent int not null,
    course_id int,
    workload_id int not null,
    foreign key (request_id) references request(request_id),
    foreign key (task_type_id) references task_type(task_type_id),
    foreign key (course_id) references course(course_id)
);


create table auth_staff_proctoring_request(
	request_id int primary key,
    class_proctoring_id int not null,
    foreign key (request_id) references request(request_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);


create table ta_leave_request(
	request_id int primary key,
    is_urgent bool,
    leave_start_date datetime not null,
    leave_end_date datetime not null,
    foreign key (request_id) references request(request_id)
);

create index start_date_end_date_idx on ta_leave_request(leave_start_date, leave_end_date);


create table instructor_additional_ta_request(
	request_id int primary key,
    ta_count int,
    is_sent_to_secretary bool not null,
    class_proctoring_id int not null,
    foreign key (request_id) references request(request_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);


create table notification(
	notification_id int primary key auto_increment,
    receiver_id int,
    request_id int,
    notification_type varchar(50),
    is_read boolean,
    description varchar(200),
    foreign key (receiver_id) references user(user_id),
    foreign key (request_id) references request(request_id)
);


create table log(
	log_id int primary key auto_increment,
    message varchar(200),
    log_type varchar(20),
    log_date datetime
);

create index log_date_idx on log(log_date);


create table course_ta_instructor_form(
	form_id int primary key auto_increment,
    course_id int not null,
    instructor_id int not null,
    semester_id int,
    sent_date datetime,
    min_ta_load int,
    max_ta_load int,
    number_of_grader int,
    must_have_tas varchar(200),
    preferred_tas varchar(500),
    preferred_graders varchar(200),
    avoided_tas varchar(200),
    description varchar(500),
    foreign key (course_id) references course(course_id),
    foreign key (semester_id) references semester(semester_id)
);

insert into time_interval (day, start_time, end_time) values
	('Monday', '08:30:00', '09:20:00'),
    ('Monday', '09:30:00', '10:20:00'),
    ('Monday', '10:30:00', '11:20:00'),
    ('Monday', '11:30:00', '12:20:00'),
    ('Monday', '12:30:00', '13:20:00'),
    ('Monday', '13:30:00', '14:20:00'),
    ('Monday', '14:30:00', '15:20:00'),
    ('Monday', '15:30:00', '16:20:00'),
    ('Monday', '16:30:00', '17:20:00'),
    ('Tuesday', '08:30:00', '09:20:00'),
    ('Tuesday', '09:30:00', '10:20:00'),
    ('Tuesday', '10:30:00', '11:20:00'),
    ('Tuesday', '11:30:00', '12:20:00'),
    ('Tuesday', '12:30:00', '13:20:00'),
    ('Tuesday', '13:30:00', '14:20:00'),
    ('Tuesday', '14:30:00', '15:20:00'),
    ('Tuesday', '15:30:00', '16:20:00'),
    ('Tuesday', '16:30:00', '17:20:00'),
    ('Wednesday', '08:30:00', '09:20:00'),
    ('Wednesday', '09:30:00', '10:20:00'),
    ('Wednesday', '10:30:00', '11:20:00'),
    ('Wednesday', '11:30:00', '12:20:00'),
    ('Wednesday', '12:30:00', '13:20:00'),
    ('Wednesday', '13:30:00', '14:20:00'),
    ('Wednesday', '14:30:00', '15:20:00'),
    ('Wednesday', '15:30:00', '16:20:00'),
    ('Wednesday', '16:30:00', '17:20:00'),
    ('Thursday', '08:30:00', '09:20:00'),
    ('Thursday', '09:30:00', '10:20:00'),
    ('Thursday', '10:30:00', '11:20:00'),
    ('Thursday', '11:30:00', '12:20:00'),
    ('Thursday', '12:30:00', '13:20:00'),
    ('Thursday', '13:30:00', '14:20:00'),
    ('Thursday', '14:30:00', '15:20:00'),
    ('Thursday', '15:30:00', '16:20:00'),
    ('Thursday', '16:30:00', '17:20:00'),
    ('Friday', '08:30:00', '09:20:00'),
    ('Friday', '09:30:00', '10:20:00'),
    ('Friday', '10:30:00', '11:20:00'),
    ('Friday', '11:30:00', '12:20:00'),
    ('Friday', '12:30:00', '13:20:00'),
    ('Friday', '13:30:00', '14:20:00'),
    ('Friday', '14:30:00', '15:20:00'),
    ('Friday', '15:30:00', '16:20:00'),
    ('Friday', '16:30:00', '17:20:00');
    

insert into user_type (user_type_id, user_type_name) values
	(1, 'ta'),
	(2, 'instructor'),
	(3, 'department secretary'),
	(4, 'deans office'),
	(5, 'admin');
    
insert into faculty (faculty_id, faculty_name) values
	(1, 'Engineering Faculty');
    
insert into department (department_id, department_name, department_code, faculty_id) values
	(1, 'Computer Engineering', 'CS', 1),
    (2, 'Industrial Engineering', 'IE', 1);
    
insert into semester (semester_id, year, term) values
	(1, '2024-2025', 1),
    (2, '2024-2025', 2),
    (3, '2025-2026', 1);
    
insert into general_variable (general_variable_id ,semester_id, ta_proctoring_cap_time) values
	(1, 2, 25);
    
insert into user (user_id, bilkent_id, name, surname, email, phone_number, is_active) values
	(1, '23103131', 'Emir', 'Atlas', 'emirinmaili@bilkent.edu.tr', '+905353533535', true),
    (2, '21482054', 'Cemil', 'Cemal', 'cemalcemil@bilkent.edu.tr', '+905504234254', true),
    (3, '21249583', 'Cazi', 'Cacir', 'cacircazi@bilkent.edu.tr', '+9054323945729', true),
    (4, '13583456', 'Can', 'Baron', 'baron@cs.bilkent.edu.tr', '+9024357465745', true),
    (5, '12546455', 'Serdar', 'Hayatım', 'serdar@cs.bilkent.edu.tr', '+904837556345', true),
    (6, '12234755', 'Random', 'Iehocası', 'iecihoca@ie.bilkent.edu.tr', '+90643474674', true),
    (7, '42595', 'Begüm', 'Hanım', 'begumhanım@bilkent.edu.tr', '+9049753957345', true),
    (8, '46532', 'Vegün', 'Hanım', 'vegunhanım@bilkent.edu.tr', '+9054279494793', true),
    (9, '31', 'Müh', 'Dekanlığı', 'dekanlık@bilkent.edu.tr', '+90543859642925', true),
    (10, '23139433', 'Zakir', 'Jakir', 'jacirkazir@bilkent.edu.tr', '+905382468334', true);
    
insert into login (login_id, user_id, password, user_type_id) values
	(1, 1, '$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi', 5),
    (2, 1, '$2a$10$iksFnrsDdEvoKcDfKiTvoO0pfWHXEB5..eAa.zjeQSpk.eQoQo4e2', 1),
    (3, 8, "$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi", 3),  -- secretary
    (4, 9, "$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi", 4); -- deans
    
    
insert into deans_office (user_id, faculty_id) values
	(9, 1);
    
insert into department_secretary (user_id, department_id) values
	(7, 1),
    (8, 2);
    
insert into instructor (user_id, department_id) values
	(4, 1),
    (5, 1),
    (6, 2);
    
insert into course (course_id, department_id, course_code, course_name, coordinator_id) values
	(1, 1, 311, 'Linear Algebra and Beverages in Computer Science', 4),
    (2, 1, 211, 'Data Structures', 4),
    (3, 1, 466, 'Cacirology', 5),
    (4, 2, 176, 'Course 4', 4),
    (5, 1, 234, 'Course 5', 5),
    (6, 2, 173, 'Course 6', 6),
    (7, 1, 501, 'Course 7', 6);
    
insert into ta_type (ta_type_id, type_name, ta_load) values
	(1, "regular", 2),
    (2, "scholarship", 1),
    (3, "part time", 1);
    
insert into ta (user_id, department_id, course_id, class, ta_type_id) values
	(1, 1, 1, 9, 1),
    (2, 1, 2, 5, 2),
    (3, 2, 4, 5, 3),
    (10, 1, 1, 5, 1);
    
insert into student (student_id, bilkent_id, name, surname, email, phone_number, is_active, department_id, class) values
	(1, '23103131', 'Emir', 'Atlas', 'emirinmaili@bilkent.edu.tr', '+905353533535', true, 1, 9),
    (2, '21482054', 'Cemil', 'Cemal', 'cemalcemil@bilkent.edu.tr', '+905504234254', true, 1, 5),
    (3, '21249583', 'Cazi', 'Cacir', 'cacircazi@bilkent.edu.tr', '+9054323945729', true, 2, 5),
    (4, '23139433', 'Zakir', 'Jakir', 'jacirkazir@bilkent.edu.tr', '+905382468334', true, 1, 5),
    (5, '22133545', 'Başar', 'Çubukçuoğlu', 'basarcokfenabasar@bilkent.edu.tr', '+904657362572', true, 1, 4),
    (6, '21243253', 'Serdar', 'Bozdağ', 'serdarserdar@bilkent.edu.tr', '+905349694673', true, 2, 3),
    (7, '23143153', 'Ata', 'Kılıç', 'atatata@bilkent.edu.tr', '+9048792378624', true, 1, 3),
    (8, '23472546', 'Alp Eren', 'Karslıoğlu', 'alpkarslıoglueren@bilkent.edu.tr', '+902434624626', true, 2, 4);
    
INSERT INTO class_proctoring (class_proctoring_id, course_id, event_name, creator_id, section_no, start_date, end_date, ta_count, is_complete) VALUES
	(1, 1, 'Midterm 2', 6, 5, '2025-05-06 20:00:00', '2025-05-06 23:00:00', 3, false),
	(2, 2, 'Final Exam', 4, 3, '2025-05-07 17:00:00', '2025-05-07 20:00:00', 2, false),
	(3, 2, 'Midweek', 4, 1, '2025-05-08 10:00:00', '2025-05-08 12:00:00', 2, false),
	(4, 1, 'Event 4', 5, 1, '2025-05-09 10:00:00', '2025-05-09 12:00:00', 1, false),
	(5, 3, 'Event 5', 9, 2, '2025-05-10 10:00:00', '2025-05-10 12:00:00', 2, false),
    (6, 7, 'Master Proctoring', 9, 3, '2025-05-11 8:00:00', '2025-05-11 10:00:00', 1, false);

INSERT INTO task_type (task_type_id, course_id, task_type_name, time_limit) VALUES
	(1, 4, 'Lab Grading', 2),
	(2, 1, 'Homework', 3),
	(3, 2, '31', 3),
	(4, 1, 'Task Type 4', 2),
	(5, 3, 'Task Type 5', 1);

INSERT INTO request (request_id, sender_user_id, receiver_user_id, sent_date, is_approved, response_date, description) VALUES
	(1, 7, 8, '2025-05-06 10:00:00', true, '2025-05-10 10:00:00' , 'Request 1 for swap'),
	(2, 3, 6, '2025-05-07 10:00:00', false, null, 'Request 2 for swap'),
	(3, 2, 10, '2025-05-08 10:00:00', false, null, 'Request 3 for swap'),
	(4, 6, 10, '2025-05-09 10:00:00', true, '2025-05-10 10:00:00', 'Request 4 for swap'),
	(5, 1, 3, '2025-05-10 10:00:00', false, null, 'Request 5 for workload'),
	(6, 5, 3, '2025-05-11 10:00:00', false, null, 'Request 6 for workload'),
	(7, 10, 8, '2025-05-12 10:00:00', true, '2025-05-15 10:00:00', 'Request 7 for workload'),
	(8, 6, 7, '2025-05-13 10:00:00', false, null, 'Request 8 for workload'),
	(9, 5, 3, '2025-05-14 10:00:00', true, '2025-05-15 10:00:00', 'Request 9 for instructor_ta'),
	(10, 4, 7, '2025-05-15 10:00:00', false, null, 'Request 10 for instructor_ta'),
	(11, 6, 2, '2025-05-16 10:00:00', false, null, 'Request 11 for instructor_ta'),
	(12, 3, 10, '2025-05-17 10:00:00', true, '2025-05-20 10:00:00', 'Request 12 for instructor_ta'),
	(13, 3, 9, '2025-05-18 10:00:00', false, null, 'Request 13 for availability'),
	(14, 2, 4, '2025-05-19 10:00:00', true, '2025-05-27 10:00:00', 'Request 14 for availability'),
	(15, 1, 2, '2025-05-20 10:00:00', true, '2025-05-27 10:00:00', 'Request 15 for availability'),
	(16, 1, 4, '2025-05-21 10:00:00', true, '2025-05-27 10:00:00', 'Request 16 for availability'),
	(17, 1, 6, '2025-05-22 10:00:00', true, '2025-05-27 10:00:00', 'Request 17 for from_dean'),
	(18, 1, 3, '2025-05-23 10:00:00', false, null, 'Request 18 for from_dean'),
	(19, 7, 4, '2025-05-24 10:00:00', true, '2025-05-27 10:00:00', 'Request 19 for from_dean'),
	(20, 7, 8, '2025-05-25 10:00:00', false, null, 'Request 20 for from_dean'),
	(21, 5, 9, '2025-05-06 8:00:00', true, '2025-05-10 10:00:00' , 'Request 1 for swap'),
	(22, 6, 9, '2025-05-06 10:00:00', false, '2025-05-10 10:00:00' , 'Request 1 for swap'),
	(23, 7, 9, '2025-05-06 5:00:00', false, '2025-05-10 7:00:00' , 'test');

INSERT INTO ta_swap_request (request_id, class_proctoring_id) VALUES
	(1, 2),
	(2, 4),
	(3, 1),
	(4, 5);
    
INSERT INTO ta_workload_request (request_id, task_type_id, time_spent, course_id, workload_id) VALUES
	(5, 4, 5, 4, 1),
	(6, 1, 5, 4, 2),
	(7, 1, 1, 3, 3),
	(8, 3, 4, 2, 4);
    
INSERT INTO auth_staff_proctoring_request (request_id, class_proctoring_id) VALUES
	(9, 3),
	(10, 4),
	(11, 3),
	(12, 2);
    
INSERT INTO ta_leave_request (request_id, is_urgent, leave_start_date, leave_end_date) VALUES
	(13, false, '2025-05-18 10:00:00', '2025-05-19 10:00:00'),
	(14, true, '2025-05-19 10:00:00', '2025-05-21 23:00:00'),
	(15, false, '2025-05-20 07:00:00', '2025-05-24 14:00:00'),
	(16, false, '2025-05-21 23:00:00', '2025-05-23 07:00:00');
    
INSERT INTO instructor_additional_ta_request (request_id, ta_count, is_sent_to_secretary, class_proctoring_id) VALUES
	(17, 2, true, 3),
	(18, 2, false, 2),
	(19, 2, true, 1),
	(20, 1, false, 5),
	(21, 2, false, 3),
	(22, 2, false, 3),
	(23, 3, false, 2);
    
INSERT INTO class_proctoring_ta_relation (class_proctoring_id, ta_id, is_paid, is_complete, is_open_to_swap) VALUES
	(1, 3, false, false, true),
	(2, 1, false, false, true),
	(3, 1, true, false, true),
	(3, 3, true, true, false),
	(4, 1, false, true, false),
	(4, 3, true, true, true),
	(5, 3, true, false, false),
	(5, 2, true, false, true);
    
INSERT INTO offered_course (offered_course_id, course_id, section_no, semester_id) VALUES
	(1, 1, 3, 1),
	(2, 1, 1, 2),
	(3, 1, 2, 3),
	(4, 6, 3, 1),
	(5, 6, 3, 2),
	(6, 6, 1, 3),
	(7, 5, 2, 1),
	(8, 5, 2, 2),
	(9, 5, 2, 3),
	(10, 4, 1, 1),
	(11, 4, 1, 2),
	(12, 4, 2, 3);
    
INSERT INTO course_student_relation (offered_course_id, student_id) VALUES
	(1, 8), (1, 4), (1, 2),
	(2, 5), (2, 3), (2, 1),
	(3, 2), (3, 4), (3, 6),
	(4, 3), (4, 7), (4, 5),
	(5, 2), (5, 8), (5, 7),
	(6, 3), (6, 2), (6, 6),
	(7, 8), (7, 4), (7, 1),
	(8, 6), (8, 3), (8, 2),
	(9, 4), (9, 3), (9, 8),
	(10, 3), (10, 5), (10, 7),
	(11, 4), (11, 6), (11, 3),
	(12, 5), (12, 2), (12, 1);

INSERT INTO offered_course_schedule_relation (offered_course_id, time_interval_id) VALUES
	(1, 8), (1, 4),
	(2, 28), (2, 45),
	(3, 3), (3, 43),
	(4, 18), (4, 2),
	(5, 10), (5, 21),
	(6, 31), (6, 19),
	(7, 7), (7, 24),
	(8, 22), (8, 35),
	(9, 20), (9, 13),
	(10, 40), (10, 5),
	(11, 9), (11, 34),
	(12, 12), (12, 26);
    
INSERT INTO course_instructor_relation (offered_course_id, instructor_id) VALUES
	(1, 4), (2, 4), (3, 5), (4, 4), (5, 6), (6, 6),
	(7, 6), (8, 6), (9, 5), (10, 6), (11, 4), (12, 6);
    
INSERT INTO class_proctoring_classroom (class_proctoring_id, classroom) VALUES
	(1, 'B-160'),
	(2, 'B-067'),
	(3, 'A-049'),
	(4, 'A-324'),
	(5, 'BC-232');
    
INSERT INTO notification (notification_id, receiver_id, request_id, notification_type, is_read) VALUES
	(1, 6, 2, "REQUEST", true),
    (2, 10, 3, "APPROVAL", false),
    (3, 10, 4, "ASSIGNMENT", true);
    
INSERT INTO proctoring_application (application_id, class_proctoring_id, visible_department_id, applicant_count_limit, application_type, is_complete, finish_date) VALUES
	(1, 2, 2, 10, "NOT_DEFINED", false, '2025-05-19 10:00:00'),
    (2, 4, 1, 5, "NOT_DEFINED", false, '2025-05-20 10:00:00'),
    (3, 3, 2, 15, "NOT_DEFINED", false, '2025-05-21 10:00:00'),
    (4, 5, 1, 10, "NOT_DEFINED", false, '2025-05-22 10:00:00');
    
INSERT INTO proctoring_application_ta_relation (proctoring_application_ta_relation_id, ta_id, proctoring_application_id) VALUES
	(1, 2, 1),
    (2, 1, 1),
    (3, 3, 2),
    (4, 2, 4);
    
insert into course_ta_instructor_form (form_id, course_id, instructor_id, semester_id, sent_date, min_ta_load, max_ta_load, number_of_grader, must_have_tas, preferred_tas, preferred_graders, avoided_tas, description) values
	(1, 1, 4, 1, "2025-05-06 10:00:00", 3, 5, 2, "ali 1*ali 2", "ali 5", "ali 7", "", "i want tas"),
    (2, 2, 5, 1, "2025-05-06 11:00:00", 1, 3, 4, "ali 3", "ali 5*ali 6", "", "ali 4", "more tas"),
    (3, 3, 6, 1, "2025-05-05 17:00:00", 5, 6, 0, "", "ali 2*ali 4*ali 6", "ali 8", "ali 1","tatata");
    

