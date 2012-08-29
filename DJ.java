import java.util.*;
class DJ
{
    Graph sG,eG;
    PriorityQueue<Vertex> Q;
    static final double INFINITE=1000000;
    List<Vertex> algo(Graph G,int x,int y)
    {
        int n=G.num;
        Vertex a[][]=G.a,b[]=G.b;
        int i,j;
        
		b[0].dist=0;		  
		b[1].dist=INFINITE;
		Q=new PriorityQueue<Vertex>(n*n+2);
		Q.add(b[0]);
		Debug.print("b[0] queued");
		for(i=0;i<n;i++)
            for(j=0;j<n;j++)
            {
				a[i][j].dist=INFINITE;
                a[i][j].previous=null;
				Q.add(a[i][j]);
				//Debug.print("a["+i+"]["+j+"] queued");
			}
		Q.add(b[1]);
		Debug.print("b[1] queued");
		/*ADDITIONAL SEGMENT>>*/
		PriorityQueue<Vertex> q=new PriorityQueue<Vertex>(n*n+2);
		for(i=0;i<n*n+2;i++)
		{
			Vertex u=Q.poll();
			//System.out.println("u.x="+u.x+" u.y="+u.y);
			q.add(u);
		}
		Q=q;
		/*<<ADDITIONAL SEGMENT*/
        while(!(Q.isEmpty()))
        {
            Vertex u=Q.peek();
            if(u.dist==INFINITE)break;
            Q.poll();
			//System.out.println("u.x="+u.x+" u.y="+u.y);
            for(i=0;i<u.link.size();i++)
            {
                Vertex v=u.link.get(i).adjacent(u);
                if(!(Q.contains(v)))continue;
                double t=u.dist+u.link.get(i).weight;
                if(t<v.dist)
                {
                    v.dist=t;
                    v.previous=u;
                }
            }
			/*ADDITIONAL SEGMENT>>*
			q=new PriorityQueue<Vertex>();
			for(i=0;i<Q.size();i++)
			{
				Vertex tmp=Q.poll();
				q.add(tmp);
			}
			Q=q;
			/*<<ADDITIONAL SEGMENT*/
        }
		//For debugging....
		//Debug.displayDistances(G,new Frame());
		
		List<Vertex> shortest=new ArrayList<Vertex>();
        Vertex target=b[1];
		Debug.print(target.x+" , "+target.y);

        while(target.previous!=b[0])
        {
			/*ADDITIONAL SEGMENT>>*/
			//Debug.print(target.x+" , "+target.y);
            shortest.add(target);
            target=target.previous;
        }
		shortest.add(target);
		shortest.add(b[0]);
        return shortest;
    }
}
