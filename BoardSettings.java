import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
class BoardSettings
{
	public double scale;
	private int order;
	BoardSettings()
	{
		order=13;
	}

	void setOrder(int n)
	{
		order=n;
	}

	int getOrder()
	{
		return order;
	}
	void findScale(Dimension screenSize)
	{
		if(order!=0)
		scale=screenSize.getHeight()/(2.3*order);
		System.out.println(scale);
	}
}
