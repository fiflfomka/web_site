package ru.th.DAO;

import ru.th.models.Halls;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.utils.HibernateSessionFactoryUtil;

public class HallsDAO {

    public Halls findById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Halls b = session.get(Halls.class, id);
        t.commit();
        return b;
    }

}
