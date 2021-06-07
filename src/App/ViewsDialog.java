package App;

import java.awt.*;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Figure3D.Figure;

public class ViewsDialog extends JPanel{
    Figure fig;
    JDialog dialog;
    private boolean reactive = false;

    public ViewsDialog(JFrame win, Figure figure){
        dialog = new JDialog(win, false);
        setBackground(new Color(238,238,238));
        fig = figure;

        dialog.setUndecorated(true);
        dialog.setSize(150,450);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setFocusable(false);
        dialog.add(this, BorderLayout.CENTER);
    }

    public void setReactive(boolean reactive){
        this.reactive = reactive;
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
        g2.setPaint(new Color(200,200,200));
        g2.fillRoundRect(14, 28, 123, 115, 30, 30);
        g2.fillRoundRect(14, 168, 123, 115, 30, 30);
        g2.fillRoundRect(14, 306, 123, 115, 30, 30);

        g2.setClip(14, 28, 123, 115);
        fig.frontView(g2,73,100,reactive);
        g2.setClip(14, 168, 123, 115);
        fig.topView  (g2,73,240,reactive);
        g2.setClip(14, 306, 123, 115);
        fig.sideView (g2,73,390,reactive);
        g2.setClip(0,0,getWidth(), getHeight());

        g2.setFont(new Font("Arial", Font.BOLD, 13));
        g2.setPaint(getBackground());
        g2.fillRoundRect(0,  39, 121, 23, 20, 20);
        g2.fillRoundRect(0, 178, 121, 23, 20, 20);
        g2.fillRoundRect(0, 316, 121, 23, 20, 20);
        g2.setPaint(new Color(100,100,100));
        g2.drawString("Vista frontal",  25, 55);
        g2.drawString("Vista superior", 25, 194);
        g2.drawString("Vista lateral",  25, 332);

    }
}
