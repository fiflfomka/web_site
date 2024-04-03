package ru.th.DAO;

import org.hibernate.Transaction;
import org.jetbrains.annotations.NotNull;
import ru.th.models.Performance;
import ru.th.models.EnumPlayGenre;
import org.hibernate.Session;
import ru.th.utils.HibernateSessionFactoryUtil;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PerformanceDAO {

    public boolean save(Performance elem) {
		if (!canBeAdded(elem)) {
			return false;
		}
		Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        session.persist(elem);
        session.getTransaction().commit();
		return true;
    }

    public void deleteById(Integer id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Performance b = session.get(Performance.class, id);
        session.delete(b);
        session.getTransaction().commit();
    }

	public List<Performance> getAll() {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		CriteriaQuery<Performance> criteriaQuery = session.getCriteriaBuilder().createQuery(Performance.class);
		criteriaQuery.from(Performance.class);
		List<Performance> lst = session.createQuery(criteriaQuery).getResultList();
		t.commit();
		return lst;
	}

	public List<Performance> getByTheaterId(Integer theater_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		List<Performance> res = session.createQuery("from Performance where theater_id = :ID", Performance.class)
			.setParameter("ID", theater_id)
			.getResultList();
	    t.commit();
        return res;
    }

    public List<Performance> getByPlayId(Integer play_id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		List<Performance> res = session.createQuery("from Performance where play_id = :ID", Performance.class)
			.setParameter("ID", play_id)
			.getResultList();
	    t.commit();
        return res;
    }

    public List<Performance> getByText(@NotNull String pattern) {
		if (pattern.isEmpty()) {
			return new ArrayList<>();
		}
		Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		List<Performance> res_1 = session.createNativeQuery(
				"with TMP_1 as (" +
						"SELECT play_id FROM Play WHERE name LIKE :PATTERN" +
						") SELECT * FROM Performance NATURAL JOIN TMP_1",
				Performance.class)
				    .setParameter("PATTERN", "%" + pattern + "%")
				    .getResultList();
		List<Performance> res_2 = session.createNativeQuery(
				"with TMP_1 as (" +
						"SELECT theater_id FROM Theater WHERE name LIKE :PATTERN" +
						") SELECT * FROM Performance NATURAL JOIN TMP_1",
				Performance.class)
				    .setParameter("PATTERN", "%" + pattern + "%")
				    .getResultList();
		t.commit();
		res_1.removeAll(res_2);
		res_1.addAll(res_2);
        return res_1;
    }

    public List<Performance> getWithParameters(EnumPlayGenre play_genre, Timestamp lo, Timestamp hi ) {
		Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
        Transaction t = session.beginTransaction();
		String query = "select * from Performance where start_time between :LO and :HI";
		if (play_genre != null) {
	    	query = "WITH TMP_1 AS (" +
					"SELECT play_id FROM Play where genre = cast_play_genre ('" + play_genre + "')" +
					") SELECT * FROM Performance natural join TMP_1 where start_time between :LO and :HI";
		}
		if (lo == null) {
	    	lo = new Timestamp(0,0,1,0,0,0,0);
		}
		if (hi == null) {
			hi = new Timestamp(200,0,1,0,0,0,0);
		}
        List<Performance> res = session.createNativeQuery(query,
			    Performance.class)
				.setParameter("LO", lo)
				.setParameter("HI", hi)
				.getResultList();
		t.commit();
        return res;
    }
    
    // use this method to check if this Performance could be added in this hall at this time
    // start_time, end_time and hall_id is nit null
    private @NotNull Boolean canBeAdded(@NotNull Performance p) {
		if (p.getHall_id() == null) {
		    throw new NullPointerException("Performance.canBeAdded(): hall_id is null");
		}
		if (p.getStart_time() == null) {
			throw new NullPointerException("Performance.canBeAdded(): start_time is null");
		}
		if (p.getEnd_time() == null) {
			throw new NullPointerException("Performance.canBeAdded(): end_time is null");
		}
		Session session = HibernateSessionFactoryUtil.getSessionFactory().getCurrentSession();
		Transaction t = session.beginTransaction();
		List<Performance> lst = session.createNativeQuery(
			    "SELECT * from Performance WHERE hall_id = :HALL " +
			    "and (" +
			    "(:LO between start_time and end_time) or (:HI between start_time and end_time) or " +
			    "(start_time between :LO and :HI) or (end_time between :LO and :HI)" +
			    ")", Performance.class)
				.setParameter("LO", p.getStart_time())
				.setParameter("HI", p.getEnd_time())
				.setParameter("HALL", p.getHall_id())
				.getResultList();
		t.commit();
        return lst.isEmpty();
    }

}
