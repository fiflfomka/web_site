package ru.th.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.utils.HibernateSessionFactoryUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public abstract class CommonDAO<BaseClass, IdClass extends Serializable> {
    public Class<BaseClass> base_class;
    public Class<IdClass> id_class;
    
    public CommonDAO(Class<BaseClass> BaseClass, Class<IdClass> IdClass) {
        base_class = BaseClass;
        id_class = IdClass;
    }

    public BaseClass findById(IdClass id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        BaseClass b = session.get(base_class, id);
        t.commit();
        return b;
    }

    public List<BaseClass> getAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        CriteriaQuery<BaseClass> criteriaQuery = session.getCriteriaBuilder().createQuery(base_class);
        criteriaQuery.from(base_class);
        List<BaseClass> lst = session.createQuery(criteriaQuery).getResultList();
        t.commit();
        return lst;
    }

    public void save(BaseClass elem) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        //session.saveOrUpdate(elem);
        session.persist(elem);
        session.getTransaction().commit();
    }

    public void deleteById(IdClass id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BaseClass b = session.get(base_class, id);
        session.delete(b);
        session.getTransaction().commit();
    }

}
