package model;

import renderOperation.RendererLine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominik Mandinec on 23/10/2019
 */

public class Polygon implements IGeoObject {

    private List<Point> list = new ArrayList<>();

    @Override
    public void draw(RendererLine rl) {
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                rl.drawLineDDA(list.get(0).getX(), list.get(0).getY(), list.get(i).getX(), list.get(i).getY());
            } else {
                rl.drawLineDDA(list.get(i).getX(), list.get(i).getY(), list.get(i + 1).getX(), list.get(i + 1).getY());
            }
        }
    }

    @Override
    public void currentDraw(RendererLine rl, int x, int y) {
        rl.drawLineDDA(list.get(0).getX(), list.get(0).getY(), x, y);
        rl.drawLineDDA(list.get(list.size() - 1).getX(), list.get(list.size() - 1).getY(), x, y);
    }

    @Override
    public void addPoint(int x, int y) {
        list.add(new Point(x, y));
    }

    public List<Point> getList() {
        return list;
    }
}
