package dat;

import dat.config.HibernateConfig;
import dat.dao.HobbyDAO;
import dat.dao.PersonDAO;
import dat.dao.PhoneDAO;
import dat.entities.*;
import dat.scripts.FillScripts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest
{

    private PhoneDAO phoneDAO;
    private HobbyDAO hobbyDAO;
    private PersonDAO personDAO;
    private EntityManagerFactory emf;

    @BeforeAll
    void setUp()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobbiestest", "create");
        phoneDAO = PhoneDAO.getInstance(emf);
        hobbyDAO = HobbyDAO.getInstance(emf);
        personDAO = PersonDAO.getInstance(emf);
    }

    @BeforeEach
    void fillDatabaseBeforeEachTest()
    {
        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery(FillScripts.zipcodeFill).executeUpdate();
            em.getTransaction().commit();
        }
    }


    @Test
    void cascadePersistPerson()
    {
        Person p = new Person("John", LocalDate.of(1990, 1, 1));
        PersonDetail pd = p.addPersonDetail("Doe", "john@email.com",
                "password", new Address("sovsevej", "1", 2750));

        personDAO.createPerson(p);


    }


    @AfterAll
    void tearDown()
    {
        emf.close();
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
        List<Phone> phoneList = phoneDAO.getAllNumbersByPerson(new Person());
        int expected = 2;
        assertEquals(expected, phoneList.size());
    }

    @Test
    void getAllPersonsByHobby()
    {
        // US - 3
        Hobby hobby = hobbyDAO.find(1);
        List<Person> hobbies = hobbyDAO.getAllPersonsByHobby(hobby);
        int expected = 2;
        assertEquals(expected, hobbies.size());
    }

    @Test
    void getNumberOfPeopleByHobby()
    {
        // US - 4
        Hobby hobby = hobbyDAO.find(1);
        hobbyDAO.getHobbyCount(hobby);
        int expected = 2;
        assertEquals(expected, hobbyDAO.getHobbyCount(hobby));
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