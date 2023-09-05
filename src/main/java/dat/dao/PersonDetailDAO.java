package dat.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PersonDetailDAO
{
    private static EntityManagerFactory emf;

    private static PersonDetailDAO instance;

    public static PersonDetailDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new PersonDetailDAO();
        }
        return instance;
    }
}
