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

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest
{

    private static PhoneDAO phoneDAO;
    private static HobbyDAO hobbyDAO;
    private static PersonDAO personDAO;
    private static EntityManagerFactory emf;

    @BeforeAll
    static void setUp()
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
        Person p = new Person("John", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        p.addPhone(new Phone("12345678", PhoneType.HOME));

        personDAO.createPerson(p);

        assertEquals("John", personDAO.readPerson(p.getP_id()).getName());
    }


    @AfterAll
    static void tearDown()
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
        Person p = new Person("John", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        p.addPhone(new Phone("12345678", PhoneType.HOME));
        p.addPhone(new Phone("22446688", PhoneType.MOBILE));
        personDAO.createPerson(p);
        // US - 2
        List<Phone> phoneList = phoneDAO.getAllNumbersByPerson(p);
        int expected = 2;
        assertEquals(expected, phoneList.size());
    }

    @Test
    void getAllPersonsByHobby()
    {
        Hobby hobby = new Hobby();
        hobbyDAO.addHobby(hobby);
        // US - 3
        hobbyDAO.find(hobby.getH_id()-1);
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