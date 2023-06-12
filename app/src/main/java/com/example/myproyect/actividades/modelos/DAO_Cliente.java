package com.example.myproyect.actividades.modelos;

import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Usuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO_Cliente {

    public static ArrayList<Usuario> listarClientes() {//PARA V. MANTENIMIENTO DE EMPLEADO
        ArrayList<Usuario> lista = new ArrayList<>();
        Connection cnx = null;
        try {
            cnx = ConexionMySQL.getConexion();
            CallableStatement csta = cnx.prepareCall("{call sp_ListarCLI()}");
            ResultSet rs = csta.executeQuery();
            Usuario user;
            while (rs.next()) {
                String DNI = rs.getString(1);
                String nom = rs.getString(2);
                String ape = rs.getString(3);
                String correo = rs.getString(4);
                String clave = rs.getString(5);
                String cel = rs.getString(6);
                user = new Usuario(DNI, nom, ape, correo, clave, cel);
                lista.add(user);
            }
        } catch (Exception e) {
            System.out.println("ERROR AC listarClientes(): " + e);
        }
        ConexionMySQL.cerrarConexion(cnx);
        return lista;
    }

    public static boolean ConsultarCLI(String correo, String pass){
        //PARA V. LOGIN
        //PARA V. RESET PASS
        boolean b= false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_consultarCLI(?,?)}");
            csta.setString(1, correo);
            csta.setString(2, pass);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b = true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR AC ConsultarDni(): "+e);}

        return b;
    }
    public static Usuario ObtenerCLI(String correo, String pass){
        //PARA V. LOGIN
        Usuario user = null;
        boolean b= false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_consultarCLI(?,?)}");
            csta.setString(1, correo);
            csta.setString(2, pass);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) {
                String dni = rs.getString(1);
                String nom = rs.getString(2);
                String ape = rs.getString(3);
                String email = rs.getString(4);
                String clave = rs.getString(5);
                String cel = rs.getString(6);
                user = new Usuario(dni,nom,ape,email,clave,cel);
            }

            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR AC ConsultarDni(): "+e);}

        return user;
    }

    public static String insertarCLI(Usuario user){
        String msg=null;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=	cnx.prepareCall("{call sp_InsertarCLI(?,?,?,?,?,?)}");
            csta.setString(1, user.getDNI());
            csta.setString(2, user.getNombre());
            csta.setString(3, user.getApellido());
            csta.setString(4, user.getCorreo());
            csta.setString(5, user.getClave());
            csta.setString(6, user.getCelular());
            csta.executeUpdate();
            msg="Usuario registrado correctamente";
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){
            System.out.println("ERROR AC insertar(): " +e);
            msg= "Error al registrar!";
        }

        return msg;
    }

    public static boolean ConsultarCorreo(String correo){
        //PARA V. REGISTRARSE & RESET_PASS
        boolean b = false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_consultarCorreoCLI(?)}");
            csta.setString(1, correo);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b= true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("Error AC ConsultarCorreo(): "+e);}
        return b;
    }
    public static boolean ConsultarCelular(String celular){

        boolean b = false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta=cnx.prepareCall("{call sp_ConsultarCelularCLI(?)}");
            csta.setString(1, celular);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b= true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR[DAO] ConsultarCelular(): "+e);}
        return b;
    }


    public static boolean ConsultarDni(String dni){//PARA CONFIRMAR EL RESET PASS
        //PARA V. RESET PASS
        boolean b= false;
        try{
            Connection cnx=ConexionMySQL.getConexion();
            CallableStatement csta = cnx.prepareCall("{call sp_consultarDniCLI(?)}");
            csta.setString(1, dni);
            ResultSet rs= csta.executeQuery();
            if(rs.next()) b = true;
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("ERROR AC ConsultarDni(): "+e);}
        return b;
    }

    public static String editarPass(String dni, String pass){
        String msg=null;
        try {
            Connection cnx = ConexionMySQL.getConexion();
            CallableStatement psta = cnx.prepareCall("{call sp_editarPassCLI(?,?)}");
            psta.setString(1, dni);
            psta.setString(2, pass);
            psta.executeQuery();
            ConexionMySQL.cerrarConexion(cnx);
            msg="Se actualizó su contraseña";
        } catch (Exception e) {
            System.out.println("Error editarPass: "+e);
            msg="Error al actualizar la contraseña";
        }
        return msg;
    }

    public static  String updateDatos(String correo, String celular){
        String msg= "";
        if(ConsultarCorreo(correo)){
            msg = "Correo ya existe ";
        }else if(ConsultarCelular(celular)){
            msg = msg+"Celular ya existe ";
        }else{
            String dni = Login_Activity.getUsuario().getDNI();
            try {
                Connection cnx = ConexionMySQL.getConexion();
                CallableStatement psta = cnx.prepareCall("{call sp_EditarDatosCLI(?,?,?)}");
                psta.setString(1, dni);
                psta.setString(2, correo);
                psta.setString(3, celular);
                psta.executeQuery();
                ConexionMySQL.cerrarConexion(cnx);
                msg = "Datos actualizados correctamente";
                Login_Activity.usuario.setCorreo(correo);
                Login_Activity.usuario.setCelular(celular);
            } catch (Exception e) {
                System.out.println("ERROR[DAO] updateDatos(): "+e);
                msg = "Error al actualizar"+e;
            }
        }

        return msg;
    }

    public static String deleteCLI(){
        String msg="";
        String dni = Login_Activity.getUsuario().getDNI();
        try {
            Connection cnx = ConexionMySQL.getConexion();
            CallableStatement psta = cnx.prepareCall("{call sp_EliminarCLI(?)}");
            psta.setString(1, dni);
            psta.executeQuery();
            ConexionMySQL.cerrarConexion(cnx);
            msg = "Usuario eliminado correctamente";

        } catch (Exception e) {
            System.out.println("ERROR[DAO] updateDatos(): "+e);
            msg = "Error al eliminar"+e;
        }
        return msg;
    }



}
