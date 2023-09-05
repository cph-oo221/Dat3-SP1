package dat.dao;

import dat.dto.AdressIdStreetNumberDTO;
import dat.entities.Person;
import dat.entities.PersonDetail;
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
        String street = person.getPersonDetail().getAddress().getStreet();
        String number = person.getPersonDetail().getAddress().getNumber();


        try
        {
            AdressIdStreetNumberDTO aDTO = adressDAO.getIdByStreetAndNumber(street, number);
            person.getPersonDetail().getAddress().setId(aDTO.getId());
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
