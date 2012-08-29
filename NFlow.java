import java.util.*;

class NFlow implements AI
{
    Board board;
    int val;
    int sv,ev;
    FlowData ef,sf;
    int n;
    ArrayList<Integer> possible_moves,mv;
    Integer moves[];
    NFlow(int n,int val)
    {
        this.val=val;
        this.n=n;
        if(val==1)
        {
            ef=new BlueFlowData(n);
            sf=new RedFlowData(n);
            sv=1;
            ev=2;
        }
        else
        {
            ef=new RedFlowData(n);
            sf=new BlueFlowData(n);
            sv=2;
            ev=1;
        }
        possible_moves=new ArrayList<Integer>();
        mv=new ArrayList<Integer>();
        moves=new Integer[n*n+1];
        for(int i=1;i<=n*n;i++)        
        {              
            moves[i]=new Integer(i);
            possible_moves.add(moves[i]);
        }
        
    }
    public void nextMove(int x1,int y1,int val,int count)
    {
        int x,y;
        int k;
        ArrayList<FEdge> edge;
        while(true)
        {
            if(x1<0&&y1<0)
            {
                x=y=n/2;
                k=x*n+y+1;
                break;
            }
            updateAI(x1,y1,val,count);
            possible_moves.remove(moves[x1*n+y1+1]);
            if(count==1)
            {
                x=y=n/2;
                if(x==x1&&y==y1)x++;
                k=x*n+y+1;
                break;
            }
            
            int mv=possible_moves.size();
            ArrayList<Move> emf,smf;
            emf=new ArrayList<Move>();
            smf=new ArrayList<Move>();
            FlowData etf,stf;
            for(int i=0;i<mv;i++)
            {
                etf=new FlowData(ef);   
                stf=new FlowData(sf);
                int j=possible_moves.get(i);
                etf.update(etf.capacity,sv,(j-1)/n,(j-1)%n);
                stf.update(stf.capacity,sv,(j-1)/n,(j-1)%n);
                etf.maxFlow();
                stf.maxFlow();
                emf.add(new Move(j,etf.max_flow,stf.max_flow));
                smf.add(new Move(j,stf.max_flow,etf.max_flow));
            }
            
            Move max=emf.get(0),min=smf.get(0);
            for(int i=1;i<emf.size();i++)
            {
                if(max.flow<emf.get(i).flow)max=emf.get(i);
                if(emf.get(i).flow>emf.get(0).flow)emf.remove(i);
                else if(emf.get(i).flow<emf.get(0).flow)
                {
                    for(int j=0;j<i;j++)emf.remove(0);
                    i=0;
                }
            }
            
            for(int i=1;i<smf.size();i++)
            {
                if(min.flow>smf.get(i).flow)min=smf.get(i);
                if(smf.get(i).flow<smf.get(0).flow)smf.remove(i);
                else if(smf.get(i).flow>smf.get(0).flow)
                {
                    for(int j=0;j<i;j++)smf.remove(0);
                    i=0;
                }
            }
            
            if(smf.get(0).flow==FlowData.INFINITE)
            {
                k=smf.get(0).k;
                break;
            }
            if(emf.get(0).flow==0)
            {
                k=emf.get(0).k;
                break;
            }
            
            
            for(int i=1;i<smf.size();i++)
            {
                if(smf.get(i).opp>smf.get(0).opp)smf.remove(i);
                else if(smf.get(i).opp<smf.get(0).opp)
                {
                    for(int j=0;j<i;j++)smf.remove(0);
                    i=0;
                }
            }
            
            for(int i=1;i<emf.size();i++)
            {
                if(emf.get(i).opp<emf.get(0).opp)emf.remove(i);
                else if(emf.get(i).opp>emf.get(0).opp)
                {
                    for(int j=0;j<i;j++)emf.remove(0);
                    i=0;
                }
            }
            
            k=emf.get(0).k;
            
            if(emf.get(0).opp>(3*min.flow+1*smf.get(0).flow)/(3+1))k=symmetry(emf);
            else if(smf.get(0).opp<(3*max.flow+1*emf.get(0).flow)/(3+1))k=symmetry(smf);
            else
            
           k=symmetry(emf);
           /**/
            break;
        }
        possible_moves.remove(moves[k]);
        x=(k-1)/n;
        y=(k-1)%n;
        updateAI(x, y, sv, count+1);        
        board.place((int)board.data.a[x][y].x,(int)board.data.a[x][y].y);
    }
    
    int symmetry(ArrayList<Move> m)
    {
        int d=Math.abs(m.get(0).k-n*n/2);
        for(int i=1;i<m.size();i++)
        {
            if(d>Math.abs(m.get(i).k-n*n/2))
            {
                Move.swap(m.get(0),m.get(i));
                d=Math.abs(m.get(0).k-n*n/2);
            }
        }
        return m.get(0).k;
    }
    
    public void init(Board b)
    {
        board=b;
    }
    
    public void updateAI(int x,int y,int val,int count)
    {
        ef.update(ef.capacity,val,x,y);
        sf.update(sf.capacity,val,x,y);
    }
}