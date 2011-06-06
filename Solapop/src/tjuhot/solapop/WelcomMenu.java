package tjuhot.solapop;

import tjuhot.solapop.R;
import android.app.Activity;
import android.content.Intent;
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
					sleep(2000);
					//change
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				/// TODO
				Intent intent= new Intent(WelcomMenu.this,GameMenu.class);
				startActivity(intent);
				WelcomMenu.this.finish();
        	}
        }.start();
    }
}