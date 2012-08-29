import java.util.*;
class RedFlowData extends FlowData
{
    RedFlowData(int n)
    
    {
        super(n,1);
        int i;
        double a[][]=new double[n*n+2][n*n+2];
        for(i=0;i<a.length;i++)
        {
            if(i%n==1)
            {
                join(a,i,i+n,UNITCAP);
                join(a,i,i+n+1,UNITCAP);
                join(a,i,0,INFINITE);
                join(a,0,i,INFINITE);
            }
            else if(1<i&&i<n)
            {
                join(a,i,i-1,UNITCAP);
                join(a,i,i+1,UNITCAP);
                join(a,i,i+n,UNITCAP);
                join(a,i,i+n+1,UNITCAP);
            }
            else if(i%n==0)
            {
                join(a,i,i-1,UNITCAP);
                join(a,i,i+n-1,UNITCAP);
                join(a,i,n*n+1,INFINITE);
                join(a,n*n+1,i,INFINITE);
            }
            else if(n*(n-1)+1<i&&i<n*n)
            {
                join(a,i,i-n-1,INFINITE);
                join(a,i,i-n,INFINITE);
                join(a,i,i-1,UNITCAP);
                join(a,i,i+1,UNITCAP);
            }
            else
            {
                join(a,i,i+1,UNITCAP);
                join(a,i,i-1,UNITCAP);
                join(a,i,i+n+1,UNITCAP);
                join(a,i,i+n,UNITCAP);
                join(a,i,i-n,UNITCAP);
                join(a,i,i-n-1,UNITCAP);
            }
        }
        capacity=a;
    }
}