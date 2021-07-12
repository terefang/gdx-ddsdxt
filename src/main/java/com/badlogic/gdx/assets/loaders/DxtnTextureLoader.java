package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;

/** {@link AssetLoader} for {@link Texture} instances. The pixel data is loaded asynchronously. The texture is then created on the
 * rendering thread, synchronously. Passing a {@link TextureLoader.TextureParameter} to
 * {@link AssetManager#load(String, Class, AssetLoaderParameters)} allows one to specify parameters as can be passed to the
 * various Texture constructors, e.g. filtering, whether to generate mipmaps and so on.
 */
public class DxtnTextureLoader
extends TextureLoader
{
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
        else
        {
            super.loadAsync(manager, fileName, file,parameter);
            return;
        }

        if (!info.data.isPrepared()) info.data.prepare();
    }

}
