package tjuhot.solapop;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import android.util.Log;

public class ActionProxy {
	public interface MissListener{
		void onMiss();
	}
	List<MissListener>	m_misslisteners;
	
	List<Rat> m_rat;
	List<__AUX_ActionHelper> m_helpers;
	private static final String DB_FILTER="Solapop.ActionProxy";
	
	void debug(String msg){
		Log.d(DB_FILTER, msg);
	}
	
	public ActionProxy(List<Rat> r) {
		m_rat = r;
		m_misslisteners = new ArrayList<MissListener>();
		m_helpers = new ArrayList<__AUX_ActionHelper>();
	}
	public void addMissListener(MissListener ml){
		m_misslisteners.add(ml);
	}
	

	
	public void step() {
		for (int i = 0; i < m_helpers.size(); ++i) {
			__AUX_ActionHelper hp = m_helpers.get(i);
			Rat r = m_rat.get(hp.index);
			if (hp.step_count < 0) {
				r.setHit();
				r.showNext();
				++hp.step_count;
			} else if (r.isHole()) {
				
			} else if (r.isHitted()) {
				r.showNext();
				hp.step_count = -10;
				hp.ismiss = false;
			} else if (!r.isHighest()) {
				r.showNext();
			} else {
				++hp.step_count;
				if (hp.step_count >= hp.step_high) {
					r.showNext();
				}
			}
		}
		Iterator<__AUX_ActionHelper> it = m_helpers.iterator();
		while (it.hasNext()) {
			__AUX_ActionHelper helper = it.next();
			Rat rat = m_rat.get((helper.index));
			if (rat.isHole()) {
				if(helper.ismiss){
					for(MissListener ml:m_misslisteners){
						ml.onMiss();
					}
				}
				it.remove();
			}
		}
	}

	public void showRat(int step_high) {
		int index = 0;
		List<Integer> avaliableIndex = new ArrayList<Integer>();
		for (; index < m_rat.size(); ++index) {
			boolean flag = true;
			for (__AUX_ActionHelper h : m_helpers) {
				if (h.index == index) {
					flag = false;
					break;
				}
			}
			if (flag == true) {
				avaliableIndex.add(index);
			}
		}
		index = (int) (Math.random() * (avaliableIndex.size() + 0.5f));
		if (index == m_rat.size()) {
			return;
		} else {
			__AUX_ActionHelper hp = new __AUX_ActionHelper();
			hp.step_high = step_high;
			hp.index = index;
			m_helpers.add(hp);
			m_rat.get(index).showNext();
		}
	}

	class __AUX_ActionHelper {
		public int step_high;
		public int index;
		public int step_count;
		public boolean ismiss;
		public __AUX_ActionHelper() {
			step_count = 0;
			ismiss = true;
		}
	}

}
