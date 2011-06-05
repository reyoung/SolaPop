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

public class ComboShow extends Entity {
	private int maxCombo;
	private int mCombo;
	private Engine	mEngine;
	private Texture mFontTexture;
	private Font mFont;
	private Text mText;
	public ComboShow(int aCombo,Engine aEngine,Context cont){
		super(0,0);	
		mCombo = aCombo;
		maxCombo = 0;
		mEngine = aEngine;
		initFont(cont);
	}
	private void initFont(Context cont)
	{
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, cont, "fonts/Plok.ttf", 32, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mEngine.getFontManager().loadFont(mFont);
		mText = new Text(0, 50, mFont, String.format("Combo:%s", mCombo),
				HorizontalAlign.CENTER);
		this.attachChild(mText);
	}
	public void onComboChanged(int aCombo)
	{
		if(aCombo > maxCombo)maxCombo = aCombo;
		mCombo = aCombo;
		try
		{
			this.detachChild(mText);
		}
		catch( ArrayIndexOutOfBoundsException ae)
		{
		}
		mText = new Text(0, 50, mFont, String.format("Combo:%s", mCombo),
				HorizontalAlign.CENTER);
		this.attachChild(mText);
	}
	public int getMaxCombo()
	{
		return maxCombo;
	}
	public void clearCombo()
	{
		try
		{
			this.detachChild(mText);
		}
		catch( ArrayIndexOutOfBoundsException ae)
		{
		}
	}
}
