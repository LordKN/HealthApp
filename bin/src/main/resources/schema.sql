CREATE TABLE coach (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100),
	birth_date DATE,
	sex VARCHAR(10),
	address VARCHAR(255),
	phone_no VARCHAR(50),
	email VARCHAR(100),
	photo_path VARCHAR(255),
	emergency_contact_name VARCHAR(100),
	emergency_contact_phone VARCHAR(50),
	emergency_contact_relationship VARCHAR(50),
	specialty VARCHAR(50),
	years_of_experience INT,
	open_for_new_client BOOLEAN,
	client_count INT,
	workplace VARCHAR(255),
	description TEXT
);

CREATE TABLE client (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100),
	birth_date DATE,
	sex VARCHAR(10),
	address VARCHAR(255),
	phone_no VARCHAR(50),
	email VARCHAR(100),
	photo_path VARCHAR(255),
	emergency_contact_name VARCHAR(100),
	emergency_contact_phone VARCHAR(50),
	emergency_contact_relationship VARCHAR(50),
	activity_level VARCHAR(20),
	workout_preference VARCHAR(255),
	barriers TEXT,
	fitness_goal VARCHAR(20),
	height DOUBLE,
	weight DOUBLE,
	body_fat DOUBLE,
	availability VARCHAR(255),
	sleep_pattern VARCHAR(50),
	stress_level VARCHAR(20),
	waiver_accepted BOOLEAN
);

CREATE TABLE certificate (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	coach_id BIGINT,
	cer_name VARCHAR(255),
	iss_org VARCHAR(255),
	description TEXT,
	FOREIGN KEY (coach_id) REFERENCES coach(id) ON DELETE CASCADE
);

CREATE TABLE medical_history (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	client_id BIGINT,
	note TEXT,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
)

CREATE TABLE workout (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL,
	`desc` TEXT
);

CREATE TABLE exercise (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR (255) NOT NULL,
	description TEXT,
	muscle_group VARCHAR(50)
);

CREATE TABLE workout_exercise (
	workout_id BIGINT NOT NULL,
	exercise_id BIGINT NOT NULL,
	PRIMARY KEY (workout_id, exercise_id),
	FOREIGN KEY (workout_id) REFERENCES workout(id) ON DELETE CASCADE,
	FOREIGN KEY (exercise_id) REFERENCES exercise(id) ON DELETE CASCADE
);
