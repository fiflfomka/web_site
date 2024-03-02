-- полностью или частично уничтожить схему

drop trigger tickets_update ON inside_theater.Performance;
drop function inside_theater.create_tickets;

REVOKE ALL PRIVILEGES ON inter_theater.Man FROM expert;
REVOKE ALL PRIVILEGES ON inter_theater.Play FROM expert;
REVOKE ALL PRIVILEGES ON inter_theater.Actor FROM expert;
DROP ROLE expert;

REVOKE ALL PRIVILEGES ON inter_theater.Man FROM client;
REVOKE ALL PRIVILEGES ON inter_theater.Play FROM client;
REVOKE ALL PRIVILEGES ON inter_theater.Actor FROM client;
REVOKE ALL PRIVILEGES ON inside_theater.Performance FROM client;
REVOKE ALL PRIVILEGES ON inside_theater.Theater FROM client;
REVOKE ALL PRIVILEGES ON inside_theater.Halls FROM client;
REVOKE ALL PRIVILEGES ON inside_theater.FreeSeats FROM client;
DROP ROLE client;

REVOKE ALL PRIVILEGES ON inside_theater.FreeSeats FROM cashier;
REVOKE ALL PRIVILEGES ON inside_theater.Performance FROM cashier;
REVOKE ALL PRIVILEGES ON inside_theater.FreePlacesCnt FROM cashier;
DROP ROLE cashier;

REVOKE ALL PRIVILEGES ON inside_theater.HallSeats FROM content_manager;
REVOKE ALL PRIVILEGES ON inside_theater.Halls FROM content_manager;
REVOKE ALL PRIVILEGES ON inside_theater.FreeSeats FROM content_manager;
REVOKE ALL PRIVILEGES ON inside_theater.Performance FROM content_manager;
REVOKE ALL PRIVILEGES ON inter_theater.Play FROM content_manager;
DROP ROLE content_manager;

drop table passwd.Passwords;
drop type passwd.profession;

drop table inside_theater.FreeSeats;
drop table inside_theater.Performance;
drop table inside_theater.HallSeats;
drop table inside_theater.Halls;
drop table inside_theater.Theater;
drop type inside_theater.hall_seat_type;

drop table inter_theater.Actor;
drop table inter_theater.Play;
drop table inter_theater.Man;
drop type inter_theater.play_genre;

drop schema passwd cascade;
drop schema inside_theater cascade;
drop schema inter_theater cascade;
