package com.example.study_list;

import android.text.TextUtils;
import android.view.View;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // 引用login_fragment.xml布局文件
//        return inflater.inflate(R.layout.login_fragment, container, false);
//    }
//
//    private boolean isValidLogin(String username, String password) {
//        String correctUsername = "syc";
//        String correctPassword = "123456";
//        return username.equals(correctUsername) && password.equals(correctPassword);
//    }
//
//    private void handleLogin() {
//        EditText usernameEditText = getView().findViewById(R.id.eamil);
//        EditText passwordEditText = getView().findViewById(R.id.pass);
//        String username = usernameEditText.getText().toString();
//        String password = passwordEditText.getText().toString();
//
//        if (isValidLogin(username, password)) {
//            navigateToMainActivity();
//        } else {
//            System.out.println("登录失败");
//        }
//    }
//    private void navigateToMainActivity() {
//        Intent intent = new Intent(requireContext(), MainActivity.class);
//        startActivity(intent);
//    }
//
//}

    public static void setLoggedInStatus(boolean isLoggedIn) {
        MainActivity.isLoggedIn = isLoggedIn;
    }

    private Button btnLogin;
    private Button btnRegister;
    private EditText zhanghu,mima;

    private String username = "syc";
    private String pass = "123456";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        androidx.appcompat.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null) {
            actionBar.setTitle("登录");
        }


        btnLogin = findViewById(R.id.btn_Login);
        btnRegister = findViewById(R.id.btn_signup);
        zhanghu = findViewById(R.id.eamil);
        mima = findViewById(R.id.pass);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到你的项目或Activity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class); // 替换YourProjectActivity为实际的Activity类名
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = zhanghu.getText().toString();
                String password = mima.getText().toString();

                if (TextUtils.equals(account, username) && TextUtils.equals(password, pass)) {
                    // 登录成功，设置登录状态为true
                    setLoggedInStatus(true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }}