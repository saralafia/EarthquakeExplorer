/**
 * This is the interface for all geometries
 * Author: Behzad Vahedi and Sara Lafia
 * 
 * TODO: see if we can create objects in interfaces
 * TODO: update public Geometry envelope();
 * TODO: update public Geometry buffer();
 * 
 * classes that share implementations: buffer and point (implement Geometry)
 * points do not belong to the Surface nor to the Curve interfaces, which do not have more than a single dimension
 * buffers are a class executed on points
 * this is in accordance with OGC standards (see UML diagram)
 */

package Model;

public interface Geometry 
{
	public int dimension();
	public String geometryType();
}
