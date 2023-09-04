package dat.dao;

import jakarta.persistence.EntityManagerFactory;

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
}
