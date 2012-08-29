import java.awt.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class HexApplet extends Applet
{
    int ex,ey;
    int bx1,bx2,by1,by2;
    Board b=null;
    public void init()
    {
        Dimension d=getSize();
        ey=(int)d.getHeight();
        ex=(int)d.getWidth();
        if(ex>653)ex=653;
        if(ey>489)ey=489;
        by2=ey;
        by1=ey-15;
        bx1=ex/2-70;
        bx2=ex/2+70;
        Button play=new Button("Play Game");
        setLayout(new BorderLayout());
        Panel p=new Panel();
        p.setLayout(new FlowLayout(FlowLayout.CENTER));
        p.add(play);
        add(p,BorderLayout.SOUTH);
        play.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(b!=null)b.dispose();
                b=null;
                (b=new Board()).setVisible(true);
            }
        });
    }
    public void start()
    {
    }
    public void stop()
    {
    }
    public void paint(Graphics g)
    {
        g.drawImage(new ImageIcon("splash_applet.jpg").getImage(),0,0,ex,ey-10,Color.WHITE,null);
        g.setColor(Color.ORANGE);
        //g.drawString("Play Game!",bx1,by2);
    }
    public void destroy()
    {

    }
}
