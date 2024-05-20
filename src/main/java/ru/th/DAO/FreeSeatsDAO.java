package ru.th.DAO;

import org.hibernate.Transaction;
import ru.th.models.FreeSeats;
import ru.th.models.FreeSeatsPK;
import org.hibernate.Session;
import ru.th.utils.HibernateSessionFactoryUtil;
import java.util.List;

public class FreeSeatsDAO {
    
    public boolean deleteById(FreeSeatsPK id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        FreeSeats b = session.get(FreeSeats.class, id);
        if (b == null) {
            session.getTransaction().rollback();
            return false;
        }
        try {
            session.delete(b);
        } catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        session.getTransaction().commit();
        return true;
    }

    public boolean deleteMass(List<FreeSeatsPK> lst) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for (FreeSeatsPK id : lst) {
            FreeSeats b = session.get(FreeSeats.class, id);
            if (b == null) {
                session.getTransaction().rollback();
                return false;
            }
            try {
                session.delete(b);
            } catch (Exception e) {
                session.getTransaction().rollback();
                return false;
            }
        }
        session.getTransaction().commit();
        return true;
    }

    public FreeSeats findById(FreeSeatsPK id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        FreeSeats b = session.get(FreeSeats.class, id);
        session.getTransaction().commit();
        return b;
    }

    public List<FreeSeats> findByPerformance(Integer performance_id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<FreeSeats> res = session.createQuery("from FreeSeats where performance_id = :ID", FreeSeats.class)
			.setParameter("ID", performance_id)
			.getResultList();
	    t.commit();
        return res;
    }

}
