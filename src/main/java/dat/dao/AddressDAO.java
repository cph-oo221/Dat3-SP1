package dat.dao;

import dat.dto.AdressIdStreetNumberDTO;
import jakarta.persistence.Access;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDAO
{
    private static EntityManagerFactory emf;

    private static AddressDAO instance;


    public static AddressDAO getInstance(EntityManagerFactory _emf)
    {
        if (instance == null)
        {
            emf = _emf;
            instance = new AddressDAO();
        }
        return instance;
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
