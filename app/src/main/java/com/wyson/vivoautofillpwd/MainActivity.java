package com.wyson.vivoautofillpwd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editPwd;
    private Button btnService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editPwd = findViewById(R.id.edit_pwd);
        btnService = findViewById(R.id.btn_service);
        if (!AccessibilityUtils.isStartAccessibilityService(this,"InstallHelperService")){
            startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));
            Toast.makeText(this, "服务未开启", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "服务已开启", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String password = PreferenceUtils.getString(getApplicationContext(),
                PreferenceUtils.KEY_PASSWORD, "");
        if (!TextUtils.isEmpty(password)) {
            editPwd.setText(password);
            editPwd.setSelection(password.length());
        }
    }

    public void savePwd(View view){
        PreferenceUtils.putString(this,
                PreferenceUtils.KEY_PASSWORD,editPwd.getText().toString().trim());

    }

    public void openSettings(View view) {
        startActivity(new Intent(android.provider.Settings.ACTION_ACCESSIBILITY_SETTINGS));
    }
}
