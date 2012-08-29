class Block
{
	double x,y;
	int i,j;
	double polyX[],polyY[];
	private int st=0;
	static final int BLANK=0,
		     RED_BEAD=1,
		     BLUE_BEAD=2,
		     RED_PATH=3,
		     BLUE_PATH=4;

	void setState(int x)
	{
		st=x;
	}

	int getState()
	{
		return st;
	}
	double[] polyXIn()
	{
		return new double[]{polyX[0],polyX[1]+1.5,polyX[2]+1.5,polyX[3],polyX[4]-1.5,polyX[5]-1.5};
	}
	double[] polyYIn()
	{
		return new double[]{polyY[0]+1.5,polyY[1],polyY[2],polyY[3],polyY[4],polyY[5]-1.5};
	}

}