package com.example.myproyect.actividades.actividades.usuario;

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
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ConsultarReservaUser_Activity extends AppCompatActivity {
    Button btnVolver;
    TextView txtv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_reserva_user);

        asignarReferencias();
        mostrar();

    }
    private void asignarReferencias(){
        btnVolver = findViewById(R.id.btnVolver_ConsultRsvUser);
        btnVolver.setOnClickListener(view -> {
            startActivity(new Intent(this, BienvenidoActivity.class));
            finish();
        });
        txtv = findViewById(R.id.txtv_consultasRsv_user);

    }

    private void mostrar(){
        txtv.setLines(10);
        txtv.setEllipsize(TextUtils.TruncateAt.END);
        txtv.setMovementMethod(new ScrollingMovementMethod());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Reserva> listaRsv1 = new ArrayList<>();
        List<Reserva> listaRsv2 = new ArrayList<>();
        List<Reserva> listaRsv3 = new ArrayList<>();
        List<Reserva> listaRsv4 = new ArrayList<>();
        List<Reserva> listaRsvTotal = new ArrayList<>();

        listaRsv1 = DAO_Reserva.ConsultarRsv("reserva_losa1");
        listaRsv2 = DAO_Reserva.ConsultarRsv("reserva_losa2");
        listaRsv3 = DAO_Reserva.ConsultarRsv("reserva_losa3");
        listaRsv4 = DAO_Reserva.ConsultarRsv("reserva_losa4");

        listaRsvTotal.addAll(listaRsv1);
        listaRsvTotal.addAll(listaRsv2);
        listaRsvTotal.addAll(listaRsv3);
        listaRsvTotal.addAll(listaRsv4);

        if(listaRsvTotal.size() == 0 ){
            txtv.setText("NO TIENE RESERVAS");
        }else{
            txtv.setText("");
            final String dni = Login_Activity.getUsuario().getDNI();
            int i=1;
            for (Reserva reserva : listaRsvTotal) {
                txtv.append("| RESERVA #"+i+"\t|"+"\n");
                txtv.append("----------------------------------"+"\n");
                txtv.append("FECHA: " + reserva.getDia() + "\nHORA: ");
                String[] arrayDni = reserva.getArrayDni();
                if (arrayDni[0] == dni) txtv.append("3pm");
                if (arrayDni[1] == dni) txtv.append("5pm");
                if (arrayDni[2] == dni) txtv.append("7pm");
                txtv.append("\nLUGAR: " + reserva.getId_losa() + "\n\n");
                txtv.append("----------------------------------"+"\n");
                i++;
            }
            }
            Toast.makeText(this, "Tiene "+listaRsvTotal.size()+" reservas", Toast.LENGTH_SHORT).show();

    }




}