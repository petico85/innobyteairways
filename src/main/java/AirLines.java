import java.util.ArrayList;

public class AirLines {

    private ArrayList<AirLine> airLines;

    public AirLines()
    {
        airLines = new ArrayList<AirLine>();
    }

    /**
     * Visszaadja az összes légitársaságot tartalmazó listát
     * Ha üres akkor null értékkel tér vissza
     * */
    public ArrayList<AirLine> getAirlines()
    {
        if(!airLines.isEmpty())
        {
            return airLines;
        }
        else
        {
            return null;
        }
    }

    /**
     * Ha minden redben, hozzáad egy elemet a listához és true értékkel tér vissza
     * ha az index már létezik, akkor nem adja hozzá az elemet és false értékkel tér vissza
     * */
    public boolean add(int ID, String name)
    {
        for(AirLine element : airLines)
        {
            if(element.ID == ID)
            {
                return false;
            }
        }

        AirLine addedAL = new AirLine(ID, name);
        airLines.add(addedAL);
        return true;
    }

    /**
     * ID alapján keresés
     * Ha nincs ilyen ID null értékkel tér vissza
     * Gondolkoztam, hogy csak a nevet adja vissza, de aztán úgy döntöttem, jobb egységként kezelni az ID-t és a nevet
     * */
    public AirLine getAirline(int ID)
    {
        for(AirLine element : airLines)
        {
            if(element.ID == ID)
            {
                return element;
            }
        }
        return null;
    }


}
