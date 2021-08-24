package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.github.terefang.gdx.ddsdxt.rgb.RGBATextureData;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.nio.ByteBuffer;

/** {@link JavaTextureLoader} for {@link Texture} instances. The pixel data is loaded synchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class JavaTextureLoader
{
    @SneakyThrows
    public static Texture loadTexture(String _fileName, FileHandle _file, TextureLoader.TextureParameter _param)
    {
        Pixmap _px = JavaPixmapLoader.load(_fileName, _file);
        return new Texture(_px, (_param == null) ? false : _param.genMipMaps);
    }

}
