package com.example.walk_it;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int ACTIVITY_REQUEST_CODE = 1;

    private Button bttSfida1=null,bttSfida2=null,bttSfida3=null,bttSfidaPersonalizzata=null;
    private TextView tvSfida1=null, tvSfida2=null, tvSfida3=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bttSfida1=findViewById(R.id.bttSfida1);
        bttSfida2=findViewById(R.id.bttSfida2);
        bttSfida3=findViewById(R.id.bttSfida3);
        bttSfidaPersonalizzata=findViewById(R.id.bttSfidaPersonalizzata);
        tvSfida1=findViewById(R.id.tvSfida1);
        tvSfida2=findViewById(R.id.tvSfida2);
        tvSfida3=findViewById(R.id.tvSfida3);
        bttSfida1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida1.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        bttSfida2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida2.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });

        bttSfida3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _text=tvSfida3.getText().toString();

                Intent intent=new Intent("sfida");//mettere stringa nel file strings
                //al posto di sfida 1 mettere path del file da aprire
                intent.putExtra("NOME_SFIDA",_text);
                startActivity(intent);
            }
        });
    }
}