package com.github.terefang.gdx.ddsdxt.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.utils.Array;
import com.github.terefang.gdx.ddsdxt.GenericPixmapLoader;
import com.github.terefang.gdx.ddsdxt.GenericPixmapLoaderFactory;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;
import com.github.terefang.gdx.ddsdxt.load.JavaPixmapLoader;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/** {@link JavaPixmapAssetLoader} for {@link Texture} instances. The pixel data is loaded synchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class JavaPixmapAssetLoader extends SynchronousAssetLoader<Pixmap, AssetLoaderParameters<Pixmap>>
{
    private List<String> suffixes = new Vector();

    public static JavaPixmapAssetLoader register(AssetManager _mgr, FileHandleResolver _resolver)
    {
        JavaPixmapAssetLoader _tl = new JavaPixmapAssetLoader(_resolver);
        for(String _suffix : ImageIO.getReaderFileSuffixes())
        {
            _tl.suffixes.add("."+_suffix);
            _mgr.setLoader(Pixmap.class, "."+_suffix, _tl);
        }
        return _tl;
    }

    public static void register(AssetManager _mgr)
    {
        register(_mgr, new AbsoluteFileHandleResolver());
    }

    public JavaPixmapAssetLoader(FileHandleResolver _resolver)
    {
        super(_resolver);
    }

    @SneakyThrows
    @Override
    public Pixmap load(AssetManager _manager, String _fileName, FileHandle _file, AssetLoaderParameters<Pixmap> _parameter)
    {
        return JavaPixmapLoader.load(_fileName, _file);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String _fileName, FileHandle _file, AssetLoaderParameters<Pixmap> _parameter)
    {
        return null;
    }
}
