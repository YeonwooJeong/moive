package com.yonoo.movie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {

    Button lotte,cgv ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select);


        final Intent it_cgv = new Intent(this, CgvActivity.class);
        cgv = (Button) findViewById(R.id.cgv);
        cgv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(it_cgv);
            }
        });


        final Intent it_lotte = new Intent(this, LotteActivity.class);
        lotte = (Button) findViewById(R.id.lotte);
        lotte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(it_lotte);
            }
        });
    }

}
