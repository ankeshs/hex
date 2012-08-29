import java.io.*;
import java.util.*;


class AIMethods
{
    static Node a[][];
    public static void init(int n)
    {
        a=new Node[n][n];
        for(int i=0;i<n;i++)
            for(int j=0;j<n;j++)
            {
                a[i][j]=new Node();
                a[i][j].x=i;
                a[i][j].y=j;
            }
    }


    public static boolean gameOver(Board board,int x,int y,int count)
    {
        a[x][y].val=board.data.a[x] [y].getState();

        if(Algo.gameOver(a,x,y,a[x][y].val))
        {
            return true;
        }
        return false;
    }
    public static AI getAI(String name,int n,int val)
    {
        if(name.equals("Easy"))
            return (new EasyMiniMax(n,val));
        else if(name.equals("Medium"))
            return (new NextMM(n,val));
        else if(name.equals("Hard"))
            return (new MiniMax(n,val));
        return null;
    }
}