package com.github.terefang.gdx.ddsdxt.load;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.github.terefang.gdx.ddsdxt.GenericPixmapLoader;
import com.github.terefang.gdx.ddsdxt.GenericPixmapLoaderFactory;

public class DDSPixmapLoaderFactory implements GenericPixmapLoaderFactory, GenericPixmapLoader {
    @Override
    public boolean canLoadPixmap(String _fileName, FileHandle _file) {
        return _fileName.endsWith(".dds") || _fileName.endsWith(".dds.gz");
    }

    @Override
    public GenericPixmapLoader getInstance() {
        return this;
    }

    @Override
    public Pixmap loadPixmap(String _fileName, FileHandle _file)
    {
        return DDSLoader.fromDdsToPixmap(_fileName, _file);
    }
}
