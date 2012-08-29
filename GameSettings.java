import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
class GameSettings
{
	private int type;
	static final int HUMAN_VS_HUMAN=0,HUMAN_VS_AI=1;
	static final int PLAYER_RED=0,PLAYER_BLUE=1;
	AI ai;
	int pcol=0;
	boolean swap=false;
	boolean audio=false;
	int theme=0;

	GameSettings()
	{
		type=0;
	}

	void setType(int x)
	{
		type=x;
	}

	int getType()
	{
		return type;
	}

}