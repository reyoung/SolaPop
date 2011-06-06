package tjuhot.solapop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FileSelector extends ListActivity {

	private List<String> items = null;
	private List<String> itemsMask = null;
	private String filePath = null;
	private String homePath = null;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		
		String status = Environment.getExternalStorageState();
		  if (status.equals(Environment.MEDIA_MOUNTED)) {
			  homePath="mnt/sdcard/"; 
		        File file=new File(homePath+"SolaPop"); 
		        if(!file.exists()) 
		         file.mkdir();
		  } else {
			  Toast.makeText(this, "sdcard not exist,choose slp file yourslef!", Toast.LENGTH_LONG).show();
			  homePath="/";
		  }
		
		  filePath = homePath;
		  fillList(new File(filePath).listFiles());
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		filePath = items.get((int) id);
		File file = new File(filePath);
		if (file.isDirectory()) {
			fillList(file.listFiles());
		} else{
			Toast.makeText(FileSelector.this, ""+filePath, Toast.LENGTH_SHORT)
					.show();
			Intent intent= new Intent(FileSelector.this,GameMain.class);
			Bundle bundle=new Bundle();
			bundle.putString("filepath", filePath);
			intent.putExtras(bundle);
			startActivity(intent);
			this.finish();
		}
	}

	private void fillList(File[] files) {
		items = new ArrayList<String>();
		itemsMask = new ArrayList<String>();
		// 遍例整个files目录下的所有文件及文件夹
		Log.e("TAG", "rec file: "+filePath);
		Log.e("TAG", "rec home: "+homePath);
		if(filePath!=homePath&&filePath.length()>homePath.length()){
			String parentStr=new File(filePath).getParent();
			items.add(parentStr);
			itemsMask.add("..");
			
		}
		boolean existslp=false;
		for (File file : files){
			if(file.isDirectory()||file.getName().toLowerCase().endsWith(".slp")){
				existslp=true;
			}
		}
		if(existslp==false)
		{
			Toast.makeText(FileSelector.this, "NO SLP FILE！", Toast.LENGTH_SHORT)
			.show();
		}
		
		for (File file : files) {
			if (file.isDirectory()
					|| file.getName().toLowerCase().endsWith(".slp")){
				items.add(file.getPath().toString());
				String p=file.getPath().toString();
				for(int i=p.length()-1;i>0;i--){
					if(p.charAt(i)=='/')
					{
						p=p.substring(i+1,p.length());
						break;
					}
				}
				itemsMask.add(p);
			}
		}
		ArrayAdapter<String> fileList = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, itemsMask);
		setListAdapter(fileList);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if ((filePath).equals(homePath)) {
				this.finish();
				break;
			} else {
				filePath = new File(filePath).getParent();
				fillList(new File(filePath).listFiles());
				
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}