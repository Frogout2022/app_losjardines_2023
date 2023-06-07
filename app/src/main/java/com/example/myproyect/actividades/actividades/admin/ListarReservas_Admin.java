package com.example.myproyect.actividades.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListarReservas_Admin extends AppCompatActivity {
    Button Salir;
    TextView listado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reservas_admin);
        referencias();
        mostrar();
    }
    private void referencias(){
        listado = findViewById(R.id.txtvListadoRsvCLI_Admin);

    }
    private void mostrar(){
        listado.setLines(20);
        listado.setEllipsize(TextUtils.TruncateAt.END);
        listado.setMovementMethod(new ScrollingMovementMethod());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<Reserva> listaRsv = new ArrayList<>();
        listaRsv = DAO_Reserva.listarReservasCLI();

        if(listaRsv.size() == 0){
            Toast.makeText(this, "No hay reservas", Toast.LENGTH_SHORT).show();
        }else{
            listado.setText("");
            for (Reserva reserva : listaRsv) {
                listado.append("FECHA: " + reserva.getDia() + "\nHORA: ");
                boolean[] arrayB = reserva.getArrayB();

                if(arrayB[0]) listado.append("3pm");
                if(arrayB[1]) listado.append("5pm");
                if(arrayB[2]) listado.append("7pm");

                listado.append("\n\n");
            }


            Toast.makeText(this, "Hay "+listaRsv.size()+" reservas actualmente", Toast.LENGTH_LONG).show();
        }
    }
}