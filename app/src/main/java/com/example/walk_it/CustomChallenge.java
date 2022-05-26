package com.example.walk_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomChallenge extends AppCompatActivity {
    private final String TAG = "CustomChallenge";
    private EditText etNumPassi = null;
    private EditText etTempoSfida = null;
    private Button bttConferma = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_challenge);

        etNumPassi = findViewById(R.id.etCustomPassi);
        etTempoSfida = findViewById(R.id.etCustomTempo);
        bttConferma = findViewById(R.id.bttConferma);

        bttConferma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aggiungere controlli sul contenuto di edit Text
                String _passi = etNumPassi.getText().toString();
                String _tempo = etTempoSfida.getText().toString();

                Intent intent = new Intent("sfida");
                intent.putExtra("NOME_SFIDA","Sfida Personalizzata");
                intent.putExtra("PASSI",_passi);
                intent.putExtra("TIME",_tempo);
                startActivity(intent);
            }
        });
    }
}