package dat;

import dat.config.HibernateConfig;
import dat.dao.*;
import dat.dto.HobbiesCountDTO;
import dat.entities.*;
import dat.scripts.FillScripts;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.junit.jupiter.api.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MainTest
{

    private PhoneDAO phoneDAO;
    private HobbyDAO hobbyDAO;
    private PersonDAO personDAO;
    private AddressDAO addressDAO;
    private ZipDAO zipDAO;
    private InterestDAO interestDAO;
    private EntityManagerFactory emf;

    @BeforeAll
    void setUp()
    {
        emf = HibernateConfig.getEntityManagerFactoryConfig("hobbiestest", "create");
        phoneDAO = PhoneDAO.getInstance(emf);
        hobbyDAO = HobbyDAO.getInstance(emf);
        personDAO = PersonDAO.getInstance(emf);
        zipDAO = ZipDAO.getInstance(emf);
        addressDAO = AddressDAO.getInstance(emf);
        interestDAO = InterestDAO.getInstance(emf);

        try (EntityManager em = emf.createEntityManager())
        {

            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM interest").executeUpdate();
            em.createNativeQuery("DELETE FROM phone").executeUpdate();
            em.createNativeQuery("DELETE FROM person").executeUpdate();
            em.createNativeQuery("DELETE FROM address").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE interest_i_id_seq RESTART WITH 1;").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE phone_phone_id_seq RESTART WITH 1;").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE person_p_id_seq RESTART WITH 1;").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE address_a_id_seq RESTART WITH 1;").executeUpdate();
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
                "john@email.com", "password", new Address("Søvej", "64", 2880));
        p.addPhone(new Phone("12345678", PhoneType.HOME));

        personDAO.createPerson(p);

        assertEquals("John", personDAO.readPerson(p.getP_id()).getName());
    }


    @Test
    void cascadeRemovePerson()
    {
        Person p = new Person("Joe", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("fiskevej", "1", 2750));
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
        Person p1 = new Person("lorteJoe", "Doe",  LocalDate.of(1990, 1, 1),
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
    void getAllInfoOnPerson()
    {
        Person p = new Person("migJoe", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("aldershvilevej", "46", 2750));
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
                "john@email.com", "password", new Address("Lindeholmd", "12", 2750));
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
                "john@email.com", "password", new Address("aldershvilevej", "1", 2880));
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
                "john@email.com", "password", new Address("Sovevej", "1", 2750));
        Person p2 = new Person("1John", "Do1e",  LocalDate.of(1991, 1, 1),
                "j2ohn@email.com", "passwo4rd", new Address("Sovsevej", "12", 2750));

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
        assertEquals(1099, zipDAO.getAllCityAndZip().size());
    }

    @Test
    void getHobbyListWithAmountOfPeople()
    {
        // US - 5
        Person p = new Person("Arne", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Majvej", "10", 2750));
        p.addInterest(hobbyDAO.find(1));

        personDAO.createPerson(p);

        List<HobbiesCountDTO> hobbyList = hobbyDAO.getHobbiesCount();
        int expected = 2;
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
    void deleteInterest()
    {
        Person p = new Person("Arne", "Doe",  LocalDate.of(1990, 1, 1),
                "john@email.com", "password", new Address("Majvej", "10", 2750));
        Hobby h = hobbyDAO.find(1);
        p.addInterest(h);

        personDAO.createPerson(p);

        assertTrue(p.getInterests().size() == 1);

        for(Interests i : p.getInterests())
        {
            if(i.getPerson() == p && i.getHobby() == h)
            {
                interestDAO.deleteInterest(i);
            }
        }

        assertTrue(p.getInterests().size() == 0);

    }

    @Test
    void removeOrphanInterests()
    {
        // US - 7
        Person p = new Person("Ole", "Hansen",  LocalDate.of(1985, 4, 12), "email@email.com", "password", new Address("ostevej", "137", 2740));
        p.addInterest(hobbyDAO.find(1));

        personDAO.createPerson(p);

        List<Person> list = hobbyDAO.getAllPersonsByHobby(hobbyDAO.find(1));
        assertEquals("Ole", list.get(0).getName());

        p.removeInterest(hobbyDAO.find(1));
        p.getInterests().forEach(System.out::println);
        personDAO.updatePerson(p);

        assertEquals(0, hobbyDAO.getHobbyCount(hobbyDAO.find(1)));
    }
}