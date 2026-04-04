package verdant_moss.blight.helper;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import verdant_moss.blight.units.Size;

public final class TextHelper {
	private TextHelper() {
	}
	
	public static Size GetStringSize(String text, BitmapFont font) {
		return new Size(GetStringWidth(text, font), GetStringHeight(text, font));
	}
	
	public static int GetStringWidth(String text, BitmapFont font) {
		GlyphLayout layout = new GlyphLayout(font, text);
		return (int)layout.width;
	}
	
	public static int GetStringHeight(String text, BitmapFont font) {
		GlyphLayout layout = new GlyphLayout(font, text);
		return (int)layout.height;
	}
}
