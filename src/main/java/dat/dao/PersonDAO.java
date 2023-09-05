package dat.dao;

import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonDAO
{
    private static EntityManagerFactory emf;

    private static PersonDAO instance;

    public static PersonDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new PersonDAO();
        }
        return instance;
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
