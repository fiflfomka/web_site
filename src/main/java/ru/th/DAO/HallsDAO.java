package ru.th.DAO;

import ru.th.models.Halls;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.models.Play;
import ru.th.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class HallsDAO {

    public Halls findById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Halls b = session.get(Halls.class, id);
        t.commit();
        return b;
    }

    public List<Halls> getAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<Halls> res = session.createQuery("from Halls", Halls.class)
                .getResultList();
        t.commit();
        return res;
    }

}
