package battleship;

import static battleship.Board.sparseArray;
import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

public class Battleship extends JFrame implements Runnable {
    boolean animateFirstTime = true;
    Image image;
    Graphics2D g;

    public static void main(String[] args) {
        Battleship frame = new Battleship();
        frame.setSize(Window.WINDOW_WIDTH, Window.WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Battleship() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {

                if (e.BUTTON1 == e.getButton() ) {
                    Board.AddPiece(e.getX(),e.getY());
                }

                if (e.BUTTON2 == e.getButton()) {
                }
                if (e.BUTTON3 == e.getButton()) {
                    reset();
               }
                repaint();
            }
        });
            

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {

        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
                if (e.VK_R == e.getKeyCode()) {
                    Board.Rotate();
                } else if (e.VK_SPACE == e.getKeyCode()) {
                    Board.Load();
                } else if (e.VK_LEFT == e.getKeyCode()) {
                } else if (e.VK_RIGHT == e.getKeyCode()) {
                    Board.unLoad();
                } else if (e.VK_ESCAPE == e.getKeyCode()) {
                    reset();
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }
////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || Window.xsize != getSize().width || Window.ysize != getSize().height) {
            Window.xsize = getSize().width;
            Window.ysize = getSize().height;
            image = createImage(Window.xsize, Window.ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        
        g.setColor(Color.black);
        g.fillRect(0, 0, Window.xsize, Window.ysize);

        int x[] = {Window.getX(0), Window.getX(Window.getWidth2()), Window.getX(Window.getWidth2()), Window.getX(0), Window.getX(0)};
        int y[] = {Window.getY(0), Window.getY(0), Window.getY(Window.getHeight2()), Window.getY(Window.getHeight2()), Window.getY(0)};
        int Bx[] = {Window.getX(0) ,Window.getX(Window.getWidth2()) ,Window.getX(Window.getWidth2()) ,Window.getX(0) ,Window.getX(0)};
        int By[] = {Window.getY(Window.getHeight2())/2+35 ,Window.getY(Window.getHeight2())/2+35 ,Window.getY(Window.getHeight2()) ,Window.getY(Window.getHeight2()) ,Window.getY(Window.getHeight2())/22+35};
//fill border
        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(x, y, 4);
        
        g.setColor(Color.BLUE);
        g.fillPolygon(Bx, By, 4);
// draw border
        g.setColor(Color.blue);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
        


        
              
        Board.Draw(g);

        gOld.drawImage(image, 0, 0, null);
    }

////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = .1;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
    
/////////////////////////////////////////////////////////////////////////
    public void reset() {
    Board.Reset();
    Board.sparseArray.clear();
    Player.Reset();

    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {

        if (animateFirstTime) {
            animateFirstTime = false;
            if (Window.xsize != getSize().width || Window.ysize != getSize().height) {
                Window.xsize = getSize().width;
                Window.ysize = getSize().height;
            }

            reset();

        }

        
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }

}

 