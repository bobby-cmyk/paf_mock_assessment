CREATE mybnb;

USE mybnb;

CREATE TABLE acc_occupancy (
	acc_id VARCHAR(10) NOT NULL,
	vacancy INT,
	
	CONSTRAINT pk_acc_occupancy_id PRIMARY KEY (acc_id)
);

CREATE TABLE reservations (
	resv_id CHAR(8) NOT NULL,
	name VARCHAR(128),
	email VARCHAR(128),
	acc_id VARCHAR(10) NOT NULL,
	arrival_date DATE,
	duration INT,
	
	CONSTRAINT pk_reservations_id PRIMARY KEY (resv_id),
	CONSTRAINT fk_acc_occupancy_id FOREIGN KEY (acc_id) REFERENCES acc_occupancy(acc_id)
);
