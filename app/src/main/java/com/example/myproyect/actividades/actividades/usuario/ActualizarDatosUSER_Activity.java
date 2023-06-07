package com.example.myproyect.actividades.actividades.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.RecuperarPassword_Activity;
import com.example.myproyect.actividades.entidades.Usuario;

public class ActualizarDatosUSER_Activity extends AppCompatActivity {

    TextView txtDNI, txtNOMBRES;
    Button btnReset, btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos_user);

        referencias();
        mostrar();
    }
    private void referencias(){

        txtDNI = findViewById(R.id.txtvDNI_ActualizarDatos_Actv);
        txtNOMBRES = findViewById(R.id.txtvNOMBRES_ActualizarDatos_Actv);
        btnReset = findViewById(R.id.btnResetclave_ActDatos_Actv);
        btnReset.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecuperarPassword_Activity.class);
            startActivity(intent);
            finish();
        });
        btnUpdate = findViewById(R.id.btnResetclave_ActDatos_Actv);
        btnUpdate.setOnClickListener(view -> {


        });

    }
    private void mostrar(){
        Usuario usuario = new Usuario();
        usuario = Login_Activity.getUsuario();

        txtDNI.setText(usuario.getDNI());
        txtNOMBRES.setText(usuario.getNombre()+" "+usuario.getApellido());


    }
}