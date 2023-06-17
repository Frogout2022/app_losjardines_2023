package com.example.myproyect.actividades.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.clases.Fecha;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import org.w3c.dom.Text;

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
            super.onBackPressed();
        });
        txtv = findViewById(R.id.txtv_consultasRsv_user);

    }

    private void mostrar(){
        txtv.setLines(10);
        txtv.setEllipsize(TextUtils.TruncateAt.END);
        txtv.setMovementMethod(new ScrollingMovementMethod());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<Reserva> listaRsv = DAO_Reserva.ConsultarRsv();
        if(listaRsv.size() == 0 ){
            txtv.setText("NO TIENE RESERVAS");
        }else{
            txtv.setText("");
                String anio = "";
                for(int i=0 ; i<listaRsv.size(); i++) {
                    txtv.setText(txtv.getText()+"FECHA: "+
                            listaRsv.get(i).getDia()+"\nHORA: ");

                    if(listaRsv.get(i).getArrayDni()[0].equals(Login_Activity.getUsuario().getDNI())) {
                        txtv.setText(txtv.getText()+"3pm");
                    }
                    if(listaRsv.get(i).getArrayDni()[1].equals(Login_Activity.getUsuario().getDNI())) {
                        txtv.setText(txtv.getText()+"5pm");
                    }
                    if(listaRsv.get(i).getArrayDni()[2].equals(Login_Activity.getUsuario().getDNI())) {
                        txtv.setText(txtv.getText()+"7pm");
                    }
                    txtv.setText(txtv.getText()+"\n\n");

                }
            }
            Toast.makeText(this, "Tiene "+listaRsv.size()+" reservas", Toast.LENGTH_SHORT).show();

    }


}