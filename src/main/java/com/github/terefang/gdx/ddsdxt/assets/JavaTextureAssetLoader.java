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
import com.badlogic.gdx.utils.Array;
import com.github.terefang.gdx.ddsdxt.load.JavaPixmapLoader;
import com.github.terefang.gdx.ddsdxt.load.JavaTextureLoader;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.util.List;
import java.util.Vector;

/** {@link JavaTextureAssetLoader} for {@link Texture} instances. The pixel data is loaded synchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class JavaTextureAssetLoader extends SynchronousAssetLoader<Texture, TextureLoader.TextureParameter>
{
    private List<String> suffixes = new Vector();

    public static JavaTextureAssetLoader register(AssetManager _mgr, FileHandleResolver _resolver)
    {
        JavaTextureAssetLoader _tl = new JavaTextureAssetLoader(_resolver);
        for(String _suffix : ImageIO.getReaderFileSuffixes())
        {
            _tl.suffixes.add("."+_suffix);
            _mgr.setLoader(Texture.class, "."+_suffix, _tl);
        }
        return _tl;
    }

    public static void register(AssetManager _mgr)
    {
        register(_mgr, new AbsoluteFileHandleResolver());
    }

    public JavaTextureAssetLoader(FileHandleResolver _resolver)
    {
        super(_resolver);
    }

    @SneakyThrows
    @Override
    public Texture load(AssetManager _manager, String _fileName, FileHandle _file, TextureLoader.TextureParameter _parameter)
    {
        return JavaTextureLoader.loadTexture(_fileName, _file, _parameter);
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String _fileName, FileHandle _file, TextureLoader.TextureParameter _parameter)
    {
        return null;
    }
}
