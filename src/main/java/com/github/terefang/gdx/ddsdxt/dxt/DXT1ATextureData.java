package com.github.terefang.gdx.ddsdxt.dxt;

import com.github.terefang.gdx.ddsdxt.GenericCompressedTextureData;
import com.badlogic.gdx.graphics.Pixmap;

import java.nio.ByteBuffer;

public class DXT1ATextureData extends GenericCompressedTextureData
{
    public static DXT1ATextureData from(ByteBuffer _buf, int _offset, int _w, int _h, boolean _mipmaps)
    {
        return new DXT1ATextureData(_buf, _offset, _w, _h, _mipmaps);
    }

    public DXT1ATextureData(ByteBuffer _buf, int _offset, int _w, int _h, boolean _mipmaps)
    {
        super();
        this.data = _buf;
        this.dataOffset = _offset;
        this.height = _h;
        this.width = _w;
        this.glTextureCompressionExtension = "GL_EXT_texture_compression_dxt1 GL_EXT_texture_compression_s3tc GL_NV_texture_compression_s3tc";
        this.glTextureFormat = COMPRESSED_RGBA_S3TC_DXT1_EXT;
        this.useMipMaps = _mipmaps;
        this.pixmapFormat = Pixmap.Format.RGB565;
        this.data.position(_offset);
        this.prepared = true;
    }
}
