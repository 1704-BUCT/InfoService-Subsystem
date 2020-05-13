package com.example.museum;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
* 编辑个人信息页面
* */
public class EditInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        EditText editText_name = findViewById(R.id.editText_name);
        EditText editText_birthday = findViewById(R.id.editText_birthday);
        EditText editText_email = findViewById(R.id.editText_email);
        EditText editText_phone_number = findViewById(R.id.editText_phone_number);
        editText_name.setText("YOUNG ONE");
        editText_birthday.setText("1999-01-28");
        editText_email.setText("652192782@qq.com");
        editText_phone_number.setText("18513770325");
        Button back_my_info = findViewById(R.id.back_my_info);
        back_my_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
