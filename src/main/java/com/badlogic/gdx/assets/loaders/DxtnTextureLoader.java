package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.AbsoluteFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoader;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoaderFactory;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;
import jdk.jfr.events.FileForceEvent;

/** {@link AssetLoader} for {@link Texture} instances. The pixel data is loaded asynchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class DxtnTextureLoader
extends TextureLoader
{
    public static void register(AssetManager _mgr, FileHandleResolver _resolver)
    {
        DxtnTextureLoader _tl = new DxtnTextureLoader(_resolver);
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
    public DxtnTextureLoader(FileHandleResolver _resolver) {
        super(_resolver);
    }

    public DxtnTextureLoader(FileHandleResolver _resolver, boolean _sl) {
        super(_resolver);
        this.useServiceLoader = _sl;
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextureLoader.TextureParameter parameter) {
        info.filename = fileName;
        boolean genMipMaps = false;
        if (parameter != null) {
            genMipMaps = parameter.genMipMaps;
        }

        if(this.useServiceLoader)
        {
            GenericTextureDataLoader _loader = GenericTextureDataLoaderFactory.findLoader(fileName, file);
            if(_loader!=null)
            {
                info.data = _loader.load(fileName, file, parameter);
            }
            else
            {
                super.loadAsync(manager, fileName, file,parameter);
                return;
            }
        }
        else
        if(fileName.endsWith(".dds") || fileName.endsWith(".dds.gz"))
        {
            info.data = DDSLoader.fromDDS(file, genMipMaps);
        }
        else
        if(fileName.endsWith(".dxtn") || fileName.endsWith(".dxtn.gz"))
        {
            info.data = DXTNLoader.fromDXTN(file, genMipMaps);
        }
        else
        {
            super.loadAsync(manager, fileName, file,parameter);
            return;
        }

    }

    @Override
    public Texture loadSync (AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
        if (info == null) return null;
        if (!info.data.isPrepared()) info.data.prepare();
        Texture texture = info.texture;
        if (texture != null) {
            texture.load(info.data);
        } else {
            texture = new Texture(info.data);
        }
        if (parameter != null) {
            texture.setFilter(parameter.minFilter, parameter.magFilter);
            texture.setWrap(parameter.wrapU, parameter.wrapV);
        }
        return texture;
    }
}
