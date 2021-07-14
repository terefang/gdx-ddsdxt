package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.TextureData;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoader;
import com.github.terefang.gdx.ddsdxt.GenericTextureDataLoaderFactory;

public class DXTNLoaderFactory implements GenericTextureDataLoaderFactory, GenericTextureDataLoader {
    @Override
    public boolean canLoad(String _fileName, FileHandle _file) {
        return _fileName.endsWith(".dxtn") || _fileName.endsWith(".dxtn.gz");
    }

    @Override
    public GenericTextureDataLoader getInstance() {
        return this;
    }

    @Override
    public TextureData load(String _fileName, FileHandle _file, TextureLoader.TextureParameter _param)
    {
        return DXTNLoader.fromDXTN(_file, _param==null ? false : _param.genMipMaps);
    }
}
