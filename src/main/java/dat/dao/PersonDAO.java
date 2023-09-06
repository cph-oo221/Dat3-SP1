package dat.dao;

import dat.dto.AdressIdStreetNumberDTO;
import dat.entities.Address;
import dat.entities.Person;
import dat.entities.Phone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

public class PersonDAO
{
    private static EntityManagerFactory emf;
    private static AddressDAO addressDAO;

    private static PersonDAO instance;

    private PersonDAO()
    {
        addressDAO = AddressDAO.getInstance(emf);
    }

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
        String street = person.getAddress().getStreet();
        String number = person.getAddress().getNumber();



        // check if the address already exists
        Integer id = addressDAO.getIdByStreetAndNumber(street, number);

        if (id == null)
        {
            // if not, create it
            addressDAO.createAddress(person.getAddress());
        }
        else
        {
            // if yes, set the address from db
            person.setAddress(addressDAO.readAddress(id));
        }

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(person);
            em.getTransaction().commit();
            return person;
        }
    }

    public Person getAllInfoFromPhone(Phone phone) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Person> q = em.createQuery(
                    "SELECT p FROM Person p JOIN p.phoneSet ph WHERE ph = :phone",
                    Person.class
            );
            q.setParameter("phone", phone);
            return q.getSingleResult();
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
            Address a = p.getAddress();
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            // check if other people live at the same address
            if (addressDAO.getPeopleByAdress(a.getStreet(), a.getNumber()).size() <= 0)
            {
                // if not, remove the address
               addressDAO.removeAddress(a);
            }
        }
    }

    public Person readPerson(Integer pId)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.find(Person.class, pId);
        }
    }

    public List<Person> getAllPersonsByCity(String city)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p FROM Person p WHERE p.address.zip.city = :city", Person.class)
                    .setParameter("city", city)
                    .getResultList();
        }
    }
}
