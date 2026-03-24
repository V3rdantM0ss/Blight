package verdant_moss.blight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import org.lwjgl.opengl.GL20;
import verdant_moss.blight.Blight;
import verdant_moss.hollow.Color;

import static verdant_moss.blight.Blight.BLIGHT_AURORA;

public class Graphics {
	private final Blight blight;
	private final FrameBuffer fbo;
	private final ShapeRenderer shapeRenderer;
	private final SpriteBatch batch;
	private final Matrix4 internalProjection = new Matrix4();
	private final Matrix4 screenProjection = new Matrix4();
	private float r, g, b, a;
	private DrawMode mode = DrawMode.NONE;
	
	public Graphics(Blight blight) {
		this.blight = blight;
		int internalWidth = (int)blight.getInternalSize().width;
		int internalHeight = (int)blight.getInternalSize().height;
		int windowWidth = (int)blight.getWindowSize().width;
		int windowHeight = (int)blight.getWindowSize().height;
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, internalWidth, internalHeight, false);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		internalProjection.setToOrtho2D(0, 0, internalWidth, internalHeight);
		screenProjection.setToOrtho2D(0, 0, windowWidth, windowHeight);
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
	}
	
	public void circle(float x, float y, float radius) {
		switchToShape();
		shapeRenderer.circle(x, y, radius);
	}
	
	private void switchToShape() {
		if(mode == DrawMode.TEXTURE) {
			batch.end();
		}
		if(mode != DrawMode.SHAPE) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			mode = DrawMode.SHAPE;
		}
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
		fbo.end();
		Texture tex = fbo.getColorBufferTexture();
		batch.setProjectionMatrix(screenProjection);
		batch.begin();
		batch.draw(tex, 0, 0, blight.getWindowSize().width, blight.getWindowSize().height);
		batch.end();
		batch.setProjectionMatrix(internalProjection);
	}
	
	// full image
	public void image(Texture texture, float x, float y) {
		switchToTexture();
		// flipY = true inside FBO
		batch.draw(texture,
				(int)x,
				(int)y,
				texture.getWidth(),
				texture.getHeight(),
				0, 0,
				texture.getWidth(),
				texture.getHeight(),
				false, true); // flipY = true
	}
	
	// sprite sheet / region
	public void image(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
		switchToTexture();
		batch.draw(texture,
				(int)x,
				(int)y,
				srcWidth,
				srcHeight,
				srcX, srcY,
				srcWidth, srcHeight,
				false, true); // flipY = true
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
	
	public void line(float x1, float y1, float x2, float y2) {
		switchToShape();
		shapeRenderer.line(x1, y1, x2, y2);
	}
	
	public void rect(float x, float y, float width, float height) {
		switchToShape();
		shapeRenderer.rect(x, y, width, height);
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
	}
	
	public void setColor(int red, int green, int blue, int alpha) {
		this.r = red / 255f;
		this.g = green / 255f;
		this.b = blue / 255f;
		this.a = alpha / 255f;
		shapeRenderer.setColor(r, g, b, a);
	}
	
	private enum DrawMode {
		NONE,
		SHAPE,
		TEXTURE
	}
}