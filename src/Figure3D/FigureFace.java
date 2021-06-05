package Figure3D;
import java.awt.geom.*;

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

    public double getSlope(){

        
    }
}
