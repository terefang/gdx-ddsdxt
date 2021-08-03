package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;

public interface GenericPixmapLoader
{
    public Pixmap loadPixmap(String _fileName, FileHandle _file);
}
