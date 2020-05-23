package com.hngymt.almes.pda.client;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hngymt.almes.pda.client.models.token_auth.AuthenticateModel;
import com.hngymt.almes.pda.client.models.token_auth.AuthenticateResultModel;
import com.hngymt.almes.pda.client.utils.EasyToast;
import com.hngymt.almes.pda.client.utils.ServiceCallback;
import com.hngymt.almes.pda.client.utils.ServiceHttpClient;

public class LoginActivity extends BaseActivity {
    private EditText txtUsername;
    private EditText txtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    public void initView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    public void onBtnClick(View view) {
        if (!"".equals(txtUsername.getText().toString()) && !"".equals(txtPassword.getText().toString())) {
            ServiceCallback authenticateCallback = new ServiceCallback() {
                @Override
                public void onSuccess(Object resultObj) {
                    super.onSuccess(resultObj);
                    final AuthenticateResultModel authenticateResult = (AuthenticateResultModel) resultObj;
                    ServiceHttpClient.getInstance().setAccessToken(authenticateResult.getAccessToken());
                    ServiceHttpClient.getInstance().setEncryptedAccessToken(authenticateResult.getEncryptedAccessToken());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            };
            AuthenticateModel authenticateModel = new AuthenticateModel(txtUsername.getText().toString(),
                    txtPassword.getText().toString(), false);

            ServiceHttpClient.getInstance().Post("/api/TokenAuth/Authenticate", authenticateModel,
                    AuthenticateResultModel.class, LoginActivity.this, authenticateCallback);
        } else {
            EasyToast.showText(LoginActivity.this, "请输入用户名或密码", Toast.LENGTH_SHORT);
        }
    }
}
