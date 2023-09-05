package dat;

import dat.config.HibernateConfig;
import dat.dao.PhoneDAO;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest
{

    private PhoneDAO phoneDAO;
    private EntityManagerFactory emf;

    @BeforeAll
    void setUp()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobbiestest", "create");
    }

    @AfterAll
    void tearDown()
    {
        emf.close();
    }

    private void fillDatabase()
    {

    }


    @Test
    void getUserdata()
    {
        // TODO: Write test
    }

    @Test
    void getAllPhoneNumbersForPerson()
    {
        // US - 2
        // TODO: Write test
    }

    @Test
    void getAllPersonsByHobby()
    {
        // US - 3
        // TODO: Write test
    }

    @Test
    void getNumberOfPeopleByHobby()
    {
        // TODO: Write test
    }

    @Test
    void getHobbyListWithAmountOfPeople()
    {
        // TODO: Write test
    }

    @Test
    void getUsersByCity()
    {
        // TODO: Write test
    }

    @Test
    void getPostCodesAndCityNames()
    {
        // TODO: Write test
    }

    @Test
    void getAllPersonDetailsByPhoneNumber()
    {
        // TODO: Write test
    }

    @Test
    void CRUDOnAllEntities()
    {
        // TODO: Write test
    }
}