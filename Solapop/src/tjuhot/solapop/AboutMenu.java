package tjuhot.solapop;

import tjuhot.solapop.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class AboutMenu extends Activity{
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        new Thread(){
        	public void run(){
        		try {
					sleep(5000);
					//change
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/// TODO
				AboutMenu.this.finish();
        	}
        }.start();
    }
}
