import java.util.ArrayList;

public class Flights {
    private ArrayList<Flight> flights;

    public Flights()
    {
        flights = new ArrayList<Flight>();
    }

    /**
     * Visszaadja az összes járatot tartalmazó listát
     * Ha üres akkor null értékkel tér vissza
     *
     * FELADAT RÉSZ:
     * Legyen egy függvény, ami visszaadja az összes járatot.
     *
     * */
    public ArrayList<Flight> getFlights()
    {
        if(!flights.isEmpty())
        {
            return flights;
        }
        else
        {
            return null;
        }
    }

    /**
     * Ha minden redben, hozzáad egy elemet a listához és true értékkel tér vissza
     * ha az index már létezik, akkor nem adja hozzá az elemet és false értékkel tér vissza
     * */
    public boolean add(int ID, int airlineID, int startCityID, int destinationCityID, int flightDistance, int flightTime, String name)
    {
        for(Flight element : flights)
        {
            if(element.ID == ID)
            {
                return false;
            }
        }

        Flight addedFlight = new Flight(ID,airlineID,startCityID,destinationCityID,flightDistance,flightTime,name);
        flights.add(addedFlight);
        return true;
    }

    /**
     * ID alapján keresés és visszaadja a lista megfelelő elem objektumát
     * Ha nincs ilyen ID null értékkel tér vissza
     * */
    public Flight getFlight(int ID)
    {
        for(Flight element : flights)
        {
            if(element.ID == ID)
            {
                return element;
            }
        }
        return null;
    }

    /**
     * FELADAT RÉSZ:
     * Legyen egy függvény, ami visszaadja az összes járatot egy adott városból egy másikba. (ha valamelyik
     * paraméter null, akkor aszerint nem szűrünk).
     * */
    public ArrayList<Flight> getFlightsStartToDestination(City startCity, City destinationCity)
    {
        if(startCity!=null && destinationCity!=null)//-tól-ig szűrés
        {
            return flightsFromStartToDestination(startCity,destinationCity);
        }
        else if(startCity!=null)//minden járat ami innen indul
        {
            return flightsFromStartCities(startCity);
        }
        else if(destinationCity!=null)//minden járat ami ide érkezik
        {
            return flightsToDestinationCities(destinationCity);
        }

        return null;
    }

    /**
     * FELADAT RÉSZ:
     * Legyen egy függvény, ami visszaadja az összes járatot amit az adott légitársaság üzemeltet.
     * */
    public ArrayList<Flight> getFlightsOfTheAirline(AirLine airLine)
    {
        ArrayList<Flight> resultFlights = new ArrayList<Flight>();

        for(Flight element : flights)
        {
            if(element.airlineID == airLine.ID)
            {
                resultFlights.add(element);
            }
        }

        return resultFlights;
    }


    private ArrayList<Flight> flightsFromStartCities(City startCity)
    {
        ArrayList<Flight> fromCityFlights = new ArrayList<Flight>();
        for(Flight element : flights)
        {
            if(element.startCityID == startCity.ID)
            {
                fromCityFlights.add(element);
            }
        }

        return fromCityFlights;
    }

    private ArrayList<Flight> flightsToDestinationCities(City destinationCity)
    {
        ArrayList<Flight> toCityFlights = new ArrayList<Flight>();
        for(Flight element : flights)
        {
            if(element.destinationCityID == destinationCity.ID)
            {
                toCityFlights.add(element);
            }
        }

        return toCityFlights;
    }

    private ArrayList<Flight> flightsFromStartToDestination(City startCity, City destinationCity)
    {
        ArrayList<Flight> fromCityFlights = new ArrayList<Flight>();
        for(Flight element : flights)
        {
            if(element.startCityID == startCity.ID && element.destinationCityID == destinationCity.ID)
            {
                fromCityFlights.add(element);
            }
        }

        return fromCityFlights;
    }
}
