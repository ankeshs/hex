import java.util.*;
import java.io.*;
class FirstAI extends DJ implements AI
{
    Board board;
    boolean forced=false;
	int val;
    public void init(Board b)
    {
        board=b;
    }
    FirstAI(int n,int val)
    {
		this.val=val;
		if(val==1)
        sG=new RedGraph(n);
		else
		sG=new BlueGraph(n);
    }
    public void nextMove(int x1,int y1,int val,int count)
    {
	int x,y;
        if(x1<0&&y1<0)
        {
           x=y=sG.num/2;
        }
		else if(count==1)
		{
			sG.update(x1, y1, false, true,val);
			x=y=0;//G.num/2;
			if(x==x1&&y==y1)x++;
			
		}
        else
        {
		
        sG.update(x1, y1, false, false,val);
        List<Vertex> l=algo(sG,x1,y1);
		System.out.println("l.size()="+l.size());
        int k=0,z=0,zl=0,tm=0;
		//Reduntant
		for(int i=0;i<l.size();i++)
		{
			Debug.print("l["+i+"].x",l.get(i).x);
			Debug.print("l["+i+"].y",l.get(i).y);
		}
		
        for(int i=0;i<l.size();i++)
        {
			
            if(l.get(i).val==0)
			{
				tm=i;
				while(l.get(i).val==0)
				{
					k++;
					i++;
				}
				if(k>=zl)
				{
					Debug.print("l["+i+"].x",l.get(i).x);
					Debug.print("l["+i+"].y",l.get(i).y);
					Debug.print("z",z);
					Debug.print("k",k);
					Debug.print("zl",zl);
					Debug.print("tm",tm);
					z=tm;
					zl=k;
					
				}
				k=0;
			}
        }
        x=l.get(z+zl/2).x;
        y=l.get(z+zl/2).y;
        }
		Debug.print("x",x);
		Debug.print("y",y);
        sG.update(x, y, true, count==1,(val==1)?2:1);
	board.place((int)board.data.a[x][y].x,(int)board.data.a[x][y].y);
    }
	public void updateAI(int x,int y,int val,int count)
	{
		sG.update(x, y, true, count==1,val);
	}
}
