package ru.th.DAO;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.th.models.Passwords;
import ru.th.models.PasswordsPK;
import ru.th.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class PasswordsDAO extends CommonDAO<Passwords,PasswordsPK> {
    public PasswordsDAO() {
        super(Passwords.class, PasswordsPK.class);
    }

    public Passwords findById(PasswordsPK id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        List<Passwords> b = session.createNativeQuery(
                        "SELECT * FROM Passwords WHERE user_role = (SELECT cast_profession(:UR)) " +
                                "and theater_id = :TI", Passwords.class)
                .setParameter("UR", id.user_to_str(id.user_role))
                .setParameter("TI", id.theater_id)
                .getResultList();
        t.commit();
        if (b.isEmpty()) {
            return null;
        }
        return b.get(0);
    }

    public void deleteById(PasswordsPK id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
        System.out.println("LOL KEK CHEB URECK");
        session.createNativeQuery(
                        "DELETE FROM Passwords WHERE user_role = (SELECT cast_profession(:UR)) " +
                                "and theater_id = :TI", Passwords.class)
                .setParameter("UR", id.user_to_str(id.user_role))
                .setParameter("TI", id.theater_id)
                .executeUpdate();
        t.commit();
    }
}
