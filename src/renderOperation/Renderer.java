package renderOperation;

import rasterOperation.IRaster;

/**
 * Created by Dominik Mandinec on 22/10/2019
 */

public abstract class Renderer {

    protected IRaster raster;

    public Renderer(IRaster raster) {
        setRaster(raster);
    }

    public void setRaster(IRaster raster) {
        this.raster = raster;
    }
}
