package ru.th.in_memory_db_test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.th.DAO.*;
import ru.th.models.*;
import java.sql.Timestamp;
import java.util.List;

@SpringBootTest
class FirstTest {

    @Test
    void test_Halls() {
        HallsDAO dao = new HallsDAO();
        Halls el = dao.findById(3);
        System.out.println(el.theater_id);
        System.out.println(el.plane_size_x);
        System.out.println(el.plane_size_y);

        el = new Halls();
        el.theater_id = 3;
        el.plane_size_x = 444;
        el.plane_size_y = 444;
        dao.save(el);

        Integer pk = 0;
        List<Halls> all_elem = dao.getAll();
        int i = 0;
        for (Halls element : all_elem) {
            pk = element.hall_id;
            i += 1;
        }
        System.out.println(i);
        System.out.println(i);
        System.out.println(i);

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }

        System.out.println("****************************MAN TEST SUCCESS****************************");
    }

    @Test
    void test_HallSeats() {
        HallSeatsDAO dao = new HallSeatsDAO();
        HallSeats el = dao.findById(40);
        System.out.println(el.hall_id);
        System.out.println(el.seat_group);
        System.out.println(el.seat_raw);
        System.out.println(el.seat_place);
        System.out.println(el.seat_type);

        el = new HallSeats();
        el.hall_id = 1;
        el.seat_group = 0;
        el.seat_raw = 1000;
        el.seat_place = 1000;
        el.seat_type = EnumHallSeatType.amphitheater;
        el.plane_x = 444;
        el.plane_y = 444;
        dao.save(el);

        Integer pk = 0;
        int i = 0;
        List<HallSeats> all_elem = dao.getAll();
        for (HallSeats element : all_elem) {
            pk = element.seat_id;
            i = i + 1;
        }
        System.out.println(i);
        System.out.println(pk);

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }

        System.out.println("****************************MAN TEST SUCCESS****************************");
    }

    @Test
    void test_FreeSeats() {
        FreeSeatsDAO dao = new FreeSeatsDAO();
        FreeSeatsPK pk = new FreeSeatsPK(1,1);
        FreeSeats el = dao.findById(pk);
        System.out.println(el.price);
        System.out.println(el.seat_raw);
        System.out.println(el.seat_place);
        System.out.println(el.seat_type);

        pk = new FreeSeatsPK(1,40);
        el.seat_id = 40;
        dao.save(el);

        List<FreeSeats> all_elem = dao.getAll();
        int i = 0;
        for (FreeSeats element : all_elem) {
            i = i + 1;
        }
        System.out.println(i);

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }

        System.out.println("****************************FreeSeats TEST SUCCESS****************************");
    }

    @Test
    void test_Passwords() {
        PasswordsDAO dao = new PasswordsDAO();
        PasswordsPK pk = new PasswordsPK(1, EnumProfession.cashier);
        Passwords el = dao.findById(pk);
        System.out.println(el.user_login);
        System.out.println(el.user_password);

        el = new Passwords();
        pk = new PasswordsPK(3,EnumProfession.expert);
        dao.deleteById(pk);
        el.theater_id = 3;
        el.user_role = EnumProfession.expert;
        el.user_login = "LOL";
        el.user_password = "KEK";
        dao.save(el);

        List<Passwords> all_elem = dao.getAll();
        for (Passwords element : all_elem) {
            System.out.println(element.theater_id);
            System.out.println(element.user_role);
            System.out.println(element.user_login);
            System.out.println(element.user_password);
        }

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }

        System.out.println("****************************PASSWORDS TEST SUCCESS****************************");
    }

    @Test
    void test_Performance() {
        PerformanceDAO dao = new PerformanceDAO();
        Performance el = dao.findById(2);

        el = new Performance();
        el.play_id = 2;
        el.hall_id = 8;
        el.start_time = new Timestamp(System.currentTimeMillis());
        el.end_time = new Timestamp(System.currentTimeMillis());
        el.places_price_array = List.of(1,2,3,4,5);
        dao.save(el);

        System.out.println(el.performance_id);
        System.out.println(el.play_id);
        System.out.println(el.hall_id);
        System.out.println(el.start_time);
        System.out.println(el.end_time);
        System.out.println(el.places_price_array);

        Integer pk = 20;
        List<Performance> all_elem = dao.getAll();
        for (Performance element : all_elem) {
            System.out.println(element.performance_id);
            System.out.println(element.play_id);
            System.out.println(element.start_time);
            System.out.println(element.end_time);
            pk = el.performance_id;
        }

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }
        System.out.println("****************************PERFORMANCE TEST SUCCESS****************************");
    }

    @Test
    void test_Theater() {
        TheaterDAO dao = new TheaterDAO();
        Theater el = dao.findById(2);

        System.out.println(el.name);
        System.out.println(el.address);
        System.out.println(el.phone_number);

        el = new Theater();
        el.name = "TEST DATA";
        dao.save(el);

        Integer pk = 20;
        List<Theater> all_elem = dao.getAll();
        for (Theater element : all_elem) {
            System.out.println(element.name);
            System.out.println(element.address);
            System.out.println(element.phone_number);
            pk = el.theater_id;
        }

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }
        System.out.println("****************************PERFORMANCE TEST SUCCESS****************************");
    }

    @Test
    void test_Play() {
        PlayDAO dao = new PlayDAO();
        Play el = dao.findById(1);

        System.out.println(el.play_id);
        System.out.println(el.name);
        System.out.println(el.regisseur);
        System.out.println(el.genre);

        el = new Play();
        el.regisseur = 1;
        el.release_year = 2024;
        el.name = "TEST DATA";
        el.genre = EnumPlayGenre.comedy;
        el.annotation = "TEST DATA";
        dao.save(el);

        Integer pk = 0;
        List<Play> all_elem = dao.getAll();
        for (Play element : all_elem) {
            System.out.println(element.play_id);
            System.out.println(element.name);
            System.out.println(element.regisseur);
            System.out.println(element.genre);
            pk = element.play_id;
        }
        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }
        System.out.println("****************************PLAY TEST SUCCESS****************************");
    }

    @Test
    void test_Actor() {
        ActorDAO dao = new ActorDAO();
        ActorPK pk = new ActorPK(2,8);
        Actor el = dao.findById(pk);
        System.out.println(el.actor_role);

        el = new Actor();
        pk = new ActorPK(1,11);
        el.play_id = 1;
        el.man_id = 11;
        el.actor_role = "Sel Marion";
        dao.save(el);

        List<Actor> all_elem = dao.getAll();
        for (Actor element : all_elem) {
            System.out.println(element.play_id);
            System.out.println(element.man_id);
            System.out.println(element.actor_role);
        }

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }

        System.out.println("****************************ACTOR TEST SUCCESS****************************");
    }

    @Test
    void test_Man() {
        ManDAO dao = new ManDAO();
        Man el = dao.findById(4);
        System.out.println(el.man_id);
        System.out.println(el.name);
        System.out.println(el.description);

        el = new Man();
        el.name = "Krag Wilsent";
        el.description = "horror";
        dao.save(el);

        Integer pk = 0;
        List<Man> all_elem = dao.getAll();
        for (Man element : all_elem) {
            System.out.println(element.man_id);
            System.out.println(element.name);
            System.out.println(element.description);
            pk = element.man_id;
        }

        dao.deleteById(pk);
        el = dao.findById(pk);
        if (el == null) {
            System.out.println("deleted successfully");
        }

        System.out.println("****************************MAN TEST SUCCESS****************************");
    }

}
