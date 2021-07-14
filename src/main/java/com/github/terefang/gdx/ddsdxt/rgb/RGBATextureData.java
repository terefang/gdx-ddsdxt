package com.github.terefang.gdx.ddsdxt.rgb;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.github.terefang.gdx.ddsdxt.GenericTextureData;

import java.nio.ByteBuffer;

public class RGBATextureData extends GenericTextureData
{
    public static TextureData from(ByteBuffer _buffer, int _offset, int _width, int _height, boolean _mipmaps)
    {
        return new RGBATextureData(_buffer, _offset, _width, _height, _mipmaps);
    }

    public RGBATextureData(ByteBuffer _buf, int _offset, int _width, int _height, boolean _mipmaps)
    {
        super();
        this.data = _buf;
        this.dataOffset = _offset;
        this.height = _height;
        this.width = _width;
        this.glInternalTextureFormat = GL20.GL_RGBA;
        this.glTextureFormat = GL20.GL_RGBA;
        this.glTextureType = GL20.GL_UNSIGNED_BYTE;
        this.useMipMaps = _mipmaps;
        this.pixmapFormat = Pixmap.Format.RGBA8888;

        this.data.position(_offset);
        this.prepared = true;
    }

    public static TextureData from(Gdx2DPixmap _img, boolean _mipmaps)
    {
        return new RGBATextureData(_img, _mipmaps);
    }

    public RGBATextureData(Gdx2DPixmap _img, boolean _mipmaps)
    {
        super();
        this.data = _img.getPixels();
        this.dataOffset = 0;
        this.height = _img.getHeight();
        this.width = _img.getWidth();
        this.glInternalTextureFormat = _img.getGLInternalFormat();
        this.glTextureFormat = _img.getGLFormat();
        this.glTextureType = _img.getGLType();
        this.useMipMaps = _mipmaps;
        this.pixmapFormat = Pixmap.Format.fromGdx2DPixmapFormat(_img.getFormat());

        this.data.position(0);
        this.prepared = true;
    }

}
