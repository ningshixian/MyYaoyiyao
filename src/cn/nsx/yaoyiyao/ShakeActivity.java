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
	Vibrator mVibrator;//Android手机中的震动由Vibrator实现。
	private RelativeLayout mImgUp;
	private RelativeLayout mImgDn;
	private RelativeLayout mTitle;
	//private RelativeLayout shakeover;//摇一摇之后将隐藏的“做什么”布局显示
	private RelativeLayout shakegif;
	private GifView gf1;
	private boolean f = true;
	
	//private TextView shakeText;
	private String[] what;//存儲的事情
	private String[] xiaohua;//存儲的笑话
	private String[] res;//存儲的饭点
	private String message;//intent对象的putExtra
	
	public MediaPlayer mMediaPlayer = null;    
         
	private SlidingDrawer mDrawer;
	private Button mDrawerBtn;

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);	
		setContentView(R.layout.shake_activity);
		
		//获取strings.xml中dowhat数组
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
		// 从xml中得到GifView的句柄  
	    gf1 = (GifView) findViewById(R.id.gif1);  
	     
		
		//获取MainYaoyiyao中的intent对象，尝试从intent中取出相应的代号
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
		 /* 设定SlidingDrawer被关闭的事件处理 */
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
				//确保摇之前和摇的同时，shakeover布局隐藏
				//shakeover.setVisibility(View.GONE);
				startAnim();  //开始 摇一摇手掌动画
				mShakeListener.stop();
				startVibrato(); //开始 震动
				new Handler().postDelayed(new Runnable(){
					@Override
					public void run(){
						//Toast.makeText(getApplicationContext(), "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", 500).setGravity(Gravity.CENTER,0,0).show();
						//产生一个1~10的随机整数
						int random = (int) (Math.random() * 10);
						String dowhat = what[random].toString();
						Intent intent = new Intent(ShakeActivity.this,ShakeFloat.class);
						intent.putExtra("xx", 0);
						intent.putExtra("dowhat", dowhat);
						startActivity(intent);
						//shakeText.setText(what[random].toString());
						//shakeover.setVisibility(View.VISIBLE);
						Log.d("当前对应的dowhat", "item"+random);
						mVibrator.cancel();
						mShakeListener.start();
					}
				}, 2000);
			}
				
				else if ("music".equals(message)) {
					/* 更新显示播放列表 */   
			        //musicList();					
			        final Media media = new Media();
					//确保摇之前和摇的同时，shakeover布局隐藏
					//shakeover.setVisibility(View.GONE);
					startAnim();  //开始 摇一摇手掌动画
					mShakeListener.stop();
					startVibrato(); //开始 震动
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							//Toast.makeText(getApplicationContext(), "抱歉，暂时没有找到\n在同一时刻摇一摇的人。\n再试一次吧！", 500).setGravity(Gravity.CENTER,0,0).show();
							//产生一个1~10的随机整数
							int random = (int) (Math.random() * 10);
							media.currentListItme = random;//当前歌曲索引
		    		        //media.playMusic(media.MUSIC_PATH + "music.mp3");
							media.initMediaPlayer();				
							
							//shakeText.setText(mMusicList.get(1).toString());
		    		        //shakeText.setText(media.mMediaPlayer.getDuration());
							//shakeover.setVisibility(View.VISIBLE);
							Log.d("当前对应的music", "item"+random);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
				
				else if ("xiaohua".equals(message)) {
					//确保摇之前和摇的同时，shakeover布局隐藏
					//shakeover.setVisibility(View.GONE);
					startAnim();  //开始 摇一摇手掌动画
					mShakeListener.stop();
					startVibrato(); //开始 震动
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							//产生一个1~10的随机整数
							int random = (int) (Math.random() * 20);
							String xh = xiaohua[random].toString();							
							Intent intent = new Intent(ShakeActivity.this,ShakeFloat.class);
							intent.putExtra("xx", 1);
							intent.putExtra("xiaohua", xh);
							startActivity(intent);
							//shakeText.setText(xiaohua[random].toString());
							//shakeover.setVisibility(View.VISIBLE);
							Log.d("当前对应的xiaohua", "item"+random);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
				
				else if ("gif".equals(message)) {//总是会崩溃？？？
					//确保摇之前和摇的同时，shakeover布局隐藏
					shakegif.setVisibility(View.GONE);
					startAnim();  //开始 摇一摇手掌动画
					mShakeListener.stop();
					startVibrato(); //开始 震动
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							try {
								// 设置显示的大小，拉伸或者压缩  
							    gf1.setShowDimension(600,600);
								// 设置Gif图片源
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
							    // 设置加载方式：先加载后显示、边加载边显示、只显示第一帧再显示  
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
					//确保摇之前和摇的同时，shakeover布局隐藏
					//shakeover.setVisibility(View.GONE);
					startAnim();  //开始 摇一摇手掌动画
					mShakeListener.stop();
					startVibrato(); //开始 震动
					new Handler().postDelayed(new Runnable(){
						@Override
						public void run(){
							//产生一个1~10的随机整数
							int random = (int) (Math.random() * 11);
							String restaurant = res[random].toString();
							Intent intent = new Intent(ShakeActivity.this,ShakeFloat.class);
							intent.putExtra("xx", 2);
							intent.putExtra("restaurant", restaurant);
							startActivity(intent);
							//shakeText.setText(res[random].toString());
							//shakeover.setVisibility(View.VISIBLE);
							Log.d("当前对应的饭点", "item"+random);
							mVibrator.cancel();
							mShakeListener.start();
						}
					}, 2000);
				}
			}			
		});       
        
   }
	public void startAnim () {   //定义摇一摇动画动画
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
	public void startVibrato(){		//定义震动
		mVibrator.vibrate( new long[]{500,200,500,200}, -1); //第一个｛｝里面是节奏数组， 第二个参数是重复次数，-1为不重复，非-1俄日从pattern的指定下标开始重复
	}
	
	public void shake_activity_back(View v) {     //标题栏 返回按钮
      	this.finish();
      }  
	public void linshi(View v) {     //标题栏
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