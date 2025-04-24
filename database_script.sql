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
    foreign key (course_id) references course(course_id)
);


create table class_proctoring(
	class_proctoring_id int primary key auto_increment,
    course_id int not null,
    event_name varchar(100),
    instructor_id int not null,
    section_no int,
	start_date datetime,
    end_date datetime,
    ta_count int,
    is_complete bool,
    foreign key (course_id) references course(course_id),
    foreign key (instructor_id) references instructor(user_id)
);

create index date_time_idx on class_proctoring(start_date, end_date);


create table class_proctoring_classroom(
	class_proctoring_id int,
    classroom varchar(10),
    primary key (class_proctoring_id, classroom),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
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


create table request(
	request_id int primary key auto_increment,
    sender_user_id int not null,
    receiver_user_id int not null,
    sent_date datetime not null,
    is_approved bool,
    approved_date datetime,
    description varchar(500),
    foreign key (sender_user_id) references user(user_id),
    foreign key (receiver_user_id) references user(user_id)
);

create index sent_date_idx on request(sent_date);
create index approved_date_idx on request(approved_date);


create table swap_request(
	request_id int primary key,
	class_proctoring_id int,
    foreign key (request_id) references request(request_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);


create table workload_request(
	request_id int primary key,
    task_type_id int,
    time_spent int not null,
    course_id int,
    foreign key (request_id) references request(request_id),
    foreign key (task_type_id) references task_type(task_type_id),
    foreign key (course_id) references course(course_id)
);


create table instructor_ta_proctoring_request(
	request_id int primary key,
    class_proctoring_id int not null,
    foreign key (request_id) references request(request_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);


create table ta_availability_request(
	request_id int primary key,
    is_urgent bool,
    unavailability_start_date datetime not null,
    unavailability_end_date datetime not null,
    foreign key (request_id) references request(request_id)
);

create index start_date_end_date_idx on ta_availability_request(unavailability_start_date, unavailability_end_date);


create table ta_from_dean_request(
	request_id int primary key,
    ta_count int,
    is_complete bool,
    class_proctoring_id int not null,
    foreign key (request_id) references request(request_id),
    foreign key (class_proctoring_id) references class_proctoring(class_proctoring_id)
);