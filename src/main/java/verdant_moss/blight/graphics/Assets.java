package verdant_moss.blight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import java.util.HashMap;
import java.util.Map;

import static verdant_moss.blight.Blight.BLIGHT_AURORA;

public class Assets {
	public static final String NOTO_SANS_PATH = "assets/verdant_moss/blight/fonts/Noto_Sans/static";
	public static final String NOTO_SANS_BOLD = NOTO_SANS_PATH + "/NotoSans-Bold.ttf";
	public static final String NOTO_SANS_LIGHT = NOTO_SANS_PATH + "/NotoSans-Light.ttf";
	public static final String NOTO_SANS_MEDIUM = NOTO_SANS_PATH + "/NotoSans-Medium.ttf";
	public static final String NOTO_SANS_REGULAR = NOTO_SANS_PATH + "/NotoSans-Regular.ttf";
	private static final Map<String, Texture> textures = new HashMap<>();
	private static final Map<String, Map<Integer, BitmapFont>> fonts = new HashMap<>();
	
	public static void Dispose() {
		for(Texture tex : textures.values()) {
			tex.dispose();
		}
		for(Map<Integer, BitmapFont> fontSizes : fonts.values()) {
			for(BitmapFont font : fontSizes.values()) {
				font.dispose();
			}
		}
		textures.clear();
		fonts.clear();
	}
	
	public static BitmapFont GetFont(String path, int size) {
		Map<Integer, BitmapFont> fontSizes = fonts.get(path);
		if(fontSizes != null) {
			return fontSizes.get(size);
		}
		return null;
	}
	
	public static Texture GetTexture(String name) {
		return textures.get(name);
	}
	
	public static BitmapFont LoadFont(String path, int size) {
		Map<Integer, BitmapFont> fontSizes = fonts.computeIfAbsent(path, k -> new HashMap<>());
		if(fontSizes.containsKey(size)) {
			BLIGHT_AURORA.system("Font already loaded: " + path + " size " + size);
			return fontSizes.get(size);
		}
		BLIGHT_AURORA.system("Loading font: " + path + " size " + size);
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
		FreeTypeFontGenerator.FreeTypeFontParameter param = new FreeTypeFontGenerator.FreeTypeFontParameter();
		param.size = size;
		param.magFilter = Texture.TextureFilter.Nearest;
		param.minFilter = Texture.TextureFilter.Nearest;
		BitmapFont font = generator.generateFont(param);
		generator.dispose();
		fontSizes.put(size, font);
		BLIGHT_AURORA.system("Font loaded successfully: " + path + " size " + size);
		return font;
	}
	
	public static Texture LoadTexture(String name) {
		if(textures.containsKey(name)) {
			BLIGHT_AURORA.system("Texture already loaded: " + name);
			return textures.get(name);
		}
		BLIGHT_AURORA.system("Loading texture: " + name);
		Texture tex = new Texture(Gdx.files.internal(name));
		tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		textures.put(name, tex);
		BLIGHT_AURORA.system("Texture loaded successfully: " + name);
		return tex;
	}
}