-- полностью или частично уничтожить схему

drop trigger tickets_update_trigger ON Performance;
drop trigger get_theater_trigger ON Performance;
drop function create_tickets;
drop function get_theater;


drop table Passwords;
drop type profession;

drop table FreeSeats;
drop table Performance;
drop table HallSeats;
drop table Halls;
drop table Theater;
drop type hall_seat_type;

drop table Actor;
drop table Play;
drop table Man;

drop type play_genre cascade;
drop type hall_seat_type cascade;
drop type profession cascade;
