package tjuhot.solapop;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class Scenario extends Entity {
	List<TextureRegion> m_rat_t;
	List<TextureRegion> m_hithat_t;
	Music				mMusic;
	List<Rat>			m_rats;
	public Scenario(List<TextureRegion> rat_t,List<TextureRegion> hitrat_t,Music aMusic){
		super(0,0);
		m_rat_t = rat_t;
		m_hithat_t = hitrat_t;
		mMusic = aMusic;
		m_rats = new ArrayList<Rat>();
	}
	public void step(){
		for(Rat r: m_rats){
			r.showNext();
		}
	}
	public void addRat(int x,int y){
		Rat r= new Rat(m_rat_t, m_hithat_t,mMusic);
		r.setPosition(x, y);
		this.attachChild(r);
		m_rats.add(r);
	}
	public void removeRat(int i){
		this.detachChild(m_rats.get(i));
		m_rats.remove(i);
	}
	public void onClick(int x ,int y)
	{
		int hittedlevel = -1;
		for(Rat r : m_rats)
		{
			int mx = (int)r.getX();
			int my = (int)r.getY();
			if((mx + 128) > x 
					&& mx < x
					&& my < y
					&& (my + 128) > y)
			{
				hittedlevel = r.hit();
				break;
			}
		}
		/// process hittedlevel;
	}
}
