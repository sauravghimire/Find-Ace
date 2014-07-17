package com.example.whoslucky.app.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

import com.example.whoslucky.app.MainActivity;
import com.example.whoslucky.app.R;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLoginListener;

/**
 * Created by susan on 7/17/14.
 */
public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private SimpleFacebook simpleFacebook;
    private Button buttonFacebookLogin, buttonSkipLogin;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login_acitivity);

        progressDialog = new ProgressDialog(this);
        sharedPreferences = getSharedPreferences("FB_REG", 0);

        buttonFacebookLogin = (Button) findViewById(R.id.buttonFacebookLogin);
        buttonFacebookLogin.setOnClickListener(this);

        buttonSkipLogin = (Button) findViewById(R.id.buttonSkipLogin);
        buttonSkipLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFacebookLogin:
                if (!simpleFacebook.isLogin()) {
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Logging In");
                    progressDialog.show();
                    simpleFacebook.login(loginListener);
                }
                break;

            case R.id.buttonSkipLogin:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("is_fb_login", false);
                editor.commit();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
                break;
        }
    }

    private OnLoginListener loginListener = new OnLoginListener() {
        @Override
        public void onLogin() {
            progressDialog.dismiss();
        }

        @Override
        public void onNotAcceptingPermissions(Permission.Type type) {
            progressDialog.dismiss();
        }

        @Override
        public void onThinking() {

        }

        @Override
        public void onException(Throwable throwable) {
            progressDialog.dismiss();
        }

        @Override
        public void onFail(String reason) {
            progressDialog.dismiss();
        }
    };
}
