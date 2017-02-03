package android.miguel.lab4elercicio1_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_generar, btn_comprobar, btn_puntuacion, btn_puntuacionMaxima;
    private EditText txt_numeroIntroducido;
    private int numeroGenerado, numeroIntentos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_generar = (Button) findViewById(R.id.btn_generar);
        btn_comprobar = (Button) findViewById(R.id.btn_comprobar);
        btn_puntuacion = (Button) findViewById(R.id.btn_puntuacion);
        btn_puntuacionMaxima = (Button) findViewById(R.id.btn_puntuacionMaxima);

        txt_numeroIntroducido = (EditText) findViewById(R.id.txt_numeroIntroducido);

        SharedPreferences puntuacionMaxima = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE);
        btn_puntuacionMaxima.setText(String.valueOf(puntuacionMaxima.getInt("puntuacionMaxima", 0)));


        generar(null);

        btn_generar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generar(v);
            }
        });

        btn_comprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comprobar(v);
            }
        });

    }

    private void generar(View v) {
        numeroGenerado = (int) (Math.random() * (1 - 10)) + 10;
        //limpio la puntuación
        btn_puntuacion.setBackgroundColor(Color.DKGRAY);
        btn_puntuacion.setText("0");
        numeroIntentos = 0;
    }

    private void comprobar(View v) {
        int puntuacionActual = Math.abs(numeroIntentos - 10);
        int numeroIntroducido = Integer.valueOf(txt_numeroIntroducido.getText().toString());
        int puntuacionMaxima = Integer.valueOf(btn_puntuacionMaxima.getText().toString());

        if (numeroGenerado == numeroIntroducido) {
            btn_puntuacion.setText(String.valueOf(puntuacionActual));

            if (puntuacionActual > puntuacionMaxima) {
                SharedPreferences preferenciasPuntuacionMaxima = getSharedPreferences("puntuaciones", Context.MODE_PRIVATE);
                btn_puntuacionMaxima.setText(String.valueOf(puntuacionActual));
                SharedPreferences.Editor editor = preferenciasPuntuacionMaxima.edit();
                editor.putInt("puntuacionMaxima", puntuacionActual);
                editor.commit();
            }
            btn_puntuacion.setBackgroundColor(Color.RED);
        } else {
            numeroIntentos++;
        }
    }
}
