package dat.dao;

import dat.entities.Hobby;
import dat.entities.Person;
import dat.entities.Phone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class PhoneDAO
{
    EntityManagerFactory emf;

    private static PhoneDAO instance;
    private PhoneDAO()
    {

    }

    public PhoneDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new PhoneDAO();
        }
        this.emf = emf;
        return instance;
    }


    // US 2
    public List<Phone> getAllPhones(Person person)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p.personDetail.phoneSet from Phone p where p.personDetail.person.id = :id", Phone.class)
                    .setParameter("id", person.getP_id()).getResultList();
        }
    }


    // US 4
    public String getPhoneByHobby(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery(
        }
    }
}
