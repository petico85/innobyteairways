import java.util.ArrayList;
import java.util.HashSet;


/**
 * Gráf
 * Csúcsok listája
 *
 * */
public class Graph {

    private ArrayList<Vertex> graph;

    public Graph()
    {
        graph = new ArrayList<Vertex>();
    }


    /**
     * Egész gráf visszaadás
     * */
    public ArrayList<Vertex> getGraph()
    {
        if(!graph.isEmpty())
        {
            return graph;
        }
        else
        {
            return null;
        }
    }

    /**
     * elem hozzáadása
     * */
    public boolean add(Vertex vertex)
    {
        for(Vertex element : graph)
        {
            if(element.ID == vertex.ID)
            {
                return false;
            }
        }

        graph.add(vertex);
        return true;
    }

    /**
     * ID alapján visszadja a vertexet
     * Ha nincs ilyen ID null értékkel tér vissza
     *
     * */
    public Vertex getVertex(int ID)
    {
        for(Vertex element : graph)
        {
            if(element.ID == ID)
            {
                return element;
            }
        }
        return null;
    }

    /**
     * Egy elem cseréje megadott elemre
     * */
    public boolean setVertex(int ID, Vertex vertex)
    {
        for(int i = 0; i < graph.size(); i++)
        {
            if(graph.get(i).ID == ID)
            {
                graph.set(i, vertex);
                return true;
            }
        }
        return false;
    }

    /**
     * Gráf kialakítása a repülőjáratok listájából
     * */
    public void loadGraph(Flights flights)
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
            graph.add(vertex);
        }

    }
}
