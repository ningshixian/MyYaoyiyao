package cn.nsx.yaoyiyao.util;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Environment;
import android.util.Log;

public class Media {
	
	/* 构建MediaPlayer对象 */   
    public static MediaPlayer mMediaPlayer = null;//媒体播放器不存在??
    /* 音乐的路径 */  
    public static String MUSIC_PATH = null;
    /* 当前播放歌曲的索引 */   
    public int currentListItme = 0;  
    /* 播放列表 */ 
    public List<String> mMusicList = null;
    
    private static boolean state = false;
    
    public static boolean getState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	{
    MUSIC_PATH = new String("/mnt/sdcard/music/");      
    mMusicList = new ArrayList<String>();  
    mMediaPlayer = new MediaPlayer();
	}
    
	public void initMediaPlayer()
	{
		
		try {
			File file = new File(Environment.getExternalStorageDirectory(),"music.mp3");
			mMediaPlayer.setDataSource(file.getPath());
			//mMediaPlayer.setDataSource("/mnt/sdcard/music.mp3");
			mMediaPlayer.prepare();
			mMediaPlayer.start();
//			File file = Environment.getExternalStorageDirectory();
//			String s_name = file.getAbsolutePath();
//			Log.w("Sam Info: External Storage Direct", s_name);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 播放列表 */   
    public void musicList()   
    {   
        //取得指定位置的文件设置显示到播放列表   
        File home = new File(MUSIC_PATH);   
        if (home.listFiles(new MusicFilter()).length > 0)   
        {   
            for (File file : home.listFiles(new MusicFilter()))   
            {   
                mMusicList.add(file.getName());   
            }   
        }
    }
    //播放音樂
    public void playMusic(String path)   
    {   
        try   
        {   
            /* 重置MediaPlayer */   
            //mMediaPlayer.reset();   
            /* 设置要播放的文件的路径 */   
            mMediaPlayer.setDataSource(path);   
            /* 准备播放 */   
            mMediaPlayer.prepare();   
            /* 开始播放 */   
            mMediaPlayer.start();   
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
					nextmusic();
				}
			});
        }catch (IOException e){}   
    }
    //下一首
    public void nextmusic() {
        if(currentListItme<mMusicList.size()){
        	playMusic(MUSIC_PATH+mMusicList.get(currentListItme));
         }
        else {
        	currentListItme=0;
        	playMusic(MUSIC_PATH+mMusicList.get(currentListItme)); 
       }
    }
    
    /*过滤文件类型*/
	public class MusicFilter implements FilenameFilter
	{

		@Override
		public boolean accept(File dir, String name) {
			// 这里还可以设置其他格式的音乐
			return (name.endsWith(".mp3"));
		}		
	}
	
	
}
