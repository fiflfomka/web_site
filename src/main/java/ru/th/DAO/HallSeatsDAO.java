package ru.th.DAO;

import ru.th.models.HallSeats;

public class HallSeatsDAO extends CommonDAO<HallSeats,Integer> {
    public HallSeatsDAO() {
        super(HallSeats.class, Integer.class);
    }
}
