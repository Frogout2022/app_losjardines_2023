package com.example.myproyect.actividades.modelos;

import com.example.myproyect.actividades.actividades.Login_Activity;
import com.example.myproyect.actividades.conexion.ConexionMySQL;
import com.example.myproyect.actividades.entidades.Reserva;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DAO_Losa {
    public static String consultarNombre(int id){
        String nom= "";
        try{
            Connection cnx= ConexionMySQL.getConexion();
            Statement statement = cnx.createStatement();
            String sql="SELECT nombre_losa FROM tb_losa where id= "+id;
            ResultSet rs= statement.executeQuery(sql);
            if(rs.next()) nom = rs.getString(1);
            ConexionMySQL.cerrarConexion(cnx);
        }catch(Exception e){System.out.println("Error consultarNombre(): "+e);}
        return nom;
    }

}
