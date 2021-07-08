package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   @Transactional
   public User findOneBy(String model, int series) {
//      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
//      return query.getResultList();
//      try(Session session = sessionFactory.getCurrentSession()) {
//         System.out.println("session");
//      }
//      return query.getFirstResult();
      TypedQuery<Car> query = sessionFactory.getCurrentSession().createQuery("from Car car WHERE car.model=:model AND car.series=:series", Car.class)
              .setParameter("model", model)
              .setParameter("series", series);

      Car car = query.setMaxResults(1).getSingleResult();
      User user = car.getUser();;

//      System.out.println(user == null ? "null" : user.getId());
//      System.out.println(user.getCar());

      return user;//new User("fff", "gggg", "ggggg");//car.getUser();
   }
}
