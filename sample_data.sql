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
    
insert into general_variable (semester_id, ta_proctoring_cap_time) values
	(2, 25);
    
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
	(1, 1, 'benadminim31', 5),
    (2, 1, 'simdideta', 1);
    
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
    (4, 2, 176, 'What Is IE', 6);
    
insert into ta (user_id, department_id, course_id, class) values
	(1, 1, 1, 9),
    (2, 1, 2, 5),
    (3, 2, 4, 5);
    
insert into student (student_id, bilkent_id, name, surname, email, phone_number, is_active, department_id, class) values
	(1, '23103131', 'Emir', 'Atlas', 'emirinmaili@bilkent.edu.tr', '+905353533535', true, 1, 9),
    (2, '21482054', 'Cemil', 'Cemal', 'cemalcemil@bilkent.edu.tr', '+905504234254', true, 1, 5),
    (3, '21249583', 'Cazi', 'Cacir', 'cacircazi@bilkent.edu.tr', '+9054323945729', true, 2, 5),
    (4, '23139433', 'Zakir', 'Jakir', 'jacirkazir@bilkent.edu.tr', '+905382468334', true, 1, 5),
    (5, '22133545', 'Başar', 'Çubukçuoğlu', 'basarcokfenabasar@bilkent.edu.tr', '+904657362572', true, 1, 4),
    (6, '21243253', 'Serdar', 'Bozdağ', 'serdarserdar@bilkent.edu.tr', '+905349694673', true, 2, 3),
    (7, '23143153', 'Ata', 'Kılıç', 'atatata@bilkent.edu.tr', '+9048792378624', true, 1, 3),
    (8, '23472546', 'Alp Eren', 'Karslıoğlu', 'alpkarslıoglueren@bilkent.edu.tr', '+902434624626', true, 2, 4);
    
INSERT INTO class_proctoring (class_proctoring_id, course_id, event_name, instructor_id, section_no, start_date, end_date, ta_count, is_complete) VALUES
	(1, 1, 'Midterm 2', 6, 5, '2025-05-06 20:00:00', '2025-05-06 23:00:00', 3, false),
	(2, 2, 'Final Exam', 4, 3, '2025-05-07 17:00:00', '2025-05-07 20:00:00', 2, false),
	(3, 2, 'Midweek', 4, 1, '2025-05-08 10:00:00', '2025-05-08 12:00:00', 2, false),
	(4, 1, 'Event 4', 5, 1, '2025-05-09 10:00:00', '2025-05-09 12:00:00', 1, false),
	(5, 3, 'Event 5', 6, 2, '2025-05-10 10:00:00', '2025-05-10 12:00:00', 2, false);

INSERT INTO task_type (task_type_id, course_id, task_type_name, time_limit) VALUES
	(1, 4, 'Lab Grading', 2),
	(2, 1, 'Homework', 3),
	(3, 2, '31', 3),
	(4, 1, 'Task Type 4', 2),
	(5, 3, 'Task Type 5', 1);

INSERT INTO request (request_id, sender_user_id, receiver_user_id, sent_date, is_approved, description) VALUES
	(1, 7, 8, '2025-05-06 10:00:00', true, 'Request 1 for swap'),
	(2, 3, 6, '2025-05-07 10:00:00', false, 'Request 2 for swap'),
	(3, 2, 10, '2025-05-08 10:00:00', false, 'Request 3 for swap'),
	(4, 6, 10, '2025-05-09 10:00:00', true, 'Request 4 for swap'),
	(5, 1, 3, '2025-05-10 10:00:00', false, 'Request 5 for workload'),
	(6, 5, 3, '2025-05-11 10:00:00', false, 'Request 6 for workload'),
	(7, 10, 8, '2025-05-12 10:00:00', true, 'Request 7 for workload'),
	(8, 6, 7, '2025-05-13 10:00:00', false, 'Request 8 for workload'),
	(9, 5, 3, '2025-05-14 10:00:00', true, 'Request 9 for instructor_ta'),
	(10, 4, 7, '2025-05-15 10:00:00', false, 'Request 10 for instructor_ta'),
	(11, 6, 2, '2025-05-16 10:00:00', false, 'Request 11 for instructor_ta'),
	(12, 3, 10, '2025-05-17 10:00:00', true, 'Request 12 for instructor_ta'),
	(13, 3, 9, '2025-05-18 10:00:00', false, 'Request 13 for availability'),
	(14, 2, 4, '2025-05-19 10:00:00', true, 'Request 14 for availability'),
	(15, 1, 2, '2025-05-20 10:00:00', true, 'Request 15 for availability'),
	(16, 1, 4, '2025-05-21 10:00:00', true, 'Request 16 for availability'),
	(17, 1, 6, '2025-05-22 10:00:00', true, 'Request 17 for from_dean'),
	(18, 1, 3, '2025-05-23 10:00:00', false, 'Request 18 for from_dean'),
	(19, 7, 4, '2025-05-24 10:00:00', true, 'Request 19 for from_dean'),
	(20, 7, 8, '2025-05-25 10:00:00', false, 'Request 20 for from_dean');

INSERT INTO swap_request (request_id, class_proctoring_id) VALUES
	(1, 2),
	(2, 4),
	(3, 1),
	(4, 5);
    
INSERT INTO workload_request (request_id, task_type_id, time_spent, course_id) VALUES
	(5, 4, 5, 4),
	(6, 1, 5, 4),
	(7, 1, 1, 3),
	(8, 3, 4, 2);
    
INSERT INTO instructor_ta_proctoring_request (request_id, class_proctoring_id) VALUES
	(9, 3),
	(10, 4),
	(11, 3),
	(12, 2);
    
INSERT INTO ta_availability_request (request_id, is_urgent, unavailability_start_date, unavailability_end_date) VALUES
	(13, false, '2025-05-18 10:00:00', '2025-05-19 10:00:00'),
	(14, true, '2025-05-19 10:00:00', '2025-05-21 23:00:00'),
	(15, false, '2025-05-20 07:00:00', '2025-05-24 14:00:00'),
	(16, false, '2025-05-21 23:00:00', '2025-05-23 07:00:00');
    
INSERT INTO ta_from_dean_request (request_id, ta_count, is_complete, class_proctoring_id) VALUES
	(17, 2, true, 3),
	(18, 2, false, 2),
	(19, 2, true, 1),
	(20, 1, false, 5);
    
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
	(4, 2, 3, 1),
	(5, 2, 3, 2),
	(6, 2, 1, 3),
	(7, 3, 2, 1),
	(8, 3, 2, 2),
	(9, 3, 2, 3),
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
    
INSERT INTO ta_schedule_relation (ta_id, time_interval_id) VALUES
	(1, 6), (1, 22), (1, 28),
	(2, 2), (2, 45), (2, 35),
	(3, 9), (3, 31), (3, 7);
    
INSERT INTO class_proctoring_classroom (class_proctoring_id, classroom) VALUES
	(1, 'B-160'),
	(2, 'B-067'),
	(3, 'A-049'),
	(4, 'A-324'),
	(5, 'BC-232');