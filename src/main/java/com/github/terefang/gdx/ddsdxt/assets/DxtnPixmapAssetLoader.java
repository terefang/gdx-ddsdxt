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
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Array;
import com.github.terefang.gdx.ddsdxt.GenericPixmapLoader;
import com.github.terefang.gdx.ddsdxt.GenericPixmapLoaderFactory;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoader;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoaderFactory;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;

/** {@link DxtnPixmapAssetLoader} for {@link Texture} instances. The pixel data is loaded synchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class DxtnPixmapAssetLoader extends SynchronousAssetLoader<Pixmap, AssetLoaderParameters<Pixmap>>
{
    public static DxtnPixmapAssetLoader register(AssetManager _mgr, FileHandleResolver _resolver)
    {
        DxtnPixmapAssetLoader _tl = new DxtnPixmapAssetLoader(_resolver);
        _mgr.setLoader(Pixmap.class, ".dds", _tl);
        _mgr.setLoader(Pixmap.class, ".dds.gz", _tl);
        _mgr.setLoader(Pixmap.class, ".dxtn", _tl);
        _mgr.setLoader(Pixmap.class, ".dxtn.gz", _tl);
        return _tl;
    }

    public static void register(AssetManager _mgr)
    {
        register(_mgr, new AbsoluteFileHandleResolver());
    }

    boolean useServiceLoader = false;
    public DxtnPixmapAssetLoader(FileHandleResolver _resolver)
    {
        super(_resolver);
    }


    public DxtnPixmapAssetLoader(FileHandleResolver _resolver, boolean _sl)
    {
        super(_resolver);
        this.useServiceLoader = _sl;
    }

    @Override
    public Pixmap load(AssetManager _manager, String _fileName, FileHandle _file, AssetLoaderParameters<Pixmap> _parameter)
    {
        if(this.useServiceLoader)
        {
            GenericPixmapLoader _loader = GenericPixmapLoaderFactory.findLoader(_fileName, _file);
            if(_loader!=null)
            {
                return _loader.loadPixmap(_fileName, _file);
            }
        }
        else
        if(_fileName.endsWith(".dds") || _fileName.endsWith(".dds.gz"))
        {
            return DDSLoader.fromDdsToPixmap(_fileName, _file);
        }
        else
        if(_fileName.endsWith(".dxtn") || _fileName.endsWith(".dxtn.gz"))
        {
            return DXTNLoader.fromDxtnToPixmap(_fileName, _file);
        }

        return null;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String _fileName, FileHandle _file, AssetLoaderParameters<Pixmap> _parameter)
    {
        return null;
    }
}
