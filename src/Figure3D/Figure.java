package Figure3D;

import java.util.ArrayList;
import java.awt.*;
import java.awt.geom.*;

public class Figure {
    private ArrayList<FigureFace> faces;
    int distance = 1000;
    int mz = -350;
    int despx, despy;
    Color color;

    public Figure(){
        faces = genFigure();
        color = new Color(150,255,255);
        despx = 300; despy = 200;
    }

    public void setDistance(int dist){
        distance = dist;
    }

    public void paint(Graphics2D g2){
        //Elegir orden de pintado
        ArrayList<double[]> arr = new ArrayList<>();
        for (int i = 0; i < faces.size(); i++) 
            arr.add(new double[]{i, faces.get(i).getMaxZ()});
        order(arr);

        for (double[] ds : arr) {
           FigureFace f = faces.get((int)ds[0]);
           Shape f2D = f.get2DShape(distance, mz, despx, despy);
           g2.setPaint(color);
           g2.fill(f2D);
           g2.setPaint(new Color(0,0,0,190));
           g2.setStroke(new BasicStroke(1));
           g2.draw(f2D);
           //System.out.println(f2D.getBounds2D());
           int opacity = (int)(255*f.getSlope()*0.6);
        //    System.out.println("Slope -> "+f.getSlope());
        //    System.out.println(opacity);
           if(opacity>255) opacity=255;
           if(opacity<0) opacity=0;
           g2.setPaint(new Color(0,0,0,opacity));
           g2.fill(f2D);
        }
    }



    public void move(int cx, int cy){
        despx = cx;
        despy = cy;
    }

    public void scale(double s){
        for (FigureFace figureFace : faces) {
            for (int i = 0; i < figureFace.face.length; i++) {
                figureFace.face[i].x = figureFace.face[i].x*=s;
                figureFace.face[i].y = figureFace.face[i].y*=s;
                figureFace.face[i].z = figureFace.face[i].z*=s;
            }
        }
    }

    private void order(ArrayList<double[]> arr) {
        for (int x = 0; x < arr.size(); x++) {
            for (int i = 0; i < arr.size()-x-1; i++) {
                if(arr.get(i)[1] < arr.get(i+1)[1]){
                    double tmp[] = {arr.get(i+1)[0],arr.get(i+1)[1]};
                    arr.set(i+1, arr.get(i));
                    arr.set(i, tmp);
                }
            }
        } 
    }



    private ArrayList<FigureFace> genFigure() {
        ArrayList<FigureFace> f = new ArrayList<>();
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.5, -7.25, 12.5),
            new Point3D(-1.52, -7.25, 12.5),
            new Point3D(-1.52, -7.25, 16.5),
            new Point3D(-1.02, -7.25, 16.5),
            new Point3D(-1.02, -7.25, 15.5),
            new Point3D(1.02, -7.25, 15.5),
            new Point3D(1.02, -7.25, 16.5),
            new Point3D(1.52, -7.25, 12.5),
            new Point3D(12.5, -7.25, 12.5),
            new Point3D(12.5, -7.25, 1.52),
            new Point3D(16.5, -7.25, 1.52),
            new Point3D(16.5, -7.25, 1.02),
            new Point3D(15.5, -7.25, 1.02),
            new Point3D(15.5, -7.25, -1.02),
            new Point3D(16.5, -7.25, -1.02),
            new Point3D(16.5, -7.25, -1.52),
            new Point3D(12.5, -7.25, -1.52),
            new Point3D(12.5, -7.25, -12.5),
            new Point3D(1.52, -7.25, -12.5),
            new Point3D(1.52, -7.25, -16.5),
            new Point3D(1.02, -7.25, -15.5),
            new Point3D(1.02, -7.25, -15.5),
            new Point3D(-1.02, -7.25, -15.5),
            new Point3D(-1.02, -7.25, -16.5),
            new Point3D(-1.52, -7.25, -16.5),
            new Point3D(-1.52, -7.25, -12.5),
            new Point3D(-12.5, -7.25, -12.5),
            new Point3D(-12.5, -7.25, -1.52),
            new Point3D(-16.5, -7.25, -1.52),
            new Point3D(-16.5, -7.25, -1.02),
            new Point3D(-15.5, -7.25, -1.02),
            new Point3D(-15.5, -7.25, 1.02),
            new Point3D(-16.5, -7.25, 1.02),
            new Point3D(-16.5, -7.25, 1.52),
            new Point3D(-12.5, -7.25, 1.52)
        }));
        //TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   TRIANGULOS   
        f.add(new FigureFace(new Point3D[]{
            new Point3D(1.52, -7.25, 12.5),
            new Point3D(1.52, -7.25, 16.5),
            new Point3D(1.52, 2.75, 4.52),
            new Point3D(1.52, 0.75, 4.52),
            new Point3D(1.52, 0.75, 6.50),
            new Point3D(1.52, -1.25, 6.50),
            new Point3D(1.52, -1.25, 8.50),
            new Point3D(1.52, -3.25, 8.50),
            new Point3D(1.52, -3.25, 10.50),
            new Point3D(1.52, -5.25, 10.50),
            new Point3D(1.52, -5.25, 12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.52, -7.25, 12.5),
            new Point3D(-1.52, -7.25, 16.5),
            new Point3D(-1.52, 2.75, 4.52),
            new Point3D(-1.52, 0.75, 4.52),
            new Point3D(-1.52, 0.75, 6.50),
            new Point3D(-1.52, -1.25, 6.50),
            new Point3D(-1.52, -1.25, 8.50),
            new Point3D(-1.52, -3.25, 8.50),
            new Point3D(-1.52, -3.25, 10.50),
            new Point3D(-1.52, -5.25, 10.50),
            new Point3D(-1.52, -5.25, 12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.5 , -7.25, -1.52),
            new Point3D(16.5 , -7.25, -1.52),
            new Point3D(4.52 , 2.75 , -1.52),
            new Point3D(4.52 , 0.75 , -1.52),
            new Point3D(6.50 , 0.75 , -1.52),
            new Point3D(6.50 , -1.25, -1.52),
            new Point3D(8.50 , -1.25, -1.52),
            new Point3D(8.50 , -3.25, -1.52),
            new Point3D(10.50, -3.25, -1.52),
            new Point3D(10.50, -5.25, -1.52),
            new Point3D(12.50, -5.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.5 , -7.25, 1.52),
            new Point3D(16.5 , -7.25, 1.52),
            new Point3D(4.52 , 2.75 , 1.52),
            new Point3D(4.52 , 0.75 , 1.52),
            new Point3D(6.50 , 0.75 , 1.52),
            new Point3D(6.50 , -1.25, 1.52),
            new Point3D(8.50 , -1.25, 1.52),
            new Point3D(8.50 , -3.25, 1.52),
            new Point3D(10.50, -3.25, 1.52),
            new Point3D(10.50, -5.25, 1.52),
            new Point3D(12.50, -5.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.5 , -7.25, -1.52),
            new Point3D(-16.5 , -7.25, -1.52),
            new Point3D(-4.52 , 2.75 , -1.52),
            new Point3D(-4.52 , 0.75 , -1.52),
            new Point3D(-6.50 , 0.75 , -1.52),
            new Point3D(-6.50 , -1.25, -1.52),
            new Point3D(-8.50 , -1.25, -1.52),
            new Point3D(-8.50 , -3.25, -1.52),
            new Point3D(-10.50, -3.25, -1.52),
            new Point3D(-10.50, -5.25, -1.52),
            new Point3D(-12.50, -5.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.5 , -7.25, 1.52),
            new Point3D(-16.5 , -7.25, 1.52),
            new Point3D(-4.52 , 2.75 , 1.52),
            new Point3D(-4.52 , 0.75 , 1.52),
            new Point3D(-6.50 , 0.75 , 1.52),
            new Point3D(-6.50 , -1.25, 1.52),
            new Point3D(-8.50 , -1.25, 1.52),
            new Point3D(-8.50 , -3.25, 1.52),
            new Point3D(-10.50, -3.25, 1.52),
            new Point3D(-10.50, -5.25, 1.52),
            new Point3D(-12.50, -5.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(1.52, -7.25, -12.5),
            new Point3D(1.52, -7.25, -16.5),
            new Point3D(1.52, 2.75 , -4.52),
            new Point3D(1.52, 0.75 , -4.52),
            new Point3D(1.52, 0.75 , -6.50),
            new Point3D(1.52, -1.25, -6.50),
            new Point3D(1.52, -1.25, -8.50),
            new Point3D(1.52, -3.25, -8.50),
            new Point3D(1.52, -3.25, -10.50),
            new Point3D(1.52, -5.25, -10.50),
            new Point3D(1.52, -5.25, -12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.52, -7.25, -12.5),
            new Point3D(-1.52, -7.25, -16.5),
            new Point3D(-1.52, 2.75 , -4.52),
            new Point3D(-1.52, 0.75 , -4.52),
            new Point3D(-1.52, 0.75 , -6.50),
            new Point3D(-1.52, -1.25, -6.50),
            new Point3D(-1.52, -1.25, -8.50),
            new Point3D(-1.52, -3.25, -8.50),
            new Point3D(-1.52, -3.25, -10.50),
            new Point3D(-1.52, -5.25, -10.50),
            new Point3D(-1.52, -5.25, -12.50)
        }));

        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.52,  2.75, 4.52),
            new Point3D(-1.02,  2.75, 4.52),
            new Point3D(-1.02, -7.25, 16.5),
            new Point3D(-1.52, -7.25, 16.5)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(1.52,  2.75, 4.52),
            new Point3D(1.02,  2.75, 4.52),
            new Point3D(1.02, -7.25, 16.5),
            new Point3D(1.52, -7.25, 16.5)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(4.52,  2.75, 1.52),
            new Point3D(4.52,  2.75, 1.02),
            new Point3D(16.5, -7.25, 1.02),
            new Point3D(16.5, -7.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(4.52,  2.75, -1.52),
            new Point3D(4.52,  2.75, -1.02),
            new Point3D(16.5, -7.25, -1.02),
            new Point3D(16.5, -7.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.52,  2.75, -4.52),
            new Point3D(-1.02,  2.75, -4.52),
            new Point3D(-1.02, -7.25, -16.5),
            new Point3D(-1.52, -7.25, -16.5)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(1.52,  2.75, -4.52),
            new Point3D(1.02,  2.75, -4.52),
            new Point3D(1.02, -7.25, -16.5),
            new Point3D(1.52, -7.25, -16.5)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-4.52,  2.75, 1.52),
            new Point3D(-4.52,  2.75, 1.02),
            new Point3D(-16.5, -7.25, 1.02),
            new Point3D(-16.5, -7.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-4.52,  2.75, -1.52),
            new Point3D(-4.52,  2.75, -1.02),
            new Point3D(-16.5, -7.25, -1.02),
            new Point3D(-16.5, -7.25, -1.52)
        }));


        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.02,  2.75,  4.52),
            new Point3D(-1.02, -7.25, 15.50),
            new Point3D(-1.02, -7.25, 16.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(1.02,  2.75,  4.52),
            new Point3D(1.02, -7.25, 15.50),
            new Point3D(1.02, -7.25, 16.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 4.52,  2.75, -1.02),
            new Point3D(15.50, -7.25, -1.02),
            new Point3D(16.50, -7.25, -1.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 4.52,  2.75, 1.02),
            new Point3D(15.50, -7.25, 1.02),
            new Point3D(16.50, -7.25, 1.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.02,  2.75, - 4.52),
            new Point3D(-1.02, -7.25, -15.50),
            new Point3D(-1.02, -7.25, -16.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(1.02,  2.75, - 4.52),
            new Point3D(1.02, -7.25, -15.50),
            new Point3D(1.02, -7.25, -16.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 4.52,  2.75, -1.02),
            new Point3D(-15.50, -7.25, -1.02),
            new Point3D(-16.50, -7.25, -1.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 4.52,  2.75, 1.02),
            new Point3D(-15.50, -7.25, 1.02),
            new Point3D(-16.50, -7.25, 1.02)
        }));


        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.02,  2.75,  4.52),
            new Point3D( 1.02,  2.75,  4.52),
            new Point3D( 1.02, -7.25, 15.50),
            new Point3D(-1.02, -7.25, 15.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 4.52,  2.75, -1.02),
            new Point3D( 4.52,  2.75,  1.02),
            new Point3D(15.50, -7.25,  1.02),
            new Point3D(15.50, -7.25, -1.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-1.02,  2.75, - 4.52),
            new Point3D( 1.02,  2.75, - 4.52),
            new Point3D( 1.02, -7.25, -15.50),
            new Point3D(-1.02, -7.25, -15.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 4.52,  2.75, -1.02),
            new Point3D(- 4.52,  2.75,  1.02),
            new Point3D(-15.50, -7.25,  1.02),
            new Point3D(-15.50, -7.25, -1.02)
        }));


        //RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   RECTANGULOS   
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.50, -7.25, 12.50),
            new Point3D(-12.50, -5.25, 12.50),
            new Point3D( -1.52, -5.25, 12.50),
            new Point3D( -1.52, -7.25, 12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-10.50, -5.25, 10.50),
            new Point3D(-10.50, -3.25, 10.50),
            new Point3D( -1.52, -3.25, 10.50),
            new Point3D( -1.52, -5.25, 10.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -8.50, -3.25, 8.50),
            new Point3D( -8.50, -1.25, 8.50),
            new Point3D( -1.52, -1.25, 8.50),
            new Point3D( -1.52, -3.25, 8.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -6.50, -1.25, 6.50),
            new Point3D( -6.50,  0.75, 6.50),
            new Point3D( -1.52,  0.75, 6.50),
            new Point3D( -1.52, -1.25, 6.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -4.52,  0.75, 4.52),
            new Point3D( -4.52,  2.75, 4.52),
            new Point3D( -1.52,  2.75, 4.52),
            new Point3D( -1.52,  0.75, 4.52)
        }));
        
        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.50, -7.25, 12.50),
            new Point3D(12.50, -5.25, 12.50),
            new Point3D( 1.52, -5.25, 12.50),
            new Point3D( 1.52, -7.25, 12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(10.50, -5.25, 10.50),
            new Point3D(10.50, -3.25, 10.50),
            new Point3D( 1.52, -3.25, 10.50),
            new Point3D( 1.52, -5.25, 10.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 8.50, -3.25, 8.50),
            new Point3D( 8.50, -1.25, 8.50),
            new Point3D( 1.52, -1.25, 8.50),
            new Point3D( 1.52, -3.25, 8.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 6.50, -1.25, 6.50),
            new Point3D( 6.50,  0.75, 6.50),
            new Point3D( 1.52,  0.75, 6.50),
            new Point3D( 1.52, -1.25, 6.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 4.52,  0.75, 4.52),
            new Point3D( 4.52,  2.75, 4.52),
            new Point3D( 1.52,  2.75, 4.52),
            new Point3D( 1.52,  0.75, 4.52)
        }));


        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.50, -7.25, 12.50),
            new Point3D(12.50, -5.25, 12.50),
            new Point3D(12.50, -5.25,  1.52),
            new Point3D(12.50, -7.25,  1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(10.50, -5.25, 10.50),
            new Point3D(10.50, -3.25, 10.50),
            new Point3D(10.50, -3.25,  1.52),
            new Point3D(10.50, -5.25,  1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(8.50 , -3.25, 8.50),
            new Point3D(8.50 , -1.25, 8.50),
            new Point3D(8.50 , -1.25, 1.52),
            new Point3D(8.50 , -3.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(6.50 , -1.25, 6.50),
            new Point3D(6.50 ,  0.75, 6.50),
            new Point3D(6.50 ,  0.75, 1.52),
            new Point3D(6.50 , -1.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(4.52 ,  0.75, 4.52),
            new Point3D(4.52 ,  2.75, 4.52),
            new Point3D(4.52 ,  2.75, 1.52),
            new Point3D(4.52 ,  0.75, 1.52)
        }));
        
        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.50, -7.25, -12.50),
            new Point3D(12.50, -5.25, -12.50),
            new Point3D(12.50, -5.25, - 1.52),
            new Point3D(12.50, -7.25, - 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(10.50, -5.25, -10.50),
            new Point3D(10.50, -3.25, -10.50),
            new Point3D(10.50, -3.25, - 1.52),
            new Point3D(10.50, -5.25, - 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(8.50 , -3.25, -8.50),
            new Point3D(8.50 , -1.25, -8.50),
            new Point3D(8.50 , -1.25, -1.52),
            new Point3D(8.50 , -3.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(6.50 , -1.25, -6.50),
            new Point3D(6.50 ,  0.75, -6.50),
            new Point3D(6.50 ,  0.75, -1.52),
            new Point3D(6.50 , -1.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(4.52 ,  0.75, -4.52),
            new Point3D(4.52 ,  2.75, -4.52),
            new Point3D(4.52 ,  2.75, -1.52),
            new Point3D(4.52 ,  0.75, -1.52)
        }));

        


        //
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.50, -7.25, -12.50),
            new Point3D(-12.50, -5.25, -12.50),
            new Point3D( -1.52, -5.25, -12.50),
            new Point3D( -1.52, -7.25, -12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-10.50, -5.25, -10.50),
            new Point3D(-10.50, -3.25, -10.50),
            new Point3D( -1.52, -3.25, -10.50),
            new Point3D( -1.52, -5.25, -10.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -8.50, -3.25, -8.50),
            new Point3D( -8.50, -1.25, -8.50),
            new Point3D( -1.52, -1.25, -8.50),
            new Point3D( -1.52, -3.25, -8.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -6.50, -1.25, -6.50),
            new Point3D( -6.50,  0.75, -6.50),
            new Point3D( -1.52,  0.75, -6.50),
            new Point3D( -1.52, -1.25, -6.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -4.52,  0.75, -4.52),
            new Point3D( -4.52,  2.75, -4.52),
            new Point3D( -1.52,  2.75, -4.52),
            new Point3D( -1.52,  0.75, -4.52)
        }));
        
        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.50, -7.25, -12.50),
            new Point3D(12.50, -5.25, -12.50),
            new Point3D( 1.52, -5.25, -12.50),
            new Point3D( 1.52, -7.25, -12.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(10.50, -5.25, -10.50),
            new Point3D(10.50, -3.25, -10.50),
            new Point3D( 1.52, -3.25, -10.50),
            new Point3D( 1.52, -5.25, -10.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 8.50, -3.25, -8.50),
            new Point3D( 8.50, -1.25, -8.50),
            new Point3D( 1.52, -1.25, -8.50),
            new Point3D( 1.52, -3.25, -8.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 6.50, -1.25, -6.50),
            new Point3D( 6.50,  0.75, -6.50),
            new Point3D( 1.52,  0.75, -6.50),
            new Point3D( 1.52, -1.25, -6.50)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 4.52,  0.75, -4.52),
            new Point3D( 4.52,  2.75, -4.52),
            new Point3D( 1.52,  2.75, -4.52),
            new Point3D( 1.52,  0.75, -4.52)
        }));


        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.50, -7.25, 12.50),
            new Point3D(-12.50, -5.25, 12.50),
            new Point3D(-12.50, -5.25,  1.52),
            new Point3D(-12.50, -7.25,  1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-10.50, -5.25, 10.50),
            new Point3D(-10.50, -3.25, 10.50),
            new Point3D(-10.50, -3.25,  1.52),
            new Point3D(-10.50, -5.25,  1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-8.50 , -3.25, 8.50),
            new Point3D(-8.50 , -1.25, 8.50),
            new Point3D(-8.50 , -1.25, 1.52),
            new Point3D(-8.50 , -3.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-6.50 , -1.25, 6.50),
            new Point3D(-6.50 ,  0.75, 6.50),
            new Point3D(-6.50 ,  0.75, 1.52),
            new Point3D(-6.50 , -1.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-4.52 ,  0.75, 4.52),
            new Point3D(-4.52 ,  2.75, 4.52),
            new Point3D(-4.52 ,  2.75, 1.52),
            new Point3D(-4.52 ,  0.75, 1.52)
        }));
        
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.50, -7.25, -12.50),
            new Point3D(-12.50, -5.25, -12.50),
            new Point3D(-12.50, -5.25, - 1.52),
            new Point3D(-12.50, -7.25, - 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-10.50, -5.25, -10.50),
            new Point3D(-10.50, -3.25, -10.50),
            new Point3D(-10.50, -3.25, - 1.52),
            new Point3D(-10.50, -5.25, - 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-8.50 , -3.25, -8.50),
            new Point3D(-8.50 , -1.25, -8.50),
            new Point3D(-8.50 , -1.25, -1.52),
            new Point3D(-8.50 , -3.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-6.50 , -1.25, -6.50),
            new Point3D(-6.50 ,  0.75, -6.50),
            new Point3D(-6.50 ,  0.75, -1.52),
            new Point3D(-6.50 , -1.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-4.52 ,  0.75, -4.52),
            new Point3D(-4.52 ,  2.75, -4.52),
            new Point3D(-4.52 ,  2.75, -1.52),
            new Point3D(-4.52 ,  0.75, -1.52)
        }));





        //Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    Ls    
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.5 ,  -5.25, 1.52),
            new Point3D(-12.5 ,  -5.25, 12.5),
            new Point3D(-1.52 ,  -5.25, 12.5),
            new Point3D(-1.52 ,  -5.25, 10.5),
            new Point3D(-10.5 ,  -5.25, 10.5),
            new Point3D(-10.5 ,  -5.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-10.5 ,  -3.25, 1.52),
            new Point3D(-10.5 ,  -3.25, 10.5),
            new Point3D(-1.52 ,  -3.25, 10.5),
            new Point3D(-1.52 ,  -3.25,  8.5),
            new Point3D(- 8.5 ,  -3.25,  8.5),
            new Point3D(- 8.5 ,  -3.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 8.5 ,  -1.25, 1.52),
            new Point3D(- 8.5 ,  -1.25,  8.5),
            new Point3D(-1.52 ,  -1.25,  8.5),
            new Point3D(-1.52 ,  -1.25,  6.5),
            new Point3D(- 6.5 ,  -1.25,  6.5),
            new Point3D(- 6.5 ,  -1.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 6.5 ,   0.75,  1.52),
            new Point3D(- 6.5 ,   0.75,  6.50),
            new Point3D(-1.52 ,   0.75,  6.50),
            new Point3D(-1.52 ,   0.75,  4.52),
            new Point3D(-4.52 ,   0.75,  4.52),
            new Point3D(-4.52 ,   0.75,  1.52)
        }));

        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.5 ,  -5.25, 1.52),
            new Point3D(12.5 ,  -5.25, 12.5),
            new Point3D(1.52 ,  -5.25, 12.5),
            new Point3D(1.52 ,  -5.25, 10.5),
            new Point3D(10.5 ,  -5.25, 10.5),
            new Point3D(10.5 ,  -5.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(10.5 ,  -3.25, 1.52),
            new Point3D(10.5 ,  -3.25, 10.5),
            new Point3D(1.52 ,  -3.25, 10.5),
            new Point3D(1.52 ,  -3.25,  8.5),
            new Point3D( 8.5 ,  -3.25,  8.5),
            new Point3D( 8.5 ,  -3.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 8.5 ,  -1.25, 1.52),
            new Point3D( 8.5 ,  -1.25,  8.5),
            new Point3D(1.52 ,  -1.25,  8.5),
            new Point3D(1.52 ,  -1.25,  6.5),
            new Point3D( 6.5 ,  -1.25,  6.5),
            new Point3D( 6.5 ,  -1.25, 1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 6.5 ,   0.75,  1.52),
            new Point3D( 6.5 ,   0.75,  6.50),
            new Point3D(1.52 ,   0.75,  6.50),
            new Point3D(1.52 ,   0.75,  4.52),
            new Point3D(4.52 ,   0.75,  4.52),
            new Point3D(4.52 ,   0.75,  1.52)
        }));



        f.add(new FigureFace(new Point3D[]{
            new Point3D(-12.5 ,  -5.25, -1.52),
            new Point3D(-12.5 ,  -5.25, -12.5),
            new Point3D(-1.52 ,  -5.25, -12.5),
            new Point3D(-1.52 ,  -5.25, -10.5),
            new Point3D(-10.5 ,  -5.25, -10.5),
            new Point3D(-10.5 ,  -5.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(-10.5 ,  -3.25, -1.52),
            new Point3D(-10.5 ,  -3.25, -10.5),
            new Point3D(-1.52 ,  -3.25, -10.5),
            new Point3D(-1.52 ,  -3.25, - 8.5),
            new Point3D(- 8.5 ,  -3.25, - 8.5),
            new Point3D(- 8.5 ,  -3.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 8.5 ,  -1.25, -1.52),
            new Point3D(- 8.5 ,  -1.25, - 8.5),
            new Point3D(-1.52 ,  -1.25, - 8.5),
            new Point3D(-1.52 ,  -1.25, - 6.5),
            new Point3D(- 6.5 ,  -1.25, - 6.5),
            new Point3D(- 6.5 ,  -1.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(- 6.5 ,   0.75,  -1.52),
            new Point3D(- 6.5 ,   0.75,  -6.50),
            new Point3D(-1.52 ,   0.75,  -6.50),
            new Point3D(-1.52 ,   0.75,  -4.52),
            new Point3D(-4.52 ,   0.75,  -4.52),
            new Point3D(-4.52 ,   0.75,  -1.52)
        }));

        f.add(new FigureFace(new Point3D[]{
            new Point3D(12.5 ,  -5.25, -1.52),
            new Point3D(12.5 ,  -5.25, -12.5),
            new Point3D(1.52 ,  -5.25, -12.5),
            new Point3D(1.52 ,  -5.25, -10.5),
            new Point3D(10.5 ,  -5.25, -10.5),
            new Point3D(10.5 ,  -5.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(10.5 ,  -3.25, -1.52),
            new Point3D(10.5 ,  -3.25, -10.5),
            new Point3D(1.52 ,  -3.25, -10.5),
            new Point3D(1.52 ,  -3.25, - 8.5),
            new Point3D( 8.5 ,  -3.25, - 8.5),
            new Point3D( 8.5 ,  -3.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 8.5 ,  -1.25, -1.52),
            new Point3D( 8.5 ,  -1.25, - 8.5),
            new Point3D(1.52 ,  -1.25, - 8.5),
            new Point3D(1.52 ,  -1.25, - 6.5),
            new Point3D( 6.5 ,  -1.25, - 6.5),
            new Point3D( 6.5 ,  -1.25, -1.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( 6.5 ,   0.75,  -1.52),
            new Point3D( 6.5 ,   0.75,  -6.50),
            new Point3D(1.52 ,   0.75,  -6.50),
            new Point3D(1.52 ,   0.75,  -4.52),
            new Point3D(4.52 ,   0.75,  -4.52),
            new Point3D(4.52 ,   0.75,  -1.52)
        }));




        // ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA    ARRIBA   
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -4.52 ,  2.75,  -4.52),
            new Point3D(  4.52 ,  2.75,  -4.52),
            new Point3D(  4.52 ,  2.75,   4.52),
            new Point3D( -4.52 ,  2.75,   4.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -3.02 ,  7.25,  -3.02),
            new Point3D(  3.02 ,  7.25,  -3.02),
            new Point3D(  3.02 ,  7.25,   3.02),
            new Point3D( -3.02 ,  7.25,   3.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -3.02 ,  6.75,  -3.02),
            new Point3D(  3.02 ,  6.75,  -3.02),
            new Point3D(  3.02 ,  6.75,   3.02),
            new Point3D( -3.02 ,  6.75,   3.02)
        }));

        f.add(new FigureFace(new Point3D[]{
            new Point3D( -3.02 ,  7.25,   3.02),
            new Point3D(  3.02 ,  7.25,   3.02),
            new Point3D(  3.02 ,  6.75,   3.02),
            new Point3D( -3.02 ,  6.75,   3.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(  3.02 ,  7.25,   3.02),
            new Point3D(  3.02 ,  7.25,  -3.02),
            new Point3D(  3.02 ,  6.75,  -3.02),
            new Point3D(  3.02 ,  6.75,   3.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(  3.02 ,  7.25,  -3.02),
            new Point3D( -3.02 ,  7.25,  -3.02),
            new Point3D( -3.02 ,  6.75,  -3.02),
            new Point3D(  3.02 ,  6.75,  -3.02)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -3.02 ,  7.25,  -3.02),
            new Point3D( -3.02 ,  7.25,   3.02),
            new Point3D( -3.02 ,  6.75,   3.02),
            new Point3D( -3.02 ,  6.75,  -3.02)
        }));


        f.add(new FigureFace(new Point3D[]{
            new Point3D( -2.52 ,  6.75,  2.52),
            new Point3D( -2.52 ,  2.75,  2.52),
            new Point3D( -2.52 ,  2.75, -2.52),
            new Point3D( -2.52 ,  6.75, -2.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -2.52 ,  6.75, -2.52),
            new Point3D( -2.52 ,  2.75, -2.52),
            new Point3D(  2.52 ,  2.75, -2.52),
            new Point3D(  2.52 ,  6.75, -2.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(  2.52 ,  6.75, -2.52),
            new Point3D(  2.52 ,  2.75, -2.52),
            new Point3D(  2.52 ,  2.75,  2.52),
            new Point3D(  2.52 ,  6.75,  2.52)
        }));

        f.add(new FigureFace(new Point3D[]{
            new Point3D( -2.52 ,  6.75,  2.52),
            new Point3D(  2.52 ,  6.75,  2.52),
            new Point3D(  2.52 ,  2.75,  2.52),
            new Point3D(  1.48 ,  2.75,  2.52),
            new Point3D(  1.48 ,  5.75,  2.52),
            new Point3D( -1.52 ,  5.75,  2.52),
            new Point3D( -1.52 ,  2.75,  2.52),
            new Point3D( -2.52 ,  2.75,  2.52)
        }));

        //MARCO
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -1.52 ,  5.75,  2.52),
            new Point3D( -1.52 ,  5.75,  2.77),
            new Point3D( -1.52 ,  2.75,  2.77),
            new Point3D( -1.52 ,  2.75,  2.52)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -1.52 ,  5.75,  2.52),
            new Point3D(  1.48 ,  5.75,  2.52),
            new Point3D(  1.48 ,  5.75,  2.77),
            new Point3D( -1.52 ,  5.75,  2.77)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(  1.48 ,  5.75,  2.52),
            new Point3D(  1.48 ,  2.75,  2.52),
            new Point3D(  1.48 ,  2.75,  2.77),
            new Point3D(  1.48 ,  5.75,  2.77)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -1.52 ,  2.75,  2.77),
            new Point3D( -1.52 ,  5.75,  2.77),
            new Point3D(  1.48 ,  5.75,  2.77),
            new Point3D(  1.48 ,  2.75,  2.77),
            new Point3D(  1.23 ,  5.50,  2.77),
            new Point3D( -1.27 ,  5.50,  2.77),
            new Point3D( -1.27 ,  2.75,  2.77)
        }));

        f.add(new FigureFace(new Point3D[]{
            new Point3D( -1.27 ,  5.50,  2.77),
            new Point3D( -1.27 ,  5.50,  1.37),
            new Point3D( -1.27 ,  2.75,  1.37),
            new Point3D( -1.27 ,  2.75,  2.77)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D(  1.23 ,  5.50,  1.37),
            new Point3D(  1.23 ,  5.50,  2.77),
            new Point3D(  1.23 ,  2.75,  2.77),
            new Point3D(  1.23 ,  2.75,  1.37)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -1.27 ,  5.50,  1.37),
            new Point3D( -1.27 ,  5.50,  2.77),
            new Point3D(  1.23 ,  5.50,  2.77),
            new Point3D(  1.23 ,  5.50,  1.37)
        }));
        f.add(new FigureFace(new Point3D[]{
            new Point3D( -1.27 ,  5.50,  1.37),
            new Point3D(  1.23 ,  5.50,  1.37),
            new Point3D(  1.23 ,  2.75,  1.37),
            new Point3D( -1.27 ,  2.75,  1.37)
        }));
        return f;
    }
}
