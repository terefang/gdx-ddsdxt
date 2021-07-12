package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.ByteBuffer;

public class GenericCompressedTextureData extends GenericTextureData implements TextureData
{
    // GL_EXT_texture_compression_dxt1
    public static final int COMPRESSED_RGB_S3TC_DXT1_EXT                      =0x83F0;
    public static final int COMPRESSED_RGBA_S3TC_DXT1_EXT                     =0x83F1;
    // GL_EXT_texture_compression_s3tc
    //public static final int COMPRESSED_RGB_S3TC_DXT1_EXT                   =0x83F0;
    //public static final int COMPRESSED_RGBA_S3TC_DXT1_EXT                  =0x83F1;
    public static final int COMPRESSED_RGBA_S3TC_DXT3_EXT                  =0x83F2;
    public static final int COMPRESSED_RGBA_S3TC_DXT5_EXT                  =0x83F3;

    protected ByteBuffer data;
    protected int dataOffset;
    protected boolean isPrepared = false;
    protected int width;
    protected int height;
    protected boolean useMipMaps = false;
    protected Pixmap.Format pixmapFormat;
    protected int glTextureFormat;
    protected String glTextureCompressionExtension;

    @Override
    public void consumeCustomData(int _target)
    {
        if (!this.isPrepared) throw new GdxRuntimeException("Call prepare() before calling consumeCompressedData()");

        if (this.glTextureCompressionExtension!=null)
        {
            boolean _found = false;
            for(String _ext : this.glTextureCompressionExtension.split(" "))
            {
                if(Gdx.graphics.supportsExtension(_ext))
                    _found = true;
            }
            if(!_found) throw new GdxRuntimeException("Extension "+this.glTextureCompressionExtension+" not supported");
        }

        Gdx.gl.glCompressedTexImage2D(_target, 0, this.glTextureFormat, this.width, this.height, 0, this.data.capacity()-dataOffset, this.data);

        if (this.useMipMaps()) Gdx.gl.glGenerateMipmap(GL20.GL_TEXTURE_2D);

        this.isPrepared = false;
    }

    @Override
    public boolean isManaged() {
        return true;
    }
}
