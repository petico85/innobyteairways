import java.util.ArrayList;
import java.util.HashSet;

/**
 * Ez az osztály végzi el az útkeresést a megadott városok között.
 * */
public class ShortestWay {


    public ShortestWay()
    {

    }

    public Flights searchShortestWay(Flights flights, int startCityID, int destinationCityID)
    {
        ArrayList<Integer> cityIDs = new ArrayList<Integer>();
        ArrayList<Vertex> Q = loadGraph(flights);


        //ha az indulás és a cél megegyezik, vagy valamelyik nem része az útvonalaknak, akkor autómatukusan null értékkel tér vissza
        if(startCityID == destinationCityID || !existCities(Q,startCityID,destinationCityID))
        {
            return null;
        }

        /**
         * Ez egy célhoz módosított dijkstra algoritmus
         * Minden élhez megtudtuk a legrövidebb utakat.
         * Minden élből megtudhatjuk hogy melyik út vezetett oda, így ha végzett az algoritmus akkor a célvárosból visszafejthető az út
         * */
        cityIDs.add(startCityID);//kezdőelemt választunk
        while(cityIDs.size()>0) {

            int actCityId = cityIDs.get(0);//első csúcspontot kiszedjük feldolgozni
            cityIDs.remove(0);//eltávolítjuk a bejárandó csúcspontok listájáról mert most be fogjuk járni
            int actVertexID = grapIndexFromID(actCityId, Q);//az aktuális csúcs indexe
            Vertex actVertex = Q.get(actVertexID);//kenyerjük a csúcspont tulajdonságait

            for (int flightID : actVertex.flightsFrom)//végigmegyünk az innen induló járatokon
            {
                Flight actFlight = flights.getFlight(flightID);//kinyerjük az aktuális út adatait
               // System.out.println(actFlight.name);
                int qIndex = grapIndexFromID(actFlight.destinationCityID, Q);//a gráf megfelelő indexe
                Vertex destnationVertex = Q.get(qIndex);//a célcsúcs adatai
                int distance = actVertex.distance + actFlight.flightDistance;//távolság a célcsúcsig

                if (destnationVertex.distance == 0 || destnationVertex.distance > distance)//ha kissebb akkor módosítunk
                {
                    destnationVertex.distance = distance;//beállítjuk az új távolságot
                    destnationVertex.flightTo = flightID;//megmondjuk melyik járat (él) vezetett ide
                    Q.set(qIndex, destnationVertex);//beírjuk a gráfba a módosított csúcsot
                }
                cityIDs.add(destnationVertex.ID); //hozzáadjuk
            }
            actVertex.flightsFrom.clear();//kiürítjük, ezzel a csúcspontal végeztünk, innen több él akkor sem indul ha megint ideérnénk.
           // System.out.println(cityIDs);

        }


        Vertex destinationVertex = Q.get(grapIndexFromID(destinationCityID, Q));//célállomás adatai
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
    private Flights calculatedWay(int startCityID, Vertex destinationVertex, ArrayList<Vertex> Q, Flights flights) {
        Flights shortestWay = new Flights();
        ArrayList<Integer> flightIDs = new ArrayList<Integer>();

        Vertex actVertex = destinationVertex;
        Flight actFlight = flights.getFlight(actVertex.flightTo);
        while(actVertex.ID != startCityID)
        {
            flightIDs.add(actFlight.ID);
            actVertex =  Q.get(grapIndexFromID(actFlight.startCityID, Q));//előző város
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
    private boolean existCities(ArrayList<Vertex> Q, int startCityID, int destinationCityID)
    {
        boolean start = false;
        boolean destiantion = false;
        int i = 0;
        while((start == false || destiantion == false) && i < Q.size())
        {
            if(Q.get(i).ID == startCityID){
                start = true;
            }
            if(Q.get(i).ID == destinationCityID){
                destiantion = true;
            }
            i++;
        }

        return (start && destiantion);
    }


    /**
     * Kialakítjuk a gráfot
     * */
    private ArrayList<Vertex> loadGraph(Flights flights)
    {
        ArrayList<Vertex> basicQ = new ArrayList<Vertex>();
        //az összes útvégződést hozzáadjuk, ami benne van a gráfban elejét és végét is, de csak egyszer
        HashSet<Integer> duplicateRemove = new HashSet<Integer>();
        ArrayList<Integer> vertexIDs = new ArrayList<Integer>();
        for(Flight element : flights.getFlights())
        {
            if(duplicateRemove.add(element.startCityID))
            {
                vertexIDs.add(element.startCityID);
            }
            if(duplicateRemove.add(element.destinationCityID))
            {
                vertexIDs.add(element.destinationCityID);
            }
        }

        //Megvannak, a csúcs ID-k, elkezdjük a feltöltést
        for(int id : vertexIDs)
        {

            ArrayList<Integer> destinations = new ArrayList<Integer>();
            //kinyerjük az adott pontból milyen járatok indulnak
            ArrayList<Flight> flightsFrom = flights.flightsFromStartCities(id);
            for(Flight element : flightsFrom)
            {
                destinations.add(element.ID);
            }
            Vertex vertex = new Vertex(id, destinations);//létrehozzunk egy csúcspontot a Város ID,vel és az onnan induló járatok id-jével.
            basicQ.add(vertex);
        }

        return  basicQ;
    }


    //GRÁF FÜGGVÉNYEK

    private int grapIndexFromID(int id, ArrayList<Vertex> Q)
    {
        for(int i=0; i < Q.size(); i++)
        {
            if(Q.get(i).ID == id)
            {
                return i;
            }
        }
        return 0;
    }

    /**
     * gráf csúcs osztály
     * */
    private class Vertex{

        public int ID;
        public int distance; //távolság a pontig
        public ArrayList<Integer> flightsFrom;
        public int flightTo; //honnan érkezett

        public Vertex(int ID, ArrayList<Integer> flightsFrom)
        {
            distance = 0;//még nem számoltunk távolságot
            flightTo = -1;//még nem ért ide senki sehonnan

            this.ID = ID;
            this.flightsFrom = flightsFrom;

        }
    }
}
