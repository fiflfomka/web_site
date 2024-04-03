package ru.th.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.models.Passwords;
import ru.th.models.PasswordsPK;
import ru.th.utils.HibernateSessionFactoryUtil;

public class PasswordsDAO {

    public Passwords findById(PasswordsPK id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        Passwords b = session.get(Passwords.class, id);
        t.commit();
        return b;
    }

}
