import java.util.*;
class BlueFlowData extends FlowData
{
    BlueFlowData(int n)
    
    {
        super(n,2);
        int i;
        double a[][]=new double[n*n+2][n*n+2];
        for(i=0;i<a.length;i++)
        {
            if(i>=1&&i<=n)
            {
                join(a,i,i+n,UNITCAP);
                join(a,i,i+n+1,UNITCAP);
                join(a,i,0,INFINITE);
                join(a,0,i,INFINITE);
            }
            else if(i>1&&i%n==1)
            {
                join(a,i,i-n,UNITCAP);
                join(a,i,i+1,UNITCAP);
                join(a,i,i+n,UNITCAP);
                join(a,i,i+n+1,UNITCAP);
            }
            else if(i%n==0&&i>n&&i<n*n)
            {
                join(a,i,i-1,UNITCAP);
                join(a,i,i+n,UNITCAP);
                join(a,i,i-n-1,UNITCAP);
                join(a,i,i-n,UNITCAP);
            }
            else if(n*(n-1)<i&&i<=n*n)
            {
                join(a,i,n*n+1,INFINITE);
                join(a,n*n+1,i,INFINITE);
                join(a,i,i-n-1,UNITCAP);
                join(a,i,i-n,UNITCAP);
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