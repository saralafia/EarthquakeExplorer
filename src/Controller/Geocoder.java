package Controller;

/* 
 * not implemented
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException; 
*/
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONObject;
import Model.OurPoint;

public class Geocoder 
{
	private OurPoint geocodedPoint;
	public Geocoder(String address) 
	{
		geocoding(address);
	}
	public void geocoding(String addr) 
	{
		// build a URL
		try
		{
		String s = "http://maps.google.com/maps/api/geocode/json?" +
				"sensor=false&address=";
		s += URLEncoder.encode(addr, "UTF-8");
		URL url = new URL(s);

		// read from the URL
		Scanner scan = new Scanner(url.openStream());
		String str = new String();
		while (scan.hasNext())
			str += scan.nextLine();
		scan.close();

		// build a JSON object
		JSONObject obj = new JSONObject(str);
		if (! obj.getString("status").equals("OK"))
			this.geocodedPoint = new OurPoint(-1,-1);

		// get the first result
		JSONObject res = obj.getJSONArray("results").getJSONObject(0);
		System.out.println(res.getString("formatted_address"));
		JSONObject loc =
				res.getJSONObject("geometry").getJSONObject("location");
		System.out.println("lat: " + loc.getDouble("lat") +
				", lng: " + loc.getDouble("lng"));
		
		this.geocodedPoint = new OurPoint(loc.getDouble("lng"),loc.getDouble("lat"));
		//return geocodedPoint;
		
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public OurPoint getGeocodedPoint() {
		return geocodedPoint;
	}
}
