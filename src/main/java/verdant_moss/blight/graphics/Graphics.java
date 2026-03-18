package verdant_moss.blight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.lwjgl.opengl.GL20;
import verdant_moss.blight.Blight;
import verdant_moss.hollow.Color;

import static verdant_moss.blight.Blight.BLIGHT_AURORA;

public class Graphics {
	private final Blight blight;
	private final FrameBuffer fbo;
	private final ShapeRenderer shapeRenderer;
	private final SpriteBatch batch;
	private float r, g, b, a;
	
	public Graphics(Blight blight) {
		this.blight = blight;
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, (int)blight.getInternalSize().width,
				(int)blight.getInternalSize().height, false);
		shapeRenderer = new ShapeRenderer();
		batch = new SpriteBatch();
		shapeRenderer.getProjectionMatrix().setToOrtho2D(0, 0, blight.getInternalSize().width,
				blight.getInternalSize().height);
	}
	
	public void begin() {
		fbo.begin();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		resetColor();
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
	
	public void circle(float x, float y, float radius) {
		shapeRenderer.circle((int)x, (int)y, (int)radius);
	}
	
	public void dispose() {
		shapeRenderer.dispose();
		batch.dispose();
		fbo.dispose();
	}
	
	public void end() {
		shapeRenderer.end();
		fbo.end();
		Texture tex = fbo.getColorBufferTexture();
		batch.begin();
		batch.draw(tex, 0, blight.getWindowSize().height, blight.getWindowSize().width,
				-blight.getWindowSize().height);
		batch.end();
	}
	
	public void line(float x1, float y1, float x2, float y2) {
		shapeRenderer.line((int)x1, (int)y1, (int)x2, (int)y2);
	}
	
	public void rect(float x, float y, float width, float height) {
		shapeRenderer.rect((int)x, (int)y, (int)width, (int)height);
	}
	
	public void setColor(int red, int green, int blue, int alpha) {
		this.r = red / 255f;
		this.g = green / 255f;
		this.b = blue / 255f;
		this.a = alpha / 255f;
		shapeRenderer.setColor(r, g, b, a);
	}
}