package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class InterestDAO
{
    EntityManagerFactory emf;

    private static InterestDAO instance;
    private InterestDAO()
    {

    }

    public InterestDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new InterestDAO();
        }
        this.emf = emf;
        return instance;
    }
}
