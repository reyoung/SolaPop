package tjuhot.solapop.menu;

import tjuhot.solapop.R;
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
					//change
					Intent intent= new Intent(WelcomMenu.this,GameMenu.class);
					startActivity(intent);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/// TODO
				WelcomMenu.this.finish();
        	}
        }.start();
    }
}