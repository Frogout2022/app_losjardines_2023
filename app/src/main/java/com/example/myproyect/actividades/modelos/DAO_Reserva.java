package com.example.myproyect.actividades.modelos;

import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Reserva;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DAO_Reserva {


    public static  ArrayList<Reserva> listarReservaSemanal() {// PARA LA ACT. TABLA RESERVAS - CLI
        ArrayList<Reserva> lista = new ArrayList<>();
        Connection cnx = null;
        try {
            cnx = ConexionMySQL.getConexion();
            CallableStatement csta = cnx.prepareCall("{call sp_ListarRESERVA_semanal()}");
            ResultSet rs = csta.executeQuery();
            Reserva reserva;

            while (rs.next()) {
                boolean[] ab = new boolean[3];
                String dia = rs.getString(2);
                ab[0] = rs.getBoolean(3);
                ab[1] = rs.getBoolean(4);
                ab[2] = rs.getBoolean(5);

                reserva = new Reserva(dia, ab);
                lista.add(reserva);
            }

        } catch (Exception e) {
            System.out.println("ERROR listarReserva_semanal(): " + e);
        }

        ConexionMySQL.cerrarConexion(cnx);
        return lista;
    }
    public static boolean LlenarTablaFEcha(){
        Connection cnx = ConexionMySQL.getConexion();
        Boolean b=false;
        try {
            CallableStatement csta = cnx.prepareCall("{call sp_insertar_Fechas}");
            b = csta.execute();
        }catch (Exception e){
            System.out.println("ERROR AC listarReserva(): " + e);
        }
        ConexionMySQL.cerrarConexion(cnx);
        return b;
    }


    public static List<Reserva> ConsultarRsv(){
        List<Reserva> lista = new ArrayList<>();
        String dni = Login_Activity.getUsuario().getDNI();
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_ConsultarRsvCLI(?)}");
            csta.setString(1, dni);
            ResultSet rs= csta.executeQuery();
            Reserva reserva=null;
            while(rs.next()){
                String dia;
                boolean[] arrayb = new boolean[3];
                String[] arrayDni = new String[3];
                String dniBD = "";
                // 1 -> ID
                dia = rs.getString(2);

                arrayb[0] = rs.getBoolean(3);
                arrayb[1] = rs.getBoolean(4);
                arrayb[2] = rs.getBoolean(5);


                dniBD = rs.getString(6);
                if(dniBD == null) dniBD="";
                arrayDni[0] = dniBD;

                dniBD = rs.getString(7);
                if(dniBD == null) dniBD="";
                arrayDni[1] = dniBD;

                dniBD = rs.getString(8);
                if(dniBD == null) dniBD="";
                arrayDni[2] = dniBD;


                reserva = new Reserva(dia, arrayb,arrayDni);
                lista.add(reserva);
            }

            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("Error sp_ConsultarRsvCLI(): "+e);}
        return lista;
    }

    public static List<Reserva> listarReservasCLI(){ //LISTAR TODAS LAS RESERVAS DEL AÑO
        //PARA ACTV. LISTAR_RSV_ADMIN

        ArrayList<Reserva> lista = new ArrayList<>();
        Connection cnx = null;
        try {
            cnx = ConexionMySQL.getConexion();
            CallableStatement csta = cnx.prepareCall("{call sp_ListarReservasCLI()}");
            ResultSet rs = csta.executeQuery();
            Reserva reserva;

            while (rs.next()) {

                String dia ;
                boolean[] arrayB = new boolean[3];
                String[] arrayDni = new String[3];
                String dniBD = ""; //evitar valores nulos

                // index 1 = id_reserva
                dia = rs.getString(2);

                arrayB[0] = rs.getBoolean(3);
                arrayB[1] = rs.getBoolean(4);
                arrayB[2] = rs.getBoolean(5);

                dniBD = rs.getString(6);
                if(dniBD == null) dniBD="";
                arrayDni[0] = dniBD;

                dniBD = rs.getString(7);
                if(dniBD == null) dniBD="";
                arrayDni[1] = dniBD;

                dniBD = rs.getString(8);
                if(dniBD == null) dniBD="";
                arrayDni[2] = dniBD;


                reserva = new Reserva(dia, arrayB, arrayDni);
                lista.add(reserva);
            }

        } catch (Exception e) {
            System.out.println("ERROR DAO sp_ListarReservasCLI(): " + e);
        }

        ConexionMySQL.cerrarConexion(cnx);
        return lista;
    }

    public static String insertarRSV(String dia, int h){
        //editar
        String msg=null;
        String dni = Login_Activity.getUsuario().getDNI();
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=	cnx.prepareCall("{call sp_ReservarH"+h+"(?,?)}");
            csta.setString(1,dia);
            csta.setString(2,dni);

            csta.executeUpdate();
            msg="Reserva registrada correctamente";
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){
            System.out.println("ERROR AC insertarRSV(): " +e);
            msg= "Error al reservar!";
        }

        return msg;
    }

}
