package ru.th.DAO;

import ru.th.models.Halls;

public class HallsDAO extends CommonDAO<Halls,Integer> {
    public HallsDAO() {
        super(Halls.class, Integer.class);
    }
}
