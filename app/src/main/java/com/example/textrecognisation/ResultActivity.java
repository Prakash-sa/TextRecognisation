package com.example.textrecognisation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    private Button backbu;
    private TextView resultTe;
    private String resultStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        resultTe=findViewById(R.id.result_is);
        backbu=findViewById(R.id.back_bu);
        resultStr=getIntent().getStringExtra(LCOTextRecognisation.RESULT_TEXT);
        resultTe.setText(resultStr);
        backbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }
}
