import model.*;
import model.Point;
import model.Polygon;
import rasterOperation.IRaster;
import rasterOperation.RasterBufferedImage;
import renderOperation.RendererLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * trida pro kresleni na platno: zobrazeni pixelu, ovladani mysi
 *
 * @author PGRF FIM UHK
 * @version 2017
 */
public class CanvasMouse {

    private JPanel panel;
    private BufferedImage img;

    private int regPol = 0;
    private Boolean regTrue = false;

    private RendererLine rl;
    private IGeoObject line = new Line();

    private IRaster raster;

    private int rPolIn = 0;

    private Boolean polygonFirst = true;
    private IGeoObject pl;
    private int lPolIn = 0;
    private List<IGeoObject> lPol = new ArrayList<>();
    private List<IGeoObject> rPol = new ArrayList<>();

    private int count = 3;
    private double alpha = 0.0000001;

    private IGeoObject rpl;

    private int x, y;
    private float r;

    private int color = 0x00ffff;
    private GeometryObjectType type;

    public CanvasMouse(int width, int height) {
        JFrame frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("Dominik Mandinec - UKOL 01 - UHK FIM 2019");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };
        panel.setPreferredSize(new Dimension(width, height));

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);

        panel.setFocusable(true);
        panel.requestFocusInWindow();

        raster = new RasterBufferedImage(img);
        rl = new RendererLine(raster);

        draw();
    }

    private void reset() {
        line.getList().clear();
        lPol.clear();
        rPol.clear();
        rPolIn = 0;
        lPolIn = 0;
        clear();
        panel.repaint();
    }

    private void drawPoint(int x, int y) {
        lPol.get(lPolIn).addPoint(x, y, color);
        clear();
        lPol.get(lPolIn).draw(rl);
        panel.repaint();
    }


    public void draw() {
        panel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_Q) {
                    reset();
                    System.out.println(type);
                } else if (keyCode == KeyEvent.VK_W) {
                    type = GeometryObjectType.LINE;
                    System.out.println(type);
                } else if (keyCode == KeyEvent.VK_E) {
                    type = GeometryObjectType.POLYGON;
                    System.out.println(type);
                } else if (keyCode == KeyEvent.VK_R) {
                    type = GeometryObjectType.REGULAR_POLYGON;
                    System.out.println(type);
                }
            }
        });

        panel.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                RegularPolygon rpTemp;
                if (regPol == 3 || regPol == 1) {
                    int notches = e.getWheelRotation();
                    System.out.println();

                    if (notches < 0) {
                        System.out.println(notches);
                        count += 1;
                    } else if (notches > 0 && count > 3) {
                        System.out.println(notches);
                        count -= 1;
                    }
                } else if (regPol == 2) {
                    System.out.println("Jede kolečko regPol 2");
                    int notches = e.getWheelRotation();
                    System.out.println();

                    if (notches < 0) {
                        alpha += 0.05;
                    } else if (notches > 0) {
                        alpha -= 0.05;
                    }
                }

                clear();
                rpTemp = new RegularPolygon(new Point(x, y, color), r, alpha, count, color);
                rpTemp.draw(rl);
                panel.repaint();
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (type == GeometryObjectType.REGULAR_POLYGON) {

                    if (regPol == 0 && e.getButton() == MouseEvent.BUTTON1) {
                        count = 3; // nastavý výchozí hodnotu na 3
                        // Kliknutí pro uložení středu
                        x = e.getX();
                        y = e.getY();
                        regPol = 1;
                        regTrue = true;
                        System.out.println("konec reg pol 0");
                    } else if (regPol == 1 && e.getButton() == MouseEvent.BUTTON1) {
                        // Kliknutí pro uložení délky
                        r = (float) Math.sqrt((e.getY() - y) * (e.getY() - y) + (e.getX() - x) * (e.getX() - x));
                        regPol = 2;
                        System.out.println("konec reg pol 1");
                    } else if (regPol == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        regPol = 3;
                    } else if (regPol == 3 && e.getButton() == MouseEvent.BUTTON1) {
                        clear();
                        rpl = new RegularPolygon(new Point(x, y, color), r, alpha, count, color);
                        rPol.add(rpl);
                        regTrue = false;
                        rPol.get(rPolIn).draw(rl);
                        panel.repaint();
                        System.out.println("konec reg pol 2");
                        rPolIn += 1;
                        regPol = 0;
                        alpha = 0.0000001;
                    }
                }
            }
        });

        panel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (type == GeometryObjectType.REGULAR_POLYGON) {
                    clear();
                    if (regTrue && regPol == 1) {
                        clear();
                        RegularPolygon rp = new RegularPolygon(new Point(x, y, color), (float) Math.sqrt((e.getY() - y) * (e.getY() - y) + (e.getX() - x) * (e.getX() - x)), Math.PI / alpha, count, color);
                        rp.draw(rl);
                        panel.repaint();
                    }
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (type == GeometryObjectType.LINE) {
                    // Line
                    drawLine(e.getX(), e.getY());
                    panel.repaint();
                } else if (type == GeometryObjectType.POLYGON) {
                    if (polygonFirst && e.getButton() == MouseEvent.BUTTON1) {
                        pl = new Polygon();
                        lPol.add(pl);
                        drawPoint(e.getX(), e.getY());
                        polygonFirst = false;
                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        if (!polygonFirst) {
                            polygonFirst = true;
                            lPolIn += 1;
                        }
                    }
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (type == GeometryObjectType.POLYGON) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        drawPoint(e.getX(), e.getY());
                    }
                }
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent g) {
                if (type == GeometryObjectType.LINE) {
                    clear();
                    line.draw(rl);
                    line.currentDraw(rl, g.getX(), g.getY());
                    panel.repaint();
                } else if (type == GeometryObjectType.POLYGON) {
                    System.out.println("draged buton : " + g.getButton());
                    clear();
                    pl.draw(rl);
                    pl.currentDraw(rl, g.getX(), g.getY());
                    panel.repaint();
                }
            }
        });

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (type == GeometryObjectType.LINE) {
                    drawLine(e.getX(), e.getY());
                    panel.repaint();
                }
            }
        });
    }

    public void drawLine(int x, int y) {
        line.addPoint(x, y, color);
        clear();
        line.draw(rl);
        panel.repaint();
    }

    /***
     * Vykreslení objektů co byly vykresleny
     */
    public void drawSave() {
        line.draw(rl);

        for (IGeoObject iGeoObject : lPol) {
            for (int j = 0; j < iGeoObject.getList().size(); j++) {
                if (iGeoObject.getList().size() > 2) {
                    iGeoObject.draw(rl);
                }
            }
        }

        for (IGeoObject iGeoObject : rPol) {
            for (int j = 0; j < iGeoObject.getList().size(); j++) {
                if (iGeoObject.getList().size() > 2) {
                    iGeoObject.draw(rl);
                }
            }
        }
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
        drawSave();
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void start() {
        clear();
        img.getGraphics().drawString("Use mouse buttons", 5, img.getHeight() - 5);
        panel.repaint();
    }

}