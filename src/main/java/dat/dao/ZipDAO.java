package dat.dao;

import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
