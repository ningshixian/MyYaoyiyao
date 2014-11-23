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
 * ����
 */
public class ChatActivity extends Activity implements OnClickListener{
    /** Called when the activity is first created. */

	private boolean ComMsg = true;
	private boolean ToMsg = false;
	
	private Button mBtnSend;//������Ϣ��ť
	private Button mBtnBack;//���ذ�ť
	private EditText mEditTextContent;//��Ϣ�༭��
	private ListView mListView;
	private ChatMsgViewAdapter mAdapter;//������
	private List<ChatMsgEntity> mDataArrays = null;//�������Դ
	
	private Handler mHandler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			//�ȴ��������߳�������ݷ���
			ChatMsgEntity fromMassage = (ChatMsgEntity) msg.obj;
			mDataArrays.add(fromMassage);
			mAdapter.notifyDataSetChanged();//���½���
		};
	};
	
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_xiaohei);
        //����activityʱ���Զ����������
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); 
        
        initView();//��ɱ����ĳ�ʼ��
        
        initData();//������ݵĳ�ʼ��        
        
    }   
    
	//��ɱ����ĳ�ʼ��
    public void initView()
    {
    	mListView = (ListView) findViewById(R.id.listview);
    	mBtnSend = (Button) findViewById(R.id.btn_send);
    	mBtnSend.setOnClickListener(this);
    	mBtnBack = (Button) findViewById(R.id.btn_back);
    	mBtnBack.setOnClickListener(this);    	
    	mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
    }
    
    //��ʼ�����ݣ���ʼ��ʾ���������ݣ�
    public void initData()
    {    	
    	mDataArrays = new ArrayList<ChatMsgEntity>();//��Ϣ���ݼ�
    	ChatMsgEntity entity = new ChatMsgEntity();//ʵ��һ����Ϣ��
    	entity.setDate(new Date());//����ʱ��
    	entity.setMsgType(true);//������Ϣ���ͣ�true�������,false������
    	entity.setText("��ã�СĽΪ������");//��Ϣ����
    	mDataArrays.add(entity);//����Ϣʵ����Ӹ����ݼ� 
    	
    	//����Ϣ����  ͨ�����������õ�ListView��
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
		final String contString = mEditTextContent.getText().toString();//��ȡ���ͱ༭������
		if (contString.length() > 0)//���ݲ�Ϊ��,
		{
			//�����Ƿ��͵���Ϣ��װ��ChatMsgEntity���У�ͬʱ��ʾ��ListView��
			ChatMsgEntity toMessage = new ChatMsgEntity(new Date(), contString,ToMsg);			
			mDataArrays.add(toMessage);
			mAdapter.notifyDataSetChanged();
			
			mEditTextContent.setText("");//��������				
			
			new Thread(){//���߳��������������
				public void run() {
					//����Ϣ���ݷ��͵�ͼ�������,���ݷ���fromMessage
					ChatMsgEntity fromMessage = HTTPUtil.sendMessage(contString);
					Message m = Message.obtain();
					m.obj = fromMessage;
					mHandler.sendMessage(m);//ͨ��Handler��ɽ������
				};
			}.start();			
		}
		else {//�����ϢΪ��
			Toast.makeText(ChatActivity.this, "������Ϣ����Ϊ��", Toast.LENGTH_SHORT).show();
			return;
		}
	}	
	
	//��ȡ��ǰʱ��
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
    
    
    public void head_xiaohei(View v) {     //���ͷ����ϸ��Ϣ
    	Intent intent = new Intent (ChatActivity.this,InfoXiaohei.class);			
		startActivity(intent);	
      } 
}