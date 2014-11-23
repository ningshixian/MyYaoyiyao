
package cn.nsx.yaoyiyao;

import java.util.Date;

import android.R.bool;

public class ChatMsgEntity {
    private static final String TAG = ChatMsgEntity.class.getSimpleName();

    private String name;

    private Date date;

    private String text;

    private boolean MsgType = true;//×ó±ß
        
    public boolean getMsgType() {
		return MsgType;
	}

	public void setMsgType(boolean msgType) {
		this.MsgType = msgType;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date2) {
        this.date = date2;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
    public ChatMsgEntity() {
    }

    public ChatMsgEntity(Date date, String text, boolean MsgType) {
        super();
        this.date = date;
        this.text = text;
        this.MsgType = MsgType;
    }

}
