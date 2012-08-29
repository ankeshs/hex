import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
class NewGame extends Dialog implements ActionListener,ChangeListener,ItemListener
{
	BoardSettings bst=null;
	GameSettings gst=null;
	JSlider order;
	JLabel oi;
	JCheckBox swap,audio,rems;
	JRadioButton red,blue;
	JComboBox type,theme;
	JList ai;
	
	static NewGame settings;
	
	Board board;
	NewGame(Board board)
	{
		super(board,"New Game",true);
		this.board=board;
		setSize(500,300);
		setLocationRelativeTo(board);

		addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent e)
		{
			dispose();
		}
		});

		order=new JSlider(JSlider.HORIZONTAL,7,19,13);
		order.setMajorTickSpacing(1);
		oi=new JLabel("13");
		swap=new JCheckBox("Swap Rule");
		swap.setSelected(false);
		audio=new JCheckBox("Mute Audio");
		audio.setSelected(false);
		rems=new JCheckBox("Remember Settings");
		rems.setSelected(false);
		red=new JRadioButton("Red",true);
		blue=new JRadioButton("Blue");
		ButtonGroup bg=new ButtonGroup();
		bg.add(red);
		bg.add(blue);
		type=new JComboBox();
		type.addItem("Human vs Human");
		type.addItem("Human vs AI");
		theme=new JComboBox();
		theme.addItem("Classic RED-BLUE");
		theme.addItem("Modern BLACK-GREEN");
		theme.addItem("Coin War");
		theme.setSelectedIndex(0);
		ai=new JList(new String[]{"Easy","Medium","Hard"});//AI.getAIList());
		ai.setEnabled(false);
		ai.setSelectedIndex(1);
		setLayout(new BorderLayout());
		JButton ok=new JButton("Ok"),cancel=new JButton("Cancel");

		JPanel p=new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.RIGHT));
		p.add(ok);
		p.add(cancel);
		setLayout(new BorderLayout());
		add(p,BorderLayout.SOUTH);
		p=new JPanel();
		p.setLayout(new FlowLayout(FlowLayout.CENTER));
		p.add(new JLabel("Grid Order:"));
		p.add(order);
		p.add(oi);
		add(p,BorderLayout.NORTH);
		p=new JPanel();
		JPanel pl=new JPanel();
		p.setLayout(new GridLayout(1,2));
		pl.setLayout(new GridLayout(8,1));
		pl.add(new JLabel("Theme:"));
		pl.add(theme);
		pl.add(new JLabel("Player 1 takes:"));
		JPanel rp=new JPanel();
		rp.setLayout(new GridLayout(1,2));
		rp.add(red);
		rp.add(blue);
		pl.add(rp);
		pl.add(new JLabel(""));
		pl.add(swap);
		pl.add(audio);
		pl.add(rems);
		p.add(pl);
		pl=new JPanel();
		pl.setLayout(new BorderLayout());
		rp=new JPanel();
		rp.setLayout(new GridLayout());
		rp.add(new JLabel("Game Type:"));
		rp.add(type);
		pl.add(rp,BorderLayout.NORTH);
		rp=new JPanel();
		rp.setLayout(new BorderLayout());
		rp.add(new JLabel("Select AI"),BorderLayout.NORTH);
		rp.add(ai,BorderLayout.CENTER);
		pl.add(rp,BorderLayout.CENTER);
		p.add(pl);
		add(p,BorderLayout.CENTER);
		p=pl=rp=null;
		if(NewGame.settings!=null)
		{
		    order.setValue(settings.order.getValue());		    
		    oi.setText(""+order.getValue());
		    swap.setSelected(settings.swap.isSelected());
		    audio.setSelected(settings.audio.isSelected());		    
		    type.setSelectedIndex(settings.type.getSelectedIndex());
		    if(type.getSelectedIndex()==0)
		    {
		        ai.setEnabled(false);
		        swap.setEnabled(true);
		    }
		    else 
		    {
		        ai.setEnabled(true);
		        swap.setSelected(false);
		        swap.setEnabled(false);
		    }
		    theme.setSelectedIndex(settings.theme.getSelectedIndex());
		    switch(theme.getSelectedIndex())
		    {
		        case 0:
		        red.setText("Red");
		        blue.setText("Blue");
		        break;
		        case 1:
		        red.setText("Black");
		        blue.setText("Green");
		        break;
		        case 2:
		        red.setText("Gold");
		        blue.setText("Silver");
		        break;
		    }
		}

		ok.addActionListener(this);
		cancel.addActionListener(this);
		order.addChangeListener(this);
		type.addItemListener(this);
		theme.addItemListener(this);		
	}

	public void stateChanged(ChangeEvent e)
	{
		oi.setText(""+order.getValue());
	}

	public void itemStateChanged(ItemEvent e)
	{
		if(type.getSelectedIndex()==0)
		{
		    ai.setEnabled(false);
		    swap.setEnabled(true);
		}
		else 
		{
		    ai.setEnabled(true);
		    swap.setSelected(false);
		    swap.setEnabled(false);
		}
		switch(theme.getSelectedIndex())
		{
		    case 0:
		    red.setText("Red");
		    blue.setText("Blue");
		    break;
		    case 1:
		    red.setText("Black");
		    blue.setText("Green");
		    break;
		    case 2:
		    red.setText("Gold");
		    blue.setText("Silver");
		    break;
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("Ok"))
		{
		    if(rems.isSelected())NewGame.settings=this;
			bst=new BoardSettings();
			gst=new GameSettings();
			bst.setOrder(order.getValue());
			gst.ai=null;
			gst.setType(type.getSelectedIndex());
			gst.swap=swap.isSelected();
			gst.audio=!audio.isSelected();
			gst.theme=theme.getSelectedIndex();
			if(gst.swap&&gst.getType()==GameSettings.HUMAN_VS_AI)
			{
				if(red.isSelected())board.cst=2;
				else board.cst=1;
			}
			else
			{
				if(red.isSelected())board.cst=1;
				else board.cst=2;
			}
			if(gst.getType()==GameSettings.HUMAN_VS_AI)gst.ai=AIMethods.getAI(ai.getSelectedValue()+"",bst.getOrder(),(board.cst==1)?2:1);

			dispose();
		}
		else dispose();
	}


}