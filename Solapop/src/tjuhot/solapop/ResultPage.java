package tjuhot.solapop;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class ResultPage extends Entity {
	Engine		mEngine;
	private Texture mFontTexture;
	private Text mText;
	private Font mFont;
	public ResultPage(Engine engine,Context cont){
		super(0,0);
		mEngine = engine;
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mFont = FontFactory.createFromAsset(this.mFontTexture, cont, "fonts/Plok.ttf", 32, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mEngine.getFontManager().loadFont(mFont);
	}
	public void show(int score,int combohit){
		this.detachChildren();
		int high = 50;
		final Text textCenter = new Text(100, 60, this.mFont, String.format("Game Over!\nScore: %d\nMax Combo :%d", score,combohit), HorizontalAlign.CENTER);
		this.attachChild(textCenter);
	}
}
