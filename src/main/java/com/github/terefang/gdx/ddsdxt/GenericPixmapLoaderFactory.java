package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.files.FileHandle;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface GenericPixmapLoaderFactory
{
    public static GenericPixmapLoader findLoader(String _fileName, FileHandle _file)
    {
        ServiceLoader<GenericPixmapLoaderFactory> _sl = ServiceLoader.load(GenericPixmapLoaderFactory.class);
        Iterator<GenericPixmapLoaderFactory> _it = _sl.iterator();
        while(_it.hasNext())
        {
            GenericPixmapLoaderFactory _impl = _it.next();
            if(_impl.canLoadPixmap(_fileName,_file))
            {
                return _impl.getInstance();
            }
        }
        return null;
    }

    public boolean canLoadPixmap(String _fileName, FileHandle _file);

    public GenericPixmapLoader getInstance();
}
