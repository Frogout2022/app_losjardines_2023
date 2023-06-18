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
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproyect.R;
import com.example.myproyect.actividades.entidades.CanchaDeportiva;
import com.example.myproyect.actividades.entidades.Reserva;
import com.example.myproyect.actividades.modelos.DAO_Losa;
import com.example.myproyect.actividades.modelos.DAO_Reserva;

import java.util.ArrayList;
import java.util.List;

public class ListarReservasADMIN_Activity extends AppCompatActivity {
    Button salir, actualizar;
    TextView txtvListado, txtvMonto;
    Spinner spnLista;
    private String nombre_tabla = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_reservas_admin);
        referencias();
    }
    private void funSpinner(){
        List<CanchaDeportiva> lista = new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        lista = DAO_Losa.listarNombres();

        List<String> opciones = new ArrayList<>();
        int i=1;
        for(CanchaDeportiva canchaDeportiva : lista){
            opciones.add(i+". "+canchaDeportiva.getNombre());
            i++;
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
                //spnLista.setVisibility(View.GONE);
                txtvListado.setText("Cargando...");
                txtvMonto.setText("Cargando...");
                mostrarLista();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void mostrarLista(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        List<Reserva> listaRsv = new ArrayList<>();
        listaRsv = DAO_Reserva.listarReservasCLI(nombre_tabla);

        int contador=0;
        if(listaRsv.size() == 0 ){
            String msg = "NO HAY RESERVAS EN ESTA LOSA";
            txtvListado.setText(msg);
            txtvMonto.setText("MONTO:   S/0.00");
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();

        }else {
            txtvListado.setText("");
            StringBuilder sb = new StringBuilder();

            for (Reserva reserva : listaRsv) {
                for (int j = 0; j < 3; j++) {
                    String dni = reserva.getArrayDni()[j];
                    if (dni != null) {
                        sb.append("----------------------------------").append("\n");
                        sb.append("|      RESERVA #").append(contador+1).append("      |").append("\n");
                        sb.append("----------------------------------").append("\n");
                        sb.append(" FECHA: ").append(reserva.getDia()).append("\n");
                        int hora = 3 + (2 * j);
                        sb.append(" HORA: ").append(hora).append("pm").append("\n");
                        sb.append(" DNI: ").append(dni).append("\n");
                        sb.append("----------------------------------").append("\n\n");
                        contador++;
                    }
                }
            }
            txtvListado.setText(sb.toString());
            double total = contador * 50;
            txtvMonto.setText("MONTO TOTAL:   S/."+total);
            Toast.makeText(this, "Hay "+listaRsv.size()+" fechas con reservas actualmente en esta losa", Toast.LENGTH_LONG).show();
        }

    }

    private void referencias(){
        txtvMonto = findViewById(R.id.txtvMonto_Adm);

        spnLista = findViewById(R.id.spnListarRsvCLI_Admin);
        funSpinner();

        txtvListado = findViewById(R.id.txtvListadoRsvCLI_Admin);
        txtvListado.setLines(10);
        txtvListado.setEllipsize(TextUtils.TruncateAt.END);
        txtvListado.setMovementMethod(new ScrollingMovementMethod());

        actualizar = findViewById(R.id.btnActualizar_ListaRsvCLI_Admin);
        actualizar.setOnClickListener(view -> {
            mostrarLista();
        });
        salir = findViewById(R.id.btnSalir_ListaRsvCLI_Admin);
        salir.setOnClickListener(view -> {
            Intent intent = new Intent(this, MenuAdmin_Activity.class);
            startActivity(intent);
            finish();
        });

    }

}