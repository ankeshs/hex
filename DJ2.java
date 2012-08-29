import java.util.*;
class DJ2
{
    Graph sG,eG;
    static final double INFINITE=1000000;
    int x0,y0;
    List<Vertex> algo(Graph G,int xp,int yp)
    {
        int n=G.num;
        Vertex a[][]=G.a,b[]=G.b;
        int i,j;
               
		b[0].dist=0;		  
		b[1].dist=INFINITE;

		int size=n*n+2;
		Vertex x[]=new Vertex[size];
		int cnt=0;
		x[cnt++]=b[0];
	
		for(i=x0;i>=0;i--)
		{
            for(j=y0;j>=0;j--)
            {
				a[i][j].dist=INFINITE;
                a[i][j].previous=null;
				x[cnt++]=a[i][j];
				
			}
			 for(j=y0+1;j<n;j++)
            {
				a[i][j].dist=INFINITE;
                a[i][j].previous=null;
				x[cnt++]=a[i][j];
				
			}

		}

		for(i=x0+1;i<n;i++)
		{
            for(j=y0;j>=0;j--)
            {
				a[i][j].dist=INFINITE;
                a[i][j].previous=null;
				x[cnt++]=a[i][j];
				
			}
			 for(j=y0+1;j<n;j++)
            {
				a[i][j].dist=INFINITE;
                a[i][j].previous=null;
				x[cnt++]=a[i][j];
				
			}

		}
		x[cnt++]=b[1];
		while(size>0)
        {
            int mi=min(x);
			Vertex u=x[mi];
            if(u.dist==INFINITE)break;
            x[mi]=null;
			size--;
            for(i=0;i<u.link.size();i++)
            {
                Vertex v=u.link.get(i).adjacent(u);
                if(!(find(x,v)))continue;
                double t=u.dist+u.link.get(i).weight;
                if(t<v.dist)
                {
                    v.dist=t;
                    v.previous=u;
                }
            }
		}
				
		List<Vertex> shortest=new ArrayList<Vertex>();
        Vertex target=b[1];
	
		while(target.previous!=b[0])
        {
			
			shortest.add(target);
            target=target.previous;
        }
		shortest.add(target);
		shortest.add(b[0]);
        return shortest;
    }
	int min(Vertex x[])
	{
		int k=0;
		while(k<x.length&&x[k]==null)k++;
		for(int i=k+1;i<x.length;i++)
		{
			if(x[i]!=null)
			{
				if(x[k].dist>x[i].dist)k=i;
			}
		}
		return k;
	}

	boolean find(Vertex x[],Vertex v)
	{
		for(int i=0;i<x.length;i++)
		{
			if(x[i]==v&&v!=null)return true;
		}
		return false;
	}
	Vertex brutePattern(Graph G,int val)
	{
		
		Vertex a[][]=G.a;
		int n=G.num;
		if(val==1)
		{
			for(int i=0;i<n;i++)
			{
				int c=0;
				for(int j=0;j<n-1;j++)
				{
					if(a[i][j].val==val)
					{
						c++;
						if(c>=((n>8)?n/3:3))
						{
							if(a[i][j+1].val==0)
							return a[i][j+1];
							else
							{
								int k=j;
								while(k>0&&a[i][k].val==val)k--;
								if(k>=0&&a[i][k].val==0)
								return a[i][k];
								else j=n;
							}
						}
							
					}
					else
					{
						c=0;
					}
				}
			}
		}

		if(val==2)
		{
			for(int i=0;i<n;i++)
			{
				int c=0;
				for(int j=0;j<n-1;j++)
				{
					if(a[j][i].val==val)
					{
						c++;
						if(c>=((n>8)?n/3:3))
						{
							if(a[j+1][i].val==0)
							return a[j+1][i];
							else
							{
								int k=j;
								while(k>0&&a[k][i].val==val)k--;
								if(k>=0&&a[k][i].val==0)
								return a[k][i];
								else j=n;

							}
						}
							
					}
					else
					{
						c=0;
					}
				}
			}
		}
		return null;
	}

	boolean checkAlt(List<Vertex> l,int k)
	{
		for(int i=0;i<l.get(k).link.size();i++)
		{
			Vertex v=l.get(k).link.get(i).adjacent(l.get(k));
			if(v.val!=0)continue;
			for(int j=0;j<l.get(k+1).link.size();j++)
			{
				Vertex u=l.get(k+1).link.get(j).adjacent(l.get(k+1));
				if(u==v)return true;
			}
			for(int j=0;j<l.get(k-1).link.size();j++)
			{
				Vertex u=l.get(k-1).link.get(j).adjacent(l.get(k-1));
				if(u==v)return true;
			}

		}
		return false;
	}

}