import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * XML file-ok beolvasása
 *
 * használt dokumentum
 * http://www.tutorialspoint.com/java_xml/java_dom_parse_document.htm
 *
 * */
public class LoadXML {

    private String xmlDir;

    public LoadXML(String xmlDir)
    {
        this.xmlDir = xmlDir;
    }

    /**
     * Légitársaságok beolvasása XML-ből;
     * */
    public AirLines loadAirlines(String filename)
    {
        AirLines airLines = new AirLines();

        Document doc = loadXmlToDoc(xmlDir + "/" +filename);
        if(doc != null)
        {
            NodeList nList = doc.getElementsByTagName("Airline");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nodeItem = nList.item(i);//A forech nem működik ezeken az elemeken
                if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {//megfelelő tipusú csomópont
                    Element element = (Element) nodeItem;

                    int ID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String companyName = element.getElementsByTagName("CompanyName").item(0).getTextContent();
                    airLines.add(ID,companyName);
                }
            }

        }
        return airLines;
    }

    /**
     * Városok beolvasása XML-ből;
     * */
    public Cities loadCities(String filename)
    {
        Cities cities = new Cities();
        Document doc = loadXmlToDoc(xmlDir + "/" +filename);
        if(doc != null)
        {
            NodeList nList = doc.getElementsByTagName("City");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nodeItem = nList.item(i);//A forech nem működik ezeken az elemeken
                if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {//megfelelő tipusú csomópont
                    Element element = (Element) nodeItem;

                    int ID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    String settlementName = element.getElementsByTagName("SettlementName").item(0).getTextContent();
                    int population = Integer.parseInt(element.getElementsByTagName("Population").item(0).getTextContent());
                    cities.add(ID,settlementName,population);
                }
            }
        }

        return cities;
    }

    /**
     * Járatok beolvasása XML-ből;
     * */
    public Flights loadFlights(String filename)
    {
        Flights flights = new Flights();

        Document doc = loadXmlToDoc(xmlDir + "/" +filename);
        if(doc != null)
        {
            NodeList nList = doc.getElementsByTagName("Flight");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nodeItem = nList.item(i);//A forech nem működik ezeken az elemeken
                if (nodeItem.getNodeType() == Node.ELEMENT_NODE) {//megfelelő tipusú csomópont
                    Element element = (Element) nodeItem;

                    int ID = Integer.parseInt(element.getElementsByTagName("ID").item(0).getTextContent());
                    int airlineID = Integer.parseInt(element.getElementsByTagName("AirlineID").item(0).getTextContent());
                    int startCityID = Integer.parseInt(element.getElementsByTagName("StartCityID").item(0).getTextContent());
                    int destinationCityID = Integer.parseInt(element.getElementsByTagName("DestinationCityID").item(0).getTextContent());
                    int flightDistance = Integer.parseInt(element.getElementsByTagName("FlightDistance").item(0).getTextContent());
                    int fightTime = Integer.parseInt(element.getElementsByTagName("FlightTime").item(0).getTextContent());
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    flights.add(ID,airlineID,startCityID,destinationCityID,flightDistance,fightTime,name);
                }
            }
        }

        return flights;
    }


    /**
     * Fájl beolvasása dokumentumba
     * */
    private Document loadXmlToDoc(String inputname)
    {
        Document doc = null;
        try {
            File inputFile = new File( inputname);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            System.err.println("hibás XML fájl:" + inputname);
        } catch (SAXException e) {
            e.printStackTrace();
            System.err.println("hibás XML fájl:" + inputname);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("hibás fájlnév:" + inputname);
        }

        return doc;
    }



}
