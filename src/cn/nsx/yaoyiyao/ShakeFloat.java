package cn.nsx.yaoyiyao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
 * ҡһҡ������
 */
public class ShakeFloat extends Activity {

	private TextView floatMessage;
	private TextView shakeTitle;
	private Button onemore;	
	public static ShakeFloat instance = null;
	public int state = 0;//�ж�״̬�Ƿ���ʾ
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shake_float);	
		instance = this;
		floatMessage = (TextView) findViewById(R.id.float_message);
		shakeTitle = (TextView) findViewById(R.id.shake_title);
		xx();
		onemore = (Button) findViewById(R.id.onemore);
		onemore.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
	}

	private void xx() {
		Intent intent = getIntent();
		int xx = (Integer) intent.getExtras().get("xx");
		if (xx==0) {
			String dowhat = intent.getExtras().getString("dowhat");
			floatMessage.setText(dowhat);
			shakeTitle.setText("��Ҫ��ʲô");
			
		}else if(xx==1) {
			String xiaohua = intent.getExtras().getString("xiaohua");
			floatMessage.setText(xiaohua);
			shakeTitle.setText("Ц��һ��");
			
		}else if (xx==2) {
			String restaurant = intent.getExtras().getString("restaurant");
			floatMessage.setText(restaurant);
			shakeTitle.setText("ѡ����");
			
		}
		
	}
}
