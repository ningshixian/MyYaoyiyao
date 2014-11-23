package cn.nsx.yaoyiyao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.nsx.yaoyiyao.R;
import cn.nsx.yaoyiyao.util.HTTPUtil;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 聊天活动
 */
public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */

	private boolean ComMsg = true;
	private boolean ToMsg = false;
	
	private Button mBtnSend;//发送消息按钮
	private Button mBtnBack;//返回按钮
	private EditText mEditTextContent;//消息编辑框
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;//适配器
	private List<ChatMsgEntity> mDataArrays = null;//存放数据源
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			//等待接受子线程完成数据返回
			ChatMsgEntity fromMassage = (ChatMsgEntity) msg.obj;
			mDataArrays.add(fromMassage);
			mAdapter.notifyDataSetChanged();//更新界面
		};
	};
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_xiaohei);
        //启动activity时不自动弹出软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        
        initView();//完成变量的初始化
        
        initData();//完成数据的初始化        
        
    }   
    
	//完成变量的初始化
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    }
    
    //初始化数据（开始显示的聊天数据）
    public void initData()
    {    	
    	mDataArrays = new ArrayList<ChatMsgEntity>();//消息数据集
    	ChatMsgEntity entity = new ChatMsgEntity();//实例一个消息类
    	entity.setDate(new Date());//设置时间
    	entity.setMsgType(true);//设置消息类型，true代表接收,false代表发送
    	entity.setText("你好，小慕为您服务");//消息内容
    	mDataArrays.add(entity);//将消息实体添加给数据集 
    	
    	//将消息数据  通过适配器设置到ListView中
    	mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
    	mListView.setAdapter(mAdapter);		
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.btn_send:
			send();
			break;
		case R.id.btn_back:
			finish();
			break;
		}
	}
	
	private void send()
	{
		final String contString = mEditTextContent.getText().toString();//获取发送编辑框内容
		if (contString.length() > 0)//内容不为空,
		{
			//将我们发送的消息封装到ChatMsgEntity类中，同时显示到ListView中
			ChatMsgEntity toMessage = new ChatMsgEntity(new Date(), contString,ToMsg);			
			mDataArrays.add(toMessage);
			mAdapter.notifyDataSetChanged();
			
			mEditTextContent.setText("");//输入框清空				
			
			new Thread(){//在线程中完成网络请求
				public void run() {
					//将消息内容发送到图灵机器人,数据返回fromMessage
					ChatMsgEntity fromMessage = HTTPUtil.sendMessage(contString);
					Message m = Message.obtain();
					m.obj = fromMessage;
					mHandler.sendMessage(m);//通过Handler完成界面更新
				};
			}.start();			
		}
		else {//如果消息为空
			Toast.makeText(ChatActivity.this, "发送消息不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
	}	
	
	//获取当前时间
    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));       
        
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        			
        return sbBuffer.toString();
    }
    
    
    public void head_xiaohei(View v) {     //点击头像详细信息
    	Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		startActivity(intent);	
      } 
}