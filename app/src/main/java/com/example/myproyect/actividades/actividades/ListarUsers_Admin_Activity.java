package com.example.myproyect.actividades.actividades;

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
import com.example.myproyect.actividades.entidades.Usuario;
import com.example.myproyect.actividades.modelos.DAO_Cliente;
import com.mysql.fabric.xmlrpc.Client;

import java.util.ArrayList;
import java.util.List;

public class ListarUsers_Admin_Activity extends AppCompatActivity {
    Button btnRegresar;
    TextView txtListar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_users_admin);

        asignarReferencias();
        listar();
    }
    private void asignarReferencias(){
        btnRegresar = findViewById(R.id.btnRegresar_ListarUsersAdmn);
        btnRegresar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuAdmin_Activity.class);
            startActivity(intent);
            finish();

        });
        txtListar = findViewById(R.id.txtvListarUsers_Admin);
    }
    private void listar(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        List<Usuario> listaUsers = DAO_Cliente.listarClientes();

        txtListar.setLines(50);
        txtListar.setEllipsize(TextUtils.TruncateAt.END);
        txtListar.setMovementMethod(new ScrollingMovementMethod());
        if(listaUsers.size()!=0){
            for(int i=1; i<listaUsers.size(); i++){//omitimos el primer usuario
                txtListar.setText(txtListar.getText()+ "\n"+
                        "DNI: "+listaUsers.get(i).getDNI()+"\n"+
                        "NOMBRE: "+listaUsers.get(i).getNombre()+"\n"+
                        "APELLIDO: "+listaUsers.get(i).getApellido()+"\n"+
                        "CORREO: "+listaUsers.get(i).getCorreo()+"\n"+
                        "CELULAR: "+listaUsers.get(i).getCelular()+"\n"+

                        "------------------------");
            }
        }else{
            Toast.makeText(this, "No hay usuarios registrados", Toast.LENGTH_SHORT).show();
        }


    }
}