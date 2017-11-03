package Controller;

/* 
 * not implemented
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;
import org.json.JSONObject;
import java.util.ArrayList;
import Model.Buffer;
 */
import Model.OurPoint;

public class Utility 
{
	// empty constructor
	public Utility() 
	{
		// TODO Auto-generated constructor stub
	}
	// implemented methods
	// calculates geographic distance between two points
	public static double distanceCalculator(OurPoint firstPoint, OurPoint secondPoint) 
	{
		double theta = firstPoint.getX() - secondPoint.getX();
		double dist = Math.sin(deg2rad(firstPoint.getY())) * Math.sin(deg2rad(secondPoint.getY())) + Math.cos(deg2rad(firstPoint.getY())) * Math.cos(deg2rad(secondPoint.getY())) * Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515 * 1.609344;
		return (dist);
	}
	// converts decimal degrees to radians
	private static double deg2rad(double deg) 
	{
		return (deg * Math.PI / 180.0);
	}
	//converts radians to decimal degrees
	private static double rad2deg(double rad) 
	{
		return (rad * 180 / Math.PI);
	}
	// unimplemented methods
	/*
	public void zoomToFeature()
	{

	}
	public void zoomToExtent()
	{

	}
	public Buffer createBuffer()
	{
		return null;
	}
	public ArrayList<String> getInfo()
	{
		return null;
	}
	 */
}
