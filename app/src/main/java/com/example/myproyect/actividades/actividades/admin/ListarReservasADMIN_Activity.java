package com.example.myproyect.actividades.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.MenuAdmin_Activity;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListarReservasADMIN_Activity extends AppCompatActivity {
    Button salir, actualizar;
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
        actualizar = findViewById(R.id.btnActualizar_ListaRsvCLI_Admin);
        actualizar.setOnClickListener(view -> {
            mostrar();
        });
        salir = findViewById(R.id.btnSalir_ListaRsvCLI_Admin);
        salir.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuAdmin_Activity.class);
            startActivity(intent);
            finish();
        });

    }
    private void mostrar(){
        listado.setLines(60);
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
                boolean[] arrayB = reserva.getArrayB();
                String[] arrayDni = reserva.getArrayDni();

                listado.append("-------------------------------------------"+"\n");
                listado.append("FECHA: " + reserva.getDia() + "\n");
                listado.append("-------------------------------------------"+"\n");
                for (int i = 0; i < arrayB.length; i++) {
                    if (arrayB[i]) {
                        String hora = "";
                        switch (i) {
                            case 0:
                                hora = "3pm";
                                break;
                            case 1:
                                hora = "5pm";
                                break;
                            case 2:
                                hora = "7pm";
                                break;
                        }
                        listado.append("HORA: " + hora + " -> ");
                        listado.append("DNI: " + arrayDni[i] + "\n");
                    }
                }

                listado.append("######################");
                listado.append("\n\n\n");

            }
            Toast.makeText(this, "Hay "+listaRsv.size()+" reservas actualmente", Toast.LENGTH_LONG).show();
        }
    }
}