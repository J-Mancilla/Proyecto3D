package Figure3D;

import java.util.ArrayList;
import java.awt.*;

public class Figure {
    private ArrayList<FigureFace> faces;
    int distance;
    int mz = -350;
    int despx, despy;
    Color color;

    public Figure(){
        faces = genFigure();
        reset();
    }

    public void setDistance(int dist){
        distance = dist;
    }

    public void setSolid(boolean solid){
        if(solid)
            color = new Color(116,144,175);
        else
            color = new Color(116,144,175,175);
    }

    public void paint(Graphics2D g2){
        //Elegir orden de pintado y sombreado maximo
        //double maxSlope = 0;
        ArrayList<double[]> arr = new ArrayList<>();
        for (int i = 0; i < faces.size(); i++){
            double slope = faces.get(i).getSlope();
            // if(slope > maxSlope)
            //     maxSlope = slope;
            arr.add(new double[]{i, faces.get(i).getOrder(), slope});
        }
        order(arr);

        for (double[] ds : arr) {
           FigureFace f = faces.get((int)ds[0]);
           Shape f2D = f.get2DShape(distance, mz, despx, despy);
           g2.setPaint(color);
           g2.fill(f2D);
           g2.setPaint(new Color(0,0,0,40));
           g2.setStroke(new BasicStroke(1));
           g2.draw(f2D);
           //int opacity = (int)(ds[2]/maxSlope*150);
           int opacity = (int)(ds[2]);
           g2.setPaint(new Color(0,0,0,opacity));
           g2.fill(f2D);
        }
    }

    public void reset(){
        color = new Color(116,144,175,175);
        distance = 5500;
        despx = 500; despy = 260;
        for (FigureFace figureFace : faces) 
            figureFace.reset(); 
    }

    public boolean contains(int x, int y){
        for (FigureFace figureFace : faces) 
            if(figureFace.get2DShape(distance, mz, despx, despy).contains(x, y))
                return true;
        return false;
    }

    public void move(int cx, int cy){
        despx += cx;
        despy += cy;
    }

    public void scale(double s){
        scale(s, s, s);
    }

    public void scale(double sx, double sy, double sz){
        for (FigureFace figureFace : faces) {
            for (int i = 0; i < figureFace.face.length; i++) {
                figureFace.originalFace[i].x*=sx; 
                figureFace.originalFace[i].y*=sy; 
                figureFace.originalFace[i].z*=sz; 
                figureFace.face[i].x*=sx;
                figureFace.face[i].y*=sy;
                figureFace.face[i].z*=sz;
            }
        }
    }

    public void rotate(int grx, int gry, int grz){
        double radx = Math.toRadians(grx),
               ca1  = Math.cos(radx),
               sa1  = Math.sin(radx);
        double rady = Math.toRadians(gry),
               ca2  = Math.cos(rady),
               sa2  = Math.sin(rady);
        double radz = Math.toRadians(grz),
               ca3  = Math.cos(radz),
               sa3  = Math.sin(radz);
        for (FigureFace figureFace : faces) {
            for (int i = 0; i < figureFace.face.length; i++) {
                double x = figureFace.originalFace[i].x,
                       y = figureFace.originalFace[i].y,
                       z = figureFace.originalFace[i].z; 
                figureFace.face[i].x = x*(ca2*ca3)+y*((sa1*-sa2)*ca3+(ca1*-sa3))+z*((ca1*-sa2)*ca3+(-sa1*-sa3));
                figureFace.face[i].y = x*(ca2*sa3)+y*((sa1*-sa2)*sa3+(ca1*ca3))+z*((ca1*-sa2)*sa3+(-sa1*ca3));
                figureFace.face[i].z = x*sa2+y*(sa1*ca2)+z*(ca1*ca2);
                x = figureFace.original[i].x;
                y = figureFace.original[i].y;
                z = figureFace.original[i].z;
                figureFace.withRot[i].x = x*(ca2*ca3)+y*((sa1*-sa2)*ca3+(ca1*-sa3))+z*((ca1*-sa2)*ca3+(-sa1*-sa3));
                figureFace.withRot[i].y = x*(ca2*sa3)+y*((sa1*-sa2)*sa3+(ca1*ca3))+z*((ca1*-sa2)*sa3+(-sa1*ca3));
                figureFace.withRot[i].z = x*sa2+y*(sa1*ca2)+z*(ca1*ca2);
            }
        }
    }

    public ArrayList<FigureFace> rotateX(int degrees, boolean reactive){
        ArrayList<FigureFace> fig = new ArrayList<>();
        double rad = Math.toRadians(degrees),
               cos = Math.cos(rad),
               sen = Math.sin(rad);
        for (FigureFace figureFace : faces) {
            Point3D points[] = new Point3D[figureFace.face.length];
            for (int i = 0; i < figureFace.face.length; i++) {
                double x, y, z;
                if(reactive){
                    x = figureFace.withRot[i].x;
                    y = figureFace.withRot[i].y;
                    z = figureFace.withRot[i].z; 
                }else{
                    x = figureFace.original[i].x;
                    y = figureFace.original[i].y;
                    z = figureFace.original[i].z; 
                }
                points[i]= new Point3D(
                                        x,
                                        y*cos-z*sen,
                                        y*sen+z*cos
                                    );
            }
            fig.add(new FigureFace(points));
        }
        return fig;
    }
    public ArrayList<FigureFace> rotateY(int degrees, boolean reactive){
        ArrayList<FigureFace> fig = new ArrayList<>();
        double rad = Math.toRadians(degrees),
               cos = Math.cos(rad),
               sen = Math.sin(rad);
        for (FigureFace figureFace : faces) {
            Point3D points[] = new Point3D[figureFace.face.length];
            for (int i = 0; i < figureFace.face.length; i++) {
                double x, y, z;
                if(reactive){
                    x = figureFace.withRot[i].x;
                    y = figureFace.withRot[i].y;
                    z = figureFace.withRot[i].z;
                }else{
                    x = figureFace.original[i].x;
                    y = figureFace.original[i].y;
                    z = figureFace.original[i].z;
                }
                points[i]= new Point3D(
                                        x*cos-z*sen,
                                        y,
                                        x*sen+z*cos
                                    );
            }
            fig.add(new FigureFace(points));
        }
        return fig;
    }
    public ArrayList<FigureFace> getCopy(boolean reactive){
        ArrayList<FigureFace> fig = new ArrayList<>();
        for (FigureFace figureFace : faces) {
            Point3D points[] = new Point3D[figureFace.face.length];
            for (int i = 0; i < figureFace.face.length; i++) {
                if(reactive){
                    points[i]= new Point3D( 
                                        figureFace.withRot[i].x,
                                        figureFace.withRot[i].y,
                                        figureFace.withRot[i].z
                                    );
                }else{
                    points[i]= new Point3D( 
                                        figureFace.original[i].x,
                                        figureFace.original[i].y,
                                        figureFace.original[i].z
                                    );
                }
            }
            fig.add(new FigureFace(points));
        }
        return fig;
    }

    public void topView(Graphics2D g2, int x, int y, boolean reactive){
        ArrayList<FigureFace> ft = rotateX(90, reactive);
        for (FigureFace figureFace : ft) 
            for (int i = 0; i < figureFace.face.length; i++)
                figureFace.face[i].z  = 0;
        paintView(g2, ft, x, y);
    }
    public void sideView(Graphics2D g2, int x, int y, boolean reactive){
        ArrayList<FigureFace> ft = rotateY(90, reactive);
        for (FigureFace figureFace : ft) 
            for (int i = 0; i < figureFace.face.length; i++)
                figureFace.face[i].z  = 0;
        paintView(g2, ft, x, y);
    }
    public void frontView(Graphics2D g2, int x, int y, boolean reactive){
        //double figT[][] = rotacionY(90);
        ArrayList<FigureFace> ft = getCopy(reactive);
        paintView(g2, ft, x, y);
    }

    public void paintView(Graphics2D g2, ArrayList<FigureFace> figT, int x, int y){
        ArrayList<double[]> arr = new ArrayList<>();
        for (int i = 0; i < figT.size(); i++){
            double slope = figT.get(i).getSlope();
            arr.add(new double[]{i, figT.get(i).getOrder(), slope});
        }
        order(arr);
        for (double[] ds : arr) {
           FigureFace f = figT.get((int)ds[0]);
           Shape f2D = f.get2DShape(900, -350, x, y);
           g2.setPaint(new Color(100,100,100,115));
           g2.fill(f2D);
           g2.setPaint(new Color(255,255,255,40));
           g2.setStroke(new BasicStroke(1));
           g2.draw(f2D);
           int opacity = (int)(ds[2]);
           g2.setPaint(new Color(0,0,0,opacity));
           g2.fill(f2D);
        }
    }

    private void order(ArrayList<double[]> arr) {
        for (int x = 0; x < arr.size(); x++) {
            for (int i = 0; i < arr.size()-x-1; i++) {
                if(arr.get(i)[1] > arr.get(i+1)[1]){
                    double tmp[] = {arr.get(i+1)[0],arr.get(i+1)[1],arr.get(i+1)[2]};
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
            new Point3D(1.52, -7.25, 16.5),
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
            new Point3D(1.02, -7.25, -16.5),
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
            new Point3D(  1.23 ,  2.75,  2.77),
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
