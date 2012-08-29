import javax.swing.*;
class HexGame
{
    public static void main(String args[])throws Exception
    {
        JFrame f=new JFrame();
        f.add(new JLabel(new ImageIcon("splash_program.jpg")));
        f.setUndecorated(true);
        f.setSize(660,520);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        try
        {
            Thread.sleep(1500);
        }
        catch(Exception e)
        {
        }
        f.dispose();   
        new Board().setVisible(true);
    }
}