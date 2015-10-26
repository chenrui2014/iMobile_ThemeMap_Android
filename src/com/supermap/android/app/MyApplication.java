package com.supermap.android.app;


import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.supermap.data.Environment;
import com.supermap.data.Workspace;
import com.supermap.data.WorkspaceConnectionInfo;
import com.supermap.data.WorkspaceType;
import com.supermap.android.configuration.DefaultDataConfiguration;
import com.supermap.android.filemanager.MyAssetManager;
import com.supermap.android.filemanager.MySharedPreferences;


public class MyApplication extends Application {
	public static String DATAPATH = "";
	public static String SDCARD = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	
	private static MyApplication sInstance = null;
	private Workspace mWorkspace = null;
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		
		DATAPATH = this.getFilesDir().getAbsolutePath() + "/";
		sInstance = this;
		
		
		Environment.setLicensePath(DefaultDataConfiguration.LicensePath);
		Environment.initialization(this);
		
		MySharedPreferences.init(this);
		MyAssetManager.init(this);
		
		new DefaultDataConfiguration().autoConfig();
		
		mWorkspace = new Workspace();
		WorkspaceConnectionInfo info = new WorkspaceConnectionInfo();
		info.setServer(DefaultDataConfiguration.WorkspacePath);
		info.setType(WorkspaceType.SMWU);
		if(!mWorkspace.open(info))
		{
			showError("工作空间损坏");
		}
		
		
	}
	
	/**
	 * 获取当前打开的工作空间
	 * @return
	 */
	public Workspace  getOpenedWorkspace()
	{
		return mWorkspace;
	}
	
	/**
	 * 获取当前应用对象
	 * @return
	 */
	public static MyApplication getInstance()
	{
		return sInstance;
	}
	
	/**
	 * Toast显示信息
	 * @param info
	 */
	public void showInfo(String info)
	{
		Toast toast = Toast.makeText(sInstance, info, 500);
		toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		
	}
	
	/**
	 * Toast显示错误信息
	 * @param err
	 */
	public void showError (String err) 
	{
		Toast toast = Toast.makeText(sInstance, "Error: " + err, 500);
		toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
		toast.show();
		Log.e(this.getClass().getName(), err);
	}
	
	/**
	 * 获取显示尺寸值
	 * @param dp
	 * @return
	 */
	public static int dp2px (int dp) 
	{
		int density = (int) (dp*sInstance.getResources().getDisplayMetrics().density);
		
		return density;
	}
}

