package ru.th.DAO;

import ru.th.models.Performance;

public class PerformanceDAO extends CommonDAO<Performance,Integer> {
    public PerformanceDAO() {
        super(Performance.class, Integer.class);
    }
}
