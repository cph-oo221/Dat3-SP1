package dat.dao;

import dat.entities.Interests;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InterestDAO
{
    private static EntityManagerFactory emf;

    private static InterestDAO instance;
    public static InterestDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new InterestDAO();
        }
        return instance;
    }

    public void deleteInterest(Interests i)
    {
        try(EntityManager em = emf.createEntityManager())
        {
            i.getPerson().removeInterestManually(i.getHobby());
            em.getTransaction().begin();
            em.remove(i);
            em.getTransaction().commit();
        }
    }
}