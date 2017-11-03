package Controller;

/* 
 * not implemented
import java.net.MalformedURLException;
import org.json.JSONString; 
*/
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import org.json.JSONArray;
import org.json.JSONObject;
import Model.Earthquake;

public class GeoJSONParser 
{
	// member variable
	static ArrayList<Earthquake> quakes = new ArrayList<Earthquake>();
	// getter
	public static ArrayList<Earthquake> getQuakes() 
	{
		return quakes;
	}
	// constructor
	public GeoJSONParser(String dataSourceLink) throws IOException
	{
		// remove all loaded earthquakes from array
		quakes.clear();
		URL dataURL = new URL(dataSourceLink);
		Scanner scanner = new Scanner(dataURL.openStream());
		String featureCollectionStream = new String();
		while (scanner.hasNext())
			featureCollectionStream += scanner.nextLine();
		scanner.close();
		// save scanner to feature collection as object
		JSONObject featureCollection = new JSONObject(featureCollectionStream);
		JSONArray features = featureCollection.getJSONArray("features"); // .getJSONObject(0);
		// iterator to read GeoJSON collection
		Iterator<Object> featuresIterator = features.iterator();
		// initialize x, y
		double x, y = 0.0;
		// construct objects from GeoJSON
		while(featuresIterator.hasNext())
		{
			JSONObject earthquake = (JSONObject) featuresIterator.next();
			JSONObject earthquakeGeom = earthquake.getJSONObject("geometry");
			JSONArray coordinates = (JSONArray) earthquakeGeom.get("coordinates");
			JSONObject properties = earthquake.getJSONObject("properties");
			String id = (String)earthquake.get("id");
			if(coordinates.get(0) instanceof Integer)
			{
				x = (Integer)coordinates.get(0) * 1.0;
			}
			else
			{
				x = (double)coordinates.get(0);
			}
			if(coordinates.get(1) instanceof Integer)
			{
				y = (Integer)coordinates.get(1) * 1.0;
			}
			else
			{
				y = (double)coordinates.get(1);
			}
			quakes.add(new Earthquake(x, y));
			if (coordinates.get(2) instanceof Integer)
			{
				quakes.get(quakes.size()-1).setDepth(((Integer)coordinates.get(2) * 1.0));
			}
			else
			{
				quakes.get(quakes.size()-1).setDepth((double)coordinates.get(2));
			}
			if(properties.get("mag").toString() != "null")
			{
				quakes.get(quakes.size()-1).setMagnitude(Double.valueOf(properties.get("mag").toString()));
				// we can add something for the cases when mag is null. e.g. -10
			}
			else
			{
				quakes.get(quakes.size()-1).setMagnitude(-10.0); 
			}
			quakes.get(quakes.size()-1).setPlace((String)properties.get("place"));
			quakes.get(quakes.size()-1).setTime((long)properties.get("time"));
			quakes.get(quakes.size()-1).setId(id);
		}
		// test number of quakes returned from stream
		// System.out.println(quakes.size());
	}

}