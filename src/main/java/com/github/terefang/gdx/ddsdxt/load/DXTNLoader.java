package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.*;
import com.github.terefang.gdx.ddsdxt.dxt.DXT1ATextureData;
import com.github.terefang.gdx.ddsdxt.dxt.DXT1TextureData;
import com.github.terefang.gdx.ddsdxt.dxt.DXT3TextureData;
import com.github.terefang.gdx.ddsdxt.dxt.DXT5TextureData;
import com.github.terefang.gdx.ddsdxt.rgb.RGBATextureData;
import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class DXTNLoader
{
    public static final int TYPE_RGBA = 0x52474241;
    public static final int TYPE_DXT0 = 0x44585430;

    /** Creates texturedata from a dxtn/dxtn.gz file.
     * @param _dxtFile the dxtn file
     * @param _mipmaps create mipmaps */
    @SneakyThrows
    public static final TextureData fromDXTN(FileHandle _dxtFile, boolean _mipmaps)
    {
        byte[] _dxt = null;
        if(_dxtFile.name().endsWith(".gz"))
        {
            GZIPInputStream _is = new GZIPInputStream(_dxtFile.read(8192));
            _dxt = StreamUtils.copyStreamToByteArray(_is);
            StreamUtils.closeQuietly(_is);
        }
        else
        {
            _dxt = _dxtFile.readBytes();
        }

        int _type = getFourCC(_dxt);
        ByteBuffer _buf = BufferUtils.newUnsafeByteBuffer(_dxt.length-16);
        _buf.put(_dxt, 16, _dxt.length-16);
        _buf.position(0);
        switch(_type)
        {
            case TYPE_DXT0:
                return DXT1TextureData.from(_buf, 0, getWidth(_dxt), getHeight(_dxt), _mipmaps);
            case DDS.DXT1:
                return DXT1ATextureData.from(_buf, 0, getWidth(_dxt), getHeight(_dxt), _mipmaps);
            case DDS.DXT3:
                return DXT3TextureData.from(_buf, 0, getWidth(_dxt), getHeight(_dxt), _mipmaps);
            case DDS.DXT5:
                return DXT5TextureData.from(_buf, 0, getWidth(_dxt), getHeight(_dxt), _mipmaps);
            case TYPE_RGBA:
                return RGBATextureData.from(_buf, 0, getWidth(_dxt), getHeight(_dxt), _mipmaps);
            default:
                throw new GdxRuntimeException("Unsupported DXTN Texture");
        }
    }

    public static int getFourCC(byte[] buffer) {
        return (buffer[0] & 0xFF) << 24 | (buffer[1] & 0xFF) << 16 | (buffer[2] & 0xFF) << 8 | (buffer[3] & 0xFF);
    }

    public static int getPixelFormatFlags(byte[] buffer) {
        return (buffer[4] & 0xFF) << 24 | (buffer[5] & 0xFF) << 16 | (buffer[6] & 0xFF) << 8 | (buffer[7] & 0xFF);
    }

    public static int getWidth(byte[] buffer) {
        return (buffer[8] & 0xFF) << 24 | (buffer[9] & 0xFF) << 16 | (buffer[10] & 0xFF) << 8 | (buffer[11] & 0xFF);
    }

    public static int getHeight(byte[] buffer) {
        return (buffer[12] & 0xFF) << 24 | (buffer[13] & 0xFF) << 16 | (buffer[14] & 0xFF) << 8 | (buffer[15] & 0xFF);
    }

    /** Creates dxtn/dxtn.gz from dds file.
     * @param _ddsFile the dds file
     * @param _alpha mark dxt1 as dxt1a
     * @param _dxtFile the dxtn file
     */
    @SneakyThrows
    public static final void convertDDSToDXTN(FileHandle _ddsFile, boolean _alpha, FileHandle _dxtFile)
    {
        byte[] _dds = _ddsFile.readBytes();
        DataBuffer _dos = new DataBuffer(16+_dds.length-128);

        int _type = DDS.getType(_dds);
        switch(_type)
        {
            case DDS.DXT1:
                _dos.writeInt(_alpha ? DDS.DXT1 : TYPE_DXT0);
                break;
            case DDS.DXT3:
            case DDS.DXT5:
                _dos.writeInt(_type);
                break;
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
                _dos.writeInt(TYPE_RGBA);
                break;
            default:
                throw new GdxRuntimeException("Unsupported DDS Texture");
        }

        _dos.writeInt(DDS.getPixelFormatFlags(_dds));
        _dos.writeInt(DDS.getWidth(_dds));
        _dos.writeInt(DDS.getHeight(_dds));

        ByteBuffer _buf = DDS.read(_dds, 0, false);
        _buf.position(0);

        byte[] _tmp = new byte[_buf.capacity()];
        _buf.get(_tmp);
        _dos.write(_tmp);

        if(_dxtFile.name().endsWith(".gz"))
        {
            GZIPOutputStream _out = new GZIPOutputStream(_dxtFile.write(false, 8192));
            _out.write(_dos.getBuffer());
            _out.flush();
            StreamUtils.closeQuietly(_out);
        }
        else
        {
            _dxtFile.writeBytes(_dos.getBuffer(), false);
        }
    }

}
