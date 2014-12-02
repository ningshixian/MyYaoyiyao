package cn.nsx.yaoyiyao;

/*
 * ʹ�û�����λ����:�����û�ʵ�����󣬷����û���ǰλ�õĻ�����λ����
 * ������λ����ͬʱ��֧�ֽ�϶�λ����ķ�������빦�ܣ����߶�λ��λ�����ѹ��ܺ͵���Χ�����ܡ�
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
	//Hight_Accuracy�߾��ȡ�Battery_Saving�͹��ġ�Device_Sensors���豸(GPS)
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

		/* 1����ʼ��LocationClient��,LocationClient����������߳������� */
		mLocationClient = new LocationClient(this.getApplicationContext());// ����LocationClient��
		mMyLocationListener = new MyLocationListener();
		mLocationClient.registerLocationListener(mMyLocationListener);// 3��ע���������

		layout = (LinearLayout) findViewById(R.id.my_location_layout);
		layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "��ʾ����������ⲿ�رմ��ڣ�",
						Toast.LENGTH_SHORT).show();
			}
		});
		InitLocation();
		mLocationClient.start();//4��start��������λSDK��
	}

	/**
	 * 5��ʵ��BDLocationListener�ӿڣ�ʵ��ʵλ�ص�����
	 */
	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// BDLocation�࣬��װ�˶�λSDK�Ķ�λ�������BDLocationListener��onReceive�����л�ȡ��
			// ͨ�������û����Ի�ȡerror code��λ�õ����꣬���Ȱ뾶����Ϣ��
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
				// ��Ӫ����Ϣ
				sb.append("\noperationers : ");
				sb.append(location.getOperators());
			}
			logMsg(sb.toString());
			Log.i("BaiduLocationApiDem", sb.toString());
		}

	}

	/**
	 * ��ʾ�����ַ���
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
		mLocationClient.stop();//stop���رն�λSDK��
		return true;
	}

	/* 2�����ö�λ���� */
	private void InitLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(tempMode);// ���ö�λģʽ
		option.setCoorType(tempcoor);// ���صĶ�λ����ǰٶȾ�γ�ȣ�Ĭ��ֵgcj02
		int span = 1000;
		option.setScanSpan(span);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);// ��Ҫ���������λ�ã����صĶ�λ���������ַ��Ϣ
		option.setNeedDeviceDirect(true);// ���صĶ�λ��������ֻ���ͷ�ķ���
		mLocationClient.setLocOption(option);// ���ò���
	}

}
