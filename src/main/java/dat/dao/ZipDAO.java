package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class ZipDAO
{
    EntityManagerFactory emf;

    private static ZipDAO instance;
    private ZipDAO()
    {

    }

    public static ZipDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new ZipDAO();
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
