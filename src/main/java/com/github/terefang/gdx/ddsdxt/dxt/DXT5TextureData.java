package com.github.terefang.gdx.ddsdxt.dxt;

import com.github.terefang.gdx.ddsdxt.GenericCompressedTextureData;
import com.badlogic.gdx.graphics.Pixmap;

import java.nio.ByteBuffer;

public class DXT5TextureData extends GenericCompressedTextureData
{
    public static DXT5TextureData from(ByteBuffer _buf, int _offset, int _w, int _h, boolean _mipmaps)
    {
        return new DXT5TextureData(_buf, _offset, _w, _h, _mipmaps);
    }

    public DXT5TextureData(ByteBuffer _buf, int _offset, int _w, int _h, boolean _mipmaps)
    {
        super();
        this.data = _buf;
        this.dataOffset = _offset;
        this.height = _h;
        this.width = _w;
        this.glTextureCompressionExtension = "GL_EXT_texture_compression_s3tc GL_NV_texture_compression_s3tc";
        this.glTextureFormat = COMPRESSED_RGBA_S3TC_DXT5_EXT;
        this.useMipMaps = _mipmaps;
        this.pixmapFormat = Pixmap.Format.RGB565;
    }
}
