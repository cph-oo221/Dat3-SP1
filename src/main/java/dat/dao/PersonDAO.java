package dat.dao;

import dat.dto.AdressIdStreetNumberDTO;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
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
        AddressDAO adressDAO = AddressDAO.getInstance(emf);
        String street = person.getAddress().getStreet();
        String number = person.getAddress().getNumber();


        try
        {
            AdressIdStreetNumberDTO aDTO = adressDAO.getIdByStreetAndNumber(street, number);
            person.getAddress().setId(aDTO.getId());
        }
        catch (NoResultException e)
        {

        }

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        }
    }

    public Person readPerson(Integer i)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Person.class, i);
        }
    }


    public void updatePerson(Person person)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(person);
            em.getTransaction().commit();
        }
    }

    public void removePerson(Person p)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
        }
    }
}
