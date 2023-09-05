package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class PhoneDAO
{
    EntityManagerFactory emf;

    private static PhoneDAO instance;
    private PhoneDAO()
    {

    }

    public static PhoneDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new PhoneDAO();
        }
        instance.setEmf(emf);
        return instance;
    }

    // Must be run after every getInstance() call, to avoid nullpointer exception
    private void setEmf(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
}
