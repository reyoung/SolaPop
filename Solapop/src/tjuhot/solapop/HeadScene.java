package tjuhot.solapop;

import org.anddev.andengine.entity.Entity;

public class HeadScene extends Entity {
	private int mCurrentScore;
	private int mTotalScore;
	public HeadScene(int totalBeat)
	{
		mCurrentScore = 0;
		mTotalScore = totalBeat * 2;
	}
	public void fresh(int hitScore)
	{
		mCurrentScore += hitScore;
	}
}
