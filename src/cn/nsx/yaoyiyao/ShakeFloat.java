package cn.nsx.yaoyiyao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
/*
 * Ò¡Ò»Ò¡µ¯³ö´°
 */
public class ShakeFloat extends Activity {

	private TextView floatMessage;
	private Button onemore;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shake_float);		
		floatMessage = (TextView) findViewById(R.id.float_message);
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
		}else if(xx==1) {
			String xiaohua = intent.getExtras().getString("xiaohua");
			floatMessage.setText(xiaohua);
		}else if (xx==2) {
			String restaurant = intent.getExtras().getString("restaurant");
			floatMessage.setText(restaurant);
		}
		
	}
}
