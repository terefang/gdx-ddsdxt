package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.terefang.gdx.ddsdxt.load.DDS;
import com.github.terefang.gdx.ddsdxt.load.JavaPixmapLoader;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;

public class JavaPixmapIO
{
    static public Pixmap read(FileHandle _file)
    {
        return JavaPixmapLoader.load(_file.name(), _file);
    }

    @SneakyThrows
    static public void writeJPG(FileHandle _file, Pixmap _pixmap)
    {
        writeType("jpg", false, _file, _pixmap);
    }

    @SneakyThrows
    static public void writePNG(FileHandle _file, Pixmap _pixmap)
    {
        writeType("png", true, _file, _pixmap);
    }

    @SneakyThrows
    static public void writeType(String _type, boolean _alpha, FileHandle _file, Pixmap _pixmap)
    {
        ImageIO.write(pixmapToImage(_pixmap, _alpha), _type, _file.file());
    }

    static BufferedImage pixmapToImage(Pixmap _pixmap, boolean _alpha)
    {
        Pixmap.Format _fmt = _pixmap.getFormat();
        BufferedImage _img = new BufferedImage(_pixmap.getWidth(), _pixmap.getHeight(), _alpha ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        ByteBuffer _pbuf = _pixmap.getPixels();
        _pbuf.position(0);
        int _i = 0;
        while(_pbuf.hasRemaining())
        {
            switch(_fmt)
            {
                case RGBA8888:
                {
                    int _b = _pbuf.get() & 0xff;
                    int _g = _pbuf.get() & 0xff;
                    int _r = _pbuf.get() & 0xff;
                    int _a = _pbuf.get() & 0xff;

                    _img.setRGB(_i % _pixmap.getWidth(), _i / _pixmap.getWidth(), (_a << 24) | (_b << 16) | (_g << 8) | _r);
                    _i++;
                    break;
                }
                case RGBA4444:
                {
                    int _r = _pbuf.get() & 0xff;
                    int _g = (_r & 0xf) | ((_r & 0xf) << 4);
                    _r = (_r & 0xf0) | ((_r & 0xf0) >>> 4);
                    int _b = _pbuf.get() & 0xff;
                    int _a = (_b & 0xf) | ((_b & 0xf) << 4);
                    _b = (_b & 0xf0) | ((_b & 0xf0) >>> 4);

                    _img.setRGB(_i % _pixmap.getWidth(), _i / _pixmap.getWidth(), (_a << 24) | (_b << 16) | (_g << 8) | _r);
                    _i++;
                    break;
                }
                case RGB888:
                {
                    int _r = _pbuf.get() & 0xff;
                    int _g = _pbuf.get() & 0xff;
                    int _b = _pbuf.get() & 0xff;
                    int _a = 255;

                    _img.setRGB(_i % _pixmap.getWidth(), _i / _pixmap.getWidth(), (_a << 24) | (_b << 16) | (_g << 8) | _r);
                    _i++;
                    break;
                }
                case RGB565: {
                    int _rgb = _pbuf.getShort() & 0xffff;
                    int _r = DDS.BIT5[((_rgb & DDS.R5G6B5_MASKS[0]) >> 11)];
                    int _g = DDS.BIT6[((_rgb & DDS.R5G6B5_MASKS[1]) >> 5)];
                    int _b = DDS.BIT5[((_rgb & DDS.R5G6B5_MASKS[2]))];
                    int _a = 255;

                    _img.setRGB(_i % _pixmap.getWidth(), _i / _pixmap.getWidth(), (_a << 24) | (_b << 16) | (_g << 8) | _r);
                    _i++;
                    break;
                }
                default:
                    throw new GdxRuntimeException("Unsupported Pixmap Format");
            }
        }
        return _img;
    }
}
