package tjuhot.solapop.menu;

import android.app.Activity;
import android.os.Bundle;

public class WelcomMenu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcom);
        new Thread(){
        	public void run(){
        		try {
					sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Todo
				SolaPopStart.this.finish();
        	}
        }.start();
    }
}