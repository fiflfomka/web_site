package ru.th.DAO;

import org.hibernate.Transaction;
import ru.th.models.HallSeats;
import org.hibernate.Session;
import ru.th.utils.HibernateSessionFactoryUtil;
import java.util.List;


public class HallSeatsDAO {

    public List<HallSeats> findByHall(Integer hall_id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<HallSeats> res = session.createNativeQuery("Select * from HallSeats where hall_id = :ID", HallSeats.class)
			.setParameter("ID", hall_id)
			.getResultList();
    	t.commit();
        return res;
    }

}
