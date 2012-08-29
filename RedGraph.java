import java.util.*;
class RedGraph extends Graph
{
    RedGraph(int n)
    {
        super(n);
        int i,j;
        for(i=0;i<n;i++)
		{
			Edge.linkUp(a[i][0],b[0],Graph.LIKE_BLANK);
		}
        for(j=0;j<n-1;j++)Edge.linkUp(a[0][j],a[0][j+1],Graph.BLANK_BLANK);
        for(i=1;i<n;i++)
            for(j=1;j<n-1;j++)
            {
                Edge.linkUp(a[i][j],a[i-1][j-1],Graph.BLANK_BLANK);
                Edge.linkUp(a[i][j],a[i][j-1],Graph.BLANK_BLANK);
                Edge.linkUp(a[i][j],a[i-1][j],Graph.BLANK_BLANK);
            }
        for(i=1;i<n-1;i++)
        {
            Edge.linkUp(a[i][n-1],a[i-1][n-2],Graph.BLANK_BLANK);
            Edge.linkUp(a[i][n-1],a[i][n-2],Graph.BLANK_BLANK);
            Edge.linkUp(a[i][n-1],b[1],Graph.LIKE_BLANK);
        }
        Edge.linkUp(a[0][n-1],b[1],Graph.LIKE_BLANK);
        Edge.linkUp(a[n-1][n-1],b[1],Graph.LIKE_BLANK);
    }
    void update(int x,int y,int val,boolean forced)
    {
       update(x,y,val==1,forced,val);
    }
}
