package com.example.myproyect.actividades.actividades.usuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.BienvenidoActivity;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.actividades.RecuperarPassword_Activity;
import com.example.myproyect.actividades.clases.MostrarMensaje;
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;

public class ActualizarDatosUSER_Activity extends AppCompatActivity {

    TextView txtDNI, txtNOMBRES;
    Button btnReset, btnUpdate, btnSalir;
    EditText txtCorreo, txtCel;
    final String email = Login_Activity.getUsuario().getCorreo();
    final String cel = Login_Activity.getUsuario().getCelular();
    boolean Datos_iguales = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_datos_user);

        referencias();
        mostrar();
    }
    private void referencias(){
        txtCorreo = findViewById(R.id.txtCorreo_ActualizarDatos_Actv);
        txtCel = findViewById(R.id.txtCelular_ActualizarDatos_Actv);
        btnSalir = findViewById(R.id.btnSALIR_ActDatos_Actv);
        btnSalir.setOnClickListener(view -> {
            Intent intent = new Intent(this, BienvenidoActivity.class);
            startActivity(intent);
            this.finish();
        });


        btnUpdate = findViewById(R.id.btnUpdate_ActDatos_Actv);
        txtCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método llamado antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Método llamado mientras el texto está cambiando
                //Toast.makeText(ActualizarDatosUSER_Activity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Método llamado después de que el texto ha cambiado
                String texto = s.toString(); // Obtener el texto actual del EditText
                // Realizar alguna acción con el texto
                Datos_iguales = s.toString().equals(email);
                if(Datos_iguales){
                    btnUpdate.setEnabled(false);
                }else btnUpdate.setEnabled(true);
                //Toast.makeText(ActualizarDatosUSER_Activity.this, b+"", Toast.LENGTH_SHORT).show();
            }
        });

        txtCel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Método llamado antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Método llamado mientras el texto está cambiando
                //Toast.makeText(ActualizarDatosUSER_Activity.this, s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Método llamado después de que el texto ha cambiado
                String texto = s.toString(); // Obtener el texto actual del EditText
                // Realizar alguna acción con el texto
                Datos_iguales = s.toString().equals(cel);
                if(Datos_iguales){
                    btnUpdate.setEnabled(false);
                }else btnUpdate.setEnabled(true);
                //Toast.makeText(ActualizarDatosUSER_Activity.this, b+"", Toast.LENGTH_SHORT).show();
            }
        });


        txtDNI = findViewById(R.id.txtvDNI_ActualizarDatos_Actv);

        txtNOMBRES = findViewById(R.id.txtvNOMBRES_ActualizarDatos_Actv);
        btnReset = findViewById(R.id.btnResetclave_ActDatos_Actv);
        btnReset.setOnClickListener(view -> {
            Intent intent = new Intent(this, RecuperarPassword_Activity.class);
            startActivity(intent);
            finish();
        });


        btnUpdate.setOnClickListener(view -> {
            update();
        });




    }

    private void update(){
        String correoEdit = txtCorreo.getText().toString();
        String celEdit = txtCel.getText().toString();


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String msg = DAO_Cliente.updateDatos(correoEdit, celEdit);
        MostrarMensaje.mensaje(msg, this);
        if(!msg.substring(0,4).equals("Error")){
            btnUpdate.setEnabled(false);
            mostrar();
        }

    }
    private void mostrar(){
        Usuario usuario = new Usuario();
        usuario = Login_Activity.getUsuario();
        String nombres = usuario.getNombre()+" "+usuario.getApellido();

        txtDNI.setText(usuario.getDNI());
        txtNOMBRES.setText(nombres.toUpperCase());
        txtCorreo.setText(usuario.getCorreo());
        txtCel.setText(usuario.getCelular());


    }
}