package App;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Figure3D.Figure;

import java.awt.*;
import java.awt.event.*;

public class Home extends JPanel{
    JFrame win;
    Figure f;
    JSlider sldX, sldY, sldZ, sldS;
    boolean arrastrar = false;
    int rotaX, rotaY, rotaZ;

    public Home(){
        win = new JFrame("Transformaciones en 3D");
        win.setSize(1080,720);
        win.add(this);
        f = new Figure();
        rotaX = rotaY = rotaZ = 0;

        sldX = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sldX.setMinorTickSpacing(15);
        sldX.setMajorTickSpacing(90);
        sldX.setPaintTicks(true);
        sldX.setPaintLabels(true);
        sldX.setPaintTrack(true);
        JPanel ps1 = new JPanel(new GridLayout(1,1));
        TitledBorder tb1 = new TitledBorder("Rotar en eje X");
        tb1.setTitleJustification(TitledBorder.CENTER);
        ps1.setBorder(tb1);
        ps1.add(sldX);
        sldY = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sldY.setMinorTickSpacing(15);
        sldY.setMajorTickSpacing(90);
        sldY.setPaintTicks(true);
        sldY.setPaintLabels(true);
        JPanel ps2 = new JPanel(new GridLayout(1,1));
        TitledBorder tb2 = new TitledBorder("Rotar en eje Y");
        tb2.setTitleJustification(TitledBorder.CENTER);
        ps2.setBorder(tb2);
        ps2.add(sldY);
        sldZ = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sldZ.setMinorTickSpacing(15);
        sldZ.setMajorTickSpacing(90);
        sldZ.setPaintTicks(true);
        sldZ.setPaintLabels(true);
        JPanel ps3 = new JPanel(new GridLayout(1,1));
        TitledBorder tb3 = new TitledBorder("Rotar en eje Z");
        tb3.setTitleJustification(TitledBorder.CENTER);
        ps3.setBorder(tb3);
        ps3.add(sldZ);

        JPanel south = new JPanel(new GridLayout(1,3));
        south.add(ps1);
        south.add(ps2);
        south.add(ps3);
        win.add(south, BorderLayout.SOUTH);
        
        sldS = new JSlider(JSlider.VERTICAL, 0, 2300, 1000);
        sldS.setMinorTickSpacing(50);
        sldS.setMajorTickSpacing(100);
        sldS.setPaintTicks(true);
        sldS.setPaintLabels(true);
        win.add(sldS, BorderLayout.EAST);

        sldX.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if(sldX.getValue()!=rotaX){
                    f.rotacionXYZH(-rotaX, 0, 0);
                    rotaX = sldX.getValue();
                    f.rotacionXYZH(rotaX, 0, 0);
                    repaint();
                    System.out.println("x"+sldX.getValue());
                }
            }
        });
        sldY.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if(sldY.getValue()!=rotaY){
                    f.rotacionXYZH(0, -rotaY, 0);
                    rotaY = sldY.getValue();
                    f.rotacionXYZH(0, rotaY, 0);
                    repaint();
                    System.out.println("y"+sldY.getValue());
                }
            }
        });
        sldZ.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                if(sldZ.getValue()!=rotaZ){
                    f.rotacionXYZH(0, 0, -rotaZ);
                    rotaZ = sldZ.getValue();
                    f.rotacionXYZH(0, 0, rotaZ);
                    repaint();
                    System.out.println("z"+sldZ.getValue());
                }
            }
        });
        sldS.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                int dist = sldS.getValue();
                //f.setDist(dist);
                repaint();
            }
        });

        addMouseWheelListener(new MouseWheelListener(){
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int sentido = e.getWheelRotation();
                if(sentido>0)
                    f.scale(1.05);
                else if (sentido<0)
                    f.scale(0.95);
                repaint();
            }
        });

        addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e) {
                int cx = e.getX(), cy = e.getY();
                //arrastrar = f.dentro(cx, cy);
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                if(arrastrar){
                    int cx = e.getX(), cy = e.getY();
                    //f.mover(cx, cy);
                    repaint();
                }
            }
        });

        win.setVisible(true);
        win.setLocationRelativeTo(null);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Características de presentación del renderizado
        g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON );
        g2.setRenderingHint( RenderingHints.KEY_RENDERING,
        RenderingHints.VALUE_RENDER_QUALITY );
        // f.conv2D();
        // f.dibujar(g);
        // f.vistaSup(g);
        // f.vistaLat(g);
        // f.vistaFro(g);
        f.paint(g2);
    }
}