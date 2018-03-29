import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CitiesTest {

    @Test
    public void basicTest()
    {
        Cities testcities = new Cities();
        //még üres, tehát null-t kapunk vissza
        assertEquals(null, testcities.getCities());
        //első elemet hozzáadja
        assertEquals(true, testcities.add(0,"Budapest", 1750000));
        //azonos ID hozzáadása nem sikerülhet
        assertEquals(false, testcities.add(0,"Budapest", 1750000));
        //a listához csak egy elem került eddig
        assertEquals(1, testcities.getCities().size());
        //a egyetlen elemének neve
        assertEquals("Budapest", testcities.getCities().get(0).name);
        //a városok közül egy elem az ID alapján
        assertEquals(null, testcities.getCity(1)); //nincs ilyen ID
        assertEquals("testAirline1", testcities.getCity(0).name);
    }
}