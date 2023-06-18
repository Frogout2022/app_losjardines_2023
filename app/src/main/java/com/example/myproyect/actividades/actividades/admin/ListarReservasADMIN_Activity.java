package com.example.myproyect.actividades.actividades.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Administrador;
import com.example.myproyect.actividades.modelos.DAO_Cliente;
import com.example.myproyect.actividades.modelos.DAO_Losa;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListarReservasADMIN_Activity extends AppCompatActivity {
    Button salir, actualizar;
    TextView listado;
    Spinner spnLista;
    private String nombre_tabla = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reservas_admin);
        referencias();
        //mostrar();
    }
    private void funSpinner(){
        List<CanchaDeportiva> lista = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = DAO_Losa.listarNombres();

        List<String> opciones = new ArrayList<>();
        for(CanchaDeportiva canchaDeportiva : lista){
            opciones.add(canchaDeportiva.getNombre());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spnLista.setAdapter(adapter);

        List<CanchaDeportiva> finalLista = lista;
        spnLista.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //String opcion = (String) adapterView.getItemAtPosition(i);
                nombre_tabla = finalLista.get(i).getNombre_tabla();
                   mostrarLista(nombre_tabla);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void mostrarLista(String nom_tb){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Reserva> listaRsv = new ArrayList<>();
        listaRsv = DAO_Reserva.listarReservasCLI(nom_tb);

        if(listaRsv.size() == 0 ){
            Toast.makeText(this, "NO HAY RESERVAS EN ESTA LOSA", Toast.LENGTH_SHORT).show();
            listado.setText("NO HAY RESERVAS EN ESTA LOSA");
        }else{
            listado.setText("");
            int i=0, can=1;
            for (Reserva reserva : listaRsv) {
                listado.append("----------------------------------"+"\n");
                listado.append("| RESERVA #"+can+"\t|"+"\n");
                listado.append("----------------------------------"+"\n");
                listado.append(" FECHA: " + reserva.getDia() + "\n HORA: ");
                String[] arrayDni = reserva.getArrayDni();
                if(arrayDni[0] != null){
                    listado.append(" 3pm"+"\n");
                    listado.append(" DNI: "+listaRsv.get(i).getArrayDni()[0] +" ");
                }
                if(arrayDni[1] != null){
                   listado.append(" 5pm"+"\n");
                    listado.append(" DNI: "+listaRsv.get(i).getArrayDni()[1] +" ");
                }
                if(arrayDni[2] != null){
                    listado.append(" 7pm"+"\n");
                    listado.append(" DNI: "+listaRsv.get(i).getArrayDni()[2] +" ");
                }
                listado.append("----------------------------------"+"\n\n");
                i++;
                can++;
            }
            Toast.makeText(this, "Hay "+listaRsv.size()+" reservas actualmente en esta losa", Toast.LENGTH_LONG).show();
        }

    }


    private void referencias(){
        spnLista = findViewById(R.id.spnListarRsvCLI_Admin);
        funSpinner();

        listado = findViewById(R.id.txtvListadoRsvCLI_Admin);
        actualizar = findViewById(R.id.btnActualizar_ListaRsvCLI_Admin);
        actualizar.setOnClickListener(view -> {
            mostrarLista(nombre_tabla);
        });
        salir = findViewById(R.id.btnSalir_ListaRsvCLI_Admin);
        salir.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuAdmin_Activity.class);
            startActivity(intent);
            finish();
        });

    }

}