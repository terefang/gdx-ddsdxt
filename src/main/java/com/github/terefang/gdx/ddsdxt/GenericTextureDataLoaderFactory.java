package com.github.terefang.gdx.ddsdxt;

import com.badlogic.gdx.files.FileHandle;

import java.util.Iterator;
import java.util.ServiceLoader;

public interface GenericTextureDataLoaderFactory
{
    public static GenericTextureDataLoader findLoader(String _fileName, FileHandle _file)
    {
        ServiceLoader<GenericTextureDataLoaderFactory> _sl = ServiceLoader.load(GenericTextureDataLoaderFactory.class);
        Iterator<GenericTextureDataLoaderFactory> _it = _sl.iterator();
        while(_it.hasNext())
        {
            GenericTextureDataLoaderFactory _impl = _it.next();
            if(_impl.canLoad(_fileName,_file))
            {
                return _impl.getInstance();
            }
        }
        return null;
    }

    public boolean canLoad(String _fileName, FileHandle _file);

    public GenericTextureDataLoader getInstance();
}
