package jucaipen.livechat.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import jucaipen.livechat.MainActivity;
import jucaipen.livechat.R;

/**
 * Created by Administrator on 2017/3/21.
 */

public class LoginActivity extends Activity {
    private EditText etAccount;
    private SharedPreferences chat_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        etAccount = (EditText) findViewById(R.id.etAccount);
    }

    public void onLogin(View view) {
        String string = etAccount.getText().toString();
        if (string == null) {
            return;
        }
    }
}
