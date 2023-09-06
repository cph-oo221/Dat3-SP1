package dat.dao;

import dat.entities.Zip;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZipDAO
{
    private static EntityManagerFactory emf;

    private static ZipDAO instance;

    public static ZipDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new ZipDAO();
        }

        return instance;
    }

    public List<Zip> getAllCityAndZip()
    {
        try(EntityManager em = emf.createEntityManager())
        {
            TypedQuery<Zip> q = (TypedQuery<Zip>) em.createQuery("SELECT z.id, z.city FROM Zip z");
            return q.getResultList();
        }
    }
}
