package tjuhot.solapop;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.audio.music.Music;
import org.anddev.andengine.audio.sound.Sound;
import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

import tjuhot.solapop.ActionProxy.MissListener;

import android.graphics.Point;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

public class Scenario extends Entity implements MissListener,OnCompletionListener{
	List<TextureRegion> m_rat_t;
	List<TextureRegion> m_hithat_t;
	Sound				mMouseHitSound;
	List<Rat>			m_rats;
	Sprite				m_backgroud;
	Music				m_bgm;
	ActionProxy			m_actionProxy;
	HeadScene			m_hScene;
	ILevel				m_curLevel;
	int					m_curBeat;	
	Engine				mEngine;
	
	int					m_hitcount;
	private static final String DB_FILTER="Solapop.Scenario";
	
	void debug(String msg){
		Log.d(DB_FILTER, msg);
	}
	public void increseCombo(){
		++m_hitcount;
		
	}
	public void clearCombo(){
		m_hitcount = 0;
	}
	
	public Scenario(List<TextureRegion> rat_t,List<TextureRegion> hitrat_t,
			Sound aMusic,Engine aEngine){
		super(0,0);
		m_rat_t = rat_t;
		m_hithat_t = hitrat_t;
		mMouseHitSound = aMusic;
		mEngine = aEngine;
		m_rats = new ArrayList<Rat>();
		m_actionProxy = new ActionProxy(m_rats);
		clearCombo();
		m_actionProxy.addMissListener(this);
	}
	public void step(){
		m_actionProxy.step();
		int rsize=  m_curLevel.getRatSize()+m_curBeat;
		List<Long> beats = m_curLevel.getBeats();
		int mpos = this.m_bgm.getMediaPlayer().getCurrentPosition();
		for(int i=m_curBeat;i<rsize;++i){
			int pos = beats.get(i).intValue();
			if(pos-mpos<100){
				m_actionProxy.showRat(50);
				++m_curBeat;
			}else{
				break;
			}
		}
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
		if(hittedlevel!=-1){
			this.m_hScene.hitLevelProc(hittedlevel);
			increseCombo();
		}
	}
	public void loadLevel(ILevel l){
		this.detachChildren();
		m_backgroud = new Sprite(0, 0, l.getBackground());
		this.attachChild(m_backgroud);
		m_hScene = new HeadScene(l.getBeats().size(),mEngine);
		this.attachChild(m_hScene);
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
		m_bgm.setOnCompletionListener(this);
		m_curLevel = l;
		m_curBeat = 0;
	}
	public void onMiss() {
		this.clearCombo();
	}
	public void onCompletion(MediaPlayer mp) {
		this.detachChildren();
		
	}
}
