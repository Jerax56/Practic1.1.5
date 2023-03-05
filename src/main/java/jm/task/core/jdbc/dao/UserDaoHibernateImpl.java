package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static Util util;

    public UserDaoHibernateImpl(Util util) {
        this.util = util;
    }

    private static final SessionFactory sessionFactory = util.getSessionFactory();

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE `mydbtest`.`User` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(30) NOT NULL,\n" +
                "  `lastname` VARCHAR(30) NOT NULL,\n" +
                "  `age` INT(3) NULL,\n" +
                "  PRIMARY KEY (`id`));";
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS User ";
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);
            session.save(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            User user = session.get(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String HQL = "from User";
            session.beginTransaction();
            ;
            List<User> list = session.createQuery(HQL).getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.getCurrentSession()) {
            String HQL = "delete User";
            session.beginTransaction();
            session.createQuery(HQL).executeUpdate();
            session.getTransaction().commit();
        }

    }
}
