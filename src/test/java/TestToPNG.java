import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.github.terefang.gdx.ddsdxt.load.DDSLoader;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;
import lombok.SneakyThrows;

import java.util.Arrays;

public class TestToPNG implements ApplicationListener {

    @SneakyThrows
    @Override
    public void create()
    {
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
