package ru.th.DAO;

import ru.th.models.Man;

public class ManDAO extends CommonDAO<Man,Integer> {

    public ManDAO() {
        super(Man.class, Integer.class);
    }

}
