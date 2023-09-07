package dat.dao;

import dat.dto.HobbiesCountDTO;
import dat.entities.Hobby;
import dat.entities.Interest;
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
            for (Interest interest : hobby.getInterests())
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
    public Long getHobbyCount(Hobby hobby)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT COUNT(p) FROM Person p Join p.interests h WHERE h.hobby.h_id = :hobbyId", Long.class)
                    .setParameter("hobbyId", hobby.getH_id())
                    .getSingleResult();
        }
    }


    // US - 5
    public List<HobbiesCountDTO> getHobbiesCount()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT new dat.dto.HobbiesCountDTO(h, COUNT(i.person.p_id)) From Interest i Join i.hobby h GROUP BY h", HobbiesCountDTO.class)
                    .getResultList();
        }
    }
 }
