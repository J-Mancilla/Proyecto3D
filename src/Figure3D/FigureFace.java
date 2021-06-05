package Figure3D;
import java.awt.geom.*;
import java.awt.*;

public class FigureFace {
    Point3D face[];
    Point3D originalFace[];

    public FigureFace(Point3D points[]){
        face = points.clone();
        originalFace = points.clone();
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

    public double getSlope(){
        double sum = 0;
        if(face[0].z!=0)
            sum+=face[face.length-1].z/face[0].z;
        //0 1 2 3 4 5
        for (int i = 0; i < face.length-1; i++) 
            if(face[i+1].z!=0)
                sum+=face[i+1].z-face[i].z/face[i+1].y-face[i].y;
        return sum/face.length;
    }

    public double getMaxZ(){
        double max = face[0].z;
        for (int i = 1; i < face.length; i++)
            if(face[i].z > max)
                max = face[i].z;
        return max;
    }
}
