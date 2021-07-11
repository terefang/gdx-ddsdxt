## GDX-DDSDXT

pure java code for libgdx to load and bind: 

* compressed DXT1/3/5 textures from DDS format files (.dds)
* various uncompressed argb/xrgb textures from DDS format files (.dds)

### TODO

implement more image formats from foreign engines to facilitate reuse (if that makes sense).

* .vtf -- Source Texture -- https://developer.valvesoftware.com/wiki/Valve_Texture_Format

### DO NOTS

* .tga -- shitty legacy format -- use png/jpg/dds
* .bmp -- yet another legacy format -- use png/jpg/dds

### PROBLEM AREAS

* .dds dxt1 textures are not flagged for alpha -- you have to use the loader-parameter.

### HISTORIC

* Neverwinter Nights Aurora Engine -- BioDDS Format, pre-cursor to MS-DDS -- https://nwn.wiki/display/NWN1/DDS