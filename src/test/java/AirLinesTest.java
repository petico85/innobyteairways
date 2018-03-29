import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AirLinesTest {

    @Test
    public void test()
    {
        AirLines testAirlines = new AirLines();
        //még üres, tehát null-t kapunk vissza
        assertEquals(null, testAirlines.getAirlines());
        //első elemet hozzáadja
        assertEquals(true, testAirlines.add(0,"testAirline1"));
        //azonos ID hozzáadása nem sikerülhet
        assertEquals(false, testAirlines.add(0,"testAirline1"));
        //a listához csak egy elem került eddig
        assertEquals(1, testAirlines.getAirlines().size());
        //a egyetlen elemének neve
        assertEquals("testAirline1", testAirlines.getAirlines().get(0).name);
        //légitársaság egy eleme az ID alapján
        assertEquals(null, testAirlines.getAirline(1)); //nincs ilyen ID
        assertEquals("testAirline1", testAirlines.getAirline(0).name);
    }

}