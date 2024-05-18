BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

-- левая верхняя часть: понятия, не привязанные к одному театру

create type play_genre as enum ('comedy', 'drama', 'tragedy');

CREATE FUNCTION cast_play_genre(varchar) RETURNS play_genre AS $$
    SELECT CASE $1
        WHEN 'comedy' THEN 'comedy'::play_genre
        WHEN 'tragedy' THEN 'tragedy'::play_genre
        WHEN 'drama' THEN 'drama'::play_genre
    END;
$$ LANGUAGE SQL;

CREATE CAST (varchar AS play_genre) WITH FUNCTION cast_play_genre(varchar) AS ASSIGNMENT;


CREATE TABLE Man (
	man_id SERIAL PRIMARY KEY,
	name text,
	description text DEFAULT NULL
);


CREATE TABLE Play (
	play_id SERIAL PRIMARY KEY,
	name text NOT NULL,
	regisseur integer,
	release_year integer,
	genre play_genre NOT NULL,
	annotation text,
	CONSTRAINT FK_Play_regisseur FOREIGN KEY (regisseur)
		REFERENCES Man(man_id) ON DELETE SET NULL
);


CREATE TABLE Actor (
	play_id integer NOT NULL,
	man_id integer NOT NULL,
	actor_role text,
	CONSTRAINT FK_Actor_man_id FOREIGN KEY (man_id)
		REFERENCES Man(man_id) ON DELETE RESTRICT,
	CONSTRAINT FK_Actor_play_id FOREIGN KEY (play_id)
		REFERENCES Play(play_id) ON DELETE CASCADE
);


ALTER TABLE Actor ADD CONSTRAINT ActorPK PRIMARY KEY (play_id, man_id);


-- правая часть: данные актуальны для конкретного театра

create type hall_seat_type as enum ('stalls', 'balcony', 'amphitheater');

CREATE FUNCTION cast_hall_seat_type(varchar) RETURNS hall_seat_type AS $$
    SELECT CASE $1
        WHEN 'stalls' THEN 'stalls'::hall_seat_type
        WHEN 'balcony' THEN 'balcony'::hall_seat_type
        WHEN 'amphitheater' THEN 'amphitheater'::hall_seat_type
    END;
$$ LANGUAGE SQL;

CREATE CAST (varchar AS hall_seat_type) WITH FUNCTION cast_hall_seat_type(varchar) AS ASSIGNMENT;


CREATE TABLE Theater (
	theater_id SERIAL PRIMARY KEY,
	name text NOT NULL,
	address text,
	phone_number text
);


CREATE TABLE Halls (
	hall_id SERIAL PRIMARY KEY,
	theater_id integer NOT NULL,
	plane_size_x integer NOT NULL,
	plane_size_y integer NOT NULL,
	CONSTRAINT FK_Halls_theater_id FOREIGN KEY (theater_id)
		REFERENCES Theater(theater_id) ON DELETE CASCADE
);


CREATE TABLE HallSeats (
	seat_id SERIAL PRIMARY KEY,
	hall_id integer NOT NULL,
	seat_group integer NOT NULL,
	seat_raw integer NOT NULL,
	seat_place integer NOT NULL,
	seat_type hall_seat_type NOT NULL,
	plane_x integer NOT NULL,
	plane_y integer NOT NULL,
	CONSTRAINT FK_HallSeats_hall_id FOREIGN KEY (hall_id)
		REFERENCES Halls(hall_id) ON DELETE CASCADE
);

CREATE INDEX HallSeats_hall_id ON HallSeats(hall_id);


-- билеты (на всё той же схеме)

CREATE TABLE Performance (
	performance_id SERIAL PRIMARY KEY,
	play_id integer NOT NULL,
	hall_id integer NOT NULL,
	theater_id integer,
	start_time timestamp NOT NULL,
	end_time timestamp NOT NULL,
	places_price_array integer[] NOT NULL,
    theater_name text,
    play_name text,
	CONSTRAINT FK_Performance_hall_id FOREIGN KEY (hall_id)
		REFERENCES Halls(hall_id) ON DELETE RESTRICT,
	CONSTRAINT FK_Performance_theater_id FOREIGN KEY (theater_id)
		REFERENCES Theater(theater_id) ON DELETE RESTRICT,
	CONSTRAINT FK_Performance_play_id FOREIGN KEY (play_id)
		REFERENCES Play(play_id) ON DELETE RESTRICT
);

CREATE INDEX Performance_play_id ON Performance(play_id);
CREATE INDEX Performance_hall_id ON Performance(hall_id);
CREATE INDEX Performance_theater_id ON Performance(theater_id);
CREATE INDEX Performance_start_time ON Performance(start_time);
CREATE INDEX Performance_end_time ON Performance(end_time);


CREATE TABLE FreeSeats (
	performance_id integer,
	seat_id integer,
	price integer NOT NULL,
	seat_raw integer NOT NULL,
	seat_place integer NOT NULL,
	seat_type hall_seat_type NOT NULL,
	plane_x integer NOT NULL,
	plane_y integer NOT NULL,
	CONSTRAINT FK_FreeSeats_performance_id FOREIGN KEY (performance_id)
		REFERENCES Performance(performance_id) ON DELETE CASCADE
);

ALTER TABLE FreeSeats ADD CONSTRAINT PK_FreeSeats PRIMARY KEY (performance_id, seat_id);

-- пароли


create type profession as enum ('content_manager', 'cashier', 'expert');

CREATE FUNCTION cast_profession(varchar) RETURNS profession AS $$
    SELECT CASE $1
        WHEN 'content_manager' THEN 'content_manager'::profession
        WHEN 'cashier' THEN 'cashier'::profession
        WHEN 'expert' THEN 'expert'::profession
    END;
$$ LANGUAGE SQL;

CREATE CAST (varchar AS profession) WITH FUNCTION cast_profession(varchar) AS ASSIGNMENT;


CREATE TABLE Passwords (
	theater_id integer NOT NULL,
	user_role profession NOT NULL,
	user_login text NOT NULL,
	user_password text NOT NULL,
	CONSTRAINT FK_Theater_id FOREIGN KEY (theater_id)
		REFERENCES Theater(theater_id) ON DELETE SET NULL
);

ALTER TABLE Passwords ADD CONSTRAINT PK_Passwords PRIMARY KEY (user_login, user_password);


-- действие "добавить выступление" включает в себя также вставку большого количества строк в FreeSeats,
-- эта функция не проверяет корректности данных и должна всегда отрабатывать успешно (неуспех
-- означает ошибку в базе или при вызове)

CREATE FUNCTION create_tickets() RETURNS trigger AS $$
DECLARE
	str RECORD;
BEGIN
	FOR str IN (SELECT * FROM HallSeats WHERE hall_id = NEW.hall_id) LOOP
		INSERT INTO FreeSeats (performance_id, seat_id, price, seat_raw,
		                                      seat_place, seat_type,plane_x, plane_y) VALUES (
			NEW.performance_id, str.seat_id, NEW.places_price_array[str.seat_group + 1],
			str.seat_raw, str.seat_place, str.seat_type, str.plane_x, str.plane_y);
	END LOOP;
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE FUNCTION get_theater() RETURNS trigger AS $$
BEGIN
	NEW.theater_id = (SELECT theater_id FROM Halls WHERE hall_id = NEW.hall_id);
	NEW.theater_name = (SELECT name FROM Theater WHERE theater_id = NEW.theater_id);
	NEW.play_name = (SELECT name FROM Play WHERE play_id = NEW.play_id);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER tickets_update_trigger
	AFTER INSERT ON Performance
	FOR EACH ROW
	EXECUTE FUNCTION create_tickets();

CREATE TRIGGER get_theater_trigger
	BEFORE INSERT ON Performance
	FOR EACH ROW
	EXECUTE FUNCTION get_theater();

COMMIT;
