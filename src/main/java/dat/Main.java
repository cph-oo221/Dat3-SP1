package dat;

import dat.config.HibernateConfig;
import dat.dao.AddressDAO;
import dat.dao.PersonDAO;
import dat.entities.*;
import jakarta.persistence.EntityManagerFactory;

import java.time.LocalDate;
import java.util.Date;

public class Main
{
    public static void main(String[] args)
    {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryConfig("hobbiestest", "update");
        PersonDAO personDAO = PersonDAO.getInstance(emf);
       // personDAO.createPerson(p);

        /*Person p1 = new Person("Jane", LocalDate.of(1994, 1, 1));
        PersonDetail pd1 = p1.addPersonDetail("Doe", "jane@email.com",
                "password", new Address("sovsevej", "1", 2750));
        personDAO.createPerson(p1);*/
    }
}
