package com.github.terefang.gdx.ddsdxt.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Array;

import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoader;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoaderFactory;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;

/** {@link DxtnTextureAssetLoader} for {@link Texture} instances. The pixel data is loaded synchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class DxtnTextureAssetLoader extends SynchronousAssetLoader<Texture, TextureLoader.TextureParameter>
{
    public static void register(AssetManager _mgr, FileHandleResolver _resolver)
    {
        DxtnTextureAssetLoader _tl = new DxtnTextureAssetLoader(_resolver);
        _mgr.setLoader(Texture.class, ".dds", _tl);
        _mgr.setLoader(Texture.class, ".dds.gz", _tl);
        _mgr.setLoader(Texture.class, ".dxtn", _tl);
        _mgr.setLoader(Texture.class, ".dxtn.gz", _tl);
    }

    public static void register(AssetManager _mgr)
    {
        register(_mgr, new AbsoluteFileHandleResolver());
    }

    boolean useServiceLoader = false;
    public DxtnTextureAssetLoader(FileHandleResolver _resolver) {
        super(_resolver);
    }


    public DxtnTextureAssetLoader(FileHandleResolver _resolver, boolean _sl) {
        super(_resolver);
        this.useServiceLoader = _sl;
    }

    @Override
    public Texture load(AssetManager _manager, String _fileName, FileHandle _file, TextureLoader.TextureParameter _parameter)
    {
        boolean _genMipMaps = false;
        if (_parameter != null) {
            _genMipMaps = _parameter.genMipMaps;
        }

        TextureData _textureData = null;
        if(this.useServiceLoader)
        {
            GenericTextureDataLoader _loader = GenericTextureDataLoaderFactory.findLoader(_fileName, _file);
            if(_loader!=null)
            {
                _textureData = _loader.load(_fileName, _file, _parameter);
            }
        }
        else
        if(_fileName.endsWith(".dds") || _fileName.endsWith(".dds.gz"))
        {
            _textureData = DDSLoader.fromDDS(_file, _genMipMaps);
        }
        else
        if(_fileName.endsWith(".dxtn") || _fileName.endsWith(".dxtn.gz"))
        {
            _textureData = DXTNLoader.fromDXTN(_file, _genMipMaps);
        }

        if(_textureData==null) return null;

        Texture _texture = new Texture(_textureData);
        if (_parameter != null) {
            _texture.setFilter(_parameter.minFilter, _parameter.magFilter);
            _texture.setWrap(_parameter.wrapU, _parameter.wrapV);
        }
        return _texture;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String _fileName, FileHandle _file, TextureLoader.TextureParameter _parameter) {
        return null;
    }
}
