package ru.th.DAO;

import ru.th.models.Play;

public class PlayDAO extends CommonDAO<Play,Integer> {

    public PlayDAO() {
        super(Play.class, Integer.class);
    }

}
