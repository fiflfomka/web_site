package ru.th.DAO;

import org.hibernate.Transaction;
import ru.th.models.Man;
import org.hibernate.Session;
import ru.th.utils.HibernateSessionFactoryUtil;
import java.util.List;

public class ManDAO {

    public void saveOrUpdate(Man elem) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.saveOrUpdate(elem);
        session.getTransaction().commit();
    }

    public void deleteById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Man b = session.get(Man.class, id);
        session.delete(b);
        session.getTransaction().commit();
    }

    public Man findById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Man b = session.get(Man.class, id);
        session.getTransaction().commit();
        return b;
    }

    public List<Man> findByName(String name){
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<Man> res = session.createQuery("from Man where name LIKE :ID", Man.class)
			.setParameter("ID", "%" + name + "%")
			.getResultList();
    	t.commit();
        return res;
    }

}
