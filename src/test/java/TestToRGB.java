import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.github.terefang.gdx.ddsdxt.JavaPixmapIO;
import com.github.terefang.gdx.ddsdxt.assets.JavaPixmapAssetLoader;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;
import com.github.terefang.gdx.ddsdxt.load.JavaPixmapLoader;
import lombok.SneakyThrows;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class TestToRGB implements ApplicationListener {

    @SneakyThrows
    @Override
    public void create()
    {
        ByteBuffer _buf = ByteBuffer.allocateDirect(4096*4096*3);
        for(int _r =0; _r<256; _r++)
        {
            for(int _g =0; _g<256; _g++)
            {
                for(int _b =0; _b<256; _b++)
                {
                    _buf.put((byte) _r);
                    _buf.put((byte) _g);
                    _buf.put((byte) _b);
                }
            }
        }
        _buf.position(0);
        Pixmap _px = new Pixmap(4096, 4096,Pixmap.Format.RGB888);
        _px.setPixels(_buf);
        JavaPixmapIO.writePNG(Gdx.files.local("src/test/resources/rgb.png"), _px);
        System.exit(0);
    }

    public static void main(String[] args) throws Exception
    {
        TestToRGB _t = new TestToRGB();
        HeadlessApplication application = new HeadlessApplication(_t);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
