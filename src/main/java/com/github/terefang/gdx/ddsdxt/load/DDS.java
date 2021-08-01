package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.nio.ByteBuffer;

class DDS {
    /* -- original code from DDSReader.java
     * -- Copyright (c) 2015 Kenji Sasaki
     * -- Released under the MIT license.
     * -- https://github.com/npedotnet/DDSReader/blob/master/LICENSE
     */

    static final class Order {
        Order(int redShift, int greenShift, int blueShift, int alphaShift) {
            this.redShift = redShift;
            this.greenShift = greenShift;
            this.blueShift = blueShift;
            this.alphaShift = alphaShift;
        }
        public int redShift;
        public int greenShift;
        public int blueShift;
        public int alphaShift;
    }
    public static final Order ORDER_ARGB = new Order(16, 8, 0, 24);
    public static final Order ORDER_ABGR = new Order(0, 8, 16, 24);

    public static final Order ORDER_RGBA = new Order(24, 16, 8, 0);
    public static final Order ORDER_BGRA = new Order(8, 16, 24, 0);

    // Image Type
    static final int DXT1 = (0x44585431);
    static final int DXT3 = (0x44585433);
    static final int DXT5 = (0x44585435);
    static final int DX10 = (0x44583130);
    static final int DX10_HEADER_OFFSET = (128);
    static final int DX10_OFFSET = (DX10_HEADER_OFFSET+20);

    static final int D3D10_RESOURCE_DIMENSION_UNKNOWN   = 0;
    static final int D3D10_RESOURCE_DIMENSION_BUFFER    = 1;
    static final int D3D10_RESOURCE_DIMENSION_TEXTURE1D = 2;
    static final int D3D10_RESOURCE_DIMENSION_TEXTURE2D = 3;
    static final int D3D10_RESOURCE_DIMENSION_TEXTURE3D = 4;

    static final int DXGI_FORMAT_UNKNOWN                                    = 0;
    /*

    static final int DXGI_FORMAT_R32G32B32A32_TYPELESS                      = 1;
    static final int DXGI_FORMAT_R32G32B32A32_FLOAT                         = 2;
    static final int DXGI_FORMAT_R32G32B32A32_UINT                          = 3;
    static final int DXGI_FORMAT_R32G32B32A32_SINT                          = 4;
    static final int DXGI_FORMAT_R32G32B32_TYPELESS                         = 5;
    static final int DXGI_FORMAT_R32G32B32_FLOAT                            = 6;
    static final int DXGI_FORMAT_R32G32B32_UINT                             = 7;
    static final int DXGI_FORMAT_R32G32B32_SINT                             = 8;
    static final int DXGI_FORMAT_R16G16B16A16_TYPELESS                      = 9;
    static final int DXGI_FORMAT_R16G16B16A16_FLOAT                         = 10;
    static final int DXGI_FORMAT_R16G16B16A16_UNORM                         = 11;
    static final int DXGI_FORMAT_R16G16B16A16_UINT                          = 12;
    static final int DXGI_FORMAT_R16G16B16A16_SNORM                         = 13;
    static final int DXGI_FORMAT_R16G16B16A16_SINT                          = 14;
    static final int DXGI_FORMAT_R32G32_TYPELESS                            = 15;
    static final int DXGI_FORMAT_R32G32_FLOAT                               = 16;
    static final int DXGI_FORMAT_R32G32_UINT                                = 17;
    static final int DXGI_FORMAT_R32G32_SINT                                = 18;
    static final int DXGI_FORMAT_R32G8X24_TYPELESS                          = 19;
    static final int DXGI_FORMAT_D32_FLOAT_S8X24_UINT                       = 20;
    static final int DXGI_FORMAT_R32_FLOAT_X8X24_TYPELESS                   = 21;
    static final int DXGI_FORMAT_X32_TYPELESS_G8X24_UINT                    = 22;
    static final int DXGI_FORMAT_R10G10B10A2_TYPELESS                       = 23;
    static final int DXGI_FORMAT_R10G10B10A2_UNORM                          = 24;
    static final int DXGI_FORMAT_R10G10B10A2_UINT                           = 25;
    static final int DXGI_FORMAT_R11G11B10_FLOAT                            = 26;
    static final int DXGI_FORMAT_R8G8B8A8_TYPELESS                          = 27;
    static final int DXGI_FORMAT_R8G8B8A8_UNORM                             = 28;
    static final int DXGI_FORMAT_R8G8B8A8_UNORM_SRGB                        = 29;
    static final int DXGI_FORMAT_R8G8B8A8_UINT                              = 30;
    static final int DXGI_FORMAT_R8G8B8A8_SNORM                             = 31;
    static final int DXGI_FORMAT_R8G8B8A8_SINT                              = 32;
    static final int DXGI_FORMAT_R16G16_TYPELESS                            = 33;
    static final int DXGI_FORMAT_R16G16_FLOAT                               = 34;
    static final int DXGI_FORMAT_R16G16_UNORM                               = 35;
    static final int DXGI_FORMAT_R16G16_UINT                                = 36;
    static final int DXGI_FORMAT_R16G16_SNORM                               = 37;
    static final int DXGI_FORMAT_R16G16_SINT                                = 38;
    static final int DXGI_FORMAT_R32_TYPELESS                               = 39;
    static final int DXGI_FORMAT_D32_FLOAT                                  = 40;
    static final int DXGI_FORMAT_R32_FLOAT                                  = 41;
    static final int DXGI_FORMAT_R32_UINT                                   = 42;
    static final int DXGI_FORMAT_R32_SINT                                   = 43;
    static final int DXGI_FORMAT_R24G8_TYPELESS                             = 44;
    static final int DXGI_FORMAT_D24_UNORM_S8_UINT                          = 45;
    static final int DXGI_FORMAT_R24_UNORM_X8_TYPELESS                      = 46;
    static final int DXGI_FORMAT_X24_TYPELESS_G8_UINT                       = 47;
    static final int DXGI_FORMAT_R8G8_TYPELESS                              = 48;
    static final int DXGI_FORMAT_R8G8_UNORM                                 = 49;
    static final int DXGI_FORMAT_R8G8_UINT                                  = 50;
    static final int DXGI_FORMAT_R8G8_SNORM                                 = 51;
    static final int DXGI_FORMAT_R8G8_SINT                                  = 52;
    static final int DXGI_FORMAT_R16_TYPELESS                               = 53;
    static final int DXGI_FORMAT_R16_FLOAT                                  = 54;
    static final int DXGI_FORMAT_D16_UNORM                                  = 55;
    static final int DXGI_FORMAT_R16_UNORM                                  = 56;
    static final int DXGI_FORMAT_R16_UINT                                   = 57;
    static final int DXGI_FORMAT_R16_SNORM                                  = 58;
    static final int DXGI_FORMAT_R16_SINT                                   = 59;
    static final int DXGI_FORMAT_R8_TYPELESS                                = 60;
    static final int DXGI_FORMAT_R8_UNORM                                   = 61;
    static final int DXGI_FORMAT_R8_UINT                                    = 62;
    static final int DXGI_FORMAT_R8_SNORM                                   = 63;
    static final int DXGI_FORMAT_R8_SINT                                    = 64;
    static final int DXGI_FORMAT_A8_UNORM                                   = 65;
    static final int DXGI_FORMAT_R1_UNORM                                   = 66;
    static final int DXGI_FORMAT_R9G9B9E5_SHAREDEXP                         = 67;
    static final int DXGI_FORMAT_R8G8_B8G8_UNORM                            = 68;
    static final int DXGI_FORMAT_G8R8_G8B8_UNORM                            = 69;
    */
    static final int DXGI_FORMAT_BC1_TYPELESS                               = 70;
    static final int DXGI_FORMAT_BC1_UNORM                                  = 71;
    static final int DXGI_FORMAT_BC1_UNORM_SRGB                             = 72;
    static final int DXGI_FORMAT_BC2_TYPELESS                               = 73;
    static final int DXGI_FORMAT_BC2_UNORM                                  = 74;
    static final int DXGI_FORMAT_BC2_UNORM_SRGB                             = 75;
    static final int DXGI_FORMAT_BC3_TYPELESS                               = 76;
    static final int DXGI_FORMAT_BC3_UNORM                                  = 77;
    static final int DXGI_FORMAT_BC3_UNORM_SRGB                             = 78;
    /*
    static final int DXGI_FORMAT_BC4_TYPELESS                               = 79;
    static final int DXGI_FORMAT_BC4_UNORM                                  = 80;
    static final int DXGI_FORMAT_BC4_SNORM                                  = 81;
    static final int DXGI_FORMAT_BC5_TYPELESS                               = 82;
    static final int DXGI_FORMAT_BC5_UNORM                                  = 83;
    static final int DXGI_FORMAT_BC5_SNORM                                  = 84;
    */
    static final int DXGI_FORMAT_B5G6R5_UNORM                               = 85;
    static final int DXGI_FORMAT_B5G5R5A1_UNORM                             = 86;
    static final int DXGI_FORMAT_B8G8R8A8_UNORM                             = 87;
    static final int DXGI_FORMAT_B8G8R8X8_UNORM                             = 88;
    static final int DXGI_FORMAT_R10G10B10_XR_BIAS_A2_UNORM                 = 89;
    static final int DXGI_FORMAT_B8G8R8A8_TYPELESS                          = 90;
    static final int DXGI_FORMAT_B8G8R8A8_UNORM_SRGB                        = 91;
    static final int DXGI_FORMAT_B8G8R8X8_TYPELESS                          = 92;
    static final int DXGI_FORMAT_B8G8R8X8_UNORM_SRGB                        = 93;
    /*
    static final int DXGI_FORMAT_BC6H_TYPELESS                              = 94;
    static final int DXGI_FORMAT_BC6H_UF16                                  = 95;
    static final int DXGI_FORMAT_BC6H_SF16                                  = 96;
    static final int DXGI_FORMAT_BC7_TYPELESS                               = 97;
    static final int DXGI_FORMAT_BC7_UNORM                                  = 98;
    static final int DXGI_FORMAT_BC7_UNORM_SRGB                             = 99;
    static final int DXGI_FORMAT_AYUV                                       = 100;
    static final int DXGI_FORMAT_Y410                                       = 101;
    static final int DXGI_FORMAT_Y416                                       = 102;
    static final int DXGI_FORMAT_NV12                                       = 103;
    static final int DXGI_FORMAT_P010                                       = 104;
    static final int DXGI_FORMAT_P016                                       = 105;
    static final int DXGI_FORMAT_420_OPAQUE                                 = 106;
    static final int DXGI_FORMAT_YUY2                                       = 107;
    static final int DXGI_FORMAT_Y210                                       = 108;
    static final int DXGI_FORMAT_Y216                                       = 109;
    static final int DXGI_FORMAT_NV11                                       = 110;
    static final int DXGI_FORMAT_AI44                                       = 111;
    static final int DXGI_FORMAT_IA44                                       = 112;
    static final int DXGI_FORMAT_P8                                         = 113;
    static final int DXGI_FORMAT_A8P8                                       = 114;
    static final int DXGI_FORMAT_B4G4R4A4_UNORM                             = 115;
    static final int DXGI_FORMAT_P208                                       = 116;
    static final int DXGI_FORMAT_V208                                       = 117;
    static final int DXGI_FORMAT_V408                                       = 118;
    static final int DXGI_FORMAT_SAMPLER_FEEDBACK_MIN_MIP_OPAQUE            = 119;
    static final int DXGI_FORMAT_SAMPLER_FEEDBACK_MIP_REGION_USED_OPAQUE    = 120;
    */
    static final int A1R5G5B5 = ((1 << 16) | 2);
    static final int X1R5G5B5 = ((2 << 16) | 2);
    static final int A4R4G4B4 = ((3 << 16) | 2);
    static final int X4R4G4B4 = ((4 << 16) | 2);
    static final int R5G6B5 = ((5 << 16) | 2);
    static final int R8G8B8 = ((1 << 16) | 3);
    static final int A8B8G8R8 = ((1 << 16) | 4);
    static final int X8B8G8R8 = ((2 << 16) | 4);
    static final int A8R8G8B8 = ((3 << 16) | 4);
    static final int X8R8G8B8 = ((4 << 16) | 4);

    // RGBA Masks
    static final int[] A1R5G5B5_MASKS = {0x7C00, 0x03E0, 0x001F, 0x8000};
    static final int[] X1R5G5B5_MASKS = {0x7C00, 0x03E0, 0x001F, 0x0000};
    static final int[] A4R4G4B4_MASKS = {0x0F00, 0x00F0, 0x000F, 0xF000};
    static final int[] X4R4G4B4_MASKS = {0x0F00, 0x00F0, 0x000F, 0x0000};
    static final int[] R5G6B5_MASKS = {0xF800, 0x07E0, 0x001F, 0x0000};
    static final int[] R8G8B8_MASKS = {0xFF0000, 0x00FF00, 0x0000FF, 0x000000};
    static final int[] A8B8G8R8_MASKS = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
    static final int[] X8B8G8R8_MASKS = {0x000000FF, 0x0000FF00, 0x00FF0000, 0x00000000};
    static final int[] A8R8G8B8_MASKS = {0x00FF0000, 0x0000FF00, 0x000000FF, 0xFF000000};
    static final int[] X8R8G8B8_MASKS = {0x00FF0000, 0x0000FF00, 0x000000FF, 0x00000000};

    // BIT4 = 17 * index;
    static final int[] BIT5 = {0, 8, 16, 25, 33, 41, 49, 58, 66, 74, 82, 90, 99, 107, 115, 123, 132, 140, 148, 156, 165, 173, 181, 189, 197, 206, 214, 222, 230, 239, 247, 255};
    static final int[] BIT6 = {0, 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 45, 49, 53, 57, 61, 65, 69, 73, 77, 81, 85, 89, 93, 97, 101, 105, 109, 113, 117, 121, 125, 130, 134, 138, 142, 146, 150, 154, 158, 162, 166, 170, 174, 178, 182, 186, 190, 194, 198, 202, 206, 210, 215, 219, 223, 227, 231, 235, 239, 243, 247, 251, 255};

    public static final int DDPF_ALPHAPIXELS = 0x00000001; // Alpha channel is present

    public static int getDx10DxgiFormat(byte[] buffer) {
        return (buffer[DX10_HEADER_OFFSET] & 0xFF) | (buffer[DX10_HEADER_OFFSET+1] & 0xFF) << 8 | (buffer[DX10_HEADER_OFFSET+2] & 0xFF) << 16 | (buffer[DX10_HEADER_OFFSET+3] & 0xFF) << 24;
    }
    public static int getDx10ResourceDimension(byte[] buffer) {
        return (buffer[DX10_HEADER_OFFSET+4] & 0xFF) | (buffer[DX10_HEADER_OFFSET+5] & 0xFF) << 8 | (buffer[DX10_HEADER_OFFSET+6] & 0xFF) << 16 | (buffer[DX10_HEADER_OFFSET+7] & 0xFF) << 24;
    }
    public static int getDx10MiscFlag(byte[] buffer) {
        return (buffer[DX10_HEADER_OFFSET+8] & 0xFF) | (buffer[DX10_HEADER_OFFSET+9] & 0xFF) << 8 | (buffer[DX10_HEADER_OFFSET+10] & 0xFF) << 16 | (buffer[DX10_HEADER_OFFSET+11] & 0xFF) << 24;
    }
    public static int getDx10ArraySize(byte[] buffer) {
        return (buffer[DX10_HEADER_OFFSET+12] & 0xFF) | (buffer[DX10_HEADER_OFFSET+13] & 0xFF) << 8 | (buffer[DX10_HEADER_OFFSET+14] & 0xFF) << 16 | (buffer[DX10_HEADER_OFFSET+15] & 0xFF) << 24;
    }
    public static int getDx10MiscFlags2(byte[] buffer) {
        return (buffer[DX10_HEADER_OFFSET+16] & 0xFF) | (buffer[DX10_HEADER_OFFSET+17] & 0xFF) << 8 | (buffer[DX10_HEADER_OFFSET+18] & 0xFF) << 16 | (buffer[DX10_HEADER_OFFSET+19] & 0xFF) << 24;
    }

    public static int getHeight(byte[] buffer) {
        return (buffer[12] & 0xFF) | (buffer[13] & 0xFF) << 8 | (buffer[14] & 0xFF) << 16 | (buffer[15] & 0xFF) << 24;
    }

    public static int getWidth(byte[] buffer) {
        return (buffer[16] & 0xFF) | (buffer[17] & 0xFF) << 8 | (buffer[18] & 0xFF) << 16 | (buffer[19] & 0xFF) << 24;
    }

    public static int getMipmap(byte[] buffer) {
        return (buffer[28] & 0xFF) | (buffer[29] & 0xFF) << 8 | (buffer[30] & 0xFF) << 16 | (buffer[31] & 0xFF) << 24;
    }

    public static int getPixelFormatFlags(byte[] buffer) {
        return (buffer[80] & 0xFF) | (buffer[81] & 0xFF) << 8 | (buffer[82] & 0xFF) << 16 | (buffer[83] & 0xFF) << 24;
    }

    public static int getFourCC(byte[] buffer) {
        return (buffer[84] & 0xFF) << 24 | (buffer[85] & 0xFF) << 16 | (buffer[86] & 0xFF) << 8 | (buffer[87] & 0xFF);
    }

    public static int getBitCount(byte[] buffer) {
        return (buffer[88] & 0xFF) | (buffer[89] & 0xFF) << 8 | (buffer[90] & 0xFF) << 16 | (buffer[91] & 0xFF) << 24;
    }

    public static int getRedMask(byte[] buffer) {
        return (buffer[92] & 0xFF) | (buffer[93] & 0xFF) << 8 | (buffer[94] & 0xFF) << 16 | (buffer[95] & 0xFF) << 24;
    }

    public static int getGreenMask(byte[] buffer) {
        return (buffer[96] & 0xFF) | (buffer[97] & 0xFF) << 8 | (buffer[98] & 0xFF) << 16 | (buffer[99] & 0xFF) << 24;
    }

    public static int getBlueMask(byte[] buffer) {
        return (buffer[100] & 0xFF) | (buffer[101] & 0xFF) << 8 | (buffer[102] & 0xFF) << 16 | (buffer[103] & 0xFF) << 24;
    }

    public static int getAlphaMask(byte[] buffer) {
        return (buffer[104] & 0xFF) | (buffer[105] & 0xFF) << 8 | (buffer[106] & 0xFF) << 16 | (buffer[107] & 0xFF) << 24;
    }

    public static ByteBuffer read(byte[] _buffer, int _mipmapLevel, boolean _unDxt) {

        // header
        int _width = getWidth(_buffer);
        int _height = getHeight(_buffer);
        int _mipmap = getMipmap(_buffer);

        // type
        int _type = getType(_buffer);
        if (_type == 0) return null;
        int _subType = (_type==DX10) ? getDx10DxgiFormat(_buffer) : 0;

        // offset
        int _offset = (_type==DX10) ? DX10_OFFSET : 128; // header size
        int _chunkLen = 0;
        if (_mipmapLevel > 0 && _mipmapLevel < _mipmap) {
            for (int i = 0; i < _mipmapLevel; i++) {
                switch (_type) {
                    case DXT1:
                        _offset += dxt1Length(_width, _height);
                        break;
                    case DXT3:
                    case DXT5:
                        _offset += dxt5Length(_width, _height);
                        break;
                    case A1R5G5B5:
                    case X1R5G5B5:
                    case A4R4G4B4:
                    case X4R4G4B4:
                    case R5G6B5:
                    case R8G8B8:
                    case A8B8G8R8:
                    case X8B8G8R8:
                    case A8R8G8B8:
                    case X8R8G8B8:
                        _offset += (_type & 0xFF) * _width * _height;
                        break;
                    case DX10:
                        switch(_subType)
                        {

                            default:
                                throw new GdxRuntimeException("Unsupported DDS/DX10 Texture");
                        }
                    default:
                        throw new GdxRuntimeException("Unsupported DDS Texture");
                }
                _width /= 2;
                _height /= 2;
            }
            if (_width <= 0) _width = 1;
            if (_height <= 0) _height = 1;
        }

        switch (_type) {
            case DXT1:
                if(!_unDxt)
                {
                    _chunkLen = dxt1Length(_width, _height);
                }
                else
                {
                    return decodeDXT1(_width, _height, _offset, _buffer, ORDER_ABGR);
                }
                break;
            case DXT3:
                if(!_unDxt)
                {
                    _chunkLen = dxt5Length(_width, _height);
                }
                else
                {
                    return decodeDXT3(_width, _height, _offset, _buffer, ORDER_ABGR);
                }
            case DXT5:
                if(!_unDxt)
                {
                    _chunkLen = dxt5Length(_width, _height);
                }
                else 
                {
                    return decodeDXT5(_width, _height, _offset, _buffer, ORDER_ABGR);
                }
                break;
            case A1R5G5B5:
                return readA1R5G5B5(_width, _height, _offset, _buffer, ORDER_ABGR);
            case X1R5G5B5:
                return readX1R5G5B5(_width, _height, _offset, _buffer, ORDER_ABGR);
            case A4R4G4B4:
                return readA4R4G4B4(_width, _height, _offset, _buffer, ORDER_ABGR);
            case X4R4G4B4:
                return readX4R4G4B4(_width, _height, _offset, _buffer, ORDER_ABGR);
            case R5G6B5:
                return readR5G6B5(_width, _height, _offset, _buffer, ORDER_ABGR);
            case R8G8B8:
                return readR8G8B8(_width, _height, _offset, _buffer, ORDER_ABGR);
            case A8B8G8R8:
                return readA8B8G8R8(_width, _height, _offset, _buffer, ORDER_ABGR);
            case X8B8G8R8:
                return readX8B8G8R8(_width, _height, _offset, _buffer, ORDER_ABGR);
            case A8R8G8B8:
                return readA8R8G8B8(_width, _height, _offset, _buffer, ORDER_ABGR);
            case X8R8G8B8:
                return readX8R8G8B8(_width, _height, _offset, _buffer, ORDER_ABGR);
            default:
                throw new GdxRuntimeException("Unsupported DDS Texture");
        }

        ByteBuffer _buf = BufferUtils.newUnsafeByteBuffer(_chunkLen);
        _buf.put(_buffer, _offset, _chunkLen);
        _buf.position(0);
        return _buf;
    }

     static int dxt1Length(int _w, int _h) {
        return 8 * ((_w + 3) / 4) * ((_h + 3) / 4);
    }

     static int dxt5Length(int _w, int _h) {
        return 16 * ((_w + 3) / 4) * ((_h + 3) / 4);
    }

     static int getType(byte[] buffer) {

        int type = 0;

        int flags = getPixelFormatFlags(buffer);

        if ((flags & 0x04) != 0)
        {
            // DXT or other 4CC
            type = getFourCC(buffer);
        }
        else
        if ((flags & 0x40) != 0)
        {
            // RGB
            int bitCount = getBitCount(buffer);
            int redMask = getRedMask(buffer);
            int greenMask = getGreenMask(buffer);
            int blueMask = getBlueMask(buffer);
            int alphaMask = ((flags & 0x01) != 0) ? getAlphaMask(buffer) : 0; // 0x01 alpha
            if (bitCount == 16) {
                if (redMask == A1R5G5B5_MASKS[0] && greenMask == A1R5G5B5_MASKS[1] && blueMask == A1R5G5B5_MASKS[2] && alphaMask == A1R5G5B5_MASKS[3]) {
                    // A1R5G5B5
                    type = A1R5G5B5;
                } else if (redMask == X1R5G5B5_MASKS[0] && greenMask == X1R5G5B5_MASKS[1] && blueMask == X1R5G5B5_MASKS[2] && alphaMask == X1R5G5B5_MASKS[3]) {
                    // X1R5G5B5
                    type = X1R5G5B5;
                } else if (redMask == A4R4G4B4_MASKS[0] && greenMask == A4R4G4B4_MASKS[1] && blueMask == A4R4G4B4_MASKS[2] && alphaMask == A4R4G4B4_MASKS[3]) {
                    // A4R4G4B4
                    type = A4R4G4B4;
                } else if (redMask == X4R4G4B4_MASKS[0] && greenMask == X4R4G4B4_MASKS[1] && blueMask == X4R4G4B4_MASKS[2] && alphaMask == X4R4G4B4_MASKS[3]) {
                    // X4R4G4B4
                    type = X4R4G4B4;
                } else if (redMask == R5G6B5_MASKS[0] && greenMask == R5G6B5_MASKS[1] && blueMask == R5G6B5_MASKS[2] && alphaMask == R5G6B5_MASKS[3]) {
                    // R5G6B5
                    type = R5G6B5;
                } else {
                    // Unsupported 16bit RGB image
                }
            } else if (bitCount == 24) {
                if (redMask == R8G8B8_MASKS[0] && greenMask == R8G8B8_MASKS[1] && blueMask == R8G8B8_MASKS[2] && alphaMask == R8G8B8_MASKS[3]) {
                    // R8G8B8
                    type = R8G8B8;
                } else {
                    // Unsupported 24bit RGB image
                }
            } else if (bitCount == 32) {
                if (redMask == A8B8G8R8_MASKS[0] && greenMask == A8B8G8R8_MASKS[1] && blueMask == A8B8G8R8_MASKS[2] && alphaMask == A8B8G8R8_MASKS[3]) {
                    // A8B8G8R8
                    type = A8B8G8R8;
                } else if (redMask == X8B8G8R8_MASKS[0] && greenMask == X8B8G8R8_MASKS[1] && blueMask == X8B8G8R8_MASKS[2] && alphaMask == X8B8G8R8_MASKS[3]) {
                    // X8B8G8R8
                    type = X8B8G8R8;
                } else if (redMask == A8R8G8B8_MASKS[0] && greenMask == A8R8G8B8_MASKS[1] && blueMask == A8R8G8B8_MASKS[2] && alphaMask == A8R8G8B8_MASKS[3]) {
                    // A8R8G8B8
                    type = A8R8G8B8;
                } else if (redMask == X8R8G8B8_MASKS[0] && greenMask == X8R8G8B8_MASKS[1] && blueMask == X8R8G8B8_MASKS[2] && alphaMask == X8R8G8B8_MASKS[3]) {
                    // X8R8G8B8
                    type = X8R8G8B8;
                } else {
                    // Unsupported 32bit RGB image
                }
            }
        } else {
            // YUV or LUMINANCE image
        }
        return type;
    }

     static ByteBuffer readA1R5G5B5(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int rgba = (buffer[index] & 0xFF) | (buffer[index + 1] & 0xFF) << 8;
            index += 2;
            int r = BIT5[(rgba & A1R5G5B5_MASKS[0]) >> 10];
            int g = BIT5[(rgba & A1R5G5B5_MASKS[1]) >> 5];
            int b = BIT5[(rgba & A1R5G5B5_MASKS[2])];
            int a = 255 * ((rgba & A1R5G5B5_MASKS[3]) >> 15);
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readX1R5G5B5(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int rgba = (buffer[index] & 0xFF) | (buffer[index + 1] & 0xFF) << 8;
            index += 2;
            int r = BIT5[(rgba & X1R5G5B5_MASKS[0]) >> 10];
            int g = BIT5[(rgba & X1R5G5B5_MASKS[1]) >> 5];
            int b = BIT5[(rgba & X1R5G5B5_MASKS[2])];
            int a = 255;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readA4R4G4B4(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int rgba = (buffer[index] & 0xFF) | (buffer[index + 1] & 0xFF) << 8;
            index += 2;
            int r = 17 * ((rgba & A4R4G4B4_MASKS[0]) >> 8);
            int g = 17 * ((rgba & A4R4G4B4_MASKS[1]) >> 4);
            int b = 17 * ((rgba & A4R4G4B4_MASKS[2]));
            int a = 17 * ((rgba & A4R4G4B4_MASKS[3]) >> 12);
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readX4R4G4B4(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int rgba = (buffer[index] & 0xFF) | (buffer[index + 1] & 0xFF) << 8;
            index += 2;
            int r = 17 * ((rgba & A4R4G4B4_MASKS[0]) >> 8);
            int g = 17 * ((rgba & A4R4G4B4_MASKS[1]) >> 4);
            int b = 17 * ((rgba & A4R4G4B4_MASKS[2]));
            int a = 255;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readR5G6B5(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int rgba = (buffer[index] & 0xFF) | (buffer[index + 1] & 0xFF) << 8;
            index += 2;
            int r = BIT5[((rgba & R5G6B5_MASKS[0]) >> 11)];
            int g = BIT6[((rgba & R5G6B5_MASKS[1]) >> 5)];
            int b = BIT5[((rgba & R5G6B5_MASKS[2]))];
            int a = 255;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readR8G8B8(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int b = buffer[index++] & 0xFF;
            int g = buffer[index++] & 0xFF;
            int r = buffer[index++] & 0xFF;
            int a = 255;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readA8B8G8R8(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int r = buffer[index++] & 0xFF;
            int g = buffer[index++] & 0xFF;
            int b = buffer[index++] & 0xFF;
            int a = buffer[index++] & 0xFF;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readX8B8G8R8(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int r = buffer[index++] & 0xFF;
            int g = buffer[index++] & 0xFF;
            int b = buffer[index++] & 0xFF;
            int a = 255;
            index++;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readA8R8G8B8(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int b = buffer[index++] & 0xFF;
            int g = buffer[index++] & 0xFF;
            int r = buffer[index++] & 0xFF;
            int a = buffer[index++] & 0xFF;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer readX8R8G8B8(int width, int height, int offset, byte[] buffer, Order order) {
        int index = offset;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        for (int i = 0; i < height * width; i++) {
            int b = buffer[index++] & 0xFF;
            int g = buffer[index++] & 0xFF;
            int r = buffer[index++] & 0xFF;
            int a = 255;
            index++;
            pixels.putInt((a << order.alphaShift) | (r << order.redShift) | (g << order.greenShift) | (b << order.blueShift));
        }
        return pixels;
    }

     static ByteBuffer decodeDXT1(int width, int height, int offset, byte [] buffer, Order order) {
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        int index = offset;
        int w = (width+3)/4;
        int h = (height+3)/4;
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                int c0 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8; index += 2;
                int c1 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8; index += 2;
                for(int k=0; k<4; k++) {
                    if(4*i+k >= height) break;
                    int t0 = (buffer[index] & 0x03);
                    int t1 = (buffer[index] & 0x0C) >> 2;
                    int t2 = (buffer[index] & 0x30) >> 4;
                    int t3 = (buffer[index++] & 0xC0) >> 6;
                    pixels.putInt((4*width*i+4*j+width*k+0)*4, getDXTColor(c0, c1, 0xFF, t0, order));
                    if(4*j+1 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+1)*4, getDXTColor(c0, c1, 0xFF, t1, order));
                    if(4*j+2 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+2)*4, getDXTColor(c0, c1, 0xFF, t2, order));
                    if(4*j+3 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+3)*4, getDXTColor(c0, c1, 0xFF, t3, order));
                }
            }
        }
        return pixels;
    }

     static ByteBuffer decodeDXT3(int width, int height, int offset, byte [] buffer, Order order) {
        int index = offset;
        int w = (width+3)/4;
        int h = (height+3)/4;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        int [] alphaTable = new int[16];
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                // create alpha table(4bit to 8bit)
                for(int k=0; k<4; k++) {
                    int a0 = (buffer[index++] & 0xFF);
                    int a1 = (buffer[index++] & 0xFF);
                    // 4bit alpha to 8bit alpha
                    alphaTable[4*k+0] = 17 * ((a0 & 0xF0)>>4);
                    alphaTable[4*k+1] = 17 * (a0 & 0x0F);
                    alphaTable[4*k+2] = 17 * ((a1 & 0xF0)>>4);
                    alphaTable[4*k+3] = 17 * (a1 & 0x0F);
                }
                int c0 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8; index += 2;
                int c1 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8; index += 2;
                for(int k=0; k<4; k++) {
                    if(4*i+k >= height) break;
                    int t0 = (buffer[index] & 0x03);
                    int t1 = (buffer[index] & 0x0C) >> 2;
                    int t2 = (buffer[index] & 0x30) >> 4;
                    int t3 = (buffer[index++] & 0xC0) >> 6;
                    pixels.putInt((4*width*i+4*j+width*k+0)*4, getDXTColor(c0, c1, alphaTable[4*k+0], t0, order));
                    if(4*j+1 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+1)*4, getDXTColor(c0, c1, alphaTable[4*k+1], t1, order));
                    if(4*j+2 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+2)*4, getDXTColor(c0, c1, alphaTable[4*k+2], t2, order));
                    if(4*j+3 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+3)*4, getDXTColor(c0, c1, alphaTable[4*k+3], t3, order));
                }
            }
        }
        return pixels;
    }

     static ByteBuffer decodeDXT5(int width, int height, int offset, byte [] buffer, Order order) {
        int index = offset;
        int w = (width+3)/4;
        int h = (height+3)/4;
        ByteBuffer pixels = BufferUtils.newUnsafeByteBuffer(width * height * 4);
        int [] alphaTable = new int[16];
        for(int i=0; i<h; i++) {
            for(int j=0; j<w; j++) {
                // create alpha table
                int a0 = (buffer[index++] & 0xFF);
                int a1 = (buffer[index++] & 0xFF);
                int b0 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8 | (buffer[index+2] & 0xFF) << 16; index += 3;
                int b1 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8 | (buffer[index+2] & 0xFF) << 16; index += 3;
                alphaTable[0] = b0 & 0x07;
                alphaTable[1] = (b0 >> 3) & 0x07;
                alphaTable[2] = (b0 >> 6) & 0x07;
                alphaTable[3] = (b0 >> 9) & 0x07;
                alphaTable[4] = (b0 >> 12) & 0x07;
                alphaTable[5] = (b0 >> 15) & 0x07;
                alphaTable[6] = (b0 >> 18) & 0x07;
                alphaTable[7] = (b0 >> 21) & 0x07;
                alphaTable[8] = b1 & 0x07;
                alphaTable[9] = (b1 >> 3) & 0x07;
                alphaTable[10] = (b1 >> 6) & 0x07;
                alphaTable[11] = (b1 >> 9) & 0x07;
                alphaTable[12] = (b1 >> 12) & 0x07;
                alphaTable[13] = (b1 >> 15) & 0x07;
                alphaTable[14] = (b1 >> 18) & 0x07;
                alphaTable[15] = (b1 >> 21) & 0x07;
                int c0 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8; index += 2;
                int c1 = (buffer[index] & 0xFF) | (buffer[index+1] & 0xFF) << 8; index += 2;
                for(int k=0; k<4; k++) {
                    if(4*i+k >= height) break;
                    int t0 = (buffer[index] & 0x03);
                    int t1 = (buffer[index] & 0x0C) >> 2;
                    int t2 = (buffer[index] & 0x30) >> 4;
                    int t3 = (buffer[index++] & 0xC0) >> 6;
                    pixels.putInt((4*width*i+4*j+width*k+0)*4, getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4*k+0]), t0, order));
                    if(4*j+1 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+1)*4, getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4*k+1]), t1, order));
                    if(4*j+2 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+2)*4, getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4*k+2]), t2, order));
                    if(4*j+3 >= width) continue;
                    pixels.putInt((4*width*i+4*j+width*k+3)*4, getDXTColor(c0, c1, getDXT5Alpha(a0, a1, alphaTable[4*k+3]), t3, order));
                }
            }
        }
        return pixels;
    }

     static int getDXTColor(int c0, int c1, int a, int t, Order order) {
        switch(t) {
            case 0: return getDXTColor1(c0, a, order);
            case 1: return getDXTColor1(c1, a, order);
            case 2: return (c0 > c1) ? getDXTColor2_1(c0, c1, a, order) : getDXTColor1_1(c0, c1, a, order);
            case 3: return (c0 > c1) ? getDXTColor2_1(c1, c0, a, order) : 0;
        }
        return 0;
    }

     static int getDXTColor2_1(int c0, int c1, int a, Order order) {
        // 2*c0/3 + c1/3
        int r = (2*BIT5[(c0 & 0xFC00) >> 11] + BIT5[(c1 & 0xFC00) >> 11]) / 3;
        int g = (2*BIT6[(c0 & 0x07E0) >> 5] + BIT6[(c1 & 0x07E0) >> 5]) / 3;
        int b = (2*BIT5[c0 & 0x001F] + BIT5[c1 & 0x001F]) / 3;
        return (a<<order.alphaShift)|(r<<order.redShift)|(g<<order.greenShift)|(b<<order.blueShift);
    }

     static int getDXTColor1_1(int c0, int c1, int a, Order order) {
        // (c0+c1) / 2
        int r = (BIT5[(c0 & 0xFC00) >> 11] + BIT5[(c1 & 0xFC00) >> 11]) / 2;
        int g = (BIT6[(c0 & 0x07E0) >> 5] + BIT6[(c1 & 0x07E0) >> 5]) / 2;
        int b = (BIT5[c0 & 0x001F] + BIT5[c1 & 0x001F]) / 2;
        return (a<<order.alphaShift)|(r<<order.redShift)|(g<<order.greenShift)|(b<<order.blueShift);
    }

     static int getDXTColor1(int c, int a, Order order) {
        int r = BIT5[(c & 0xFC00) >> 11];
        int g = BIT6[(c & 0x07E0) >> 5];
        int b = BIT5[(c & 0x001F)];
        return (a<<order.alphaShift)|(r<<order.redShift)|(g<<order.greenShift)|(b<<order.blueShift);
    }

     static int getDXT5Alpha(int a0, int a1, int t) {
        if(a0 > a1) switch(t) {
            case 0: return a0;
            case 1: return a1;
            case 2: return (6*a0+a1)/7;
            case 3: return (5*a0+2*a1)/7;
            case 4: return (4*a0+3*a1)/7;
            case 5: return (3*a0+4*a1)/7;
            case 6: return (2*a0+5*a1)/7;
            case 7: return (a0+6*a1)/7;
        }
        else switch(t) {
            case 0: return a0;
            case 1: return a1;
            case 2: return (4*a0+a1)/5;
            case 3: return (3*a0+2*a1)/5;
            case 4: return (2*a0+3*a1)/5;
            case 5: return (a0+4*a1)/5;
            case 6: return 0;
            case 7: return 255;
        }
        return 0;
    }
}
