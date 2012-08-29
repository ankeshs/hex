import java.util.*;
import java.awt.Frame;

class Vertex implements Comparable
{
    int val;
    int x,y;
    List<Edge> link;
    //Node x;
    double dist;
    Vertex previous;

    public int compareTo(Object o)
    {
		int f;
        Vertex v=(Vertex)o;
        if(dist<v.dist)f=-1;
        else if(dist>v.dist)f=1;
		else f=0;
		//Debug.print("Inside compare to : "+f);
        return f;
    }
}

