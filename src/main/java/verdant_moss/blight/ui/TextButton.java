package verdant_moss.blight.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import verdant_moss.blight.graphics.Graphics;
import verdant_moss.blight.helper.TextHelper;
import verdant_moss.hollow.Color;

public class TextButton extends Button {
	private String text;
	private BitmapFont font;
	private Runnable onClick;
	private Runnable onRelease;
	
	public TextButton(int x, int y, String text, BitmapFont font) {
		super(x, y, TextHelper.GetStringWidth(text, font), TextHelper.GetStringHeight(text, font));
		this.text = text;
		this.font = font;
	}
	
	@Override
	public void dispose() {
	}
	
	@Override
	public void onClick() {
		if(onClick != null) {
			onClick.run();
		}
	}
	
	@Override
	public void onRelease() {
		if(onRelease != null) {
			onRelease.run();
		}
	}
	
	@Override
	public void render(Graphics g) {
		switch(state) {
			case Button.IDLE -> {
				g.setColor(Color.BLACK);
			}
			case Button.HOVER -> {
				g.setColor(Color.LIGHT_GRAY);
			}
			case Button.PRESSED -> {
				g.setColor(Color.GRAY);
			}
		}
		g.outlineRect(bounds);
		g.setFont(font);
		g.string(text, bounds.position.x, bounds.position.y);
	}
	
	public void setOnRelease(Runnable onRelease) {
		this.onRelease = onRelease;
	}
	
	public void setOnClick(Runnable onClick) {
		this.onClick = onClick;
	}
}
