package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class ZipDAO
{
    EntityManagerFactory emf;

    private static ZipDAO instance;
    private ZipDAO()
    {

    }

    public ZipDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new ZipDAO();
        }
        this.emf = emf;
        return instance;
    }
}
