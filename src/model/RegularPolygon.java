package model;

/**
 * Created by Dominik Mandinec on 23/10/2019
 */

public class RegularPolygon extends Polygon {

    public RegularPolygon(Point s, float r, double alpha, int n) {
        for (int i = 0; i < n; i++) {
            //System.out.println();
            addPoint(
                    (int) (s.getX() + r * Math.cos(alpha + i * Math.PI * 2 / n)),
                    (int) (s.getY() + r * Math.sin(alpha + i * Math.PI * 2 / n))
            );
        }
    }
}
