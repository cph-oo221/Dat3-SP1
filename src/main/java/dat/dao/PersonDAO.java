package dat.dao;

import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class PersonDAO
{
    EntityManagerFactory emf;

    private static PersonDAO instance;
    private PersonDAO() {}

    public static PersonDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new PersonDAO();
        }
        instance.setEmf(emf);
        return instance;
    }

    // Must be run after every getInstance() call, to avoid nullpointer exception
    private void setEmf(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public Person createPerson(Person person)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        }
    }
}
