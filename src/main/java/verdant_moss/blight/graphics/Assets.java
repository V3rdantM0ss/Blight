package verdant_moss.blight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;
import java.util.Map;

public class Assets {
	private static final Map<String, Texture> textures = new HashMap<>();
	
	public static void Dispose() {
		for(Texture tex : textures.values()) {
			tex.dispose();
		}
		textures.clear();
	}
	
	public static Texture Get(String name) {
		return textures.get(name);
	}
	
	public static Texture Load(String name) {
		Texture tex = new Texture(Gdx.files.internal(name));
		tex.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
		textures.put(name, tex);
		return tex;
	}
}