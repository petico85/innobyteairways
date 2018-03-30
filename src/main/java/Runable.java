import java.util.ArrayList;

public class Runable {

    private City smallestCity = null;
    private City biggestCity = null;
    private Cities cities;
    private AirLines airlines;
    private Flights flights;
    private final String xmlDir ="xml";
    private final String airLinesXmlFile ="airlines.xml";
    private final String citiesXmlFile ="cities.xml";
    private final String flightsXmlFile ="flights.xml";

    public static void main(String[] args)
    {
          Runable runable = new Runable();
          runable.work();
    }

    public Runable()
    {
        initLists();
        smallestAndBiggestCities();
    }

    /**
     * Ez a függvény végzi el a kiírt feladatokat
     * */
    private void work()
    {
        System.out.println("Smallest city: " + smallestCity.name + ", population:" + smallestCity.population);
        System.out.println("Biggest city: " + biggestCity.name + ", population:" + biggestCity.population);
    }

    /**
     * Inicializálja a szükséges listákat és változókat
     * */
    private void initLists()
    {
        LoadXML loadXml = new LoadXML(xmlDir);
        this.airlines = loadXml.loadAirlines(airLinesXmlFile);
        this.cities = loadXml.loadCities(citiesXmlFile);
        this.flights = loadXml.loadFlights(flightsXmlFile);
    }

    /**
     * Legkissebb és legnagyobb város kiválasztása
     * */
    private void smallestAndBiggestCities()
    {
        ArrayList<City> sarchableCities = cities.getCities();
        for(City cityElement: sarchableCities)
        {
            if(smallestCity == null || smallestCity.population > cityElement.population)
            {
                smallestCity = cityElement;
            }
            if(biggestCity == null || cityElement.population > biggestCity.population)
            {
                biggestCity = cityElement;
            }
        }
    }




}
