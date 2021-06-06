package Figure3D;
import java.awt.geom.*;
import java.awt.*;

public class FigureFace {
    Point3D face[];
    Point3D originalFace[];
    private Point3D ori[];

    public FigureFace(Point3D points[]){
        face = points.clone();
        originalFace = new Point3D[face.length];
        ori = new Point3D[face.length];
        for (int i = 0; i < face.length; i++){
            originalFace[i] = new Point3D(face[i].x, face[i].y, face[i].z);
            ori[i]          = new Point3D(face[i].x, face[i].y, face[i].z);
        }
    }

    public void reset(){
        for (int i = 0; i < face.length; i++) {
            originalFace[i] = new Point3D(ori[i].x, ori[i].y, ori[i].z);
            face[i]         = new Point3D(ori[i].x, ori[i].y, ori[i].z);
        }
    }

    public Point2D[] get2D(int distance, int mz){
        Point2D fig2D[] = new Point2D[face.length];
        for (int i = 0; i < fig2D.length; i++) {
            double x = (distance*face[i].x) / (face[i].z+mz);
            double y = (distance*face[i].y) / (face[i].z+mz);
            fig2D[i] = new Point2D.Double(x,y);
        }
        return fig2D;
    }

    public Shape get2DShape(int distance, int mz, int dx, int dy){
        GeneralPath path = new GeneralPath();
        for (int i = 0; i < face.length; i++) {
            double x = (distance*face[i].x) / (face[i].z+mz);
            double y = (distance*face[i].y) / (face[i].z+mz);
            if(i==0)
                path.moveTo(x+dx, y+dy);
            else
                path.lineTo(x+dx, y+dy);
        }
        path.closePath();
        return path;
    }

    // public double getSlope(){
    //     double sum = 0;
    //     if(face[face.length-1].x-face[0].x!=0)
    //         sum+=face[face.length-1].z-face[0].z/face[face.length-1].x-face[0].x;
    //     for (int i = 0; i < face.length-1; i++) 
    //         if(face[i+1].x-face[i].x!=0)
    //             sum+=face[i+1].z-face[i].z/face[i+1].x-face[i].x;

    //     double sum2 = 0;
    //     if(face[face.length-1].x-face[0].x!=0)
    //         sum2+=(face[face.length-1].y/face[0].y);
    //     for (int i = 0; i < face.length-1; i++) 
    //         if(face[i+1].x-face[i].x!=0)
    //             sum2+=(face[i+1].y/face[i].y);
    //     // System.out.println(face[0]);
    //     // System.out.println("Slope --> "+Math.abs(sum/face.length));
    //     // System.out.println();
    //     return Math.abs((sum2/face.length));
    // }
    
    public double getSlope(){
        double sum = 0;
        int count  = 0;
        if(face[0].y!=0){
            sum+=face[face.length-1].y/face[0].y;
            count++;
        }
        for (int i = 0; i < face.length-1; i++) 
            if(face[i].y!=0){
                sum+=face[i+1].y/face[i].y;
                count++;
            }
        if(sum/count>0.99 && sum/count<1.01)
            return 30;

        sum = 0;
        count = 0;
        if(face[0].z!=0){
            sum+=face[face.length-1].z/face[0].z;
            count ++;
        }
        for (int i = 0; i < face.length-1; i++) 
            if(face[i].z!=0){
                sum+=face[i+1].z/face[i].z;
                count ++;
            }
        if(sum/count>0.9 && sum/count<1.1)
            return 0;
        return 15;
    }

    public double getMinZ(){
        double min = face[0].z;
        for (int i = 1; i < face.length; i++)
            if(face[i].z < min)
                min = face[i].z;
        return min;
    }
    public double getMaxZ(){
        double max = face[0].z;
        for (int i = 1; i < face.length; i++)
            if(face[i].z > max)
                max = face[i].z;
        return max;
    }
    public double getMinY(){
        double min = face[0].y;
        for (int i = 1; i < face.length; i++)
            if(face[i].y < min)
                min = face[i].y;
        return min;
    }
    public double getMaxY(){
        double max = face[0].y;
        for (int i = 1; i < face.length; i++)
            if(face[i].y > max)
                max = face[i].y;
        return max;
    }
    public double getMinX(){
        double min = face[0].x;
        for (int i = 1; i < face.length; i++)
            if(face[i].x < min)
                min = face[i].x;
        return min;
    }
    public double getOrder(){
        return getMaxY()+ getMinY()+getMaxZ()+getMinZ();
    }
    
}
