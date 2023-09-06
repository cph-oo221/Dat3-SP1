package dat;

import dat.config.HibernateConfig;
import dat.dao.HobbyDAO;
import dat.dao.PersonDAO;
import dat.dao.PhoneDAO;
import dat.dto.HobbiesCountDTO;
import dat.dao.ZipDAO;
import dat.entities.*;
import dat.scripts.FillScripts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
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
    private ZipDAO zipDAO;
    private EntityManagerFactory emf;

    @BeforeAll
    void setUp()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobbiestest", "create");
        phoneDAO = PhoneDAO.getInstance(emf);
        hobbyDAO = HobbyDAO.getInstance(emf);
        personDAO = PersonDAO.getInstance(emf);
        zipDAO = ZipDAO.getInstance(emf);

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            em.createNativeQuery(FillScripts.ZIPCODE_FILL).executeUpdate();
            em.createNativeQuery(FillScripts.HOBBY_FILL).executeUpdate();
            em.getTransaction().commit();
        }
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
    void getAllInfoOnPerson()
    {
        Person p = new Person("John", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        p.addPhone(new Phone("12345678", PhoneType.HOME));

        personDAO.createPerson(p);

        Person actual = personDAO.readPerson(p.getP_id());

        assertEquals(p.getName(), actual.getName());
        assertEquals(p.getP_id(), actual.getP_id());
        assertEquals(p.getSurname(), actual.getSurname());
        assertEquals(p.getPhoneSet().size(), actual.getPhoneSet().size());
        assertEquals(p.getInterests(), actual.getInterests());
    }

    @Test
    void getAllInfoFromPhone()
    {
        Person p = new Person("John", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        Phone phone = new Phone("12345678", PhoneType.HOME);
        p.addPhone(phone);

        personDAO.createPerson(p);

        Person actual = personDAO.getAllInfoFromPhone(phone);

        assertEquals(p.getName(), actual.getName());


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
        Person p = new Person("John", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        Person p2 = new Person("1John", "Do1e",  LocalDate.of(1991, 1, 1),
                "j2ohn@email.com", "passwo4rd", new Address("Sovse2vej", "12", 2750));

        Hobby hobby = new Hobby();
        hobbyDAO.addHobby(hobby);

        // US - 3
        hobby = hobbyDAO.find(hobby.getH_id()-1);
        p.addInterest(hobby);
        p2.addInterest(hobby);
        personDAO.createPerson(p);
        personDAO.createPerson(p2);
        List<Person> persons = hobbyDAO.getAllPersonsByHobby(hobby);
        int expected = 2;
        assertEquals(expected, persons.size());
    }

    @Test
    void getAllCityAndZip()
    {
        assertEquals(zipDAO.getAllCityAndZip().size(), 1099);
    }

    @Test
    void getHobbyListWithAmountOfPeople()
    {
        // US - 5

        Person p = new Person("John", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Sovsevej", "1", 2750));
        p.addInterest(hobbyDAO.find(1));

        personDAO.createPerson(p);

        List<HobbiesCountDTO> hobbyList = hobbyDAO.getHobbiesCount();
        int expected = 1;
        assertEquals(expected, hobbyList.size());
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