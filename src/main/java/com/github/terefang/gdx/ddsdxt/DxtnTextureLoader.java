package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.Array;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;

/** {@link AssetLoader} for {@link DxtnTexture} instances. The pixel data is loaded asynchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class DxtnTextureLoader
extends AsynchronousAssetLoader<Texture,TextureLoader.TextureParameter>
{
    static public class TextureLoaderInfo {
        public String filename;
        public TextureData data;
        public DxtnTexture texture;
    };

    TextureLoaderInfo info = new TextureLoaderInfo();

    public DxtnTextureLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextureLoader.TextureParameter parameter) {
        info.filename = fileName;
        boolean genMipMaps = false;
        if (parameter != null) {
            genMipMaps = parameter.genMipMaps;
        }

        if(fileName.endsWith(".dds") || fileName.endsWith(".dds.gz"))
        {
            info.data = DDSLoader.fromDDS(file, genMipMaps);
        }
        else
        if(fileName.endsWith(".dxtn") || fileName.endsWith(".dxtn.gz"))
        {
            info.data = DXTNLoader.fromDXTN(file, genMipMaps);
        }

        if (!info.data.isPrepared()) info.data.prepare();
    }

    @Override
    public DxtnTexture loadSync(AssetManager manager, String fileName, FileHandle file, TextureLoader.TextureParameter parameter) {
        if (info == null) return null;
        DxtnTexture texture = info.texture;
        if (texture != null) {
            texture.load(info.data);
        } else {
            texture = new DxtnTexture(info.data);
        }
        if (parameter != null) {
            texture.setFilter(parameter.minFilter, parameter.magFilter);
            texture.setWrap(parameter.wrapU, parameter.wrapV);
        }
        return texture;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextureLoader.TextureParameter parameter) {
        return null;
    }
}
