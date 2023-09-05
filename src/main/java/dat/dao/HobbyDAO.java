package dat.dao;

import dat.entities.Hobby;
import dat.entities.Interests;
import dat.entities.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HobbyDAO
{
    private static EntityManagerFactory emf;

    private static HobbyDAO instance;

    public static HobbyDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new HobbyDAO();
        }
        return instance;
    }


    public void addHobby(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.persist(hobby);
            em.getTransaction().commit();
        }
    }

    public Hobby find(int id)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.find(Hobby.class, id);
        }
    }

    public void deleteHobby(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            for (Interests interest : hobby.getInterests())
            {
                interest.getPerson().getInterests().remove(interest);
            }
            em.remove(hobby);
            em.getTransaction().commit();
        }
    }


    // US - 3
    public List<Person> getAllPersonsByHobby(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT p FROM Person p Join p.interests h WHERE h.hobby.h_id = :hobbyId", Person.class)
                    .setParameter("hobbyId", hobby.getH_id())
                    .getResultList();
        }
    }


    // US - 4
    public Integer getHobbyCount(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(p) FROM Person p Join p.interests h WHERE h.hobby.h_id = :hobbyId", Integer.class)
                    .setParameter("hobbyId", hobby.getH_id())
                    .getSingleResult();
        }
    }
}
