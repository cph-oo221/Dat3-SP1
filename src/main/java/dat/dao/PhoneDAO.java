package dat.dao;

import dat.entities.Hobby;
import dat.entities.Person;
import dat.entities.Phone;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneDAO
{
    private static EntityManagerFactory emf;

    private static PhoneDAO instance;

    public static PhoneDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new PhoneDAO();
        }
        return instance;
    }


    public void addPhone(Phone phone)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(phone);
            em.getTransaction().commit();
        }
    }

    public Phone find(int id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.find(Phone.class, id);
        }
    }

    public void deletePhone(Phone phone)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.remove(phone);
            phone.getPerson().getPhoneSet().remove(phone);
            em.getTransaction().commit();
        }
    }

    public void editPhone(Phone phone)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.merge(phone);
            em.getTransaction().commit();
        }
    }

    public List<Phone> getAllPhones()
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p from Phone p", Phone.class).getResultList();
        }
    }


    // US 2
    public List<Phone> getAllNumbersByPerson(Person person)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p.personDetail.phoneSet from Phone p where p.personDetail.person.id = :id", Phone.class)
                    .setParameter("id", person.getP_id()).getResultList();
        }
    }


    // TODO - THIS IS NOT NEED, MADE BY MISSTAKE
    public List<Phone> getAllNumbersByHobby(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p.personDetail.phoneSet from Phone p Join p.personDetail.person.interests i where i.hobby.id = :id", Phone.class)
                    .setParameter("id", hobby.getH_id()).getResultList();
        }
    }
}