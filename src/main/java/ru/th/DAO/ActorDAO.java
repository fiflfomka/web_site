package ru.th.DAO;

import org.hibernate.Transaction;
import ru.th.models.Actor;
import org.hibernate.Session;
import ru.th.utils.HibernateSessionFactoryUtil;
import java.util.List;

public class ActorDAO {
    
    public void save(List<Actor> lst) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        for (Actor elem : lst) {
            session.persist(elem);
        }
        session.getTransaction().commit();
    }

    public List<Actor> findByPlay(Integer play_id){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<Actor> res = session.createQuery("from Actor where play_id = :ID", Actor.class)
			.setParameter("ID", play_id)
			.getResultList();
	    t.commit();
        return res;
    }

}
