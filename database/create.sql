BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

-- левая верхняя часть: понятия, не привязанные к одному театру

CREATE SCHEMA inter_theater;
create type inter_theater.play_genre as enum ('комедия', 'драма', 'трагедия');

CREATE TABLE inter_theater.Man (
	man_id SERIAL PRIMARY KEY,
	name text,
	description text DEFAULT NULL
);

CREATE TABLE inter_theater.Play (
	play_id SERIAL PRIMARY KEY,
	name text NOT NULL,
	regisseur integer,
	release_year integer,
	genre inter_theater.play_genre  NOT NULL,
	annotation text,
	CONSTRAINT FK_Play_regisseur FOREIGN KEY (regisseur)
		REFERENCES inter_theater.Man(man_id) ON DELETE SET NULL
);

CREATE TABLE inter_theater.Actor (
	play_id integer NOT NULL,
	man_id integer NOT NULL,
	actor_role text,
	CONSTRAINT FK_Actor_man_id FOREIGN KEY (man_id)
		REFERENCES inter_theater.Man(man_id) ON DELETE CASCADE,
	CONSTRAINT FK_Actor_play_id FOREIGN KEY (play_id)
		REFERENCES inter_theater.Play(play_id) ON DELETE CASCADE
);

-- правая часть: данные актуальны для конкретного театра

CREATE SCHEMA inside_theater;
create type inside_theater.hall_seat_type as enum ('партер', 'балкон', 'амфитеатр');

CREATE TABLE inside_theater.Theater (
	theater_id SERIAL PRIMARY KEY,
	name text NOT NULL,
	address text,
	phone_number text
);

CREATE TABLE inside_theater.Halls (
	hall_id SERIAL PRIMARY KEY,
	theater_id integer NOT NULL,
	plane_size_x integer NOT NULL,
	plane_size_y integer NOT NULL,
	CONSTRAINT FK_Halls_theater_id FOREIGN KEY (theater_id)
		REFERENCES inside_theater.Theater(theater_id) ON DELETE CASCADE
);

CREATE TABLE inside_theater.HallSeats (
	seat_id SERIAL PRIMARY KEY,
	hall_id integer NOT NULL,
	seat_group integer NOT NULL,
	seat_raw integer NOT NULL,
	seat_place integer NOT NULL,
	seat_type inside_theater.hall_seat_type NOT NULL,
	plane_x integer NOT NULL,
	plane_y integer NOT NULL,
	CONSTRAINT FK_HallSeats_hall_id FOREIGN KEY (hall_id)
		REFERENCES inside_theater.Halls(hall_id) ON DELETE CASCADE
);

-- билеты (на всё той же схеме)

CREATE TABLE inside_theater.Performance (
	performance_id SERIAL PRIMARY KEY,
	play_id integer NOT NULL,
	hall_id integer NOT NULL,
	theater_id integer,
	start_time timestamp NOT NULL,
	end_time timestamp NOT NULL,
	places_price_array integer[],
	CONSTRAINT FK_Performance_hall_id FOREIGN KEY (hall_id)
		REFERENCES inside_theater.Halls(hall_id) ON DELETE RESTRICT,
	CONSTRAINT FK_Performance_theater_id FOREIGN KEY (theater_id)
		REFERENCES inside_theater.Theater(theater_id) ON DELETE RESTRICT,
	CONSTRAINT FK_Performance_play_id FOREIGN KEY (play_id)
		REFERENCES inter_theater.Play(play_id) ON DELETE RESTRICT
);

CREATE TABLE inside_theater.FreeSeats (
	performance_id integer,
	seat_id integer,
	price integer,
	seat_raw integer,
	seat_place integer,
	seat_type inside_theater.hall_seat_type,
	plane_x integer,
	plane_y integer
);

ALTER TABLE inside_theater.FreeSeats ADD CONSTRAINT PK_FreeSeats PRIMARY KEY (performance_id, seat_id);

-- пароли

CREATE SCHEMA passwd;
create type passwd.profession as enum ('content_manager', 'cashier', 'expert');

CREATE TABLE passwd.Passwords (
	theater_id integer NOT NULL,
	user_login text NOT NULL,
	user_password text NOT NULL,
	user_role passwd.profession,
	CONSTRAINT FK_Theater_id FOREIGN KEY (theater_id)
		REFERENCES inside_theater.Theater(theater_id) ON DELETE SET NULL
);

ALTER TABLE passwd.Passwords ADD CONSTRAINT FK_Passwords PRIMARY KEY (theater_id, user_role);



-- роли базы данных



-- пользователь-зритель:

CREATE ROLE client PASSWORD '123456' LOGIN;
GRANT SELECT ON inter_theater.Man TO client;
GRANT SELECT ON inter_theater.Play TO client;
GRANT SELECT ON inter_theater.Actor TO client;
GRANT SELECT ON inside_theater.Performance TO client;
GRANT SELECT ON inside_theater.Theater TO client;
GRANT SELECT ON inside_theater.Halls TO client;
GRANT SELECT ON inside_theater.FreeSeats TO client;
GRANT DELETE ON inside_theater.FreeSeats TO client;

-- кассир

CREATE ROLE cashier PASSWORD '123456' LOGIN;
GRANT SELECT ON inside_theater.FreeSeats TO cashier;
GRANT DELETE ON inside_theater.FreeSeats TO cashier;
GRANT SELECT ON inside_theater.Performance TO cashier;

-- тот кто планирует выступления в театре

CREATE ROLE content_manager PASSWORD '123456' LOGIN;
GRANT SELECT ON inside_theater.HallSeats TO content_manager;
GRANT SELECT ON inside_theater.Halls TO content_manager;
GRANT INSERT ON inside_theater.FreeSeats TO content_manager;
GRANT INSERT ON inside_theater.Performance TO content_manager;
GRANT SELECT ON inter_theater.Play TO content_manager;

-- Знаток театров, редактор, эксперт

CREATE ROLE expert PASSWORD '123456' LOGIN;
GRANT SELECT ON inter_theater.Man TO expert;
GRANT INSERT ON inter_theater.Man TO expert;
GRANT UPDATE ON inter_theater.Man TO expert;
GRANT DELETE ON inter_theater.Man TO expert;
GRANT INSERT ON inter_theater.Play TO expert;
GRANT INSERT ON inter_theater.Actor TO expert;

-- действие "добавить выступление" включает в себя также вставку большого количества строк в FreeSeats,
-- эта функция не проверяет корректности данных и должна всегда отрабатывать успешно (неуспех
-- означает ошибку в базе или при вызове)

CREATE FUNCTION inside_theater.create_tickets() RETURNS trigger AS $$
DECLARE
	str RECORD;
BEGIN
	FOR str IN (SELECT * FROM inside_theater.HallSeats WHERE HallSeats.hall_id = NEW.hall_id) LOOP
		INSERT INTO inside_theater.FreeSeats (performance_id, seat_id, price, seat_raw,
		                                      seat_place, seat_type,plane_x, plane_y) VALUES (
			NEW.performance_id, str.seat_id, NEW.places_price_array[str.seat_group],
			str.seat_raw, str.seat_place, str.seat_type, str.plane_x, str.plane_y);
	END LOOP;
	NEW.places_price_array = NULL;
	NEW.theater_id = (SELECT theater_id FROM inside_theater.Halls WHERE Halls.hall_id = NEW.hall_id);
	RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER tickets_update
	BEFORE INSERT ON inside_theater.Performance
	FOR EACH ROW
	EXECUTE FUNCTION inside_theater.create_tickets();


--CREATE FUNCTION del_ticket() RETURNS trigger AS $$
--BEGIN
--	perf := NEW.performance_id;
--	IF (SELECT seat_id FROM inside_theater.FreeSeats WHERE performance_id = perf) IS NULL THEN BEGIN
--		DELETE FROM inside_theater.Performance WHERE performance_id = perf;
--	END;
--END
--$$ LANGUAGE plpgsql;

COMMIT;
