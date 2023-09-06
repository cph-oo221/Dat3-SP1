package dat;

import dat.config.HibernateConfig;
import dat.dao.AddressDAO;
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

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest
{

    private PhoneDAO phoneDAO;
    private HobbyDAO hobbyDAO;
    private PersonDAO personDAO;
    private AddressDAO addressDAO;
    private EntityManagerFactory emf;

    @BeforeAll
    void setUp()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobbiestest", "create");

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery(FillScripts.ZIPCODE_FILL).executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hobby_h_id_seq RESTART WITH 1;");
            em.createNativeQuery(FillScripts.HOBBY_FILL).executeUpdate();
            em.getTransaction().commit();
        }
    }

    @BeforeEach
    void setUpEach()
    {
        phoneDAO = PhoneDAO.getInstance(emf);
        hobbyDAO = HobbyDAO.getInstance(emf);
        personDAO = PersonDAO.getInstance(emf);
        addressDAO = AddressDAO.getInstance(emf);
    }
    @AfterAll
    void tearDown()
    {
        emf.close();
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


    @Test
    void cascadeRemovePerson()
    {
        Person p = new Person("Joe", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        p.addPhone(new Phone("12345678", PhoneType.HOME));

        p = personDAO.createPerson(p);

        assertEquals("Joe", personDAO.readPerson(p.getP_id()).getName());

        personDAO.removePerson(p);
        PhoneDAO phoneDAO = PhoneDAO.getInstance(emf);
        AddressDAO adressDAO = AddressDAO.getInstance(emf);

        assertNull(personDAO.readPerson(p.getP_id()));
        assertEquals(0, phoneDAO.getAllNumbersByPerson(p).size());
        assertEquals(0, adressDAO.getPeopleByAdress(p.getAddress().getStreet(), p.getAddress().getNumber()).size());
    }

    @Test
    void dontRemoveAddressIfMorePeople()
    {
        Person p1 = new Person("Joe", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        Person p2 = new Person("Jane", "Doe",  LocalDate.of(1995, 2, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));

        personDAO.createPerson(p1);
        personDAO.createPerson(p2);

        personDAO.removePerson(p1);

        Integer id = addressDAO.getIdByStreetAndNumber("Sovsevej", "1");
        System.out.println(id);
        assertNotNull(id);

        // remove address if no people left
        personDAO.removePerson(p2);
        assertNull(addressDAO.readAddress(id));
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

        Person p = new Person("Ole", "Hansen",  LocalDate.of(2001, 6, 9),
                "olehansen@email.com", "1234OLE123", new Address("Kulsvirtoften", "24", 2800));
        p.addPhone(new Phone("90907856", PhoneType.HOME));
        p.addPhone(new Phone("45901289", PhoneType.HOME));

        List<Phone> phoneList = phoneDAO.getAllNumbersByPerson(personDAO.createPerson(p));

        int expected = 2;
        assertEquals(expected, phoneList.size());
    }

    @Test
    void getAllPersonsByHobby()
    {
        // US - 3
//        Hobby hobby = hobbyDAO.find(1);
//        List<Person> hobbies = hobbyDAO.getAllPersonsByHobby(hobby);
//        int expected = 2;
//        assertEquals(expected, hobbies.size());
    }

    @Test
    void getNumberOfPeopleByHobby()
    {
        // US - 4
//        Hobby hobby = hobbyDAO.find(1);
//        hobbyDAO.getHobbyCount(hobby);
//        int expected = 2;
//        assertEquals(expected, hobbyDAO.getHobbyCount(hobby));
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