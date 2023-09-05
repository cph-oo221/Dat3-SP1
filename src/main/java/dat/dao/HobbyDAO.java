package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class HobbyDAO
{
    EntityManagerFactory emf;

    private static HobbyDAO instance;
    private HobbyDAO()
    {

    }

    public static HobbyDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new HobbyDAO();
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
