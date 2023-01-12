package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

   private SessionFactory factory = Util.getSessionFactory();
   private Transaction transaction = null;
    @Override
    public void createUsersTable() {

       try ( Session session = factory.getCurrentSession()) {

           transaction = session.beginTransaction();
           session.createSQLQuery("CREATE TABLE IF NOT EXISTS User " +
                   "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                   "name VARCHAR(15) NOT NULL, lastName VARCHAR(20) NOT NULL, " +
                   "age TINYINT NOT NULL)").executeUpdate();
           transaction.commit();

       }
    }



    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = factory.getCurrentSession()) {

            User user = new User(name, lastName, age);
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try ( Session session = factory.getCurrentSession()) {

            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        try (Session session = factory.getCurrentSession()) {

            transaction = session.beginTransaction();
             userList = session.createQuery("from User").getResultList();
            transaction.commit();

        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
       try (Session session = factory.getCurrentSession()) {

           transaction = session.beginTransaction();
           session.createQuery("delete User").executeUpdate();
           transaction.commit();
       } catch (Exception e) {
           transaction.rollback();
           e.printStackTrace();
       }
    }
    @Override
    public void dropUsersTable() {
        try (Session session = Util.getSessionFactory().getCurrentSession()){
            transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS User").executeUpdate();
            transaction.commit();
        }
    }
}
