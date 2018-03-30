import java.util.ArrayList;
import java.util.HashSet;

/**
 * Ez az osztály végzi el az útkeresést a megadott városok között.
 * */
public class ShortestWay {


    public ShortestWay() { }

    /**
     * Ez egy célhoz módosított dijkstra algoritmus
     * Minden élhez megtudtuk a legrövidebb utakat.
     * Minden élből megtudhatjuk hogy melyik út vezetett oda, így ha végzett az algoritmus akkor a célvárosból visszafejthető az út
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
        while(cityIDs.size()>0) {

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
            Flights shortestWay = calculatedWay(startCityID, destinationVertex, Q, flights);
            return shortestWay;
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
        while((start == false || destiantion == false) && i < vertexList.size())
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
