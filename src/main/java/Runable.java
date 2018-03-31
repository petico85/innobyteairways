import java.util.ArrayList;

/**
 * Itt lehet futtatni a kódot.
 * Az XML könyvtárban lévő xml tipusú fájlokban vannak tárolva a szükséges adatok.
 * AZért az XML megoldást választottam, mert így azonnal futtatható, és tapasztalataim szerint érdemes minél kevesebb teret engedni a technika ördögének.
 *
 * Feladatok:
 * Légitársaság
 * - Legyen egy függvény, ami visszaadja az összes járatot amit az adott légitársaság üzemeltet.
 *  #### main.java.AirLines -->public ArrayList<AirLine> getAirlines()
 * - Legyen egy függvény, ami visszaadja az összes légitársaságot.
 *  #### main.java.Flights --> public ArrayList<Flight> getFlightsOfTheAirline(AirLine airLine)
 *
 * Járatok
 * - Legyen távolság km-ben.
 * - Legyen menetrend szerinti időintervallum.
 * - Legyen egy függvény, ami visszaadja az összes járatot.
 * #### main.java.Flights --> public ArrayList<Flight> getFlights()
 * - Legyen egy függvény, ami visszaadja az összes járatot egy adott városból egy másikba. (ha valamelyik
 * paraméter null, akkor aszerint nem szűrünk).
 * #### main.java.Flights --> public ArrayList<Flight> getFlightsStartToDestination(City startCity, City destinationCity)
 * Város
 * - Legyen neve
 * - Legyen lakossága
 *
 *
 * Megoldandó feladat
 * 1. Az alkalmazás olvassa be az objektumokat a választott adatforrásból.
 * 2. Az alkalmazás írja ki légitársaságonként, hogy melyik a legrövidebb (km-ben) repülőút a legkisebb
 * városból a legnagyobb városba, ha csak az adott társaság járatait vesszük igénybe. Ha nem tudunk
 * eljutni kizárólag az adott társaság gépeivel, akkor azt írja ki.
 * Írja ki, hogy milyen átszállások vannak melyik városban, és hogy az út összideje mennyi várakozási
 * időkkel együtt. (Tegyük fel, hogy az összes járat minden óra kezdetén indul.)
 * 3. Az alkalmazás számolja ki ugyanezt úgy is, hogy bármelyik légitársaság gépeit használhatjuk.
 * 4. Az idő intervallumok megjelenítése óra perc bontásban legyen, pl. 10 perc, 2 óra 10 perc, 34 óra 30
 * perc.
 *
 * ### A feladatok ennek az osztálynak a main függvényének indításával futnak le.
 *
 *
 * */
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


    /**
     * Beállítja a listákat és változókat
     * */
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

                 //Én úgy számoltam, hogy ha egész órában ér be a járat, már nincs idő elérni a következőt,
                 //de ha optimista verziót követném, akkor a megoldás ez lenne:
                 //waitingTime = flight.flightTime % 60;
                 //                 if(waitingTime != 0)
                 //                 {
                 //                     waitingTime = 60 - waitingTime;
                 //               }

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
     * Percekben megadott int-ből egy óra perc kialakítású Stringet készít
     * */
    private String minutesToHour(int minutes)
    {
        return minutes/60 + " óra " + minutes % 60 + " perc";
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
