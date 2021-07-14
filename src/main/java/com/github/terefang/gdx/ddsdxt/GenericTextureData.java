package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.nio.ByteBuffer;

public class GenericTextureData implements TextureData
{
    public ByteBuffer data;
    public int dataOffset;
    public boolean prepared = false;
    public int width;
    public int height;
    public boolean useMipMaps = false;
    public Pixmap.Format pixmapFormat;
    public int glTextureFormat;
    public int glTextureType;
    public int glInternalTextureFormat;

    @Override
    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    @Override
    public boolean isPrepared() {
        return this.prepared;
    }

    @Override
    public void prepare() {
        if (this.prepared) throw new GdxRuntimeException("Already prepared");
        this.data.position(dataOffset);
        this.prepared = true;
    }

    @Override
    public Pixmap consumePixmap () {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override
    public boolean disposePixmap () {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    @Override
    public void consumeCustomData(int _target)
    {
        if (!this.prepared) throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");

        Gdx.gl.glTexImage2D(_target, 0, this.glInternalTextureFormat, this.width, this.height, 0, this.glTextureFormat, this.glTextureType, this.data);

        if (this.useMipMaps()) Gdx.gl.glGenerateMipmap(GL20.GL_TEXTURE_2D);

        this.prepared = false;
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public Pixmap.Format getFormat() {
        return this.pixmapFormat;
    }

    @Override
    public boolean useMipMaps() {
        return this.useMipMaps;
    }

    @Override
    public boolean isManaged() {
        return true;
    }
}
