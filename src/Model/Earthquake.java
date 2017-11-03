package Model;

@SuppressWarnings("serial")
public class Earthquake extends OurPoint 
{
	// view - get
	// controller - set
	private String place; 
	private double magnitude, depth;
	private long time;
	private String id;

	// constructor
	public Earthquake(double lon, double lat)
	{
		super(lon, lat);
	}
	//getter and setter
	public String getPlace() 
	{
		return place;
	}
	public void setPlace(String place) 
	{
		this.place = place;
	}
	public double getMagnitude() 
	{
		return magnitude;
	}
	public void setMagnitude(double magnitude) 
	{
		this.magnitude = magnitude;
	}
	public long getTime() 
	{
		return time;
	}
	public void setTime(long time) 
	{
		this.time = time;
	}
	public double getDepth() 
	{
		return depth;
	}
	public void setDepth(double depth) 
	{
		this.depth = depth;
	}
	public String getId() 
	{
		return id;
	}
	public void setId(String id) 
	{
		this.id = id;
	}
}
