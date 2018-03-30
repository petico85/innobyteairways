import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {

    @Test
    void grapMethodsTest() {

        Graph Q = new Graph();

        assertEquals(null,Q.getGraph());

        ArrayList<Integer> flightsFrom = new ArrayList<Integer>();
        flightsFrom.add(0);
        Vertex vertex = new Vertex(0, flightsFrom);

        Q.add(vertex);
        assertEquals(1,Q.getGraph().size());
        assertEquals(false,Q.add(vertex));

        flightsFrom.add(1);
        Q.setVertex(0, new Vertex(0, flightsFrom));
        assertEquals(2,Q.getVertex(0).flightsFrom.size());

        Flights testFlights = new Flights();
        testFlights.add(0, 1, 1, 2, 1244, 110, "Eu Budapest-Paris");
        testFlights.add(1, 1, 1, 2, 1244, 110, "Eu Budapest-Paris2");
        testFlights.add(2, 1, 1, 3, 900, 70, "Eu Budapest-Berlin");
        testFlights.add(3, 2, 2, 1, 1244, 110, "Am Paris-Budapest");
        testFlights.add(4, 2, 2, 8, 4000, 300, "Am Paris-New York");

        Q.loadGraph(testFlights);
        assertEquals(5,Q.getGraph().size());


    }

}