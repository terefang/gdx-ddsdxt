package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.Vector;

/** {@link JavaPixmapLoader} for {@link Pixmap} instances. The pixel data is loaded synchronously.
 */
public class JavaPixmapLoader
{
    @SneakyThrows
    public static Pixmap load(String _fileName, FileHandle _file)
    {
        BufferedImage _img = ImageIO.read(_file.read());
        if(_img.getType()!=BufferedImage.TYPE_INT_ARGB)
        {
            BufferedImage _img8 = new BufferedImage(_img.getWidth(), _img.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D _g2d = _img8.createGraphics();
            _g2d.drawImage(_img, 0, 0, null);
            _g2d.dispose();
            _img = _img8;
        }

        Gdx2DPixmap _p2x = Gdx2DPixmap.newPixmap(_img.getWidth(), _img.getHeight(), Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
        DataBuffer _buf = _img.getData().getDataBuffer();
        ByteBuffer _bbuf = _p2x.getPixels();
        _bbuf.position(0);
        if(_buf instanceof DataBufferInt)
        {
            for(int _i : ((DataBufferInt)_buf).getData())
            {
                _bbuf.putInt(Integer.rotateLeft(_i,8));
            }
        }
        else
        {
            throw new GdxRuntimeException("invalid BufferedImage Format");
        }
        return new Pixmap(_p2x);
    }

}
