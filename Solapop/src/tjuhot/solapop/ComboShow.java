package tjuhot.solapop;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.HorizontalAlign;

import android.graphics.Color;
import android.graphics.Typeface;

public class ComboShow extends Entity {
	private int maxCombo;
	private int mCombo;
	private Engine	mEngine;
	private Texture mFontTexture;
	private Font mFont;
	private Text mText;
	public ComboShow(int aCombo,Engine aEngine){
		super(0,0);	
		mCombo = aCombo;
		maxCombo = 0;
		mEngine = aEngine;
		initFont();
	}
	private void initFont()
	{
		this.mFontTexture = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = new Font(this.mFontTexture, 
				Typeface.create(Typeface.DEFAULT, Typeface.BOLD),
				32, true, Color.RED);
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
