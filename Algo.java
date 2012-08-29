import java.util.*;

class Algo
{
	static boolean gameOver(Node a[][],int x,int y,int val)
	{
		boolean end;
		end=identifyEnd(a,x,y,val);
		Node next;
		List<Node> l1,l2;
		l1=new LinkedList<Node>();
		l2=new LinkedList<Node>();
		l1.add(a[x][y]);
		l2.add(a[x][y]);
		for(int i=0;i<6;i++)
		{
			next=fn(a,x,y,i);
			if(next==null)continue;
			if(a[x][y].val==next.val)
			{
				if(next.b[val-1][0])
				{
					a[x][y].b[val-1][0]=true;
					l1.add(next);
				}
				if(next.b[val-1][1])
				{
					a[x][y].b[val-1][1]=true;
					l2.add(next);
				}
				if(a[x][y].b[val-1][0]&&a[x][y].b[val-1][1])
					return true;
			}
		}

		if(a[x][y].b[val-1][0])
			rec(a,x,y,a[x][y].val,0,l1);
		if(a[x][y].b[val-1][1])
			rec(a,x,y,a[x][y].val,1,l2);
		return false;
	}

	static void rec(Node a[][],int x,int y,int val,int id,List<Node> l)
	{
		for(int i=0;i<6;i++)
		{
			Node next=fn(a,x,y,i);
			if(next==null) continue;
			if(l.contains(next))continue;
			if(a[x][y].val==next.val)
			{
				l.add(next);
				next.b[val-1][id]=true;
				rec(a,next.x,next.y,val,id,l);
			}
		}
	}

	static boolean identifyEnd(Node a[][],int x,int y,int val)
	{
		int n=a.length;
		if(val==1)
		{
			if(y==0)
				return(a[x][y].b[val-1][0]=true);
			if(y==n-1)
				return(a[x][y].b[val-1][1]=true);
		}

		if(val==2)
		{
			if(x==0)
				return(a[x][y].b[val-1][0]=true);
			if(x==n-1)
				return(a[x][y].b[val-1][1]=true);
		}
		return false;
	}

	static Node fn(Node a[][],int x,int y,int i)
	{
		try
		{
			switch(i)
			{
				case 0:return a[x-1][y];
				case 1:return a[x][y+1];
				case 2:return a[x+1][y+1];
				case 3:return a[x+1][y];
				case 4:return a[x][y-1];
				case 5:return a[x-1][y-1];
			}
		}
		catch(Exception e)
		{
		}
		return null;
	}
}









