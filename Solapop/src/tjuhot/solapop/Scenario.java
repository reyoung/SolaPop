package tjuhot.solapop;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.entity.Entity;
import org.anddev.andengine.opengl.texture.region.TextureRegion;

public class Scenario extends Entity {
	List<TextureRegion> m_rat_t;
	List<TextureRegion> m_hithat_t;
	List<Rat>			m_rats;
	public Scenario(List<TextureRegion> rat_t,List<TextureRegion> hitrat_t){
		super(0,0);
		m_rat_t = rat_t;
		m_hithat_t = hitrat_t;
		m_rats = new ArrayList<Rat>();
	}
	public void step(){
		for(Rat r: m_rats){
			r.showNext();
		}
	}
	public void addRat(int x,int y){
		Rat r= new Rat(m_rat_t, m_hithat_t);
		r.setPosition(x, y);
		r.mx = x;
		r.my = y;
		this.attachChild(r);
		m_rats.add(r);
	}
	public void removeRat(int i){
		this.detachChild(m_rats.get(i));
		m_rats.remove(i);
	}
	public void onClick(int x ,int y)
	{
		for(Rat r : m_rats)
		{
			if((r.mx + 128) > x 
					&& r.mx < x
					&& r.my < y
					&& (r.my + 128) > y)
			{
				r.hit();
				break;
			}
		}
	}
}
