import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
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

import java.util.Arrays;

public class TestToPNG implements ApplicationListener {

    @SneakyThrows
    @Override
    public void create()
    {
        AssetManager _mgr = new AssetManager();
        JavaPixmapAssetLoader.register(_mgr);

        for(String _fn : Arrays.asList("ruby.dxtn.gz"))
        {
            String _f = "src/test/resources/"+_fn;
            Pixmap _px = DXTNLoader.fromDxtnToPixmap(_f, Gdx.files.local(_f));
            PixmapIO.writePNG(Gdx.files.local(_f+".png"), _px);
        }

        for(String _fn : Arrays.asList("ruby_argb.dds", "ruby_dxt1.dds", "ruby_dxt1a.dds", "ruby_dxt3.dds", "ruby_dxt5.dds"))
        {
            String _f = "src/test/resources/"+_fn;
            Pixmap _px = DDSLoader.fromDdsToPixmap(_f, Gdx.files.local(_f));
            PixmapIO.writePNG(Gdx.files.local(_f+".png"), _px);
        }

        Pixmap _px = JavaPixmapLoader.load("src/test/resources/ruby.dxtn.gz.png",
                Gdx.files.local("src/test/resources/ruby.dxtn.gz.png"));
        JavaPixmapIO.writePNG(Gdx.files.local("src/test/resources/test.png"), _px);
        JavaPixmapIO.writeJPG(Gdx.files.local("src/test/resources/test.jpg"), _px);
        JavaPixmapIO.writeType("gif", true, Gdx.files.local("src/test/resources/test.gif"), _px);
        JavaPixmapIO.writeType("tif", true, Gdx.files.local("src/test/resources/test.tif"), _px);
        System.exit(0);
    }

    public static void main(String[] args) throws Exception
    {
        TestToPNG _t = new TestToPNG();
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
