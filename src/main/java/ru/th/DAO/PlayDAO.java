package ru.th.DAO;

import ru.th.models.Play;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.utils.HibernateSessionFactoryUtil;


public class PlayDAO {
    
    public Play findById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Play b = session.get(Play.class, id);
        t.commit();
        return b;
    }

    public void saveOrUpdate(Play elem) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(elem);
        session.getTransaction().commit();
    }

}
