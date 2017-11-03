package Model;

import java.util.ArrayList;
import java.util.Iterator;

public class BoundingBox 
{
	private OurPoint ul;
	public OurPoint getUl() 
	{
		return ul;
	}
	public OurPoint getLr() 
	{
		return lr;
	}
	private OurPoint lr;
	private double ulx ;
	private double uly ;
	private double lrx ;
	private double lry ;
	
	// a function to calculate the bounding box of a polygon
	BoundingBox(Polygon polygon)
	{
		ulx = Double.MAX_VALUE;
		uly = Double.MIN_VALUE;
		lrx = Double.MIN_VALUE;
		lry = Double.MAX_VALUE;

		for (int i=0; i<polygon.getNodes().size(); i++)
		{
			if (polygon.getNodes().get(i).getX()<ulx) ulx = polygon.getNodes().get(i).getX();
			if (polygon.getNodes().get(i).getX()>lrx) lrx = polygon.getNodes().get(i).getX();
			if (polygon.getNodes().get(i).getY()>uly) uly = polygon.getNodes().get(i).getY();
			if (polygon.getNodes().get(i).getY()<lry) lry = polygon.getNodes().get(i).getY();
		}
		this.ul = new OurPoint(ulx,uly);
		this.lr = new OurPoint (lrx,lry);
	}
	public BoundingBox(OurPoint upperLeft, OurPoint lowerRight) 
	{
		this.ul = upperLeft;
		this.lr = lowerRight;
	}
	// returns the bounding box of a polygon in form of an array; 
	// the first element of the array is the lower right corner of the bounding box and the second one is the upper left corner 
	// return boundingBox;
	BoundingBox(ArrayList<OurPoint> points)
	{
		ulx = Double.MAX_VALUE;
		uly = Double.MIN_VALUE;
		lrx = Double.MIN_VALUE;
		lry = Double.MAX_VALUE;
		int i = 1;
		Iterator<OurPoint> pointsIterator = points.iterator();
		while (pointsIterator.hasNext())
		{
			OurPoint tempPoint  = pointsIterator.next();
			if (tempPoint.getX()<ulx) ulx = tempPoint.getX();
			if (tempPoint.getX()>lrx) lrx = tempPoint.getX();
			if (tempPoint.getY()>uly) uly = tempPoint.getY();
			if (tempPoint.getY()<lry) lry = tempPoint.getY();
			System.out.println("i is: "+i);
			i++;
		}

		// this.ul = new OurPoint(ulx,uly);
		// this.lr = new OurPoint (lrx,lry);
		this.ul = new OurPoint(lrx,lry);
		this.lr = new OurPoint (ulx,uly);
	}
	public BoundingBox(double x1, double y1,double x2,double y2) 
	{
		this.ul = new OurPoint(x1,y1);
		this.lr = new OurPoint (x2,y2);
	}
	//The getX() and getY() methods of Point are used to determine whether the point is inside
	public boolean isInside(OurPoint p)
	{
		if(ul.getX()<lr.getX()  
				&& ul.getY()>lr.getY()
				&& ul.getX()<p.getX()  
				&& lr.getX()>p.getX()
				&& ul.getY()>p.getY()
				&& lr.getY()<p.getY()){
			return true;
		}
		else
		{
			return false;
		}
	}
}
