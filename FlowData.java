import java.util.*;
class FlowData
{
    public static final double INFINITE=Double.MAX_VALUE, UNITCAP=100, ZEROCAP=0;
    ArrayList<FEdge> edge;
    boolean top=false;
    int value;
    double[][] capacity;
    public static final int WHITE = 0, GRAY = 1, BLACK = 2;
	double[][] flow, res_capacity;
	int[] parent, color, queue;
	double[] min_capacity;
	int size, source, sink, first, last;
	double max_flow;
    int num;
    FlowData(int n,int value)
    {
        num=n;
        size=n*n+2;
        source=0;
        sink=n*n+1;
        this.value=value;
        edge=new ArrayList<FEdge>();
    }
    
    FlowData(FlowData x)
    {
        num=x.num;
        size=x.size;
        source=x.source;
        sink=x.sink;
        value=x.value;
        edge=new ArrayList<FEdge>();
        double a[][]=new double[size][size];
        for(int i=0;i<size;i++)
        for(int j=0;j<size;j++)
        a[i][j]=x.capacity[i][j];
        capacity=a;
    }
    
    void join(double a[][],int i,int j,double cap)
    {
        if(i>=0&&i<a.length&&j>=0&&j<a.length)
        {
            a[i][j]=cap;
        }
    }
    
    
    
    void update(double a[][],int val,int x,int y)
    {
        int i=x*num+y+1;
        switch(value)
        {
            case 1:
            {
                switch(val)
                {
                    case 1:
                    for(int j=0;j<a[i].length;j++)
                    {
                        if(a[i][j]>0)
                        {
                            a[i][j]=INFINITE;
                            a[j][i]=INFINITE;
                        }
                    }
                    break;
                    case 2:
                    for(int j=0;j<a[i].length;j++)
                    {
                        a[i][j]=ZEROCAP;
                        a[j][i]=ZEROCAP;
                    }
                    break;
                }
                break;
            }
            case 2:
            {
                switch(val)
                {
                    case 1:
                    for(int j=0;j<a[i].length;j++)
                    {
                        a[i][j]=ZEROCAP; 
                        a[j][i]=ZEROCAP;
                    }
                    break;
                    case 2:
                    for(int j=0;j<a[i].length;j++)
                    {
                        if(a[i][j]>0)
                        {
                            a[i][j]=INFINITE;
                            a[j][i]=INFINITE;
                        }
                    }
                    break;
                }
                break;
            }
        }
        
    }
    void maxFlow()  
	{
		flow = new double[size][size];
		res_capacity = new double[size][size];
		parent = new int[size];
		min_capacity = new double[size];
		color = new int[size];
		queue = new int[size];
 
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				res_capacity[i][j] = capacity[i][j];
 
		while (BFS(source))
		{
			max_flow += min_capacity[sink];
			int v = sink, u;
			while (v != source)
			{
				u = parent[v];
				flow[u][v] += min_capacity[sink];
				flow[v][u] -= min_capacity[sink];
				res_capacity[u][v] -= min_capacity[sink];
				res_capacity[v][u] += min_capacity[sink];
				v = u;
			}
		}
		
	}
 
	boolean BFS(int source)
	{
	    
		for (int i = 0; i < size; i++)
		{
			color[i] = WHITE;
			min_capacity[i] = INFINITE;
		}
 
		first = last = 0;
		queue[last++] = source;
		color[source] = GRAY;
 
		while (first != last)  
		{
		    top=false;
			int v = queue[first++];
			for (int u = 0; u < size; u++)
				if (color[u] == WHITE && res_capacity[v][u] > 0)
				{
					if(min_capacity[v]<res_capacity[v][u])
					{
					    min_capacity[u]=min_capacity[v];
					}
					else
					{
					    min_capacity[u]=res_capacity[v][u];
					    
					    if(top)
					    {
					        edge.remove(0);
					        edge.add(0,new FEdge(u,v));
					    }
					    else
					    {		
					        edge.add(0,new FEdge(u,v));
					        top=true;
					    }
					}
					parent[u] = v;
					color[u] = GRAY;
					if (u == sink) return true;
					queue[last++] = u;
				}
		}
		
		return false;
	}
 

}
        