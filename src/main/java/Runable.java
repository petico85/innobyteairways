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
          runable.bossMethod();
    }

    public Runable()
    {
        initLists();
        smallestAndBiggestCities();
    }

    /**
     * Kiadja a munkákat
     * */
    private void bossMethod()
    {
        System.out.println("Legkisebb város: " + smallestCity.name + ", " + smallestCity.population + " lakos");
        System.out.println("Legnagyobb város: " + biggestCity.name + ", " + biggestCity.population + " lakos");
        System.out.println("---------------------------------------------");

        System.out.println("A legrövidebb út:");
        for(AirLine element : airlines.getAirlines())
        {
            System.out.println("    " + element.name + ":");
            Flights actFlights = new Flights(flights.getFlightsOfTheAirline(airlines.getAirline(element.ID)));//a soronkövetkező légitársaság járatai
            workerMethod(actFlights);
        }

        System.out.println("Bármely légitársasággal a legrövidebb út:");
        workerMethod(flights);
    }

    /**
     * Itt történnek a számítások
     * */
    private void workerMethod(Flights actFlights)
    {
        ShortestWay sortestWay = new ShortestWay();
        Flights euWay = sortestWay.searchShortestWay(actFlights,1,10);
        if(euWay != null)
        {
            int tripTime = 0;
            int waitingTime = 0;
            for (Flight flight : euWay.getFlights()) {
                tripTime += waitingTime;
                System.out.println("        " + cities.getCity(flight.startCityID).name + " -> "
                        + cities.getCity(flight.destinationCityID).name + ": "
                        + minutesToHour(flight.flightTime));
                tripTime +=  flight.flightTime;

                /**
                 * Én úgy számoltam, hogy ha egész órában ér be a járat, már nincs idő elérni a következőt,
                 * de ha optimista verziót követném, akkor a megoldás ez lenne
                 * waitingTime = flight.flightTime % 60;
                 *                 if(waitingTime != 0)
                 *                 {
                 *                     waitingTime = 60 - waitingTime;
                 *                 }
                 * */
                waitingTime = 60 - flight.flightTime % 60;


            }
            System.out.println("        --------");
            System.out.println("        Összesen: " + minutesToHour(tripTime));
        }
        else{
            System.out.println("        Nincs járat a két állomás között.");
        }
        System.out.println();
    }

    /**
     * Percekben megadott int-ből órát csinál
     * */
    private String minutesToHour(int minutes)
    {
        String hours = "";

        hours = (Integer) minutes/60 + " óra " + minutes % 60 + " perc";

        return hours;
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
