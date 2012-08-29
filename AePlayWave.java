import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.util.LinkedList;

class AePlayWave extends Thread {
 
    private String filename;
 
    private Position curPosition;
 
    private final int EXTERNAL_BUFFER_SIZE = 524288; // 128Kb
    
    public static final int START=1,END=2,PLACE=3,AIMOVE=4,BLOCK=5,EXIT=6;
    
    static LinkedList<AePlayWave> q=new LinkedList();
 
    enum Position {
        LEFT, RIGHT, NORMAL
    };
 
    AePlayWave(String wavfile) {
        filename = wavfile;
        curPosition = Position.NORMAL;
    }
 
    public AePlayWave(String wavfile, Position p) {
        filename = wavfile;
        curPosition = p;
    }
 
    public void run() {
 
        File soundFile = new File(filename);
        if (!soundFile.exists()) {
            System.err.println("Wave file not found: " + filename);
            return;
        }
 
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
            return;
        } catch (IOException e1) {
            e1.printStackTrace();
            return;
        }
 
        AudioFormat format = audioInputStream.getFormat();
        SourceDataLine auline = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
 
        try {
            auline = (SourceDataLine) AudioSystem.getLine(info);
            auline.open(format);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
 
        if (auline.isControlSupported(FloatControl.Type.PAN)) {
            FloatControl pan = (FloatControl) auline
                    .getControl(FloatControl.Type.PAN);
            if (curPosition == Position.RIGHT)
                pan.setValue(1.0f);
            else if (curPosition == Position.LEFT)
                pan.setValue(-1.0f);
        } 
 
        auline.start();
        int nBytesRead = 0;
        byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
 
        try {
            while (nBytesRead != -1) {
                nBytesRead = audioInputStream.read(abData, 0, abData.length);
                if (nBytesRead >= 0)
                    auline.write(abData, 0, nBytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            auline.drain();
            auline.close();
			
        }
 
    }
    static void play(int val)
    {
        switch(val)
        {
            case START:
            q.add(0,new AePlayWave("./start.aiff"));
            q.get(0).start();
            break;
            case END:
            q.add(0,new AePlayWave("./end.aif"));
            q.get(0).start();
            break;
            case PLACE:
            q.add(0,new AePlayWave("./place.aiff"));
            q.get(0).start();
            break;
            case AIMOVE:
            q.add(0,new AePlayWave("./aimove.aiff"));
            q.get(0).start();
            break;
            case BLOCK:
            q.add(0,new AePlayWave("./block.aiff"));
            q.get(0).start();
            break;
            case EXIT:
            q.add(0,new AePlayWave("./exit.aiff"));
            q.get(0).start();
            break;
        }
        while(q.size()>5)q.removeLast();
    }
}
