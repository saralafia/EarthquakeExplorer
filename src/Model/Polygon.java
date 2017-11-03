package Model;
/*
 * not implemented
 import java.util.Iterator;
 */
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Polygon implements Surface, Serializable {

	// global variables
	// an arraylist of the nodes of the polygon
	private ArrayList<OurPoint> nodes = new ArrayList<OurPoint>(); 
	private double area;
	private int dimension = 2;
	private String type = "polygon";

	// constructors
	public Polygon(OurPoint a, OurPoint b, OurPoint c) 
	{
		this.nodes.add(a);
		this.nodes.add(b);
		this.nodes.add(c);
		if((c.getY()-a.getY())/(c.getX()-a.getX()) == (b.getY()-a.getY())/(b.getX()-a.getX())) 
		{
			System.out.println("The polygon is illegal. The three nodes are on the same line.");
			// the first point of the polygon is added as the last point so that the polygon is closed
		}
		this.nodes.add(nodes.get(0));
	}

	public Polygon()
	{
		// System.out.println("empty polygon created");
	}

	/* Polygon(int)
	constructs a polygon by creating a set of random points as its nodes and
	based on the number of nodes that is given as a parameter.
	 */
	public Polygon(int numberOfNodes) 
	{
		if(numberOfNodes < 3)
		{
			System.out.println("Polygon type requires three or more nodes. try again!");
		}
		else
		{
			for (int i=0; i<numberOfNodes; i++)
			{
				nodes.add(new OurPoint(Math.random()*250+100 , Math.random()*250+100));
			}
			// the first point of the polygon is added as the last point so that the polygon is closed
			//nodes.add(nodes.get(0));
			this.type = "random Polygon";
		}
	}

	// override methods from Geometry interface
	@Override
	public int dimension() 
	{
		return this.dimension;
	}

	@Override
	public String geometryType() 
	{
		return this.type;
	}

	@Override
	public double area() 
	{
		// function area; calculates the area of a polygon from it's nodes' coordinates
		System.out.println("Warning: To get a correct value for area, the input nodes of the polygon must be sorted in clockwise or counterclockwise fashion.");
		area = 0;
		int j=0;
		for (int i=0; i <nodes.size(); i++)
		{
			j = (i+1)% nodes.size();
			area += nodes.get(i).getX()*nodes.get(j).getY() - nodes.get(j).getX()*nodes.get(i).getY();
		}
		return Math.abs(area/2.0);
	}

	@Override
	public OurPoint centroid() 
	{
		double centroidX = 0;
		double centroidY = 0;
		for(int i = 0; i<nodes.size();i++)
		{
			centroidX += nodes.get(i).getX();
			centroidY += nodes.get(i).getY();
		}
		centroidX = centroidX/nodes.size();
		centroidY = centroidY/nodes.size();
		return new OurPoint(centroidX, centroidY);
	}
	// TODO figure out how to protect the getNodes method
	protected ArrayList<OurPoint> getNodes() 
	{
		return nodes;
	}
	// function addPoint
	//replaces the last element of the array with the add node and then closes the polygon
	public void addPoint(OurPoint a) 
	{
		// nodes.set(nodes.size()-1, a);
		this.nodes.add(a);
	}
	public void printPolygon()
	{
		for (int j=0; j<nodes.size()-1;j++)
		{
			System.out.println("The Coordinate of node " + (j+1) + " is: " + nodes.get(j));
		}
	}	
	// TODO implement a sorting method using each point's angle of incidence from centroid
	/*public ArrayList<Point> sort(ArrayList<Point> vertices) {
		for(int i=0;i<this.nodes.size(); i++){
			nodes.get(i).getX()
		}
	}*/
}
