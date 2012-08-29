import java.util.*;
class Edge
{
    double weight;
    Vertex v1,v2;
    Vertex adjacent(Vertex v)
    {
        if(v==v1)return v2;
        if(v==v2)return v1;
        return null;
    }
    static void linkUp(Vertex v1,Vertex v2,double wt)
    {
        Edge e=new Edge();
        e.weight=wt;
        e.v1=v1;
        e.v2=v2;
        v1.link.add(e);
        v2.link.add(e);
    }
}