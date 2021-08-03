package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.TextureData;

public interface GenericTextureDataLoader
{
    public TextureData loadTexture(String _fileName, FileHandle _file, TextureLoader.TextureParameter _param);
}
