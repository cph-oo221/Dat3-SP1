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

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery(FillScripts.ZIPCODE_FILL).executeUpdate();
            em.createNativeQuery(FillScripts.HOBBY_FILL).executeUpdate();
            em.getTransaction().commit();
        }
    }

    @BeforeEach
    void fillDatabaseBeforeEachTest()
    {

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
        // US - 6
        personDAO.createPerson(new Person("Ole", "Hansen",  LocalDate.of(1985, 4, 12),
                "olehansen@email.com", "1234OLE123", new Address("Søvej", "14", 2880)));

        personDAO.createPerson(new Person("Lars", "Olesen",  LocalDate.of(1965, 3, 8),
                "Lars@hotmail.com", "notLars123FRE", new Address("Bindeleddet", "42", 2880)));

        List<Person> personList = personDAO.getAllPersonsByCity("Bagsværd");
        int expectedSize = 2;
        int actualSize = personList.size();
        assertEquals(expectedSize, actualSize);

        String expectedName = "Ole";
        String actualName = personList.get(0).getName();
        assertEquals(expectedName, actualName);

        String expectedSurname = "Hansen";
        String actualSurname = personList.get(0).getSurname();
        assertEquals(expectedSurname, actualSurname);

        String expectedStreet = "søvej";
        String actualStreet = personList.get(0).getAddress().getStreet();
        assertEquals(expectedStreet, actualStreet);

        String expectedNumber = "14";
        String actualNumber = personList.get(0).getAddress().getNumber();
        assertEquals(expectedNumber, actualNumber);


        String expectedName2 = "Lars";
        String actualName2 = personList.get(1).getName();
        assertEquals(expectedName2, actualName2);

        String expectedSurname2 = "Olesen";
        String actualSurname2 = personList.get(1).getSurname();
        assertEquals(expectedSurname2, actualSurname2);


        String expectedStreet2 = "bindeleddet";
        String actualStreet2 = personList.get(1).getAddress().getStreet();
        assertEquals(expectedStreet2, actualStreet2);

        String expectedNumber2 = "42";
        String actualNumber2 = personList.get(1).getAddress().getNumber();
        assertEquals(expectedNumber2, actualNumber2);
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