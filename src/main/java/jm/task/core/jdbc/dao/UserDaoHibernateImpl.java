package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import jm.task.core.jdbc.model.User;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    @Override
    public void createUsersTable() {
       try (Session session = Util.getSessionFactory().getCurrentSession()) {

           session.beginTransaction();
           session.createSQLQuery("CREATE TABLE IF NOT EXISTS User " +
                   "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                   "name VARCHAR(15) NOT NULL, lastName VARCHAR(20) NOT NULL, " +
                   "age TINYINT NOT NULL)").executeUpdate();
           session.getTransaction().commit();
       }
    }



    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try ( Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.getSessionFactory().getCurrentSession()) {

            session.beginTransaction();
            List<User> userList = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            return userList;
        }
    }

    @Override
    public void cleanUsersTable() {
       try (Session session = Util.getSessionFactory().getCurrentSession()) {

           session.beginTransaction();
           session.createQuery("delete User").executeUpdate();
           session.getTransaction().commit();
       }
    }
    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()){

            session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
