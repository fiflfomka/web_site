package ru.th.DAO;

import ru.th.models.FreeSeats;
import ru.th.models.FreeSeatsPK;

public class FreeSeatsDAO extends CommonDAO<FreeSeats,FreeSeatsPK> {
    public FreeSeatsDAO() {
        super(FreeSeats.class, FreeSeatsPK.class);
    }
}
