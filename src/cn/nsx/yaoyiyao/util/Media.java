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
	
	/* ����MediaPlayer���� */   
    public MediaPlayer mMediaPlayer = null;//ý�岥����������??
    /* ���ֵ�·�� */   
    public static final String MUSIC_PATH = new String("/mnt/sdcard/");
    /* �����б� */   
    public List<String> mMusicList = new ArrayList<String>();   
    /* ��ǰ���Ÿ��������� */   
    public int currentListItme = 0;    
    	
	public void initMediaPlayer()
	{
		mMediaPlayer = new MediaPlayer();
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
	
	/* �����б� */   
    public void musicList()   
    {   
        //ȡ��ָ��λ�õ��ļ�������ʾ�������б�   
        File home = new File(MUSIC_PATH);   
        if (home.listFiles(new MusicFilter()).length > 0)   
        {   
            for (File file : home.listFiles(new MusicFilter()))   
            {   
                mMusicList.add(file.getName());   
            }   
        }
    }
    //��������
    public void playMusic(String path)   
    {   
        try   
        {   
            /* ����MediaPlayer */   
            //mMediaPlayer.reset();   
            /* ����Ҫ���ŵ��ļ���·�� */   
            mMediaPlayer.setDataSource(path);   
            /* ׼������ */   
            mMediaPlayer.prepare();   
            /* ��ʼ���� */   
            mMediaPlayer.start();   
            mMediaPlayer.setOnCompletionListener(new OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
					nextmusic();
				}
			});
        }catch (IOException e){}   
    }
    //��һ��
    public void nextmusic() {
        if(currentListItme<mMusicList.size()){
        	playMusic(MUSIC_PATH+mMusicList.get(currentListItme));
         }
        else {
        	currentListItme=0;
        	playMusic(MUSIC_PATH+mMusicList.get(currentListItme)); 
       }
    }
    
    /*�����ļ�����*/
	public class MusicFilter implements FilenameFilter
	{

		@Override
		public boolean accept(File dir, String name) {
			// ���ﻹ��������������ʽ������
			return (name.endsWith(".mp3"));
		}		
	}
	
	
}
