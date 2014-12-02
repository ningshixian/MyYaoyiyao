package cn.nsx.yaoyiyao;

/*
 * 使用基本定位功能:根据用户实际需求，返回用户当前位置的基础定位服务。
 * 基本定位功能同时还支持结合定位结果的反地理编码功能，离线定位，位置提醒功能和地理围栏功能。
 */
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyLocation extends Activity {

	private LinearLayout layout;
	private TextView locationText;
	//Hight_Accuracy高精度、Battery_Saving低功耗、Device_Sensors仅设备(GPS)
	private LocationMode tempMode = LocationMode.Hight_Accuracy;
	private String tempcoor = "gcj02";

	public LocationClient mLocationClient = null;
	public MyLocationListener mMyLocationListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mylocation);
		// dialog=new MyDialog(this);
		locationText = (TextView) findViewById(R.id.mylocation);

		/* 1、初始化LocationClient类,LocationClient类必须在主线程中声明 */
		mLocationClient = new LocationClient(this.getApplicationContext());// 声明LocationClient类
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);// 3、注册监听函数

		layout = (LinearLayout) findViewById(R.id.my_location_layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
						Toast.LENGTH_SHORT).show();
			}
		});
		InitLocation();
		mLocationClient.start();//4、start：启动定位SDK。
	}

	/**
	 * 5、实现BDLocationListener接口，实现实位回调监听
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// BDLocation类，封装了定位SDK的定位结果，在BDLocationListener的onReceive方法中获取。
			// 通过该类用户可以获取error code，位置的坐标，精度半径等信息。
			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			sb.append("time : ");
			sb.append(location.getTime());

			sb.append("\nlatitude : ");
			sb.append(location.getLatitude());
			sb.append("\nlontitude : ");
			sb.append(location.getLongitude());

			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
				sb.append("\ndirection : ");
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				sb.append(location.getDirection());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append("\naddr : ");
				sb.append(location.getAddrStr());
				// 运营商信息
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			logMsg(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	/**
	 * 显示请求字符串
	 * 
	 */
	public void logMsg(String str) {
		try {
			if (locationText != null)
				locationText.setText(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		finish();
		mLocationClient.stop();//stop：关闭定位SDK。
		return true;
	}

	/* 2、设置定位参数 */
	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// 设置定位模式
		option.setCoorType(tempcoor);// 返回的定位结果是百度经纬度，默认值gcj02
		int span = 1000;
		option.setScanSpan(span);// 设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);// 需要反编码地理位置，返回的定位结果包含地址信息
		option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
		mLocationClient.setLocOption(option);// 设置参数
	}

}
