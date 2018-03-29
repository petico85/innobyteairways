import java.util.ArrayList;

/**
 * Városok listája
 * */
public class Cities {


    private ArrayList<City> cities;

    public Cities()
    {
        cities = new ArrayList<City>();
    }

    /**
     * Visszaadja az összes várost tartalmazó listát
     * Ha üres akkor null értékkel tér vissza
     * */
    public ArrayList<City> getCities()
    {
        if(!cities.isEmpty())
        {
            return cities;
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
    public boolean add(int ID, String name, int population)
    {
        for(City element : cities)
        {
            if(element.ID == ID)
            {
                return false;
            }
        }

        City addedCity = new City(ID, name, population);
        cities.add(addedCity);
        return true;
    }

    /**
     * ID alapján keres és egy város objektummal tér vissza
     * Ha nincs ilyen ID null értékkel tér vissza
     * */
    public City getCity(int ID)
    {
        for(City element : cities)
        {
            if(element.ID == ID)
            {
                return element;
            }
        }
        return null;
    }

}
