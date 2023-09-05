package dat.dao;

import dat.dto.AdressIdStreetNumberDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class AddressDAO
{
    EntityManagerFactory emf;

    private static AddressDAO instance;
    private AddressDAO()
    {

    }

    public static AddressDAO getInstance(EntityManagerFactory emf)
    {
        if (instance == null)
        {
            instance = new AddressDAO();
        }
        instance.setEmf(emf);
        return instance;
    }
    private void setEmf(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public AdressIdStreetNumberDTO getIdByStreetAndNumber(String street, String number)
    {
        try (EntityManager em = emf.createEntityManager())
        {
            return em.createQuery("SELECT new dat.dto.AdressIdStreetNumberDTO(a.id, a.street, a.number) FROM Address a" +
                            " WHERE a.street = :street AND a.number = :number", AdressIdStreetNumberDTO.class)
                .setParameter("street", street)
                .setParameter("number", number)
                .getSingleResult();
        }
    }
}
