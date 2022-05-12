package com.bignerdranch.android.geoquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {

    Button mButtonstart;
    EditText mInputName;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mButtonstart = findViewById(R.id.namebutton);
        mInputName = findViewById(R.id.inputname);

        mButtonstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(StartActivity.this, QuizActivity.class);
                name = mInputName.getText().toString();
                i.putExtra("userinput", name);
                startActivity(i);
                finish();

                Intent i2 = new Intent(StartActivity.this, ResultActivity.class);
                name = mInputName.getText().toString();
                i2.putExtra("userinput2", name);


            }
        });

    }
}
