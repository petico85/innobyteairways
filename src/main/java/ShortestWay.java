import java.util.ArrayList;
import java.util.HashSet;

/**
 * Ez az osztály végzi el az útkeresést a megadott városok között.
 * */
public class ShortestWay {


    public ShortestWay() { }

    /**
     *
     * Minden élhez megtudtuk a legrövidebb utakat.
     * Minden élből megtudhatjuk hogy melyik út vezetett oda, így ha végzett az algoritmus akkor a célvárosból visszafejthető az út
     *
     * A Dijkstra algoritmust nézegettem, mielőtt nekiáltam  ezt megvalósítani,
     * és utólag megnézve tulajdonképpen valami nagyon hasonlót sikerült megírnom.
     *
     * létrehozok egy gráfot ami a csúcs elemek listája
     * Egy csúcs tartalmaz:
     *  - ID //az azonosításhoz
     *  - distance //itt lesz tárolva, hogy milyen hosszú út vezetett idáig.
     *  - flightsFrom //az innen induló élek azonosítóinak listája
     *  - flightTo //az ide érkező él azonosítója
     *
     *  futás:
     *  Az induló csúcsal kezdünk, berakjuk a cityIDs listába az azonosítóját
     *  Elindul egy ciklus ami addig fut amíg a cityIDs lista nem üres.
     *     Kivesszük a cityIDs lista legelső elemét ez lesz az aktuális csúcs
     *     végignézzük az aktuális csúcsból induló éleket
     *     kiszámoljuk a távolságokat az élekhez, ez az él távolság értékének és az induló csúcs távolság értékének az összege
     *     ha a célcsúcshoz még nem vezetett út, vagy vezetett, de nagyobb távolság értékkel, akkor feltöltjük ennek az útnak az adataival:
     *       Az érkező (flightTo) azonosítónak megadjuk az oda vezető él azonosítóját
     *       A távolság értékét átjavítjuk az ehhez az úthoz tartozóval
     *     a csúcs azonosítóját felvesszük a cityIDs listába
     *  Miután végimentünk az összes élen, töröljük az élek listáját az induló csúcsból, így nem tud végtelen ciklus kialakulni, ha kör van a gráfban
     *
     * */

    public Flights searchShortestWay(Flights flights, int startCityID, int destinationCityID)
    {
        ArrayList<Integer> cityIDs = new ArrayList<Integer>();
        Graph Q = new Graph();
        Q.loadGraph(flights);

        //ha az indulás és a cél megegyezik, vagy valamelyik nem része az útvonalaknak, akkor autómatukusan null értékkel tér vissza
        if(startCityID == destinationCityID || !existCities(Q,startCityID,destinationCityID))
        {
            return null;
        }

        cityIDs.add(startCityID);//kezdőelemt választunk
        while(!cityIDs.isEmpty()) {

            int actCityId = cityIDs.get(0);//első csúcspontot kiszedjük feldolgozni
            cityIDs.remove(0);//eltávolítjuk a bejárandó csúcspontok listájáról mert most be fogjuk járni
            Vertex actVertex = Q.getVertex(actCityId);//kenyerjük a csúcspont tulajdonságait

            for (int flightID : actVertex.flightsFrom)//végigmegyünk az innen induló járatokon
            {
                Flight actFlight = flights.getFlight(flightID);//kinyerjük az aktuális út adatait
                Vertex destnationVertex = Q.getVertex(actFlight.destinationCityID);//a célcsúcs adatai
                int distance = actVertex.distance + actFlight.flightDistance;//távolság a célcsúcsig

                if (destnationVertex.distance == 0 || destnationVertex.distance > distance)//ha kissebb akkor módosítunk
                {
                    destnationVertex.distance = distance;//beállítjuk az új távolságot
                    destnationVertex.flightTo = flightID;//megmondjuk melyik járat (él) vezetett ide
                    Q.setVertex(actFlight.destinationCityID, destnationVertex);//beírjuk a gráfba a módosított csúcsot
                }
                cityIDs.add(destnationVertex.ID); //hozzáadjuk
            }
            actVertex.flightsFrom.clear();//kiürítjük, ezzel a csúcspontal végeztünk, innen több él akkor sem indul ha megint ideérnénk.
        }

        Vertex destinationVertex = Q.getVertex(destinationCityID);//célállomás adatai
        if(destinationVertex.flightTo == -1) // ha nem vezetett járat a célhoz
        {
            return null;
        }
        else
        {
            return calculatedWay(startCityID, destinationVertex, Q, flights);
        }

    }


    /**
     * Visszafejti az utat a gráfban
     * */
    private Flights calculatedWay(int startCityID, Vertex destinationVertex, Graph Q, Flights flights) {
        Flights shortestWay = new Flights();
        ArrayList<Integer> flightIDs = new ArrayList<Integer>();

        Vertex actVertex = destinationVertex;
        Flight actFlight = flights.getFlight(actVertex.flightTo);
        while(actVertex.ID != startCityID)
        {
            flightIDs.add(actFlight.ID);
            actVertex =  Q.getVertex(actFlight.startCityID);//előző város
            actFlight = flights.getFlight(actVertex.flightTo);//előző repülés
        }

        //mivel visszafelé mentünk megfordítjuk és listába tesszük az eredményt
        for(int i = flightIDs.size()-1; i >= 0; i--)
        {
            shortestWay.add(flights.getFlight(flightIDs.get(i)));
        }




        return shortestWay;
    }


    /**
     * Ellenőrizzük, hogy az indulóváros és a célváros, egyáltalán részei az adott közlekedési hálózatnak
     * */
    private boolean existCities(Graph Q, int startCityID, int destinationCityID)
    {
        boolean start = false;
        boolean destiantion = false;
        int i = 0;
        ArrayList<Vertex> vertexList = Q.getGraph();
        while((!start || !destiantion) && i < vertexList.size())
        {
            if(vertexList.get(i).ID == startCityID){
                start = true;
            }
            if(vertexList.get(i).ID == destinationCityID){
                destiantion = true;
            }
            i++;
        }

        return (start && destiantion);
    }

}
