package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class PersonDAO
{
    EntityManagerFactory emf;

    private static PersonDAO instance;
    private PersonDAO()
    {

    }

    public PersonDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new PersonDAO();
        }
        this.emf = emf;
        return instance;
    }
}
