package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class PersonDetailDAO
{
    EntityManagerFactory emf;

    private static PersonDetailDAO instance;
    private PersonDetailDAO()
    {

    }

    public PersonDetailDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new PersonDetailDAO();
        }
        this.emf = emf;
        return instance;
    }
}
