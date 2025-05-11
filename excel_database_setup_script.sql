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
    sent_date_time datetime,
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

insert into user_type (user_type_id, user_type_name) values
	(1, 'ta'),
	(2, 'instructor'),
	(3, 'department secretary'),
	(4, 'deans office'),
	(5, 'admin'),
    (6, 'ta assigner');
    
insert into ta_type (ta_type_id, type_name, ta_load) values
	(1, "regular", 2),
    (2, "scholarship", 1),
    (3, "part time", 1);

insert into semester (semester_id, year, term) values
	(1, '2024-2025', 1),
    (2, '2024-2025', 2),
    (3, '2025-2026', 1);

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
    
insert into faculty (faculty_id, faculty_name) values
	(1, 'Engineering Faculty');
    
insert into department (department_id, department_name, department_code, faculty_id) values
	(1, 'Computer Engineering', 'CS', 1),
    (2, 'Industrial Engineering', 'IE', 1);
    
insert into user (user_id, bilkent_id, name, surname, email, phone_number, is_active) values
	(1, '23103131', 'dummy', 'instructor', 'dummyinstructor@bilkent.edu.tr', '+905353533535', true),
	(2, '30001', 'mühe', 'dekan', 'mühedekan@bilkent.edu.tr', '+905353533535', true), -- engineering deans office
   	(3, '40001', 'Begüm', 'Hanım', 'begümhanım@bilkent.edu.tr', '+905353533535', true), -- cs secretary
	(4, '40002', 'Vegüm', 'Hanım', 'vegümhanım@bilkent.edu.tr', '+905353533535', true); -- ie secretary
	
insert into instructor (user_id, department_id) values
	(1, 1);

insert into deans_office(user_id, faculty_id) values
	(2, 1);

insert into department_secretary(user_id, department_id) values
	(3, 1),
    (4, 2);
    
insert into login (login_id, user_id, password, user_type_id) values
	(1, 1, '$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi', 5),
    (2, 1, '$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi', 6),
	(3, 2, '$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi', 4),
    (4, 3, '$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi', 3),
	(5, 4, '$2a$10$GDsiwaSCnb3DFDsaaWGI3ORGiPg/lnTe8zfMRCt6wLJe8jB4sqjWi', 3);
    
insert into general_variable(general_variable_id, semester_id, ta_proctoring_cap_time) values
		(1, 1, 10);
