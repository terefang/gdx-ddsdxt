## GDX-DDSDXT

pure java code for libgdx to load and bind: 

* compressed DXT1/1A/3/5 textures from DDS format files (.dds)
* various uncompressed argb/xrgb textures from DDS format files (.dds)
* textures from .dxtn/.dxtn.gz format files.
* limited support for DDS/DX10, only DXT1A/3/5

### Quick Usage

**Maven Repository**

```xml
<pluginRepositories>
    <pluginRepository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </pluginRepository>
</pluginRepositories>
```

**Maven Dependency**

```xml
<dependency>
  <groupId>com.github.terefang</groupId>
  <artifactId>gdx-ddsdxt</artifactId>
  <version>2021.2</version>
</dependency>
```

#### Conversion

Convert DDS/DXT1 to DXTN format
```
DXTNLoader.convertDDSToDXTN(Gdx.files.local("test_dxt1.dds"), false, 
    Gdx.files.local("test_1.dxtn"));
```

Convert DDS/DXT1A to DXTN format
```
DXTNLoader.convertDDSToDXTN(Gdx.files.local("test_dxt1a.dds"), true, 
    Gdx.files.local("test_1a.dxtn"));
```

Convert DDS/DXT3/5 to DXTN format
```
DXTNLoader.convertDDSToDXTN(Gdx.files.local("test_dxt3.dds"), 
    Gdx.files.local("test_3.dxtn"));
DXTNLoader.convertDDSToDXTN(Gdx.files.local("test_dxt5.dds"), 
    Gdx.files.local("test_5.dxtn"));
```

#### Loading

load DDS as Texture
```
this.texture = new Texture(DDSLoader.fromDDS(Gdx.files.local("test_1.dds")));
```

load DXTN as Texture
```
this.texture = new Texture(DXTNLoader.fromDXTN(Gdx.files.local("test_5.dxtn")));
```

Asset-Manager
```
DxtnTextureLoader.register(assetManager);

assetMeneger.load("test_5.dxtn", Texture.class);
this.texture = assetMeneger.get("test_5.dxtn");
```

DxtnTextureLoader will delegate to stock libgdx for unrecognized extensions.

### TODO

implement more image formats from foreign engines to facilitate reuse (if that makes sense).

* .vtf -- Source Texture -- https://developer.valvesoftware.com/wiki/Valve_Texture_Format

### DONTS

* .tga -- shitty legacy format -- use png/jpg/dds
* .bmp -- yet another legacy format -- use png/jpg/dds

### Problem Areas

* .dds dxt1 textures are not flagged for alpha -- you have to use the loader-parameter.
* .dxtn textures -- yet another custom format, but does properly handle dxt1a 
(use "DXTNLoader.convert" to create).

### Historic

* Neverwinter Nights Aurora Engine -- BioDDS Format, pre-cursor to MS-DDS -- https://nwn.wiki/display/NWN1/DDS