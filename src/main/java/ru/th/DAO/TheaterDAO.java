package ru.th.DAO;

import ru.th.models.Theater;

public class TheaterDAO extends CommonDAO<Theater,Integer> {

    public TheaterDAO() {
        super(Theater.class, Integer.class);
    }

}
