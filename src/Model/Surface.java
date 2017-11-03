/**
 * This is an interface for polygons
 * Based on OGC Simple Features specification
 * This is an interface for polygons
 * Surface OGC definition: A Surface is a 2-dimensional geometric object.
 * class Polygon implements Surface
 * interface surface has two methods: area and centroid (in accordance with OGC)
 * 
 * classes that share implementations: Polygon (implements Surface) and Rectangle extends Polygon
 * this is in accordance with OGC standards (see UML diagram)
 */

package Model;

public interface Surface extends Geometry
{
	public double area();
	public OurPoint centroid();
}
