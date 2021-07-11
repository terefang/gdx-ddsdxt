package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.nio.ByteBuffer;

public class GenericTextureData implements TextureData
{
    protected ByteBuffer data;
    protected int dataOffset;
    protected boolean isPrepared = false;
    protected int width;
    protected int height;
    protected boolean useMipMaps = false;
    protected Pixmap.Format pixmapFormat;
    protected int glTextureFormat;
    protected int glTextureType;
    protected int glInternalTextureFormat;

    @Override
    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    @Override
    public boolean isPrepared() {
        return this.isPrepared;
    }

    @Override
    public void prepare() {
        if (isPrepared) throw new GdxRuntimeException("Already prepared");
        this.data.position(dataOffset);
        isPrepared = true;
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
        if (!this.isPrepared) throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");

        Gdx.gl.glTexImage2D(_target, 0, this.glInternalTextureFormat, this.width, this.height, 0, this.glTextureFormat, this.glTextureType, this.data);

        if (this.useMipMaps()) Gdx.gl.glGenerateMipmap(GL20.GL_TEXTURE_2D);

        this.isPrepared = false;
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
