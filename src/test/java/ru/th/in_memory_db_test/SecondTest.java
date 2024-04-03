package ru.th.in_memory_db_test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.th.DAO.*;
import ru.th.models.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
class SecondTest {

    @Test
    void test_Halls() {
        HallsDAO dao = new HallsDAO();
        Halls el = dao.findById(3);
        assert(el.getTheater_id() == 2);
        el = dao.findById(100);
        assert(el == null);
    }

    @Test
    void test_HallSeats() {
        HallSeatsDAO dao = new HallSeatsDAO();
        List<HallSeats> lst = dao.findByHall(1);
        assert(lst.size() == 29);
        lst = dao.findByHall(100);
        assert(lst.isEmpty());
    }

    @Test
    void test_FreeSeats() {
        FreeSeatsDAO dao = new FreeSeatsDAO();
        List<FreeSeats> lst = dao.findByPerformance(2);
        assert(lst.size() == 29);

        dao.deleteById(new FreeSeatsPK(2,30));
        lst = dao.findByPerformance(2);
        assert(lst.size() == 28);

        lst = dao.findByPerformance(50);
        assert(lst.isEmpty());
    }

    @Test
    void test_Passwords() {
        PasswordsDAO dao = new PasswordsDAO();
        PasswordsPK pk = new PasswordsPK("login_11", "passwd_11");
        Passwords el = dao.findById(pk);
        assert(el.getTheater_id() == 1 && el.getUser_role() == EnumProfession.content_manager);
        pk = new PasswordsPK("login_12", "passwd_12");
        el = dao.findById(pk);
        assert(el.getTheater_id() == 1 && el.getUser_role() == EnumProfession.cashier);
        pk = new PasswordsPK("login", "passwd");
        el = dao.findById(pk);
        assert(el.getTheater_id() == 2 && el.getUser_role() == EnumProfession.expert);
        pk = new PasswordsPK("NOT EXIST", "NOT EXIST");
        el = dao.findById(pk);
        assert(el == null);
    }

    @Test
    void test_Performance() {
        PerformanceDAO dao = new PerformanceDAO();
        List<Performance> lst = dao.getAll();
        assert(lst.size() == 10);
        lst = dao.getByTheaterId(1);
        assert(lst.size() == 4);
        lst = dao.getByPlayId(3);
        assert(lst.size() == 3);
        lst = dao.getByText("");
        assert(lst.isEmpty());
        lst = dao.getByText("й");
        assert(lst.size() == 8);

        Performance el = new Performance();
        el.setPlay_id(1);
        el.setPlaces_price_array(new ArrayList<>());
        el.getPlaces_price_array().add(1);
        el.getPlaces_price_array().add(2);
        el.getPlaces_price_array().add(3);
        el.getPlaces_price_array().add(4);
        el.getPlaces_price_array().add(5);

        boolean b = false;
        try {
            dao.save(el);
        } catch (NullPointerException e) {
            b = true;
        }
        assert(b);

        el.setHall_id(1);
        b = false;
        try {
            dao.save(el);
        } catch (NullPointerException e) {
            b = true;
        }
        assert(b);

        el.setStart_time(new Timestamp(124, 2, 2, 11, 0, 0, 0));
        b = false;
        try {
            dao.save(el);
        } catch (NullPointerException e) {
            b = true;
        }
        assert(b);

        el.setEnd_time(new Timestamp(124, 2, 2, 18, 0, 0, 0));
        b = dao.save(el);
        assert(!b);

        el.setStart_time(new Timestamp(124, 2, 2, 15, 0, 0, 0));
        b = dao.save(el);
        assert(b);
        lst = dao.getAll();
        assert(lst.size() == 11);

        dao.deleteById(el.getPerformance_id());
        lst = dao.getAll();
        assert(lst.size() == 10);

        Timestamp lo = new Timestamp(124, 2, 2, 8, 0, 0, 0);
        Timestamp hi = new Timestamp(124, 2, 2, 14, 0, 0, 0);
        lst = dao.getWithParameters(null, lo, hi);
        assert(lst.size() == 3);

        lo = new Timestamp(124, 2, 2, 8, 0, 0, 0);
        hi = new Timestamp(124, 2, 2, 9, 0, 0, 0);
        lst = dao.getWithParameters(null, lo, hi);
        assert(lst.isEmpty());

        lst = dao.getWithParameters(EnumPlayGenre.comedy, null, null);
        assert(lst.size() == 4);
        lst = dao.getWithParameters(EnumPlayGenre.tragedy, null, null);
        assert(lst.size() == 3);
    }

    @Test
    void test_Theater() {
        TheaterDAO dao = new TheaterDAO();
        Theater el = dao.findById(1);
        assert(Objects.equals(el.getName(), "Театр на Таганке"));
        el = dao.findById(5);
        assert(el == null);
    }

    @Test
    void test_Play() {
        PlayDAO dao = new PlayDAO();
        Play el = dao.findById(1);
        assert(Objects.equals(el.getName(), "Вишневый сад"));
        assert(el.getGenre() == EnumPlayGenre.comedy);
        el = dao.findById(2);
        assert(el.getGenre() == EnumPlayGenre.drama);
        el = dao.findById(3);
        assert(el.getGenre() == EnumPlayGenre.tragedy);

        el = new Play();
        el.setGenre(EnumPlayGenre.comedy);
        el.setName("TEST DATA");
        dao.saveOrUpdate(el);

        el = dao.findById(4);
        assert(Objects.equals(el.getName(), "TEST DATA"));
        el.setName("TEST DATA 2");
        dao.saveOrUpdate(el);
        el = dao.findById(4);
        assert(Objects.equals(el.getName(), "TEST DATA 2"));
    }

    @Test
    void test_Actor() {
        ActorDAO dao = new ActorDAO();
        List<Actor> lst = dao.findByPlay(3);
        assert(lst.size() == 3);

        lst.clear();
        lst.add(new Actor(3, 1, "TEST DATA"));
        lst.add(new Actor(3, 2, "TEST DATA"));
        lst.add(new Actor(3, 3, "TEST DATA"));

        dao.save(lst);
        lst = dao.findByPlay(3);
        assert(lst.size() == 6);
    }

    @Test
    void test_Man() {
        ManDAO dao = new ManDAO();
        List<Man> lst = dao.findByName("ил");
        System.out.println(lst.size());
	    assert(lst.size() == 3);

        Man el = new Man();
        el.setName("TEST DATA");
        el.setDescription("TEST DATA");
        dao.saveOrUpdate(el);
        lst = dao.findByName("TEST DATA");
        assert(lst.size() == 1);

        el.setName("TD2");
        dao.saveOrUpdate(el);
        lst = dao.findByName("TEST DATA");
        assert(lst.isEmpty());
        lst = dao.findByName("TD2");
        assert(lst.size() == 1);

        dao.deleteById(el.getMan_id());
        lst = dao.findByName("TD2");
        assert(lst.isEmpty());
    }

}
