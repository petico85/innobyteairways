import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShortestWayTest {

    @Test
    void searchShortestWay() {
        LoadXML loadXml = new LoadXML("xml");
        AirLines airlines = loadXml.loadAirlines("airlines.xml");
        Cities cities = loadXml.loadCities("cities.xml");
        Flights flights = loadXml.loadFlights("flights.xml");

        Flights assianFlights = new Flights(flights.getFlightsOfTheAirline(airlines.getAirline(3)));

        ShortestWay sortestWay = new ShortestWay();

        assertEquals(null, sortestWay.searchShortestWay(assianFlights,1,21));
        assertEquals(null, sortestWay.searchShortestWay(assianFlights,1,1));

        Flights AssianWay = sortestWay.searchShortestWay(assianFlights,1,10);
        for(Flight flight : AssianWay.getFlights())
        {
            System.out.println(flight.name);
        }

        System.out.println("------------------------------------");
        Flights euFlights = new Flights(flights.getFlightsOfTheAirline(airlines.getAirline(1)));
        Flights euWay = sortestWay.searchShortestWay(euFlights,1,10);
        for(Flight flight : euWay.getFlights())
        {
            System.out.println(flight.name);
        }

        System.out.println("------------------------------------");
        Flights allWay = sortestWay.searchShortestWay(flights,1,10);
        for(Flight flight : allWay.getFlights())
        {
            System.out.println(flight.name);
        }


    }
}