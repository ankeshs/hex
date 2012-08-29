import java.io.*;
import java.awt.*;
import javax.swing.*;

class Debug
{
	static boolean debug=true;
	static void print(String var,int val)
	{
		if(!debug)return;
		System.out.println(var+"="+val);
	}
	static void print(String s)
	{
		if(!debug)return;
		System.out.println(s);
	}
	static void displayDistances(Graph G,Frame f)
	{
		if(!debug)return;
		JDialog d=new JDialog(f);
		d.setSize(800,600);
		d.setLocationRelativeTo(f);
		
		JPanel p=new JPanel();
		d.setLayout(new BorderLayout());
		p.add(new JLabel("("+G.b[0].x+","+G.b[0].y+")\t"+G.b[0].val+"\t"+G.b[0].dist));
		d.add(p,BorderLayout.NORTH);
		p=new JPanel();
		p.add(new JLabel("("+G.b[1].x+","+G.b[1].y+")\t"+G.b[1].val+"\t"+G.b[1].dist));
		d.add(p,BorderLayout.SOUTH);
		p=new JPanel();
		p.setLayout(new GridLayout(G.num,G.num));
		for(int i=0;i<G.num;i++)
		{
			for(int j=0;j<G.num;j++)
			{
			JPanel pl=new JPanel();
			pl.setLayout(new GridLayout(3,1));
			pl.add(new JButton("("+G.a[i][j].x+","+G.a[i][j].y+")"));
			pl.add(new JButton(G.a[i][j].val+""));
			pl.add(new JButton(G.a[i][j].dist+""));
			p.add(pl);
			}
		}
		d.add(p,BorderLayout.CENTER);
		d.show();
	}
}