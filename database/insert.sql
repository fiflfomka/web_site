BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

INSERT INTO Man VALUES
(DEFAULT,'Антон Павлович Чехов','Известный русский писатель'),
(DEFAULT,'Горький Максим','Известный русский писатель'),
(DEFAULT,'Шекспир Уильям','Известный, не русский писатель'),
(DEFAULT,'Александрова Александра Александровна','Единственная женщина-актёр'),
(DEFAULT,'Абрамов Абрам Абрамович','Закончил МГУ и в целом большой талант'),
(DEFAULT,'Борисов Борис Борисович',DEFAULT),
(DEFAULT,'Владимиров Владимир Владимирович',DEFAULT),
(DEFAULT,'Григорьев Григорий Григорьевич',DEFAULT),
(DEFAULT,'Данилов Данила Данилович',DEFAULT),
(DEFAULT,'Евгеньев Евгений Евгениевич',DEFAULT),
(DEFAULT,'Кириллов Кирилл Кириллович',DEFAULT),
(DEFAULT,'Николаев Николай Николаевич','1950-2023');


INSERT INTO Play VALUES
(DEFAULT,'Вишневый сад',1,1903,'comedy','В пьесе описывается кризис дворянства в конце XX века.'),
(DEFAULT,'На дне',2,1902,'drama','Нужна ли надежда, когда неоткуда ждать изменений к лучшему?'),
(DEFAULT,'Ромео и Джульетта',3,1595,'tragedy','Любовь не ведает преград.');

INSERT INTO Actor VALUES
(1,4,'Раневская'),
(1,5,'Гаев'),
(1,6,'Симеонов-Пищик'),
(1,7,'Фирс'),
(2,8,'Клещ'),
(2,4,'Анна'),
(2,9,'Васька Пепел'),
(2,10,'Лука'),
(2,11,'Баронн'),
(3,4,'Джульета'),
(3,5,'Ромео'),
(3,7,'родственник Ромео');

INSERT INTO Theater VALUES
(DEFAULT,'Театр на Таганке','Земляной вал 76','8-800-555-35-35'),
(DEFAULT,'Большой театр города М','Театральная площадь, 1','8-800-555-35-35'),
(DEFAULT,'Малый театр города М','Театральный проезд, 1','8-800-555-35-35'),
(DEFAULT,'Театр юного зрителя','улица Свободы, 23','8-800-555-35-35');

INSERT INTO Halls VALUES
(DEFAULT,1,2000,1200),
(DEFAULT,1,2000,1200),
(DEFAULT,2,2000,1200),
(DEFAULT,2,2000,1200),
(DEFAULT,3,2000,1200),
(DEFAULT,3,2000,1200),
(DEFAULT,4,2000,1200),
(DEFAULT,4,2000,1200);

INSERT INTO HallSeats VALUES
(DEFAULT,1,1,1,1,'stalls',100,400),
(DEFAULT,1,1,1,2,'stalls',100,600),
(DEFAULT,1,0,1,3,'stalls',100,800),
(DEFAULT,1,0,1,4,'stalls',100,1000),
(DEFAULT,1,0,1,5,'stalls',100,1200),
(DEFAULT,1,1,1,6,'stalls',100,1400),
(DEFAULT,1,1,1,7,'stalls',100,1600),
(DEFAULT,1,2,2,1,'stalls',400,400),
(DEFAULT,1,2,2,2,'stalls',400,600),
(DEFAULT,1,1,2,3,'stalls',400,800),
(DEFAULT,1,1,2,4,'stalls',400,1000),
(DEFAULT,1,1,2,5,'stalls',400,1200),
(DEFAULT,1,2,2,6,'stalls',400,1400),
(DEFAULT,1,2,2,7,'stalls',400,1600),
(DEFAULT,1,3,1,1,'amphitheater',700,400),
(DEFAULT,1,3,1,2,'amphitheater',700,600),
(DEFAULT,1,2,1,3,'amphitheater',700,800),
(DEFAULT,1,2,1,4,'amphitheater',700,1000),
(DEFAULT,1,2,1,5,'amphitheater',700,1200),
(DEFAULT,1,3,1,6,'amphitheater',700,1400),
(DEFAULT,1,3,1,7,'amphitheater',700,1600),
(DEFAULT,1,4,1,1,'balcony',1100,300),
(DEFAULT,1,4,1,2,'balcony',1100,500),
(DEFAULT,1,4,1,3,'balcony',1100,700),
(DEFAULT,1,4,1,4,'balcony',1100,900),
(DEFAULT,1,4,1,5,'balcony',1100,1100),
(DEFAULT,1,4,1,6,'balcony',1100,1300),
(DEFAULT,1,4,1,7,'balcony',1100,1500),
(DEFAULT,1,4,1,8,'balcony',1100,1700);

-- Вообще-то залы все разные, но я создам все одинаковые, тк проектировать залы - это сложно и долго

CREATE FUNCTION multiply_halls() RETURNS void AS $$
DECLARE
	str RECORD;
	i integer;
BEGIN
	i := 2;
	WHILE i < 9 LOOP
		FOR str IN (SELECT * FROM HallSeats WHERE hall_id = 1) LOOP 
			INSERT INTO HallSeats VALUES (DEFAULT, i, str.seat_group, str.seat_raw, str.seat_place, str.seat_type, str.plane_x, str.plane_y);
		END LOOP;
		i := i + 1;
	END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT multiply_halls();

drop function multiply_halls;


INSERT INTO Performance VALUES
(DEFAULT,1,1,NULL,'2024-03-02 10:00:00','2024-03-02 12:40:00','{6000,5500,4500,3800,3000}'),
(DEFAULT,2,2,NULL,'2024-03-02 10:00:00','2024-03-02 12:40:00','{7000,6500,4500,3800,2000}'),
(DEFAULT,3,3,NULL,'2024-03-02 10:00:00','2024-03-02 12:40:00','{5000,5000,4000,3800,2500}'),
(DEFAULT,1,4,NULL,'2024-03-03 10:00:00','2024-03-02 12:40:00','{7000,6500,4500,3800,2000}'),
(DEFAULT,2,5,NULL,'2024-03-03 10:00:00','2024-03-02 12:40:00','{6000,5500,4500,3800,3000}'),
(DEFAULT,3,6,NULL,'2024-03-03 10:00:00','2024-03-02 12:40:00','{6000,5500,4500,3800,3000}'),
(DEFAULT,1,1,NULL,'2024-03-04 10:00:00','2024-03-02 12:40:00','{6000,5500,4500,3800,3000}'),
(DEFAULT,2,2,NULL,'2024-03-04 10:00:00','2024-03-02 12:40:00','{4500,4000,3200,3000,2200}'),
(DEFAULT,3,3,NULL,'2024-03-04 10:00:00','2024-03-02 12:40:00','{4500,4000,3200,3000,2200}'),
(DEFAULT,1,4,NULL,'2024-03-05 10:00:00','2024-03-02 12:40:00','{7000,6500,4500,3800,2000}');

INSERT INTO Passwords VALUES
(1,'content_manager','login_11','passwd_11'),
(1,'cashier','login_12','passwd_12'),
(2,'content_manager','login_21','passwd_21'),
(2,'cashier','login_22','passwd_22'),
(3,'content_manager','login_31','passwd_31'),
(3,'cashier','login_32','passwd_32'),
(4,'content_manager','login_41','passwd_41'),
(4,'cashier','login_42','passwd_42'),
(2,'expert','login','passwd');

-- прицепить межтеатрального эксперта к театру - это неочень надёжно, но к большому театру нормально

COMMIT;
