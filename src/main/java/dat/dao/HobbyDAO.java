package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class HobbyDAO
{
    EntityManagerFactory emf;

    private static HobbyDAO instance;
    private HobbyDAO()
    {

    }

    public HobbyDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new HobbyDAO();
        }
        this.emf = emf;
        return instance;
    }
}
