package tjuhot.solapop;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.primitive.Line;

public class HeadScene extends Entity {
	private int mCurrentScore;
	private int mTotalScore;
	public HeadScene(int totalBeat)
	{
		mCurrentScore = 0;
		mTotalScore = totalBeat * 2;
		initScore();
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
		final Line line = new Line(200, 50,600 , 50, 30);
		line.setColor((float)0.0, (float)0.0, (float)1.0);
		this.attachChild(line);
	}
	private void fresh()
	{
		float scoreRatio = (float)mCurrentScore / (float)mTotalScore;
		float scoreLength = scoreRatio * 400;
		scoreLength = scoreLength > 400 ? 400 : scoreLength;
		final Line line = new Line(200, 50,200+scoreLength ,50,30);
		if(scoreRatio < 0.6)line.setColor((float)0.0, (float)1.0, (float)0.0);
		else if(scoreRatio < 0.8)line.setColor((float)1.0, (float)0.843, (float)0.0);
		else line.setColor((float)1.0, (float)0.0, (float)0.0);
		this.attachChild(line);
	}
}
