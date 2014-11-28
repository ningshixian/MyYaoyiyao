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
	private EditText mUser; // 帐号编辑框
	private EditText mPassword; // 密码编辑框
	private SharedPreferences sp;// 保存密码，用户名
	private CheckBox autoLogin;// 自动登录复选框
	private CheckBox saveInfoItem;// 记住密码复选框

	private DatabaseHelper dbHelper;// 数据库操作对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		mUser = (EditText) findViewById(R.id.login_user_edit);
		mPassword = (EditText) findViewById(R.id.login_passwd_edit);
		saveInfoItem = (CheckBox) findViewById(R.id.rememberPW);
		autoLogin = (CheckBox) findViewById(R.id.autoLogin);
		sp = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
		// 实例化 数据库操作对象
		dbHelper = new DatabaseHelper(this, "UserInfo.db", null, 1);
		// 初始化数据
		LoadUserdata();

	}

	// 初始化用户数据
	private void LoadUserdata() {
		boolean checkstatus = sp.getBoolean("checkstatus", false);
		boolean auto = sp.getBoolean("auto", false);
		if (checkstatus) // 判读是否曾经保存过密码
		{
			// 载入用户信息
			// 获取已经存在的用户名和密码
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
		// 如果记住密码跟自动登录都被选中就选择登录跳转
		if (auto && checkstatus) {
			Intent intent = new Intent();
			intent.setClass(Login.this, MainYaoyiyao.class);
			startActivity(intent);
			finish();
		}

	}

	// 登陆按钮点击事件
	public void LoginMain(View v) {
		String account = mUser.getText().toString();
		String password = mPassword.getText().toString();

		if ("admin".equals(account) && "123".equals(password)//admin,123默认登陆
				|| login(account, password))// 判断 帐号和密码是否正确
		{
			Editor editor = sp.edit();
			if (saveInfoItem.isChecked()) {
				// 保存用户名和密码
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
		//判断输入数据是否为空
		else if (TextUtils.isEmpty(account) || TextUtils.isEmpty(password)) {
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("登录错误").setMessage("帐号或密码不能为空!!")
					.setPositiveButton("重新填写", null).create().show();
		} else {
			new AlertDialog.Builder(Login.this)
					.setIcon(
							getResources().getDrawable(
									R.drawable.login_error_icon))
					.setTitle("登录失败").setMessage("帐号或者密码不正确!!")
					.setPositiveButton("重新填写", null).create().show();
		}
	}

	// 登录验证，将输入的用户名和密码在数据库中查找，若能找到至少一行数据，返回true
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

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

}
