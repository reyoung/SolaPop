package tjuhot.solapop;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;

import android.graphics.Color;
import android.graphics.Typeface;

public class ResultPage extends Entity {
	Engine		mEngine;
	private Texture mFontTexture;
	private Text mText;
	private Font mFont;
	public ResultPage(Engine engine){
		super(0,0);
		mEngine = engine;
		this.mFontTexture = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, 
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
				32, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mEngine.getFontManager().loadFont(mFont);
	}
	public void show(int score){
		this.detachChildren();
		this.mText = new Text(0, 0, mFont, String.format("Score : %d",score));
		this.attachChild(mText);
	}
}
