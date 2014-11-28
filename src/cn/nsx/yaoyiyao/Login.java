package cn.nsx.yaoyiyao;

import cn.nsx.yaoyiyao.R;
import cn.nsx.yaoyiyao.database.DatabaseHelper;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private EditText mUser; // �ʺű༭��
	private EditText mPassword; // ����༭��
	private SharedPreferences sp;// �������룬�û���
	private CheckBox autoLogin;// �Զ���¼��ѡ��
	private CheckBox saveInfoItem;// ��ס���븴ѡ��

	private DatabaseHelper dbHelper;// ���ݿ��������

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		saveInfoItem = (CheckBox) findViewById(R.id.rememberPW);
		autoLogin = (CheckBox) findViewById(R.id.autoLogin);
		sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		// ʵ���� ���ݿ��������
		dbHelper = new DatabaseHelper(this, "UserInfo.db", null, 1);
		// ��ʼ������
		LoadUserdata();

	}

	// ��ʼ���û�����
	private void LoadUserdata() {
		boolean checkstatus = sp.getBoolean("checkstatus", false);
		boolean auto = sp.getBoolean("auto", false);
		if (checkstatus) // �ж��Ƿ��������������
		{
			// �����û���Ϣ
			// ��ȡ�Ѿ����ڵ��û���������
			String realUsername = sp.getString("username", "");
			String realPassword = sp.getString("password", "");
			if ((!realUsername.equals("")) && !(realUsername == null)
					|| (!realPassword.equals("")) || !(realPassword == null)) {
				mUser.setText("");
				mPassword.setText("");
				mUser.setText(realUsername);
				mPassword.setText(realPassword);
				saveInfoItem.setChecked(true);
			}
		} else {
			saveInfoItem.setChecked(false);
			mUser.setText("");
			mPassword.setText("");
		}
		// �����ס������Զ���¼����ѡ�о�ѡ���¼��ת
		if (auto && checkstatus) {
			Intent intent = new Intent();
			intent.setClass(Login.this, MainYaoyiyao.class);
			startActivity(intent);
			finish();
		}

	}

	// ��½��ť����¼�
	public void LoginMain(View v) {
		String account = mUser.getText().toString();
		String password = mPassword.getText().toString();

		if ("admin".equals(account) && "123".equals(password)//admin,123Ĭ�ϵ�½
				|| login(account, password))// �ж� �ʺź������Ƿ���ȷ
		{
			Editor editor = sp.edit();
			if (saveInfoItem.isChecked()) {
				// �����û���������
				editor.putString("username", mUser.getText().toString());
				editor.putString("password", mPassword.getText().toString());
				editor.putBoolean("checkstatus", true);
			}
			if (autoLogin.isChecked()) {
				editor.putBoolean("auto", true);
			}
			editor.commit();

			Intent intent = new Intent();
			intent.setClass(Login.this, LoadingActivity.class);
			startActivity(intent);
			finish();
		} 
		//�ж����������Ƿ�Ϊ��
		else if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("��¼����").setMessage("�ʺŻ����벻��Ϊ��!!")
					.setPositiveButton("������д", null).create().show();
		} else {
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("��¼ʧ��").setMessage("�ʺŻ������벻��ȷ!!")
					.setPositiveButton("������д", null).create().show();
		}
	}

	// ��¼��֤����������û��������������ݿ��в��ң������ҵ�����һ�����ݣ�����true
	public boolean login(String username, String password) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String sql = "select * from User where username=? and password=?";
		Cursor cursor = db.rawQuery(sql, new String[] { username, password });
		if (cursor.moveToFirst()) {
			cursor.close();
			return true;
		}
		return false;
	}

	public void login_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

}
