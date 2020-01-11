package renderOperation;

import rasterOperation.IRaster;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik Mandinec on 22/10/2019
 */

public class RendererLine extends Renderer {
    public RendererLine(IRaster raster) {
        super(raster);
    }

    private int x = -1;
    private int y = -1;
    private List<Integer> position = new ArrayList<>();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public List<Integer> getPosition() {
        return position;
    }

    public void addList(int i) {
        position.add(i);
    }


    /***
     * Vykreslení pomocí DDA algortimu
     */
    public void drawLineDDA(int x1, int y1, int x2, int y2) {
        int x;
        float k;
        float y;
        if (Math.abs(y2 - y1) < Math.abs(x2 - x1)) {
            if (x2 < x1) {
                x = x1;
                x1 = x2;
                x2 = x;
                x = y1;
                y1 = y2;
                y2 = x;
            }

            k = (float) (y2 - y1) / (float) (x2 - x1);
            y = (float) y1;

            for (x = x1; x <= x2; ++x) {
                raster.drawPixel(x, (int) ((double) y + 0.5D));
                y += k;
            }
        } else {
            if (y2 < y1) {
                x = x1;
                x1 = x2;
                x2 = x;
                x = y1;
                y1 = y2;
                y2 = x;
            }

            k = (float) (x2 - x1) / (float) (y2 - y1);
            y = (float) x1;

            for (x = y1; x <= y2; ++x) {
                raster.drawPixel((int) ((double) y + 0.5D), x);
                y += k;
            }
        }
    }

    /***
     * Vykreslování pomocí triviálního algoritmu
     */
    public void drawLine(int x1, int y1, int x2, int y2) {

        double k;
        if (x1 == x2) {
            k = (double) (y2 - y1);
        } else {
            k = (double) (y2 - y1) / (x2 - x1);
        }
        double q = y1 - k * x1;

        if (Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
            if (x2 < x1) {
                int tempX = x1;
                x1 = x2;
                x2 = tempX;

            }
            for (int i = x1; i <= x2; i++) {
                raster.drawPixel(i, (int) (k * i + q));
            }
        } else {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }
            for (int i = y1; i <= y2; i++) {
                raster.drawPixel((int) ((i - q) / k), i);
            }
        }
    }
}
