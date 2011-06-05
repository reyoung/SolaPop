package tjuhot.solapop;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Line;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.util.HorizontalAlign;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;

public class HeadScene extends Entity {
	private int mCurrentScore;
	private int mTotalScore;
	private Font mFont;
	private Texture mFontTexture;
	private Engine mEngine;
	private Text mText;
	public HeadScene(int totalBeat,Engine aEngine,Context cont)
	{
		mEngine = aEngine;
		mCurrentScore = 0;
		mTotalScore = totalBeat * 2;
		this.mFontTexture = new Texture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFont = FontFactory.createFromAsset(this.mFontTexture, cont, "fonts/Plok.ttf", 32, true, Color.RED);
		this.mEngine.getTextureManager().loadTexture(mFontTexture);
		this.mEngine.getFontManager().loadFont(mFont);
		mText = new Text(420, 50, mFont, String.format("%s/%s", mCurrentScore,mTotalScore),HorizontalAlign.CENTER);
		this.attachChild(mText);
		initScore();
	}
	public int getScore()
	{
		return mCurrentScore > mTotalScore? mTotalScore : mCurrentScore;
	}
	public void hitLevelProc(int hitScore)
	{
		if(hitScore >= 5)
		{
			mCurrentScore += 2;
			fresh();
		}
		else if(hitScore >= 1)
		{
			mCurrentScore += 1;
			fresh();
		}
	}
	private void initScore()
	{
		final Line line = new Line(0, 50,400 , 50, 30);
		line.setColor((float)0.0, (float)0.0, (float)1.0);
		this.attachChild(line);
	}
	private void fresh()
	{
		float scoreRatio = (float)mCurrentScore / (float)mTotalScore;
		float scoreLength = scoreRatio * 400;
		scoreLength = scoreLength > 400 ? 400 : scoreLength;
		final Line line = new Line(0, 50,0+scoreLength ,50,30);
		if(scoreRatio < 0.6)line.setColor((float)0.0, (float)1.0, (float)0.0);
		else if(scoreRatio < 0.8)line.setColor((float)1.0, (float)0.843, (float)0.0);
		else line.setColor((float)1.0, (float)0.0, (float)0.0);
		this.attachChild(line);
		this.detachChild(mText);
		mText = new Text(420, 50, mFont, String.format("%s/%s", mCurrentScore,mTotalScore),HorizontalAlign.CENTER);
		this.attachChild(mText);
	}
}
