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
	private EditText newUser; // �ʺű༭��
	private EditText newPassword; // ����༭��
	private EditText confirmPassword; // ����ȷ�Ͽ�
	private Button registBtn, clearBtn;// ע�ᡢȷ�ϰ�ť
	private EditText newEmail;// ע������

	private DatabaseHelper dbHelper;// ���ݿ��������

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
		// ʵ���� ���ݿ��������
		dbHelper = new DatabaseHelper(this, "UserInfo.db", null, 1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.registBtn://ע�ᰴť�¼�
			String newusername = newUser.getText().toString();
			String newpassword = newPassword.getText().toString();
			String confirmpwd = confirmPassword.getText().toString();
			String newemail = newEmail.getText().toString();
			if (newusername.equals("") || newpassword.equals("")//�ж�������Ϣ�Ƿ�Ϊ��
					|| confirmpwd.equals("")) {
				new AlertDialog.Builder(Regist.this)
						.setIcon(
								getResources().getDrawable(
										R.drawable.login_error_icon))
						.setTitle("ע�����").setMessage("�ʺŻ������벻��Ϊ��!")
						.setPositiveButton("������д", null).create().show();
			} else if (newpassword.equals(confirmpwd)) {//���������뽫���ϵ��û���Ϣ���뵽User���ݱ���
				
				if (register(newusername, newpassword, newemail)) {
				Toast.makeText(this, "�������ݵ����ݿ�ɹ�", Toast.LENGTH_SHORT).show();
				}
				
				new AlertDialog.Builder(Regist.this)
						.setTitle("ע��ɹ�")
						.setMessage("�����ť��ת����½����~")
						.setPositiveButton(
								"��ת",
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
						.setTitle("ע�����").setMessage("�����������벻һ�£�����������")
						.setPositiveButton("������д", null).create().show();
				newPassword.setText("");
				confirmPassword.setText("");
			}
			break;

		case R.id.clearBtn://���ð�ť�¼�
			newUser.setText("");
			newPassword.setText("");
			confirmPassword.setText("");
			newEmail.setText("");
			break;

		default:
			break;
		}
	}

	// �����ϵ��û���Ϣ���뵽User���ݱ���
	public boolean register(String newusername, String newpassword,
			String newemail) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		// ���û���Ϣ��װ��һ������
		String sql = "insert into User(username,password,email) values(?,?,?)";
		Object obj[] = { newusername, newpassword, newemail };
		db.execSQL(sql, obj);
		return true;
	}

	public void login_back(View v) { // ������ ���ذ�ť
		this.finish();
	}

}
