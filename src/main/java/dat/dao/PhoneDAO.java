package dat.dao;

import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
}
