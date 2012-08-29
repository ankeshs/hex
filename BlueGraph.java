import java.util.*;

class BlueGraph extends Graph
{
    BlueGraph(int n)
    {
        super(n);
        int i,j;
        for(j=0;j<n;j++)Edge.linkUp(a[0][j],b[0],Graph.LIKE_BLANK);
        for(i=0;i<n-1;i++)Edge.linkUp(a[i][0],a[i+1][0],Graph.BLANK_BLANK);
        for(i=1;i<n-1;i++)
            for(j=1;j<n;j++)
            {
                Edge.linkUp(a[i][j],a[i-1][j-1],Graph.BLANK_BLANK);
                Edge.linkUp(a[i][j],a[i][j-1],Graph.BLANK_BLANK);
                Edge.linkUp(a[i][j],a[i-1][j],Graph.BLANK_BLANK);
            }
        for(j=1;j<n-1;j++)
        {
            Edge.linkUp(a[n-1][j],a[n-2][j-1],Graph.BLANK_BLANK);
            Edge.linkUp(a[n-1][j],a[n-2][j],Graph.BLANK_BLANK);
            Edge.linkUp(a[n-1][j],b[1],Graph.LIKE_BLANK);
        }
        Edge.linkUp(a[n-1][0],b[1],Graph.LIKE_BLANK);      
        Edge.linkUp(a[n-1][n-1],b[1],Graph.LIKE_BLANK);
    }
    void update(int x,int y,int val,boolean forced)
    {
       update(x,y,val==2,forced,val);
    }
}