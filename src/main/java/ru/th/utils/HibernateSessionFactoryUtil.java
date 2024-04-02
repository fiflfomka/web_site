package ru.th.utils;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.th.models.*;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Man.class)
                    .addAnnotatedClass(Play.class)
                    .addAnnotatedClass(Actor.class)
                    .addAnnotatedClass(Theater.class)
                    .addAnnotatedClass(Halls.class)
                    .addAnnotatedClass(HallSeats.class)
                    .addAnnotatedClass(Passwords.class)
                    .addAnnotatedClass(Performance.class)
                    .addAnnotatedClass(FreeSeats.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
        return sessionFactory;
    }
    
}
