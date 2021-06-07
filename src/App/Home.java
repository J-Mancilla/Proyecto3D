package App;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Figure3D.Figure;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;

public class Home extends JPanel implements ChangeListener{
    JFrame win;
    Figure f;
    JSlider sldX, sldY, sldZ, sldS;
    JLabel lblDist;
    boolean move = false;
    int tx, ty, x, y;
    String lblX, lblY;
    ViewsDialog views;
    JToggleButton showBtn, reactive;
    
    //int rotaX, rotaY, rotaZ;

    public Home(){
        win = new JFrame("Transformaciones en 3D");
        win.setSize(1080,720);
        win.setBackground(new Color(238,238,238));
        setBackground(new Color(238,238,238));
        win.add(this);
        f = new Figure();
        views = new ViewsDialog(win, f);
        lblX = lblY = "";
        //rotaX = rotaY = rotaZ = 0;

        genRotPanel();
        createToolBar();
        
        sldS = new JSlider(JSlider.VERTICAL, 0, 15000, 5500){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                // Características de presentación del renderizado
                g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
                g2.setRenderingHint( RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY );
                g2.setPaint(new Color(200,200,200));
                g2.fillRect(0, 0, getWidth(), 30);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.setPaint(new Color(180,180,180));
                g2.fillRoundRect(20, 15, 10, getHeight()-30, 10, 10);
                g2.drawImage(new ImageIcon(getClass().getResource("/img/person.png")).getImage(), 13, (int)((getHeight()-30)-(getHeight()-30)/(getMaximum()*1.0)*getValue()), 24, 24, this);
            }
        };
        sldS.setPaintTrack(false);
        sldS.setPreferredSize(new Dimension(50,500));
        lblDist = new JLabel(sldS.getValue()+"", JLabel.CENTER){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                // Características de presentación del renderizado
                g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON );
                g2.setRenderingHint( RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY );
                g2.setPaint(new Color(200,200,200));
                g2.fillRect(0, 20, getWidth(), 30);
                g2.fillRoundRect(0, 0, getWidth(), getHeight() , 25, 25);
                super.paintComponent(g);
            }
        };
        lblDist.setFont(new Font("Arial", Font.BOLD, 13));
        lblDist.setForeground(new Color(100,100,100));
        lblDist.setBorder(new EmptyBorder(10,0,10,0));
        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.setBackground(getBackground());
        eastPanel.add(lblDist, BorderLayout.NORTH);
        eastPanel.add(sldS, BorderLayout.CENTER);
        
        win.add(eastPanel, BorderLayout.EAST);

        // sldX.addChangeListener(new ChangeListener(){
        //     public void stateChanged(ChangeEvent e) {
        //         if(sldX.getValue()!=rotaX){
        //             f.rotateX(-rotaX);
        //             rotaX = sldX.getValue();
        //             f.rotateX(rotaX);
        //             repaint();
        //         }
        //     }
        // });
        // sldY.addChangeListener(new ChangeListener(){
        //     public void stateChanged(ChangeEvent e) {
        //         if(sldY.getValue()!=rotaY){
        //             f.rotateY(-rotaY);
        //             rotaY = sldY.getValue();
        //             f.rotateY(rotaY);
        //             repaint();
        //         }
        //     }
        // });
        // sldZ.addChangeListener(new ChangeListener(){
        //     public void stateChanged(ChangeEvent e) {
        //         if(sldZ.getValue()!=rotaZ){
        //             f.rotateZ(-rotaZ);
        //             rotaZ = sldZ.getValue();
        //             f.rotateZ(rotaZ);
        //             repaint();
        //         }
        //     }
        // });
        sldX.addChangeListener(this);
        sldY.addChangeListener(this);
        sldZ.addChangeListener(this);
        sldS.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
                f.setDistance(sldS.getValue());
                repaint();
                sldS.repaint();
                lblDist.setText(sldS.getValue()+"");
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
                x = e.getX(); y = e.getY();
                move = f.contains(x, y);
                tx=0; ty=0;
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                move = false;
                repaint();
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e) {
                if(move){
                    //System.out.println("x="+x+" y="+y);
                    int cx = e.getX()-x, cy = e.getY()-y;
                    f.move(-tx, -ty);
                    f.move(cx, cy);
                    tx = cx; ty = cy;
                    lblX = "dX: "+tx+" px";
                    lblY = "dY: "+ty+" px";
                    repaint();
                    setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                }
            }
            @Override
            public void mouseMoved(MouseEvent e) {
                if(f.contains(e.getX(), e.getY()))
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                else
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        win.addComponentListener(new ComponentAdapter(){
            @Override
            public void componentMoved(ComponentEvent e) {
                if(showBtn.isSelected()){
                    Home.this.setDialogLocation();
                }
            }
        });

        win.setVisible(true);
        win.setResizable(false);
        win.setLocationRelativeTo(null);
        win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void genRotPanel() {
        Color fore = new Color(200,200,200);
        sldX = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sldX.setMinorTickSpacing(15);
        sldX.setMajorTickSpacing(90);
        sldX.setPaintTicks(true);
        sldX.setPaintLabels(true);
        sldX.setPaintTrack(true);
        sldX.setForeground(fore);
        JPanel ps1 = new JPanel(new GridLayout(1,1));
        ps1.setBackground(new Color(100,100,100));
        ps1.setBorder(new TitledBorder(new EmptyBorder(10,30,10,30), "Rotar en eje X", TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM, new Font("Arial", Font.BOLD, 13), fore));
        ps1.add(sldX);
        sldY = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sldY.setMinorTickSpacing(15);
        sldY.setMajorTickSpacing(90);
        sldY.setPaintTicks(true);
        sldY.setPaintLabels(true);
        sldY.setForeground(fore);
        JPanel ps2 = new JPanel(new GridLayout(1,1));
        ps2.setBackground(new Color(100,100,100));
        ps2.setBorder(new TitledBorder(new EmptyBorder(10,30,10,30), "Rotar en eje Y", TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM, new Font("Arial", Font.BOLD, 13), fore));
        ps2.add(sldY);
        sldZ = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        sldZ.setMinorTickSpacing(15);
        sldZ.setMajorTickSpacing(90);
        sldZ.setPaintTicks(true);
        sldZ.setPaintLabels(true);
        sldZ.setForeground(fore);
        JPanel ps3 = new JPanel(new GridLayout(1,1));
        ps3.setBackground(new Color(100,100,100));
        ps3.setBorder(new TitledBorder(new EmptyBorder(10,30,10,30), "Rotar en eje Z", TitledBorder.LEFT, TitledBorder.ABOVE_BOTTOM, new Font("Arial", Font.BOLD, 13), fore));
        ps3.add(sldZ);

        JPanel south = new JPanel(new GridLayout(1,3));
        south.add(ps1);
        south.add(ps2);
        south.add(ps3);
        win.add(south, BorderLayout.SOUTH);
    }

    private void createToolBar() {
        JToolBar toolBar = new JToolBar("", JToolBar.HORIZONTAL);
        toolBar.setBackground(getBackground());
        JToggleButton solidBtn = new JToggleButton(new ImageIcon(getClass().getResource("/img/BtnTra.png")));
        solidBtn.setSelectedIcon(new ImageIcon(getClass().getResource("/img/BtnSol.png")));
        solidBtn.setToolTipText("Cambiar el estilo de visualización");
        solidBtn.setFocusable(false);
        solidBtn.setBorder(null);
        solidBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        solidBtn.setSelected(false);
        solidBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                f.setSolid(solidBtn.isSelected());
                repaint();
            }
        });

        showBtn = new JToggleButton(new ImageIcon(getClass().getResource("/img/Show.png")));
        showBtn.setSelectedIcon(new ImageIcon(getClass().getResource("/img/NoShow.png")));
        showBtn.setToolTipText("Mostrar/Ocultar vistas de la figura");
        showBtn.setFocusable(false);
        showBtn.setBorder(null);
        showBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        showBtn.setSelected(false);
        showBtn.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(showBtn.isSelected()){
                    setDialogLocation();
                    views.dialog.setVisible(true);
                    reactive.setVisible(true);
                }else{
                    views.dialog.setVisible(false);
                    reactive.setVisible(false);
                }
                repaint();
            }
        });

        reactive = new JToggleButton(new ImageIcon(getClass().getResource("/img/Reactive.png")));
        reactive.setSelectedIcon(new ImageIcon(getClass().getResource("/img/ReactiveS.png")));
        reactive.setToolTipText("Activar/Desactivar vistas dinámicas (Rotaciones)");
        reactive.setFocusable(false);
        reactive.setBorder(null);
        reactive.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reactive.setSelected(false);
        reactive.setVisible(false);
        reactive.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                views.setReactive(reactive.isSelected());
                views.repaint();
                repaint();
            }
        });
        
        Action aRes=new AbstractAction("", new ImageIcon(getClass().getResource("/img/ResBtn.png"))){
            public void actionPerformed(ActionEvent arg0) {
                f.reset(); 
                sldS.setValue(5500);
                lblDist.setText("5500");
                sldX.setValue(0);
                sldY.setValue(0);
                sldZ.setValue(0);
                solidBtn.setSelected(false);
                repaint();
            }};
        aRes.putValue(Action.SHORT_DESCRIPTION,"Estaurar figura a su estado original");
        JButton btnRes = new JButton(aRes);
        constButton(getClass().getResource("/img/ResBtnH.png"),   btnRes);
        toolBar.add(btnRes);

        JPanel space1 = new JPanel();
        space1.setMaximumSize(new Dimension(30,10));
        space1.setBackground(getBackground());
        toolBar.add(space1);
        
        JPanel refButtons = new JPanel();
        refButtons.setBorder(new TitledBorder(new LineBorder(new Color(230,230,230), 3, true), "Reflejar", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial",Font.BOLD,13), new Color(150,150,150)));
        refButtons.setMaximumSize(new Dimension(240,70));
        toolBar.add(refButtons);
            
        Action aRef1=new AbstractAction("", new ImageIcon(getClass().getResource("/img/Ref1.png"))){
            public void actionPerformed(ActionEvent arg0) {
                f.scale(1,-1,1);
                repaint();
            }};
        aRef1.putValue(Action.SHORT_DESCRIPTION,"Reflejar figura");
        JToggleButton btnRef1 = new JToggleButton(aRef1);
        constButton(getClass().getResource("/img/Ref1H.png"),   btnRef1);
        btnRef1.setBorder(new EmptyBorder(0,5,0,5));
        refButtons.add(btnRef1);

        Action aRef2=new AbstractAction("", new ImageIcon(getClass().getResource("/img/Ref3.png"))){
            public void actionPerformed(ActionEvent arg0) {
                f.scale(1,1,-1);
                repaint();
            }};
        aRef2.putValue(Action.SHORT_DESCRIPTION,"Reflejar figura");
        JToggleButton btnRef2 = new JToggleButton(aRef2);
        constButton(getClass().getResource("/img/Ref3H.png"),   btnRef2);
        btnRef2.setBorder(new EmptyBorder(0,5,0,5));
        refButtons.add(btnRef2);

        Action aRef3=new AbstractAction("", new ImageIcon(getClass().getResource("/img/Ref2.png"))){
            public void actionPerformed(ActionEvent arg0) {
                f.scale(-1,1,1);
                repaint();
            }};
        aRef3.putValue(Action.SHORT_DESCRIPTION,"Reflejar figura");
        JToggleButton btnRef3 = new JToggleButton(aRef3);
        constButton(getClass().getResource("/img/Ref2H.png"),   btnRef3);
        btnRef3.setBorder(new EmptyBorder(0,5,0,5));
        refButtons.add(btnRef3);


        JPanel space2 = new JPanel();
        space2.setMaximumSize(new Dimension(30,10));
        space2.setBackground(getBackground());
        toolBar.add(space2);
        
        JPanel scaButtons = new JPanel();
        scaButtons.setBorder(new TitledBorder(new LineBorder(new Color(230,230,230), 3, true), "Escalar", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial",Font.BOLD,13), new Color(150,150,150)));
        scaButtons.setMaximumSize(new Dimension(115,70));
        toolBar.add(scaButtons);

        Action aSca1=new AbstractAction("", new ImageIcon(getClass().getResource("/img/Sca1.png"))){
            public void actionPerformed(ActionEvent arg0) {
                f.scale(1.1);
                repaint();
            }};
        aSca1.putValue(Action.SHORT_DESCRIPTION,"Hacer la figura 10% más grande");
        JButton btnSca1 = new JButton(aSca1);
        constButton(getClass().getResource("/img/Sca1H.png"),   btnSca1);
        btnSca1.setBorder(new EmptyBorder(0,5,0,5));
        scaButtons.add(btnSca1);

        Action aSca2=new AbstractAction("", new ImageIcon(getClass().getResource("/img/Sca2.png"))){
            public void actionPerformed(ActionEvent arg0) {
                f.scale(0.9);
                repaint();
            }};
        aSca2.putValue(Action.SHORT_DESCRIPTION,"Hacer la figura 10% más pequeña");
        JButton btnSca2 = new JButton(aSca2);
        constButton(getClass().getResource("/img/Sca2H.png"),   btnSca2);
        btnSca2.setBorder(new EmptyBorder(0,5,0,5));
        scaButtons.add(btnSca2);
        
        JPanel space3 = new JPanel();
        space3.setBackground(getBackground());
        space3.setMaximumSize(new Dimension(210,10));
        toolBar.add(space3);
        toolBar.add(solidBtn);

        JPanel space4 = new JPanel();
        space4.setBackground(getBackground());
        space4.setMaximumSize(new Dimension(40,10));
        toolBar.add(space4);
        toolBar.add(showBtn);

        JPanel space5 = new JPanel();
        space5.setBackground(getBackground());
        space5.setMaximumSize(new Dimension(30,10));
        toolBar.add(space5);
        toolBar.add(reactive);
        

        toolBar.setFloatable(false);
        win.add(toolBar, BorderLayout.NORTH);
    }

    private void setDialogLocation(){
        //Error al inicio por tratar de leer la posición sin tener el frame en ventana
        try {
            Point p = win.getLocationOnScreen();
            int x = (int)p.getX()-100;
            int y = (int)p.getY()+130;
            views.dialog.setLocation(x,y);
        } catch (IllegalComponentStateException e) {}
    }

    private void constButton(URL url, JButton btn){
        btn.setBackground(getBackground());
        btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setRolloverIcon(new ImageIcon(url));
    }
    private void constButton(URL url, JToggleButton btn){
        btn.setSelectedIcon(new ImageIcon(url));
        btn.setFocusable(false);
        btn.setSelected(false);
        btn.setBackground(getBackground());
        btn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
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
        if(reactive.isSelected())
            views.repaint();
        if(move){
            Font f = new Font("Arial",Font.PLAIN,10);
            g2.setFont(f);
            g2.setPaint(new Color(100,100,100));
            int posX = tx+x+15,
                posY = ty+y+15;
            g2.fillRoundRect(posX, posY, 70, 30, 15, 15);
            g2.setPaint(new Color(200,200,200));
            g2.drawRoundRect(posX, posY, 70, 30, 15, 15);
            g2.drawString(lblX, posX+10, posY+14);
            g2.drawString(lblY, posX+10, posY+25);
        }
        
    }

    

    @Override
    public void stateChanged(ChangeEvent e) {
        f.rotate(sldX.getValue(), sldY.getValue(), sldZ.getValue());
        repaint();
    }

}