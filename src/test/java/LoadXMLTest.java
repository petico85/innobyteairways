import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoadXMLTest {

    @Test
    void loadAirlines() {
        LoadXML loadXML = new LoadXML("xml");
        AirLines alines = loadXML.loadAirlines("airlines.xml");
        assertEquals(3, alines.getAirlines().size());

        System.out.println("Légitársaságok");
        for(AirLine element : alines.getAirlines())
        {
            System.out.println("[" + element.ID + "] " + element.name);
        }
        System.out.println("");
    }

    @Test
    void loadCities() {
        LoadXML loadXML = new LoadXML("xml");
        Cities cities = loadXML.loadCities("cities.xml");
        assertEquals(12, cities.getCities().size());

        System.out.println("Városok");
        for(City element : cities.getCities())
        {
            System.out.println("[" + element.ID + "] " + element.name + " - " + element.population);
        }
        System.out.println("");
    }

    @Test
    void loadFlights() {
        LoadXML loadXML = new LoadXML("xml");
        Flights flights = loadXML.loadFlights("flights.xml");
        assertEquals(28, flights.getFlights().size());

        System.out.println("Járatok:");
        for(Flight element : flights.getFlights())
        {
            System.out.println("[" + element.ID + "] " + element.name + ",  Distance:" + element.flightDistance + "km, Time:" + element.flightTime + "min");
        }

        System.out.println("");

    }
}