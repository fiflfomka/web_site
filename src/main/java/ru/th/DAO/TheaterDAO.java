package ru.th.DAO;

import ru.th.models.Theater;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.utils.HibernateSessionFactoryUtil;

public class TheaterDAO {

    public Theater findById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Theater b = session.get(Theater.class, id);
        t.commit();
        return b;
    }

}
