package model;

import renderOperation.RendererLine;

import java.util.List;

/**
 * Created by Dominik Mandinec on 23/10/2019
 */

public interface IGeoObject {
    int color = 0xff0000;
    void draw(RendererLine rl);

    void currentDraw(RendererLine rl, int x, int y);

    void addPoint(int x, int y, int color);

    List<Point> getList();
}
