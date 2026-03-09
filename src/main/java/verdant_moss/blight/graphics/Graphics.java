package verdant_moss.blight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import org.lwjgl.opengl.GL20;
import verdant_moss.blight.Blight;

public class Graphics {
	private final Blight blight;
	private final ShapeRenderer shapeRenderer;
	private FrameBuffer fbo;
	
	public Graphics(Blight blight) {
		this.blight = blight;
		fbo = new FrameBuffer(Pixmap.Format.RGBA8888, blight.getWidth(), blight.getHeight(), false);
		this.shapeRenderer = new ShapeRenderer();
	}
	
	public void dispose() {
		shapeRenderer.dispose();
	}
	public void render() {
		fbo.begin();
		Gdx.gl.glClearColor(1,1,1,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
	}
	public void renderRectangle(int x, int y, int width, int height, int red, int green, int blue) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(red / 255f, green / 255f, blue / 255f, 1f);
		shapeRenderer.rect(x, y, width, height);
		shapeRenderer.end();
	}
}