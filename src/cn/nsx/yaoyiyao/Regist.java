package cn.nsx.yaoyiyao;

import java.util.regex.Pattern;
import cn.nsx.yaoyiyao.R;
import cn.nsx.yaoyiyao.database.DatabaseHelper;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Regist extends Activity implements OnClickListener {
	private EditText newUser; // 帐号编辑框
	private EditText newPassword; // 密码编辑框
	private EditText confirmPassword; // 密码确认框
	private Button registBtn, clearBtn;// 注册、确认按钮
	private EditText newEmail;// 注册邮箱

	private DatabaseHelper dbHelper;// 数据库操作对象

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.regist);

		newUser = (EditText) findViewById(R.id.regist_user_edit);
		newPassword = (EditText) findViewById(R.id.regist_passwd_edit);
		confirmPassword = (EditText) findViewById(R.id.regist_passwd_confirm);
		newEmail = (EditText) findViewById(R.id.regist_email);
		registBtn = (Button) findViewById(R.id.registBtn);
		clearBtn = (Button) findViewById(R.id.clearBtn);
		registBtn.setOnClickListener(this);
		clearBtn.setOnClickListener(this);
		// 实例化 数据库操作对象
		dbHelper = new DatabaseHelper(this, "UserInfo.db", null, 1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registBtn://注册按钮事件
			String newusername = newUser.getText().toString();
			String newpassword = newPassword.getText().toString();
			String confirmpwd = confirmPassword.getText().toString();
			String newemail = newEmail.getText().toString();
			if (newusername.equals("") || newpassword.equals("")//判断输入信息是否为空
					|| confirmpwd.equals("")) {
				new AlertDialog.Builder(Regist.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("注册错误").setMessage("帐号或者密码不能为空!")
						.setPositiveButton("重新填写", null).create().show();
			} else if (newpassword.equals(confirmpwd)) {//若两次密码将符合的用户信息插入到User数据表中
				
				if (register(newusername, newpassword, newemail)) {
				Toast.makeText(this, "插入数据到数据库成功", Toast.LENGTH_SHORT).show();
				}
				
				new AlertDialog.Builder(Regist.this)
						.setTitle("注册成功")
						.setMessage("点击按钮跳转到登陆界面~")
						.setPositiveButton(
								"跳转",
								new android.content.DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface arg0,
											int arg1) {
										Intent intent = new Intent(Regist.this,
												Login.class);
										startActivity(intent);
										finish();
									}
								}).create().show();
			} else {
				new AlertDialog.Builder(Regist.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("注册错误").setMessage("两次密码输入不一致，请重新输入")
						.setPositiveButton("重新填写", null).create().show();
				newPassword.setText("");
				confirmPassword.setText("");
			}
			break;

		case R.id.clearBtn://重置按钮事件
			newUser.setText("");
			newPassword.setText("");
			confirmPassword.setText("");
			newEmail.setText("");
			break;

		default:
			break;
		}
	}

	// 将符合的用户信息插入到User数据表中
	public boolean register(String newusername, String newpassword,
			String newemail) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// 将用户信息组装成一条数据
		String sql = "insert into User(username,password,email) values(?,?,?)";
		Object obj[] = { newusername, newpassword, newemail };
		db.execSQL(sql, obj);
		return true;
	}

	public void login_back(View v) { // 标题栏 返回按钮
		this.finish();
	}

}
