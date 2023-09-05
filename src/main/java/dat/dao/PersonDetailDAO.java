package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class PersonDetailDAO
{
    EntityManagerFactory emf;

    private static PersonDetailDAO instance;
    private PersonDetailDAO()
    {

    }

    public static PersonDetailDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new PersonDetailDAO();
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
