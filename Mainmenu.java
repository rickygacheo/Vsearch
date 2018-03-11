package com.whu.vsearch;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.vsearch.building.BuildingSelectedActivity;
import com.vsearch.util.Constants;
import com.vsearch.util.SharePreferenceUtil;
import com.vsearch.wudainfo.WudaInfoSelectActivity;

public class Mainmenu extends Activity {
	
	private RelativeLayout search_by_word = null;
	private RelativeLayout search_by_building = null;
	private RelativeLayout search_by_info = null;
	private RelativeLayout search_by_people = null;
	private RelativeLayout serch_by_wudainfo=null;
	private Button dialog_mm = null;
	private String suggestion=null;
	// �Ƿ��˳�����
	private static Boolean isExit = false;
    // ��ʱ������
	private static Timer tExit = null;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainmenu);
		isGpsOpen();
		this.search_by_building = (RelativeLayout)super.findViewById(R.id.btn_search_by_building_mm);
		this.search_by_info = (RelativeLayout)super.findViewById(R.id.btn_search_by_info_mm);
		this.search_by_people = (RelativeLayout)super.findViewById(R.id.btn_search_by_people_mm);
		this.serch_by_wudainfo=(RelativeLayout)super.findViewById(R.id.btn_search_by_livingInfo_mm);
		this.dialog_mm = (Button)super.findViewById(R.id.btn_mm_settings);
		search_by_building.setOnClickListener(new jump_search_building());
		search_by_info.setOnClickListener(new jump_search_info());
		search_by_people.setOnClickListener(new jump_search_people());
		serch_by_wudainfo.setOnClickListener(new jump_search_livinginfo());
		dialog_mm.setOnClickListener(new Dialog());
	}
	
	public void isGpsOpen()
	{
		final SharePreferenceUtil save_util = new SharePreferenceUtil(this,
				Constants.SAVE_INFO);
		if(save_util.getIsNotifyGps())
		{
		    boolean gpsEnabled = Settings.Secure.isLocationProviderEnabled(
		                getContentResolver(), LocationManager.GPS_PROVIDER);
		        if (gpsEnabled) {
		           
		        } else {
		        	AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.MyTheme));
		    		builder.setMessage("��GPS,���Ը��õ�Ϊ���ṩλ�÷���");
		    		builder.setTitle("��ܰ��ʾ");
		    		builder.setIcon(R.drawable.tip_gps_icon);
		    		builder.setPositiveButton("��������", new DialogInterface.OnClickListener() {

		    			@Override
		    			public void onClick(DialogInterface dialog, int which) {
		    				save_util.setIsNotifyGps(false);
		    			}

		    		});
		    		builder.setNegativeButton("��֪����",null);
		    		builder.create().show();
		        	
		        }
		  
		}
		
	}
	private class jump_search_building implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent it = new Intent(Mainmenu.this,BuildingSelectedActivity.class);
			Mainmenu.this.startActivity(it);
		}
	}
	
	private class jump_search_info implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent it = new Intent(Mainmenu.this,WudaInfoSelectActivity.class);
			Mainmenu.this.startActivity(it);
		}
		
	}
	
	private class jump_search_people implements OnClickListener{
		@Override
		public void onClick(View v) {
			/*Intent it = new Intent(Mainmenu.this,Login.class);
			Mainmenu.this.startActivity(it);*/
			// TODO �Զ����ɵķ������
			Toast.makeText(Mainmenu.this, "��ģ���������ƣ������ڴ���", Toast.LENGTH_SHORT).show();
		}
	}
	private class jump_search_livinginfo implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Toast.makeText(Mainmenu.this, "��ģ���������ƣ������ڴ���", Toast.LENGTH_SHORT).show();
			
		}
	}
	private class Dialog implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO �Զ����ɵķ������
			Intent it = new Intent(Mainmenu.this,SettingActivity.class);
			Mainmenu.this.startActivity(it);
		}
		
	}
	
	protected void onDestroy() {
		MyApplication app = (MyApplication)this.getApplication();
		if (app.mBMapManager != null) {
			app.mBMapManager.destroy();
			app.mBMapManager = null;
		}
		super.onDestroy();
		System.exit(0);
	}
	
	/*-----------------���˵����水�����˳�����----------*/
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isExit == false) {
				isExit = true;
				if (tExit != null) {
					tExit.cancel(); // ��ԭ����Ӷ������Ƴ�
				}
				// ����ʵ��һ����ʱ��
				tExit = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						isExit = false;
					}
				};
				Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
				// ��ʱ���봥��task����
				tExit.schedule(task, 2000);
			} else {
				MyApplication app = (MyApplication) getApplication();
				if (app.mBMapManager != null) {
					app.mBMapManager.destroy();
					app.mBMapManager = null;
					System.out.println("-------Building.destroy()-----");
				}
				finish();
				System.exit(0);
			}
			return true;
		}
		//}
		return super.onKeyUp(keyCode, event);
	}
	
}