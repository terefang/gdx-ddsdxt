
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetManager;
import com.github.terefang.gdx.ddsdxt.assets.DxtnTextureAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.LocalFileHandleResolver;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.github.terefang.gdx.ddsdxt.load.DXTNLoader;
import lombok.SneakyThrows;

public class TestImagesAsset implements ApplicationListener {
    private Lwjgl3Application application;
    private OrthographicCamera hudCam;
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private AssetManager assetManager;

    @SneakyThrows
    @Override
    public void create()
    {
        DXTNLoader.convertDDSToDXTN(Gdx.files.local("src/test/resources/ruby_dxt1.dds"), true,
                Gdx.files.local("src/test/resources/ruby.dxtn.gz"));
        //this.hudCam = new OrthographicCamera();
        this.batch = new SpriteBatch();
        this.assetManager = new AssetManager(new LocalFileHandleResolver());
        DxtnTextureAssetLoader.register(this.assetManager, this.assetManager.getFileHandleResolver());
        //this.texture = new Texture(DDSLoader.fromDDS(Gdx.files.local("src/test/resources/ruby_dxt1a.8.dds"), false, true, false));
        this.assetManager.load("src/test/resources/ruby.dxtn.gz", Texture.class);
        this.assetManager.finishLoading();
        this.texture = this.assetManager.get("src/test/resources/ruby.dxtn.gz");
        this.sprite = new Sprite(texture);
    }

    @Override
    public void dispose()
    {
        this.batch.dispose();
        this.assetManager.dispose();
    }

    @Override
    public void render()
    {
        {
            ScreenUtils.clear(1,1,0,1, true);
        }

        {
            this.batch.begin();
            this.sprite.draw(this.batch);
            this.batch.end();
        }
    }

    @Override
    public void resize(int width, int height)
    {
        //OrthographicCamera guiCam = new OrthographicCamera();
        //guiCam.setToOrtho(false, width, height);
        //guiCam.update();
        //this.batch.setProjectionMatrix(guiCam.combined);
        System.err.println("resized");
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    public static void main(String[] args) throws Exception
    {
        for(Graphics.Monitor _monitor : Lwjgl3ApplicationConfiguration.getMonitors())
        {
            System.err.println(_monitor.name);
            for(Graphics.DisplayMode _dm : Lwjgl3ApplicationConfiguration.getDisplayModes(_monitor))
            {
                System.err.println(String.format("%d x %d @ %d / %d", _dm.width, _dm.height, _dm.bitsPerPixel, _dm.refreshRate));
            }
        }
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.useOpenGL3(true, 4, 6);
        //config.setvSyncEnabled = true;
        config.setWindowedMode(800, 600);
        config.setTitle("Terefang LibGDX Contrib 3d Example");
        config.setResizable(true);
        //config.setFullscreenMode();
        TestImagesAsset _t = new TestImagesAsset();
        Lwjgl3Application application = new Lwjgl3Application(_t, config);
    }
}
