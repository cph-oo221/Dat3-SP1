package dat.dao;

import dat.entities.Address;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDAO
{
    private static EntityManagerFactory emf;

    private static AddressDAO instance;


    public static AddressDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new AddressDAO();
        }
        return instance;
    }


    public Integer getIdByStreetAndNumber(String street, String number)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            try
            {
                street = street.toLowerCase();
                return em.createQuery("SELECT a.id FROM Address a WHERE a.street = :street AND a.number = :number", Integer.class)
                    .setParameter("street", street)
                    .setParameter("number", number)
                    .getSingleResult();
            }
            catch (NoResultException e)
            {
                return null;
            }
        }
    }

    public List<Person> getPeopleByAdress(String street, String number)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p FROM Person p WHERE p.address.street = :street AND p.address.number = :number", Person.class)
                .setParameter("street", street)
                .setParameter("number", number)
                .getResultList();
        }
    }

    public void removeAddress(Address address)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(address);
            em.getTransaction().commit();
        }
    }

    public Integer createAddress(Address address)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(address);
            em.getTransaction().commit();
            return address.getId();
        }
    }

    public Address readAddress(Integer id)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.find(Address.class, id);
        }
    }
}
