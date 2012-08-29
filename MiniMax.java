import java.util.*;
class MiniMax extends DJ2 implements AI
{
    Board board;
    boolean forced=false;
    int val;
    int sv,ev;
    boolean bruteFound=false;
    int altrn=1;
    int div=3;
    int smul=1,emul=2;
    public void init(Board b)
    {
        board=b;
    }
    MiniMax(int n,int val)
    {
        super();
        
        this.val=val;
        if(val==1)
        {
            sG=new RedGraph(n);
            eG=new BlueGraph(n);
            sv=1;
            ev=2;
            x0=n/2;
            y0=n/2;
        }
        else
        {
            sG=new BlueGraph(n);
            eG=new RedGraph(n);
            sv=2;
            ev=1;
            x0=n/2;
            y0=n/2;
        }
    }
    
    public void nextMove(int x1,int y1,int val,int count)
    {
        int x,y;
        int i,k1=0,k2=0,j1=0,j2=0;
        while(true)
        {
            if(x1<0&&y1<0)
            {
                x=y=sG.num/2;
                break;
            }
            if(count==1)
            {
                sG.update(x1, y1, false, false, ev);
                eG.update(x1, y1, true, false, ev);
                x=y=sG.num/2;
                if(x==x1&&y==y1)x++;
                break;
            }
                        
            /*Randomized origin shift*/
            Random random=new Random();
            switch(random.nextInt(13))
            {
                case 0:case 1:case 2:case 3:case 4:case 5:
                smul=1;
                emul=2;
                break;
                case 6:case 7:
                smul=2;
                emul=1;
                break;
                case 8:case 9:case 10:
                smul=1;
                emul=1;
                break;
                case 11:
                smul=1;
                emul=0;
                break;
                case 12:
                smul=0;
                emul=1;
                break;
            }
            switch(random.nextInt(6))
            {
                case 0:
                case 1: 
                x0=sG.num/2;
                y0=sG.num/2;
                altrn=1;
                div=(div==3)?2:3;
                break;
                case 5:
                case 2:
                if(ev==2)
                {
                    x0=x1;
                    y0=0;
                }
                else 
                {
                    x0=0;
                    y0=y1;
                }
                altrn++;
                break;
                case 3:
                if(ev==2)
                {
                    x0=x1;
                    y0=sG.num-2;
                }
                else 
                {
                    x0=sG.num-2;
                    y0=y1;
                }
                altrn++;
                break;
                case 4:
                if(ev==2)
                {
                    x0=x1;
                    y0=y1;
                }
                else 
                {
                    x0=x1;
                    y0=y1;
                }
                altrn++;
                break;
            }
            /*sequence end*/
            
            sG.update(x1, y1, false, false, ev);
            eG.update(x1, y1, true, false, ev);
            Vertex sc=null;
            List<Vertex> sl,el;
            List<KeyNode> a1,a2;
            if(bruteFound)
            {
                smul=3;
                emul=1;
            }
            sl=algo(sG,x1,y1);
            el=algo(eG,x1,y1);
            
            for(i=0;i<sl.size();i++)
                if((sl.get(i)).val==0)
                {
                    k1++;
                    j1=i;
                }
            if(k1==1)
            {
                x=(sl.get(j1)).x;
                y=(sl.get(j1)).y;
                break;
            }
            for(i=0;i<el.size();i++)
                if((el.get(i)).val==0)
                {
                    k2++;
                    j2=i;
                }
            if(k2==1)
            {
                x=(el.get(j2)).x;
                y=(el.get(j2)).y;
                break;
            }       


            if((sc=brutePattern(eG,ev))!=null)
            {
                bruteFound=true;                                               
            }
            
            a2=getKeys(sl,smul);
            a1=getKeys(el,emul);
            a1=merge(a1,a2);
            KeyNode k=max(a1);
            x=k.v.x;
            y=k.v.y;
            break;
                        
        }
        
        
        Debug.print("x",x);
        Debug.print("y",y);
        sG.update(x, y, true, false, sv);
        eG.update(x, y, false, false, sv);
        board.place((int)board.data.a[x][y].x,(int)board.data.a[x][y].y);
    }
    
    ArrayList<KeyNode> getKeys(List<Vertex> l,int mul)
    {
        ArrayList<KeyNode> a=new ArrayList<KeyNode>();
        int i,k=2,j=0;
        boolean f=true;
        for(i=0;i<l.size();i++)
        {
            if(l.get(i).val==0)
            {
                a.add(new KeyNode(l.get(i),0));
            }
        }
        for(i=0;i<l.size();i++)
        {
            if(l.get(i).val==0)
            {
                a.get(j).key+=k*mul;
                if(k>1)k--;
                j++;
                f=false;
            }
            else
            {
                if(f)k++;
                else k=1;
                f=true;
            }
        }
        k=2;
        j=a.size()-1;
        f=true;
        for(i=l.size()-1;i>=0;i--)
        {
            if(l.get(i).val==0)
            {
                a.get(j).key+=k*mul;;
                if(k>0)k--;
                j--;
                f=false;
            }
            else
            {
                if(f)k++;
                else k=1;
                f=true;
            }
        }
        
        return a;
        
    }
    
    List<KeyNode> merge(List<KeyNode> a1,List<KeyNode> a2)
    {
        int i,j;
        for(i=0;i<a2.size();i++)
        {
            boolean f=false;
            for(j=0;j<a1.size();j++)
            {
                if((a1.get(j).v.x==a2.get(i).v.x)&&(a1.get(j).v.y==a2.get(i).v.y))
                {
                    f=true;
                    a1.get(j).key+=a2.get(i).key;
                }
            }
            if(!f)
                a1.add(a2.get(i));
                
        }
        return a1;
    }
    
    KeyNode max(List<KeyNode> a)
    {
        KeyNode x=a.get(0);
        for(int i=1;i<a.size();i++)
        {
            if(x.key<a.get(i).key) x=a.get(i);
            System.out.println("a["+i+"]  x="+a.get(i).v.x+"  y="+a.get(i).v.y+"    key="+a.get(i).key);
        }
        return x;
    }
    public void updateAI(int x,int y,int val,int count)
    {
        sG.update(x, y, true, false,val);
    }
}
