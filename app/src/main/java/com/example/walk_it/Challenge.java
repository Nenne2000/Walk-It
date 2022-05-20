package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Challenge extends AppCompatActivity {

    TextView tvNomeSfida=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        tvNomeSfida=findViewById(R.id.tvNomeSfida);

        Intent _intent=getIntent();
        String _nomeSfida=_intent.getStringExtra("NOME_SFIDA");
        tvNomeSfida.setText(_nomeSfida);
    }
}