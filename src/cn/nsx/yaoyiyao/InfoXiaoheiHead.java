package cn.nsx.yaoyiyao;



import cn.nsx.yaoyiyao.R;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.Toast;

public class InfoXiaoheiHead extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.info_xiaohei_head);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);   //全屏显示
		Toast.makeText(getApplicationContext(), "头像图片", Toast.LENGTH_SHORT).show();
		//overridePendingTransition(R.anim.hyperspace_in, R.anim.hyperspace_out);

   }
	@Override
	public boolean onTouchEvent(MotionEvent event){
		finish();
		return true;
	}
	
}