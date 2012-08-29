import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

class Board extends Frame implements ActionListener
{
    BoardSettings bst;
    GameSettings gst;
    GameData data;
    boolean firstrun=true;
    double x0,y0,r3;
    int cst=1;
    int count=0;
    boolean gameActive=false;
    Block lmv=null;
    boolean userMove=true;
    int ptm[][];
    boolean drme=true;
    Image gold,silver;

    Board()
    {
        addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e)
        {
            exit();
        }
        });
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize);
        setLocation(0,0);
        r3=Math.sqrt(3);
        Button ng,eg,ab;
        ng=new Button("New Game");
        eg=new Button("Exit Game");
        ab=new Button("About");
        gold=new ImageIcon("./coin2.jpg").getImage();
        silver=new ImageIcon("./coin1.jpg").getImage();
        Panel pan=new Panel();
        pan.setLayout(new FlowLayout());
        pan.add(ng);
        pan.add(eg);
        pan.add(ab);
        setLayout(new BorderLayout());
        add(pan,BorderLayout.NORTH);

        newGame();
        ng.addActionListener(this);
        eg.addActionListener(this);
        ab.addActionListener(this);

        addMouseListener(new MouseAdapter()
                {
                public void mouseClicked(MouseEvent e)
                {
                int mx=e.getX(),my=e.getY();
                place(mx,my);
                }
                });



    }

    void place(int mx,int my)
    {
        if(drme)
        {
            drme=false;
            return;
        }
        
        int n=data.a.length;
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(((mx-data.a[i][j].x)*(mx-data.a[i][j].x)+(my-data.a[i][j].y)*(my-data.a[i][j].y)<bst.scale*bst.scale*.75)&&data.a[i][j].getState()==0)
                {
                    if(count==1&&gst.swap)
                    {
                        int ch = JOptionPane.showConfirmDialog(this,"Swap moves?","Swap",JOptionPane.YES_NO_OPTION);
                        if(ch==JOptionPane.YES_OPTION)
                        {
                            lmv.setState(cst);
                            switch(cst)
                            {
                                case 1:data.a[i][j].setState(2); break;
                                case 2:data.a[i][j].setState(1); break;

                            }
                            repaint((int)(lmv.x-0.8*bst.scale),(int)(lmv.y-0.8*bst.scale),(int)(1.6*bst.scale),(int)(1.6*bst.scale));
                            if(AIMethods.gameOver(this,lmv.i,lmv.j,count))
                            {
                                newGame();
                            }
                            if(gst.ai!=null)
                            gst.ai.updateAI(lmv.i,lmv.j,lmv.getState(),count);

                        }
                        else
                            data.a[i][j].setState(cst);

                    }
                    else
                        data.a[i][j].setState(cst);
                    lmv=data.a[i][j];
                    repaint((int)(data.a[i][j].x-0.8*bst.scale),(int)(data.a[i][j].y-0.8*bst.scale),(int)(1.6*bst.scale),(int)(1.6*bst.scale));
                    switch(cst)
                    {
                        case 1:cst=2; break;
                        case 2:cst=1; break;
                    }
                            
                    if(AIMethods.gameOver(this,lmv.i,lmv.j,count))
                    {
                            System.out.println("Game Over");
                            firstrun=true;
                            String win="";
                            if(lmv.getState()==Block.RED_BEAD)
                            {
                                switch(gst.theme)
                                {
                                    case 0:
                                    win="Red";
                                    break;
                                    case 1:
                                    win="Black";
                                    break;
                                    case 2:
                                    win="Gold";
                                    break;
                                }                              
                                
                            }
                            else 
                            {       
                                switch(gst.theme)
                                {
                                    case 0:
                                    win="Blue";
                                    break;
                                    case 1:
                                    win="Green";
                                    break;
                                    case 2:
                                    win="Silver";
                                    break;
                                }               
                            }
                            if(gst.audio)AePlayWave.play(AePlayWave.END);
                            JOptionPane.showMessageDialog(this,win+" wins","Game Over",JOptionPane.INFORMATION_MESSAGE);
                            newGame();
                            return;
                    }
                    
                    if(gst.audio)
                    {
                        if(gst.ai==null)AePlayWave.play(AePlayWave.PLACE);
                        else if(userMove)AePlayWave.play(AePlayWave.AIMOVE);
                    }
                    count++;
                    if(gst.ai!=null&&userMove)
                    {
                        userMove=false;
                        gst.ai.nextMove(lmv.i,lmv.j,lmv.getState(),count);
                    }
                    else userMove=true;
                    return;
                }
            }
        }
        if(gst.audio)AePlayWave.play(AePlayWave.BLOCK);
    }


    void exit()
    {
        int n = JOptionPane.showConfirmDialog(this,"Are you sure you want to exit this game?","Confirm Exit",JOptionPane.YES_NO_OPTION);
        
        if(n==JOptionPane.YES_OPTION)
        {
            if(gst.audio)
            {
                AePlayWave.play(AePlayWave.EXIT);
                try
                {
                    Thread.sleep(2000);
                }
                catch(Exception e){}
            }
            dispose();
            System.exit(0);
        }
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getActionCommand().equals("New Game"))
        {
            newGame();
        }

        if(e.getActionCommand().equals("Exit Game"))
        {
            exit();
        }
        if(e.getActionCommand().equals("About"))
        {
            JDialog f=new JDialog();
            f.add(new JLabel(new ImageIcon("splash_applet.jpg")));
            f.setSize(660,520);
            f.setLocationRelativeTo(this);
            f.setVisible(true);
        }
    }

    void newGame()
    {
        ptm=null;
        NewGame ngd=new NewGame(this);
        ngd.setVisible(true);
        if(ngd.gst==null)
        {
            if(firstrun)
            {
                bst=new BoardSettings();
                gst=new GameSettings();
                bst.setOrder(13);
                gst.setType(0);
                gst.swap=false;
                firstrun=false;
            }
            else return;
        }
        else
        {
            bst=ngd.bst;
            gst=ngd.gst;
            firstrun=false;
        }

        bst.findScale(getSize());
        x0=bst.scale*r3*(bst.getOrder()+4)/2;
        y0=bst.scale*r3*2+50;

        data=new GameData(bst.getOrder());
        getCoordinates();
        /*if(gst.ai!=null)
        {
            gst.ai.flush();
            gst.ai.start();
        }*/
        gameActive=true;
        count=0;
        lmv=null;
        AIMethods.init(bst.getOrder());
        if(gst.getType()==GameSettings.HUMAN_VS_AI)
        {   
            if(gst.ai==null)
            gst.ai=new MiniMax(bst.getOrder(),(cst==1)?2:1);
            gst.ai.init(this);
            userMove=true;
        }
        AePlayWave.q.clear();
        if(gst.audio)AePlayWave.play(AePlayWave.START);
        repaint();

    }

    void getCoordinates()
    {
        for(int i=0;i<bst.getOrder();i++)
        {
            for(int j=0;j<bst.getOrder();j++)
            {
                data.a[i][j]=new Block();
                data.a[i][j].i=i;
                data.a[i][j].j=j;
                double x=data.a[i][j].x=x0+r3*bst.scale*j;
                double y=data.a[i][j].y=y0+i*bst.scale*1.5;
                
                data.a[i][j].polyX=new double[]
                {
                    x,
                    x-r3*0.5*bst.scale,
                    x-r3*0.5*bst.scale,
                    x,
                    x+r3*0.5*bst.scale,
                    x+r3*0.5*bst.scale
                };
                data.a[i][j].polyY=new double[]
                {
                    y-bst.scale,
                    y-0.5*bst.scale,
                    y+0.5*bst.scale,
                    y+bst.scale,
                    y+0.5*bst.scale,
                    y-0.5*bst.scale
                };

            }
            x0-=r3*0.5*bst.scale;
        }

    }
    private int[] getIntArray(double a[])
    {
        int b[]=new int[a.length];
        for(int i=0;i<a.length;i++)
            b[i]=(int)a[i];
        return b;
    }

    public void drawHexBoard(Graphics g)
    {
        g.setColor(Color.BLACK);
        double mx1,mx2,mx3,mx4,my1,my2,my3,my4; //Margin attributes

        mx1=data.a[0][0].x-bst.scale*(r3/2+0.5);
        mx2=data.a[0][bst.getOrder()-1].x+bst.scale*(r3+1);
        mx3=data.a[bst.getOrder()-1][0].x-bst.scale*(r3+1);
        mx4=data.a[bst.getOrder()-1][bst.getOrder()-1].x+bst.scale*(r3/2+0.5);
        my1=data.a[0][0].y-bst.scale*1.5;
        my2=data.a[0][bst.getOrder()-1].y-bst.scale*1.5;
        my3=data.a[bst.getOrder()-1][0].y+bst.scale*1.5;
        my4=data.a[bst.getOrder()-1][bst.getOrder()-1].y+bst.scale*1.5;

        g.fillPolygon(getIntArray(new double[]{mx1,mx2,mx4,mx3}),getIntArray(new double[]{my1,my2,my4,my3}),4);
        switch(gst.theme)
        {
            case 0:
            g.setColor(Color.RED);
            break;
            case 1:
            g.setColor(Color.BLACK);
            break;
            case 2:
            g.setColor(Color.YELLOW);
            break;
        }
        g.fillPolygon(getIntArray(new double[]{mx1+1,mx3+1,(mx2+mx3)/2}),getIntArray(new double[]{my1+1,my3-1,(my2+my3)/2}),3);
        g.fillPolygon(getIntArray(new double[]{mx2-1,mx4-1,(mx2+mx3)/2}),getIntArray(new double[]{my2+1,my4-1,(my2+my3)/2}),3);
        switch(gst.theme)
        {
            case 0:
            g.setColor(Color.BLUE);
            break;
            case 1:
            g.setColor(new Color(1,175,1));
            break;
            case 2:
            g.setColor(Color.lightGray);
        }
        g.fillPolygon(getIntArray(new double[]{mx1+1,mx2-1,(mx2+mx3)/2}),getIntArray(new double[]{my1+1,my2+1,(my2+my3)/2}),3);
        g.fillPolygon(getIntArray(new double[]{mx3+1,mx4-1,(mx2+mx3)/2}),getIntArray(new double[]{my3-1,my4-1,(my2+my3)/2}),3);

        for(int i=0;i<bst.getOrder();i++)
            for(int j=0;j<bst.getOrder();j++)
            {
                g.setColor(Color.BLACK);
                g.fillPolygon(getIntArray(data.a[i][j].polyX),getIntArray(data.a[i][j].polyY),6);
                g.setColor(Color.WHITE);
                g.fillPolygon(getIntArray(data.a[i][j].polyXIn()),getIntArray(data.a[i][j].polyYIn()),6);
                g.setColor(Color.BLACK);
                double x=data.a[i][j].x,y=data.a[i][j].y;
                g.drawLine((int)x,(int)y,(int)x+1,(int)y+1);
                circle(g,x,y,data.a[i][j].getState());

            }
        if(ptm==null)return;
        if(ptm.length==0)return;
        for(int i=0;i<ptm.length;i++)
        {
            g.setColor(Color.YELLOW);
            g.fillPolygon(getIntArray(data.a[ptm[i][0]][ptm[i][1]].polyXIn()),getIntArray(data.a[ptm[i][0]][ptm[i][1]].polyYIn()),6);
            g.setColor(Color.BLACK);
            double x=data.a[ptm[i][0]][ptm[i][1]].x,y=data.a[ptm[i][0]][ptm[i][1]].y;
            circle(g,x,y,data.a[ptm[i][0]][ptm[i][1]].getState());

        }


    }

    void drawPath(int pth[][])
    {
        ptm=pth;
        repaint();
        
    }

    void circle(Graphics g,double x,double y,int st)
    {
        switch(st)
        {
            case Block.BLANK: return;
            case Block.RED_BEAD:
            switch(gst.theme)
            {
                case 0:
                g.setColor(Color.RED);
                break;
                case 1:
                g.setColor(Color.BLACK);
                break;
                case 2:
                g.drawImage(gold,(int)(x-bst.scale*r3/2*0.75),(int)(y-bst.scale*r3/2*0.75),(int)(bst.scale*r3*0.75),(int)(bst.scale*r3*0.75),Color.WHITE,null);
                return;
            }
            break;
            case Block.BLUE_BEAD:
            switch(gst.theme)
            {
                case 0:
                g.setColor(Color.BLUE);
                break;
                case 1:
                g.setColor(new Color(1,175,1));
                break;
                case 2:
                g.drawImage(silver,(int)(x-bst.scale*r3/2*0.75),(int)(y-bst.scale*r3/2*0.75),(int)(bst.scale*r3*0.75),(int)(bst.scale*r3*0.75),Color.WHITE,null);
                return;
            }
            break;
            case Block.RED_PATH:
            case Block.BLUE_PATH:
                              g.setColor(Color.GREEN);
                              break;
        }
        g.fillOval((int)(x-bst.scale*r3/2*0.75),(int)(y-bst.scale*r3/2*0.75),(int)(bst.scale*r3*0.75),(int)(bst.scale*r3*0.75));

    }


    public void paint(Graphics g)
    {
        setBackground(Color.WHITE);
        drawHexBoard(g);
    }

}