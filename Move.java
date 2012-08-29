class Move
{
    int k;
    double flow;
    int key;
    double opp;
    Move(int k, double flow, double opp)
    {
        this.k=k;
        this.flow=flow;
        this.opp=opp;
    }
    void setKey(int pos)
    {
        
    }
    static void swap(Move m1,Move m2)
    {
        int k=m1.k;
        double flow=m1.flow;
        int key=m1.key;
        double opp=m1.opp;
        m1.k=m2.k;
        m1.flow=m2.flow;
        m1.key=m2.key;
        m1.opp=m2.opp;
        m2.k=k;
        m2.flow=flow;
        m2.key=key;
        m2.opp=opp;
    }
}
