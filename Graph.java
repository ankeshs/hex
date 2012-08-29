import java.util.*;
class Graph
{
    static final double BLANK_BLANK=10;
    static final double LIKE_LIKE=0;
    static final double LIKE_BLANK=2;
    static final double UNLIKE=100000;
    int num;
    Vertex b[];
    Vertex a[][];
    Graph(int n)
    {
        num=n;
        b=new Vertex[2];
        a=new Vertex[n][n];
        (b[0]=new Vertex()).link=new ArrayList<Edge>();
        (b[1]=new Vertex()).link=new ArrayList<Edge>();
        b[0].val=b[1].val=10;
        b[0].x=b[0].y=-1;
		b[1].x=b[1].y=-2;
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
            {
                a[i][j]=new Vertex();
                a[i][j].x=i;
                a[i][j].y=j;
                a[i][j].link=new ArrayList<Edge>();
            }
    }
    void update(int x,int y,boolean f,boolean forced,int val)
    {
        a[x][y].val=val;
        
        if(f)
        {
            for(int i=0;i<a[x][y].link.size();i++)
            {
                if((a[x][y].link.get(i).weight==Graph.LIKE_BLANK))
                    a[x][y].link.get(i).weight=Graph.LIKE_LIKE;
                if((a[x][y].link.get(i).weight==Graph.BLANK_BLANK)||forced)
                    a[x][y].link.get(i).weight=Graph.LIKE_BLANK;
            
           }
        }
        else
        {
            for(int i=0;i<a[x][y].link.size();i++)
                a[x][y].link.get(i).weight=Graph.UNLIKE;
        }

    }

}