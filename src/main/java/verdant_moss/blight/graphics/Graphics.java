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
	private final ShapeRenderer shapeRenderer;
	private final SpriteBatch batch;
	private final Matrix4 internalProjection = new Matrix4();
	private final Matrix4 screenProjection = new Matrix4();
	private final Size internalSize;
	private final Size windowSize;
	private float r, g, b, a;
	private DrawMode mode = DrawMode.NONE;
	private ShapeRenderer.ShapeType currentShapeType = null;
	private BitmapFont currentFont = null;
	
	public Graphics(Blight blight) {
		this.blight = blight;
		internalSize = blight.getInternalSize();
		windowSize = blight.getWindowSize();
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int)internalSize.width, (int)internalSize.height, false);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		internalProjection.setToOrtho2D(0, 0, (int)internalSize.width, (int)internalSize.height);
		screenProjection.setToOrtho2D(0, 0, (int)windowSize.width, (int)windowSize.height);
		shapeRenderer.setProjectionMatrix(internalProjection);
		batch.setProjectionMatrix(internalProjection);
		fbo.getColorBufferTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
	}
	
	public void begin() {
		fbo.begin();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.setProjectionMatrix(internalProjection);
		batch.setProjectionMatrix(internalProjection);
		mode = DrawMode.NONE;
		currentShapeType = null;
	}
	
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		fbo.dispose();
	}
	
	public void end() {
		if(mode == DrawMode.SHAPE) {
			shapeRenderer.end();
		} else if(mode == DrawMode.TEXTURE) {
			batch.end();
		}
		mode = DrawMode.NONE;
		currentShapeType = null;
		fbo.end();
		Texture tex = fbo.getColorBufferTexture();
		batch.setProjectionMatrix(screenProjection);
		batch.begin();
		batch.draw(tex, 0, 0, (int)windowSize.width, (int)windowSize.height, 0, 0, tex.getWidth(), tex.getHeight(),
				false, true);
		batch.end();
		batch.setProjectionMatrix(internalProjection);
	}
	
	public void fillCircle(float x, float y, float radius) {
		switchToShape(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(x, flipYPoint(y), radius);
	}
	
	private void switchToShape(ShapeRenderer.ShapeType type) {
		if(mode == DrawMode.TEXTURE) {
			batch.end();
		}
		if(mode != DrawMode.SHAPE || currentShapeType != type) {
			if(mode == DrawMode.SHAPE) {
				shapeRenderer.end();
			}
			shapeRenderer.begin(type);
			currentShapeType = type;
			mode = DrawMode.SHAPE;
		}
	}
	
	public void fillRect(Rectangle rectangle) {
		fillRect(rectangle.position.x, rectangle.position.y, rectangle.size.width, rectangle.size.height);
	}
	
	public void fillRect(float x, float y, float width, float height) {
		switchToShape(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.rect(x, flipY(y, height), width, height);
	}
	
	private float flipY(float y, float height) {
		return internalSize.height - y - height;
	}
	
	public void image(Texture texture, float x, float y) {
		switchToTexture();
		batch.draw(texture, x, flipY(y, texture.getHeight()));
	}
	
	public void image(Texture texture, float x, float y, int tileX, int tileY, int srcWidth, int srcHeight) {
		switchToTexture();
		float screenY = flipY(y, srcHeight);
		batch.draw(texture, x, screenY, srcWidth, srcHeight, tileX * srcWidth, tileY * srcHeight, srcWidth, srcHeight,
				false, false);
	}
	
	public void line(float x1, float y1, float x2, float y2) {
		switchToShape(ShapeRenderer.ShapeType.Line);
		shapeRenderer.line(x1, flipYPoint(y1), x2, flipYPoint(y2));
	}
	
	public void outlineCircle(float x, float y, float radius) {
		switchToShape(ShapeRenderer.ShapeType.Line);
		shapeRenderer.circle(x, flipYPoint(y), radius);
	}
	
	public void outlineRect(Rectangle rectangle) {
		outlineRect(rectangle.position.x, rectangle.position.y, rectangle.size.width, rectangle.size.height);
	}
	
	public void outlineRect(float x, float y, float width, float height) {
		switchToShape(ShapeRenderer.ShapeType.Line);
		shapeRenderer.rect(x, flipY(y, height), width, height);
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
		shapeRenderer.setColor(r, g, b, a);
		if(currentFont != null) {
			currentFont.setColor(r, g, b, a);
		}
	}
	
	public void setColor(int red, int green, int blue, int alpha) {
		this.r = red / 255f;
		this.g = green / 255f;
		this.b = blue / 255f;
		this.a = alpha / 255f;
		shapeRenderer.setColor(r, g, b, a);
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
	
	private void switchToTexture() {
		if(mode == DrawMode.SHAPE) {
			shapeRenderer.end();
		}
		if(mode != DrawMode.TEXTURE) {
			batch.begin();
			mode = DrawMode.TEXTURE;
		}
	}
	
	private float flipYPoint(float y) {
		return internalSize.height - y;
	}
	
	private enum DrawMode {
		NONE,
		SHAPE,
		TEXTURE
	}
}