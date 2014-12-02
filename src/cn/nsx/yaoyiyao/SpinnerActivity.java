package cn.nsx.yaoyiyao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
 
public class SpinnerActivity extends Activity {
    
	private String T,P;
    private static final String[] time={"早上","中午","下午","夜宵时间"};
    private static final String[] places={"北街+后山","宿舍"};
    private TextView view01 ;
    private TextView view02 ;
    private Spinner spinner01;
    private Spinner spinner02;
    private ArrayAdapter<String> adapter01;
    private ArrayAdapter<String> adapter02;
    private Button spinnerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.spinner);
         
        view01 = (TextView) findViewById(R.id.spinnerText01);
        spinner01 = (Spinner) findViewById(R.id.Spinner01);
        view02 = (TextView) findViewById(R.id.spinnerText02);
        spinner02 = (Spinner) findViewById(R.id.Spinner02);
        
        spinnerBtn = (Button) findViewById(R.id.SpinnerButton);
        spinnerBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
	        	intent.setClass(SpinnerActivity.this,ShakeActivity.class);
	        	intent.putExtra("message", "res");
	        	intent.putExtra("places", P);
	        	intent.putExtra("time", T);
	        	startActivity(intent);	        
			}
		});
        
        //将可选内容与ArrayAdapter连接起来
        adapter01 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,time);
        adapter02 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,places);

        //设置下拉列表的风格
        adapter01.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter02.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //将adapter 添加到spinner中
        spinner01.setAdapter(adapter01);
        spinner02.setAdapter(adapter02);

        //添加事件Spinner事件监听  
        spinner01.setOnItemSelectedListener(new SpinnerSelectedListener01());
        spinner02.setOnItemSelectedListener(new SpinnerSelectedListener02());

        //设置默认值
        spinner01.setVisibility(View.VISIBLE);
        spinner02.setVisibility(View.VISIBLE);

    }    
     
    //使用数组形式操作
    class SpinnerSelectedListener01 implements OnItemSelectedListener{
 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            view01.setText("选择吃饭时间："+time[arg2]);
            T=time[arg2];
        }
 
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    
  
    class SpinnerSelectedListener02 implements OnItemSelectedListener{
 
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                long arg3) {
            view02.setText("你所在的地点："+places[arg2]);
            P=places[arg2];
        }
 
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
