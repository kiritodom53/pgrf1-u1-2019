package rasterOperation;

/**
 * Created by Dominik Mandinec on 22/10/2019
 */

public interface IRaster {
    void drawPixel(int x, int y, int color);

    int getWidth();

    int getHeight();
}
