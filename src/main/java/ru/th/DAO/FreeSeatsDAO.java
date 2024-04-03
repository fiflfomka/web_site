package ru.th.DAO;

import org.hibernate.Transaction;
import ru.th.models.FreeSeats;
import ru.th.models.FreeSeatsPK;
import org.hibernate.Session;
import ru.th.utils.HibernateSessionFactoryUtil;
import java.util.List;

public class FreeSeatsDAO {
    
    public void deleteById(FreeSeatsPK id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        FreeSeats b = session.get(FreeSeats.class, id);
        session.delete(b);
        session.getTransaction().commit();
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
