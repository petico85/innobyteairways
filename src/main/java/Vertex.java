import java.util.ArrayList;
    /**
     * gráf csúcs osztály
     * */
    public class Vertex{

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

