package cn.nsx.yaoyiyao;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;


import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import cn.nsx.yaoyiyao.R;
import cn.nsx.yaoyiyao.ShakeListener.OnShakeListener;
import cn.nsx.yaoyiyao.util.Media;

public class ShakeActivity extends Activity{
	
	ShakeListener mShakeListener = null;
	Vibrator mVibrator;//Android�ֻ��е�����Vibratorʵ�֡�
	private RelativeLayout mImgUp;
	private RelativeLayout mImgDn;
	private RelativeLayout mTitle;
	//private RelativeLayout shakeover;//ҡһҡ֮�����صġ���ʲô��������ʾ
	private RelativeLayout shakegif;
	private GifView gf1;
	private boolean f = true;
	
	//private TextView shakeText;
	private String[] what;//�惦������
	private String[] xiaohua;//�惦��Ц��
	private String[] res;//�惦�ķ���
	private String message;//intent�����putExtra
	
	public MediaPlayer mMediaPlayer = null;    
         
	private SlidingDrawer mDrawer;
	private Button mDrawerBtn;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.shake_activity);
		
		//��ȡstrings.xml��dowhat����
		what=getResources().getStringArray(R.array.dowhat);
		xiaohua=getResources().getStringArray(R.array.xiaohua);
		res = getResources().getStringArray(R.array.places);
		
		mVibrator = (Vibrator)getApplication().getSystemService(VIBRATOR_SERVICE);
		
		mImgUp = (RelativeLayout) findViewById(R.id.shakeImgUp);
		mImgDn = (RelativeLayout) findViewById(R.id.shakeImgDown);
		mTitle = (RelativeLayout) findViewById(R.id.shake_title_bar);
		//shakeover = (RelativeLayout) findViewById(R.id.shakeover);
		//shakeText = (TextView) findViewById(R.id.shakeText);
		shakegif = (RelativeLayout) findViewById(R.id.shakegif);
		// ��xml�еõ�GifView�ľ��  
	    gf1 = (GifView) findViewById(R.id.gif1);  
	     
		
		//��ȡMainYaoyiyao�е�intent���󣬳��Դ�intent��ȡ����Ӧ�Ĵ���
		message = getIntent().getStringExtra("message");
								
		mDrawer = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
        mDrawerBtn = (Button) findViewById(R.id.handle);
        mDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener()
		{	public void onDrawerOpened()
			{	
				mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_down));
				TranslateAnimation titleup = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f);
				titleup.setDuration(200);
				titleup.setFillAfter(true);
				mTitle.startAnimation(titleup);
			}
		});
		 /* �趨SlidingDrawer���رյ��¼����� */
		mDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener()
		{	public void onDrawerClosed()
			{	
				mDrawerBtn.setBackgroundDrawable(getResources().getDrawable(R.drawable.shake_report_dragger_up));
				TranslateAnimation titledn = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-1.0f,Animation.RELATIVE_TO_SELF,0f);
				titledn.setDuration(200);
				titledn.setFillAfter(false);
				mTitle.startAnimation(titledn);
			}
		});
		
		mShakeListener = new ShakeListener(this);
        mShakeListener.setOnShakeListener(new OnShakeListener() {        	
			public void onShake() {
				
				if ("dowhat".equals(message)) 
				{					
				//ȷ��ҡ֮ǰ��ҡ��ͬʱ��shakeover��������
				//shakeover.setVisibility(View.GONE);
				startAnim();  //��ʼ ҡһҡ���ƶ���
				mShakeListener.stop();
				startVibrato(); //��ʼ ��
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						//Toast.makeText(getApplicationContext(), "��Ǹ����ʱû���ҵ�\n��ͬһʱ��ҡһҡ���ˡ�\n����һ�ΰɣ�", 500).setGravity(Gravity.CENTER,0,0).show();
						//����һ��1~10���������
						int random = (int) (Math.random() * 10);
						String dowhat = what[random].toString();
						Intent intent = new Intent(ShakeActivity.this,ShakeFloat.class);
						intent.putExtra("xx", 0);
						intent.putExtra("dowhat", dowhat);
						startActivity(intent);
						//shakeText.setText(what[random].toString());
						//shakeover.setVisibility(View.VISIBLE);
						Log.d("��ǰ��Ӧ��dowhat", "item"+random);
						mVibrator.cancel();
						mShakeListener.start();
					}
				}, 2000);
			}
				
				else if ("music".equals(message)) {
					/* ������ʾ�����б� */   
			        //musicList();					
			        final Media media = new Media();
					//ȷ��ҡ֮ǰ��ҡ��ͬʱ��shakeover��������
					//shakeover.setVisibility(View.GONE);
					startAnim();  //��ʼ ҡһҡ���ƶ���
					mShakeListener.stop();
					startVibrato(); //��ʼ ��
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							//Toast.makeText(getApplicationContext(), "��Ǹ����ʱû���ҵ�\n��ͬһʱ��ҡһҡ���ˡ�\n����һ�ΰɣ�", 500).setGravity(Gravity.CENTER,0,0).show();
							//����һ��1~10���������
							int random = (int) (Math.random() * 10);
							media.currentListItme = random;//��ǰ��������
		    		        //media.playMusic(media.MUSIC_PATH + "music.mp3");
							media.initMediaPlayer();				
							
							//shakeText.setText(mMusicList.get(1).toString());
		    		        //shakeText.setText(media.mMediaPlayer.getDuration());
							//shakeover.setVisibility(View.VISIBLE);
							Log.d("��ǰ��Ӧ��music", "item"+random);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
				
				else if ("xiaohua".equals(message)) {
					//ȷ��ҡ֮ǰ��ҡ��ͬʱ��shakeover��������
					//shakeover.setVisibility(View.GONE);
					startAnim();  //��ʼ ҡһҡ���ƶ���
					mShakeListener.stop();
					startVibrato(); //��ʼ ��
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							//����һ��1~10���������
							int random = (int) (Math.random() * 20);
							String xh = xiaohua[random].toString();							
							Intent intent = new Intent(ShakeActivity.this,ShakeFloat.class);
							intent.putExtra("xx", 1);
							intent.putExtra("xiaohua", xh);
							startActivity(intent);
							//shakeText.setText(xiaohua[random].toString());
							//shakeover.setVisibility(View.VISIBLE);
							Log.d("��ǰ��Ӧ��xiaohua", "item"+random);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
				
				else if ("gif".equals(message)) {//���ǻ����������
					//ȷ��ҡ֮ǰ��ҡ��ͬʱ��shakeover��������
					shakegif.setVisibility(View.GONE);
					startAnim();  //��ʼ ҡһҡ���ƶ���
					mShakeListener.stop();
					startVibrato(); //��ʼ ��
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							try {
								// ������ʾ�Ĵ�С���������ѹ��  
							    gf1.setShowDimension(600,600);
								// ����GifͼƬԴ
							    int random = (int) (Math.random() * 10);
							    if (random>=0 && random<=2) {
							    	gf1.setGifImage(R.drawable.gif1); 
								}
							    else if (random>=3 && random<=5) {
							    	gf1.setGifImage(R.drawable.gif2);
								}
							    else if (random>=6 && random<=8) {
							    	gf1.setGifImage(R.drawable.gif3);
								}else {
									gf1.setGifImage(R.drawable.gif4);
								}							      
							    // ���ü��ط�ʽ���ȼ��غ���ʾ���߼��ر���ʾ��ֻ��ʾ��һ֡����ʾ  
							    gf1.setGifImageType(GifImageType.COVER);
							} 
							catch (Exception e) {
								e.printStackTrace();
							}
						     
							shakegif.setVisibility(View.VISIBLE);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
				else if ("res".equals(message)) {
					//ȷ��ҡ֮ǰ��ҡ��ͬʱ��shakeover��������
					//shakeover.setVisibility(View.GONE);
					startAnim();  //��ʼ ҡһҡ���ƶ���
					mShakeListener.stop();
					startVibrato(); //��ʼ ��
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							//����һ��1~10���������
							int random = (int) (Math.random() * 11);
							String restaurant = res[random].toString();
							Intent intent = new Intent(ShakeActivity.this,ShakeFloat.class);
							intent.putExtra("xx", 2);
							intent.putExtra("restaurant", restaurant);
							startActivity(intent);
							//shakeText.setText(res[random].toString());
							//shakeover.setVisibility(View.VISIBLE);
							Log.d("��ǰ��Ӧ�ķ���", "item"+random);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
			}			
		});       
        
   }
	public void startAnim () {   //����ҡһҡ��������
		AnimationSet animup = new AnimationSet(true);
		TranslateAnimation mytranslateanimup0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimup0.setDuration(1000);
		TranslateAnimation mytranslateanimup1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimup1.setDuration(1000);
		mytranslateanimup1.setStartOffset(1000);
		animup.addAnimation(mytranslateanimup0);
		animup.addAnimation(mytranslateanimup1);
		mImgUp.startAnimation(animup);
		
		AnimationSet animdn = new AnimationSet(true);
		TranslateAnimation mytranslateanimdn0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,+0.5f);
		mytranslateanimdn0.setDuration(1000);
		TranslateAnimation mytranslateanimdn1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,-0.5f);
		mytranslateanimdn1.setDuration(1000);
		mytranslateanimdn1.setStartOffset(1000);
		animdn.addAnimation(mytranslateanimdn0);
		animdn.addAnimation(mytranslateanimdn1);
		mImgDn.startAnimation(animdn);	
	}
	public void startVibrato(){		//������
		mVibrator.vibrate( new long[]{500,200,500,200}, -1); //��һ�����������ǽ������飬 �ڶ����������ظ�������-1Ϊ���ظ�����-1���մ�pattern��ָ���±꿪ʼ�ظ�
	}
	
	public void shake_activity_back(View v) {     //������ ���ذ�ť
      	this.finish();
      }  
	public void linshi(View v) {     //������
		startAnim();
      }  
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mShakeListener != null) {
			mShakeListener.stop();
		}
		if (mMediaPlayer!=null) {
			mMediaPlayer.stop();
			mMediaPlayer.release();
			this.finish();			
		}
	}
	
}