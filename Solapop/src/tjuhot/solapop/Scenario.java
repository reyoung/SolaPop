package tjuhot.solapop;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import android.graphics.Point;
import android.util.Log;

public class Scenario extends Entity {
	List<TextureRegion> m_rat_t;
	List<TextureRegion> m_hithat_t;
	Sound				mMouseHitSound;
	List<Rat>			m_rats;
	Sprite				m_backgroud;
	Music				m_bgm;
	List<Long>			m_beats;
	ActionProxy			m_actionProxy;
	
	
	private static final String DB_FILTER="Solapop.Scenario";
	
	void debug(String msg){
		Log.d(DB_FILTER, msg);
	}
	
	
	public Scenario(List<TextureRegion> rat_t,List<TextureRegion> hitrat_t,Sound aMusic){
		super(0,0);
		m_rat_t = rat_t;
		m_hithat_t = hitrat_t;
		mMouseHitSound = aMusic;
		m_rats = new ArrayList<Rat>();
		m_actionProxy = new ActionProxy(m_rats);
		
	}
	public void step(){
		
		m_actionProxy.step();
	}
	public void addRat(int x,int y){
		Rat r= new Rat(m_rat_t, m_hithat_t,mMouseHitSound);
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
	public void loadLevel(ILevel l){
		this.detachChildren();
		m_hScene = new HeadScene(l.getBeats().size());
		this.attachChild(m_hScene);
		m_backgroud = new Sprite(0, 0, l.getBackground());
		this.attachChild(m_backgroud);
		List<Point> ratpos = l.getRats();
		for(Point p : ratpos){
			this.addRat(p.x, p.y);
		}
		if(m_bgm!=null){
			m_bgm.stop();
		}
		m_bgm = l.getMusic();
		if(m_bgm!=null){
			m_bgm.play();
		}
		m_beats = l.getBeats();
	}
}
