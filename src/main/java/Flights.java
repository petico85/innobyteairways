import java.util.ArrayList;

public class Flights {
    private ArrayList<Flight> flights;

    public Flights()
    {
        flights = new ArrayList<Flight>();
    }

    public Flights(ArrayList<Flight> flights)
    {
        this.flights=flights;
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
     * Mint az fenti csak más paraméterekkel
     * */
    public boolean add(Flight addFlight)
    {
        for(Flight element : this.flights)
        {
            if(element.ID == addFlight.ID)
            {
                return false;
            }
        }

        flights.add(addFlight);
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
            return flightsFromStartToDestination(startCity.ID,destinationCity.ID);
        }
        else if(startCity!=null)//minden járat ami innen indul
        {
            return flightsFromStartCities(startCity.ID);
        }
        else if(destinationCity!=null)//minden járat ami ide érkezik
        {
            return flightsToDestinationCities(destinationCity.ID);
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


    /**
     * Public, mert ezt a metódust használom a ShortestWay osztályból is.
     * */
    public ArrayList<Flight> flightsFromStartCities(int startCityID)
    {
        ArrayList<Flight> fromCityFlights = new ArrayList<Flight>();
        for(Flight element : flights)
        {
            if(element.startCityID == startCityID)
            {
                fromCityFlights.add(element);
            }
        }

        return fromCityFlights;
    }

    private ArrayList<Flight> flightsToDestinationCities(int destinationCityID)
    {
        ArrayList<Flight> toCityFlights = new ArrayList<Flight>();
        for(Flight element : flights)
        {
            if(element.destinationCityID == destinationCityID)
            {
                toCityFlights.add(element);
            }
        }

        return toCityFlights;
    }

    private ArrayList<Flight> flightsFromStartToDestination(int startCityID, int destinationCityID)
    {
        ArrayList<Flight> fromCityFlights = new ArrayList<Flight>();
        for(Flight element : flights)
        {
            if(element.startCityID == startCityID && element.destinationCityID == destinationCityID)
            {
                fromCityFlights.add(element);
            }
        }

        return fromCityFlights;
    }
}
