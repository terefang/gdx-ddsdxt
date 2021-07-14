package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.TextureData;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoader;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoaderFactory;

public class DDSLoaderFactory implements GenericTextureDataLoaderFactory, GenericTextureDataLoader {
    @Override
    public boolean canLoad(String _fileName, FileHandle _file) {
        return _fileName.endsWith(".dds") || _fileName.endsWith(".dds.gz");
    }

    @Override
    public GenericTextureDataLoader getInstance() {
        return this;
    }

    @Override
    public TextureData load(String _fileName, FileHandle _file, TextureLoader.TextureParameter _param)
    {
        return DDSLoader.fromDDS(_file, _param==null ? false : _param.genMipMaps);
    }
}
