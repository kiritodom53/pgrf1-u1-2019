package model;

import renderOperation.RendererLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik Mandinec on 23/10/2019
 */

public class Line implements IGeoObject {
    public List<Point> list = new ArrayList<>();

    public void addPoint(int x, int y) {
        list.add(new Point(x, y));
    }

    @Override
    public void draw(RendererLine rl) {
        if (list.size() != 0) {
            for (int i = 0; i <= list.size() - 2; i = i + 2) {
                rl.drawLineDDA(list.get(i).getX(), list.get(i).getY(), list.get(i + 1).getX(), list.get(i + 1).getY());
            }
        }
    }

    @Override
    public List<Point> getList() {
        return list;
    }

    @Override
    public void currentDraw(RendererLine rl, int x, int y) {
        rl.drawLine(list.get(list.size() - 1).getX(), list.get(list.size() - 1).getY(), x, y);
    }
}
