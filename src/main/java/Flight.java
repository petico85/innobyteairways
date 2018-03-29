public class Flight {

    public int ID;
    public int airlineID;
    public int startCityID;
    public int destinationCityID;
    public int flightDistance; //km-s
    public int flightTime; //minues
    public String name;

    public Flight(int ID, int airlineID, int startCityID, int destinationCityID, int flightDistance, int flightTime, String name)
    {
        this.ID = ID;
        this.airlineID = airlineID;
        this.startCityID = startCityID;
        this.destinationCityID = destinationCityID;
        this.flightDistance = flightDistance;
        this.flightTime = flightTime;
        this.name = name;
    }
}
