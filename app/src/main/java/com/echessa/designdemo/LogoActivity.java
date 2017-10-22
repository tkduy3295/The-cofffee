package com.echessa.designdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LogoActivity extends AppCompatActivity {

    private Button btnLoginLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        btnLoginLogo = (Button) findViewById(R.id.btnLoginLogo);

        btnLoginLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
