import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FlightsTest {

    @Test
    void basicTest() {
        Flights testFlights = new Flights();
        //még üres, tehát null-t kapunk vissza
        assertEquals(null, testFlights.getFlights());
        //első elemet hozzáadja
        assertEquals(true, testFlights.add(0, 1, 1, 2, 1244, 110, "Eu Budapest-Paris"));
        //azonos ID hozzáadása nem sikerülhet
        assertEquals(false, testFlights.add(0, 1, 1, 2, 1244, 110, "Eu Budapest-Paris"));
        //a listához csak egy elem került eddig
        assertEquals(1, testFlights.getFlights().size());
        //a egyetlen elemének neve
        assertEquals("Eu Budapest-Paris", testFlights.getFlights().get(0).name);
        //a városok közül egy elem az ID alapján
        assertEquals(null, testFlights.getFlight(1)); //nincs ilyen ID
        assertEquals("Eu Budapest-Paris", testFlights.getFlight(0).name);
    }

    @Test
    void flightsStartToDestinationTesting() {

        Flights testFlights = new Flights();
        testFlights.add(0, 1, 1, 2, 1244, 110, "Eu Budapest-Paris");
        testFlights.add(1, 1, 1, 2, 1244, 110, "Eu Budapest-Paris2");
        testFlights.add(2, 1, 1, 3, 900, 70, "Eu Budapest-Berlin");
        testFlights.add(3, 2, 2, 1, 1244, 110, "Am Paris-Budapest");
        testFlights.add(4, 2, 2, 8, 4000, 300, "Am Paris-New York");

        assertEquals(5, testFlights.getFlights().size());

        /**
         * tól induló lista
         * */
        System.out.println("Járatok budapestről:");
        ArrayList<Flight> fromCityList = testFlights.getFlightsStartToDestination(new City(1,"Budapest", 1750000),null);
        assertEquals("Eu Budapest-Paris", fromCityList.get(0).name);
        assertEquals(3, fromCityList.size());
        for(Flight element : fromCityList)
        {
            System.out.println(element.name);
        }

        /**
         * érkező lista
         * */
        System.out.println();
        System.out.println("Járatok budapestre:");
        ArrayList<Flight> toCityList = testFlights.getFlightsStartToDestination(null, new City(1,"Budapest", 1750000));
        assertEquals("Am Paris-Budapest", toCityList.get(0).name);
        assertEquals(1, toCityList.size());
        for(Flight element : toCityList)
        {
            System.out.println(element.name);
        }

        /**
         * Tól ig járatok
         * */
        System.out.println();
        System.out.println("Budapestről Párizsba:");
        ArrayList<Flight> fromToCityList = testFlights.getFlightsStartToDestination(new City(1,"Budapest", 1750000),new City(2,"Paris", 2200000));
        assertEquals("Eu Budapest-Paris", fromToCityList.get(0).name);
        assertEquals(2, fromToCityList.size());
        for(Flight element : fromToCityList)
        {
            System.out.println(element.name);
        }
    }

    @Test
    void getFlightsOfTheAirlineTesting() {

        Flights testFlights = new Flights();
        testFlights.add(0, 1, 1, 2, 1244, 110, "Eu Budapest-Paris");
        testFlights.add(1, 1, 1, 2, 1244, 110, "Eu Budapest-Paris2");
        testFlights.add(2, 1, 1, 3, 900, 70, "Eu Budapest-Berlin");
        testFlights.add(3, 2, 2, 1, 1244, 110, "Am Paris-Budapest");
        testFlights.add(4, 2, 2, 8, 4000, 300, "Am Paris-New York");

        assertEquals(5, testFlights.getFlights().size());

        System.out.println("European Airline:");
        ArrayList<Flight> euFlights = testFlights.getFlightsOfTheAirline(new AirLine(1,"European Airline"));
        assertEquals(3, euFlights.size());
        for(Flight element : euFlights)
        {
            System.out.println(element.name);
        }
        System.out.println();
        System.out.println("American Airline:");
        ArrayList<Flight> amFlights = testFlights.getFlightsOfTheAirline(new AirLine(2,"American Airline"));
        assertEquals(2, amFlights.size());
        for(Flight element : amFlights)
        {
            System.out.println(element.name);
        }
        System.out.println();


    }
}