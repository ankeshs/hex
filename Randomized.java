import java.io.*;
import java.util.*;
class Randomized implements AI
{

	Board board;

	public void init(Board board)
	{
		this.board=board;
	}

	public void nextMove(int x1,int y1,int val,int count)
	{
		int n=board.bst.getOrder();
		Random r=new Random();
		int x=r.nextInt(n);
		int y=r.nextInt(n);
		while(board.data.a[x][y].getState()!=0)
		{
			x=r.nextInt(n);
			y=r.nextInt(n);
		}

		board.place((int)board.data.a[x][y].x,(int)board.data.a[x][y].y);
	}
	public void updateAI(int x,int y,int val,int count)
	{
	}
}
