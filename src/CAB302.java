import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class CAB302 extends Frame implements MouseMotionListener,MouseListener,ActionListener
{
    private static CAB302 paint =new CAB302();

    private static MenuBar mb=new MenuBar();
    private static Menu mu1 =new Menu("File");
    private static Menu mu2 =new Menu("Color");
    private static Menu mu3 =new Menu("Shape");
    private static Menu mu4 =new Menu("Other");
    private static Menu mu5 =new Menu("Draw Style");
    private static MenuItem muit1 =new MenuItem("Open");
    private static MenuItem muit2 =new MenuItem("Save");
    private static MenuItem muit3 =new MenuItem("Plot");
    private static MenuItem muit4 =new MenuItem("Polyline");
    private static MenuItem muit5 =new MenuItem("Rect");
    private static MenuItem muit6 =new MenuItem("Oval");
    private  static MenuItem muit7 =new MenuItem("Line");
    private static MenuItem muit8 =new MenuItem("Pencil");
    private static MenuItem muit9 =new MenuItem("Triangle");
    private static MenuItem muit10 =new MenuItem("Close");
    private static MenuItem muit11 =new MenuItem("Clean");
    private static MenuItem muit12 =new MenuItem("Change Color");
    private static MenuItem muit13 =new MenuItem("Static");
    private static MenuItem muit14 =new MenuItem("Fill");

    private File file2 = new File("test.vec");
    private File file;
    private final JFileChooser fc = new JFileChooser();

    private int x1;
    private int x2;
    private int y1;
    private int y2;

    private int flag=1;
    private Color pencolor;
    private Color color;

    private int[] polytriX = new int[100];
    private int[] polytriY = new int[100];
    private int polyIndex = 0;

    private static int penWidth =1;
    private static int graphStyle =0;
    private static int mode =0;

    public static void main(String[] args)
    {
        paint.setMenuBar(mb);
        paint.setTitle("Paint");
        paint.setSize(800,800);
        paint.setResizable(false);
        paint.addMouseListener(paint);
        paint.addMouseMotionListener(paint);
        paint.setVisible(true);
        paint.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){System.exit(0);}
        });


        mb.add(mu1);
        mb.add(mu2);
        mb.add(mu3);
        mb.add(mu5);
        mb.add(mu4);

        mu1.add(muit1);
        mu1.add(muit2);

        mu2.add(muit12);

        mu3.add(muit8);
        mu3.add(muit3);
        mu3.add(muit7);
        mu3.add(muit5);
        mu3.add(muit4);
        mu3.add(muit6);
        mu3.add(muit9);


        mu4.add(muit11);
        mu4.add(muit10);

        mu5.add(muit13);
        mu5.add(muit14);

        muit1.addActionListener(paint);
        muit2.addActionListener(paint);
        muit3.addActionListener(paint);
        muit4.addActionListener(paint);
        muit5.addActionListener(paint);
        muit6.addActionListener(paint);
        muit10.addActionListener(paint);
        muit11.addActionListener(paint);
        muit12.addActionListener(paint);
        muit13.addActionListener(paint);
        muit14.addActionListener(paint);
        muit7.addActionListener(paint);
        muit8.addActionListener(paint);
        muit9.addActionListener(paint);


    }
    public void actionPerformed(ActionEvent e)
    {

        MenuItem mi=(MenuItem) e.getSource();

        if(mi== muit10)
            paint.dispose();
        else if(mi== muit11){
            polyIndex =0;
            repaint();
        }
        if(mi== muit4)
            mode =3;
        else if(mi== muit5)
            mode =2;
        else if(mi== muit6)
            mode =4;
        else if(mi== muit7)
            mode =1;
        else if(mi== muit8)
            mode =0;
        else if(mi== muit9)
            mode =5;
        else if(mi== muit3)
            mode =6;

        if(mi== muit13)
            graphStyle =0;
        else if(mi== muit14)
            graphStyle =1;


        if(mi== muit1) {
         fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
         if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
             file = fc.getSelectedFile();
         }
         System.out.println(file.getName());
        }
        else if(mi== muit2)
        {
            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION){
                file2 = fc.getSelectedFile();
                try {
                    PrintWriter output = new PrintWriter(file2);
                    output.println("TEST");
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }

        }
        if(mi== muit12)
             color = JColorChooser.showDialog(CAB302.this,
                    "Choose a color", pencolor);
        if (color != null) {
            pencolor = color;
        }
    }

    public void update(Graphics g)
    {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,800,800);
        g.setColor(Color.WHITE);
        paint.setBackground(getBackground());
        paint(g);
    }
    public void paint(Graphics g)
    {
        if(flag==0){
            g.setColor(pencolor);
            Graphics2D g2 = (Graphics2D) g.create();
            Stroke stroke;
            if(graphStyle ==0){
                stroke = new BasicStroke(penWidth,  BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10) ;
                g2.setStroke(stroke);
            }

            if(mode ==0)
                g2.drawLine(x1,y1,x2,y2);
            if(mode ==1)
                g2.drawLine(x1,y1,x2,y2);
            if(mode ==2){
                int width = Math.abs(x2 - x1);
                int height = Math.abs(y2 - y1);
                if(graphStyle ==0) {
                    g2.drawRect(Math.min(x1, x2), Math.min(y1, y2), width, height);
                } else {
                    g2.fillRect(Math.min(x1, x2), Math.min(y1, y2), width, height);
                }
            }
            if(mode ==4){
                int width = Math.abs(x2 - x1);
                int height = Math.abs(y2 - y1);
                if(graphStyle ==0) {
                    g2.drawOval(Math.min(x1, x2), Math.min(y1, y2), width, height);
                }else{
                    g2.fillOval(Math.min(x1, x2), Math.min(y1, y2), width, height);
                }
            }
            if(mode ==5){

                if(polyIndex ==3){
                    g2.drawPolygon(polytriX, polytriY, 3);
                    polyIndex =0;
                }
            }
            if(mode ==6){
                g2.fillOval(Math.min(x1, x1), Math.min(y1, y1), 5, 5);
            }
            flag=1;
        }
    }

    public void mousePressed(MouseEvent e)
    {

        x1=e.getX();
        y1=e.getY();

        if(mode ==6){
            Graphics g=getGraphics();
            flag=0;
            paint(g);
        }

    }
    public void mouseDragged(MouseEvent e)
    {

        if(mode ==0)
        {
            Graphics g=getGraphics();
            x2=e.getX();
            y2=e.getY();
            flag=0;
            paint(g);
            x1=x2;
            y1=y2;
        }
    }
    public void mouseMoved(MouseEvent e){}
    public void mouseReleased(MouseEvent e){

        if(mode ==1)
        {
            Graphics g=getGraphics();
            x2=e.getX();
            y2=e.getY();
            flag=0;
            paint(g);
        }
        if(mode ==2)
        {
            Graphics g=getGraphics();
            x2=e.getX();
            y2=e.getY();
            flag=0;
            paint(g);
        }

        if(mode ==3)
        {
            Graphics g=getGraphics();
            x2=e.getX();
            y2=e.getY();
            flag=0;
            paint(g);
        }

        if(mode ==4)
        {
            Graphics g=getGraphics();
            x2=e.getX();
            y2=e.getY();
            flag=0;
            paint(g);
        }
    }
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseClicked(MouseEvent e){
        if(mode ==5){
            Graphics g=getGraphics();
            polytriX[polyIndex] = e.getX();
            polytriY[polyIndex] = e.getY();
            polyIndex++;
            flag=0;
            paint(g);
        }
    }
}