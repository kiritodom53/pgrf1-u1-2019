package model;

import renderOperation.RendererLine;

import java.util.List;

/**
 * Created by Dominik Mandinec on 23/10/2019
 */

public interface IGeoObject {
    void draw(RendererLine rl);

    void currentDraw(RendererLine rl, int x, int y);

    void addPoint(int x, int y);

    List<Point> getList();
}
