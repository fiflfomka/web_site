-- полностью или частично уничтожить схему
BEGIN TRANSACTION ISOLATION LEVEL SERIALIZABLE;

drop index HallSeats_hall_id;
drop index Performance_play_id;
drop index Performance_hall_id;
drop index Performance_theater_id;
drop index Performance_start_time;
drop index Performance_end_time;

drop trigger tickets_update_trigger ON Performance;
drop trigger get_theater_trigger ON Performance;
drop function create_tickets;
drop function get_theater;

drop table Passwords cascade;
drop type profession cascade;

drop table FreeSeats;
drop table Performance;
drop table HallSeats;
drop table Halls;
drop table Theater;
drop type hall_seat_type cascade;

drop table Actor;
drop table Play;
drop table Man;

drop type play_genre cascade;

COMMIT;
