package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;

import com.github.terefang.gdx.ddsdxt.dxt.DXT1ATextureData;
import com.github.terefang.gdx.ddsdxt.dxt.DXT1TextureData;
import com.github.terefang.gdx.ddsdxt.dxt.DXT3TextureData;
import com.github.terefang.gdx.ddsdxt.dxt.DXT5TextureData;
import com.github.terefang.gdx.ddsdxt.rgb.RGBATextureData;

import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;


public class DDSLoader
{
    /** Creates texturedata from a dds file, does not create mipmaps.
     * @param _ddsFile the dds file
     */
    @SneakyThrows
    public static final TextureData fromDdsToTexture(FileHandle _ddsFile)
    {
        return fromDdsToTexture(_ddsFile, false,true,false, false);
    }

    /** Creates texturedata from a dds file.
     * @param _ddsFile the dds file
     * @param _mipmaps create mipmaps */
    @SneakyThrows
    public static final TextureData fromDdsToTexture(FileHandle _ddsFile, boolean _mipmaps)
    {
        return fromDdsToTexture(_ddsFile, _mipmaps,true,false, false);
    }

    /** Creates texturedata from a dds file.
     * @param _ddsFile the dds file
     * @param _mipmaps create mipmaps
     * @param _alpha use alpha in dxt1 */
    @SneakyThrows
    public static final TextureData fromDdsToTexture(FileHandle _ddsFile, boolean _mipmaps, boolean _alpha)
    {
        return fromDdsToTexture(_ddsFile, _mipmaps,_alpha,false, false);
    }

    /** Creates texturedata from a dds file.
     * @param _ddsFile the dds file
     * @param _mipmaps create mipmaps
     * @param _alpha use alpha in dxt1
     * @param _unDxt decompress dxt */
    @SneakyThrows
    public static final TextureData fromDdsToTexture(FileHandle _ddsFile, boolean _mipmaps, boolean _alpha, boolean _unDxt)
    {
        return fromDdsToTexture(_ddsFile, _mipmaps,_alpha, _unDxt, false);
    }

    /** Creates texturedata from a dds file.
     * @param _ddsFile the dds file
     * @param _mipmaps create mipmaps
     * @param _alpha use alpha in dxt1
     * @param _unDxt decompress dxt */
    @SneakyThrows
    public static final TextureData fromDdsToTexture(FileHandle _ddsFile, boolean _mipmaps, boolean _alpha, boolean _unDxt, boolean _f32)
    {
        byte[] _dds = null;
        if(_ddsFile.name().endsWith(".gz"))
        {
            GZIPInputStream _is = new GZIPInputStream(_ddsFile.read(8192));
            _dds = StreamUtils.copyStreamToByteArray(_is);
            StreamUtils.closeQuietly(_is);
        }
        else
        {
            _dds = _ddsFile.readBytes();
        }

        int _type = DDS.getType(_dds);
        ByteBuffer _buf = DDS.read(_dds, 0, _unDxt);
        _buf.position(0);
        switch(_type)
        {
            case DDS.DXT1:
                if(_unDxt) return RGBATextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
                if(_alpha || ((DDS.getPixelFormatFlags(_dds) & DDS.DDPF_ALPHAPIXELS) != 0))
                {
                    return DXT1ATextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
                }
                return DXT1TextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
            case DDS.DXT3:
                if(_unDxt) return RGBATextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
                return DXT3TextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
            case DDS.DXT5:
                if(_unDxt) return RGBATextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
                return DXT5TextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
            case DDS.A1R5G5B5:
            case DDS.X1R5G5B5:
            case DDS.A4R4G4B4:
            case DDS.X4R4G4B4:
            case DDS.R5G6B5:
            case DDS.R8G8B8:
            case DDS.A8B8G8R8:
            case DDS.X8B8G8R8:
            case DDS.A8R8G8B8:
            case DDS.X8R8G8B8:
                return RGBATextureData.from(_buf, 0, DDS.getWidth(_dds), DDS.getHeight(_dds), _mipmaps);
            default:
                throw new GdxRuntimeException("Unsupported DDS Texture");
        }
    }

    @SneakyThrows
    public static Pixmap fromDdsToPixmap(String _fileName, FileHandle _ddsFile)
    {
        byte[] _dds = null;
        if(_fileName.endsWith(".gz"))
        {
            GZIPInputStream _is = new GZIPInputStream(_ddsFile.read(8192));
            _dds = StreamUtils.copyStreamToByteArray(_is);
            StreamUtils.closeQuietly(_is);
        }
        else
        {
            _dds = _ddsFile.readBytes();
        }

        Gdx2DPixmap _p2x = Gdx2DPixmap.newPixmap(DDS.getWidth(_dds), DDS.getHeight(_dds), Gdx2DPixmap.GDX2D_FORMAT_RGBA8888);
        ByteBuffer _buf = DDS.read(_dds, 0, true);
        _buf.position(0);
        _p2x.getPixels().put(_buf);
        return new Pixmap(_p2x);
    }
}
