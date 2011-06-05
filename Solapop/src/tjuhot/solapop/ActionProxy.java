package tjuhot.solapop;

import java.util.ArrayList;
import java.util.List;

public class ActionProxy {
	List<Rat>		m_rat;
	List<__AUX_ActionHelper> m_helpers;
	
	public ActionProxy(List<Rat> r){
		m_rat = r;
		m_helpers = new ArrayList<__AUX_ActionHelper>();
	}
	public void step(){
		List<Integer> removePos = new ArrayList<Integer>();
		for(int i=0;i<m_helpers.size();++i){
			__AUX_ActionHelper hp = m_helpers.get(i);
			Rat r = m_rat.get(hp.index);
			if(r.isHole()){
				removePos.add(i);
			}
			else if(!r.isHighest()){
				r.showNext();
			}
		}
	}
	public void showRat(int step_high){
		int index = 0;
		for(;index<m_rat.size();++index){
			boolean flag = true;
			for(__AUX_ActionHelper h:m_helpers){
				if(h.index==index){
					flag = false;
					break;
				}
			}
			if(flag)
				break;
		}
		if(index==m_rat.size()){
			return ;
		}else{
			__AUX_ActionHelper hp = new __AUX_ActionHelper();
			hp.step_high = step_high;
			hp.index = index;
			m_helpers.add(hp);
			m_rat.get(index).showNext();
		}
	}
	class __AUX_ActionHelper{
		public int step_high;
		public int index;
		public int step_count;
		public __AUX_ActionHelper(){
			step_count = 0;
		}
	}
	
}
