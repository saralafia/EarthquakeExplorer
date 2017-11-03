package Model;

import Controller.Utility;

public class Buffer implements Geometry 
{
	private int dimension = 2;
	private String type = "circle";
	private OurPoint center;
	private double radius;

	// constructor
	public Buffer(OurPoint center, double radius) 
	{
		this.radius = radius;
		this.center = center;
	}
	public Buffer(double radius) 
	{
		this.radius = radius;
		this.center = new OurPoint(0,0);
	}
	// methods
	public OurPoint getCenter() 
	{
		return center;
	}
	public double getRadius() 
	{
		return radius;
	}
	public void setCenter(OurPoint center) 
	{
		this.center = center;
	}
	public void setRadius(double radius) 
	{
		this.radius = radius;
	}
	@Override
	public int dimension() 
	{
		return dimension;
	}
	@Override
	public String geometryType() 
	{
		return type;
	}
	public double area() 
	{
		return (Math.PI)*(Math.pow(radius, 2));
	}
	public boolean isInside(OurPoint p)
	{
		if(radius > (Math.sqrt(
				Math.pow((center.getX()-p.getX()), 2)+ 
				Math.pow((center.getY()-p.getY()), 2))))
			return true;
		else
			return false;
	}
	public boolean isInsideGeographic(OurPoint p)
	{
		if(radius > Utility.distanceCalculator(center, p))
			return true;
		else
			return false;
	}
}
