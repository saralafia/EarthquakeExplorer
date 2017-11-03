package Model;
import java.io.Serializable;
import java.text.DecimalFormat;

@SuppressWarnings("serial")
public class OurPoint implements Geometry, Serializable
{
	private double x,y;
	public OurPoint(double xcoord, double ycoord)
	{
		x = xcoord;
		y = ycoord;
	}
	public double getX() 
	{
		return x;
	}
	public void setX(double x) 
	{
		this.x = x;
	}
	public double getY() 
	{
		return y;
	}
	public void setY(double y) 
	{
		this.y = y;
	}
	public OurPoint()
	{
		x = 0;
		y = 0;
	}
	@Override
	public int dimension() 
	{
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String geometryType() 
	{
		// TODO Auto-generated method stub
		return "Point";
	}
	public String toString()
	{
		DecimalFormat df = new DecimalFormat("#.0000000000");
		return "(" + df.format(x) + "," + df.format(y) + ")";
	}
	public double calculateDistance(OurPoint p)
	{
		double dist = Math.sqrt(Math.pow(this.x-p.getX(),2)+ Math.pow(this.y-p.getY(), 2));
		return dist;
	}
}
