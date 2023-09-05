package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class InterestDAO
{
    EntityManagerFactory emf;

    private static InterestDAO instance;
    private InterestDAO()
    {

    }

    public static InterestDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new InterestDAO();
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
