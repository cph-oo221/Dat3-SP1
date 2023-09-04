package dat.dao;

import jakarta.persistence.EntityManagerFactory;

public class AddressDAO
{
    EntityManagerFactory emf;

    private static AddressDAO instance;
    private AddressDAO()
    {

    }

    public AddressDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new AddressDAO();
        }
        this.emf = emf;
        return instance;
    }
}
