package verdant_moss.blight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import org.lwjgl.opengl.GL20;
import verdant_moss.blight.Blight;
import verdant_moss.blight.units.Rectangle;
import verdant_moss.blight.units.Size;
import verdant_moss.hollow.Color;

import static verdant_moss.blight.Blight.BLIGHT_AURORA;

public class Graphics {
	private final Blight blight;
	private final FrameBuffer fbo;
	private final ShapeRenderer shape_renderer;
	private final SpriteBatch batch;
	private final Matrix4 internal_projection = new Matrix4();
	private final Matrix4 screen_projection = new Matrix4();
	private final Size internal_size;
	private final Size window_size;
	private final Size internal_scaled_size;
	private float r, g, b, a;
	private DrawMode mode = DrawMode.NONE;
	private ShapeRenderer.ShapeType currentShapeType = null;
	private BitmapFont currentFont = null;
	
	public Graphics(Blight blight) {
		this.blight = blight;
		internal_size = blight.getInternalSize();
		window_size = blight.getWindowSize();
		internal_scaled_size = internal_size.scale(blight.getScale());
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int)internal_size.width, (int)internal_size.height, false);
		shape_renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		internal_projection.setToOrtho2D(0, 0, (int)internal_size.width, (int)internal_size.height);
		screen_projection.setToOrtho2D(0, 0, (int)window_size.width, (int)window_size.height);
		shape_renderer.setProjectionMatrix(internal_projection);
		batch.setProjectionMatrix(internal_projection);
		fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
	}
	
	public void begin() {
		fbo.begin();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape_renderer.setProjectionMatrix(internal_projection);
		batch.setProjectionMatrix(internal_projection);
		mode = DrawMode.NONE;
		currentShapeType = null;
	}
	
	public void end() {
		if(mode == DrawMode.SHAPE) {
			shape_renderer.end();
		} else if(mode == DrawMode.TEXTURE) {
			batch.end();
		}
		mode = DrawMode.NONE;
		currentShapeType = null;
		fbo.end();
		Texture tex = fbo.getColorBufferTexture();
		batch.setProjectionMatrix(screen_projection);
		batch.begin();
		batch.draw(tex, 0, 0, (int)internal_scaled_size.width, (int)internal_scaled_size.height, 0, 0, tex.getWidth(),
				tex.getHeight(), false, true);
		batch.end();
		batch.setProjectionMatrix(internal_projection);
	}
	
	public void fillCircle(float x, float y, float radius) {
		switchToShape(ShapeRenderer.ShapeType.Filled);
		shape_renderer.circle(x, flipYPoint(y), radius);
	}
	
	private void switchToShape(ShapeRenderer.ShapeType type) {
		if(mode == DrawMode.TEXTURE) {
			batch.end();
		}
		if(mode != DrawMode.SHAPE || currentShapeType != type) {
			if(mode == DrawMode.SHAPE) {
				shape_renderer.end();
			}
			shape_renderer.begin(type);
			currentShapeType = type;
			mode = DrawMode.SHAPE;
		}
	}
	
	private float flipYPoint(float y) {
		return internal_size.height - y;
	}
	
	public void fillRect(Rectangle rectangle) {
		fillRect(rectangle.position.x, rectangle.position.y, rectangle.size.width, rectangle.size.height);
	}
	
	public void fillRect(float x, float y, float width, float height) {
		switchToShape(ShapeRenderer.ShapeType.Filled);
		shape_renderer.rect(x, flipY(y, height), width, height);
	}
	
	private float flipY(float y, float height) {
		return internal_size.height - y - height;
	}
	
	public void image(Texture texture, float x, float y) {
		switchToTexture();
		batch.draw(texture, x, flipY(y, texture.getHeight()));
	}
	
	private void switchToTexture() {
		if(mode == DrawMode.SHAPE) {
			shape_renderer.end();
		}
		if(mode != DrawMode.TEXTURE) {
			batch.begin();
			mode = DrawMode.TEXTURE;
		}
	}
	
	public void image(Texture texture, float x, float y, int tileX, int tileY, int srcWidth, int srcHeight) {
		switchToTexture();
		float screenY = flipY(y, srcHeight);
		batch.draw(texture, x, screenY, srcWidth, srcHeight, tileX * srcWidth, tileY * srcHeight, srcWidth, srcHeight,
				false, false);
	}
	
	public void line(float x1, float y1, float x2, float y2) {
		switchToShape(ShapeRenderer.ShapeType.Line);
		shape_renderer.line(x1, flipYPoint(y1), x2, flipYPoint(y2));
	}
	
	public void outlineCircle(float x, float y, float radius) {
		switchToShape(ShapeRenderer.ShapeType.Line);
		shape_renderer.circle(x, flipYPoint(y), radius);
	}
	
	public void outlineRect(Rectangle rectangle) {
		outlineRect(rectangle.position.x, rectangle.position.y, rectangle.size.width, rectangle.size.height);
	}
	
	public void outlineRect(float x, float y, float width, float height) {
		switchToShape(ShapeRenderer.ShapeType.Line);
		shape_renderer.rect(x, flipY(y, height), width, height);
	}
	
	public void resetColor() {
		setColor(new Color(255, 255, 255));
	}
	
	public void setColor(Color color) {
		if(color == null) {
			BLIGHT_AURORA.warning("setColor called with null Color");
			return;
		}
		this.r = color.red / 255f;
		this.g = color.green / 255f;
		this.b = color.blue / 255f;
		this.a = color.alpha / 255f;
		shape_renderer.setColor(r, g, b, a);
		if(currentFont != null) {
			currentFont.setColor(r, g, b, a);
		}
	}
	
	public void setColor(int red, int green, int blue, int alpha) {
		this.r = red / 255f;
		this.g = green / 255f;
		this.b = blue / 255f;
		this.a = alpha / 255f;
		shape_renderer.setColor(r, g, b, a);
		if(currentFont != null) {
			currentFont.setColor(r, g, b, a);
		}
	}
	
	public void setFont(String fontPath, int size) {
		BitmapFont font = Assets.GetFont(fontPath, size);
		if(font == null) {
			BLIGHT_AURORA.warning("Font not loaded: " + fontPath + " size " + size + ". Did you call Assets" +
					".LoadFont?");
			return;
		}
		setFont(font);
	}
	
	public void setFont(BitmapFont font) {
		currentFont = font;
		if(currentFont != null) {
			currentFont.setColor(r, g, b, a);
		}
	}
	
	public void string(String text, float x, float y) {
		if(currentFont == null) {
			BLIGHT_AURORA.error("No font set. Cannot draw text.");
			return;
		}
		switchToTexture();
		if(text != null) {
			currentFont.draw(batch, text, x, flipYPoint(y));
		}
	}
	
	private enum DrawMode {
		NONE,
		SHAPE,
		TEXTURE
	}
}