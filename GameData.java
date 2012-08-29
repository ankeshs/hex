

class GameData
{
	public Block a[][];

	GameData()
	{
		this(13);
	}

	GameData(int n)
	{
		a=new Block[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				a[i][j]=new Block();
	}

}








