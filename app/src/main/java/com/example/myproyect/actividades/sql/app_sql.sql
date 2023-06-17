#drop database app_losjardines;
create database app_losjardines;
use app_losjardines;
SELECT NOW() AS fecha_hora_actual;
SET lc_time_names = 'es_ES'; #CAMBIAR ESPAÑOL EL IDIOMA

create table Cliente(
Dni_Cli char(8) primary key,
Nomb_Cli varchar(20) not null,
Ape_Cli varchar(20) not null,
Correo_Cli varchar(30) unique not null,
Contra_Cli varchar(20) not null,
Cel_Cli varchar(15) unique not null
);

insert into cliente values
('00000000', 'Nombre', 'Apellido', 'correo@email.com', '000', '111111111'),
('72673554', 'Milhos', 'Sihuay', 'mi@g.com', '123', '997653086' ),
('70829460', 'Luiggi', 'Rebatta', 'lu@g.com', '123', '969599087' ),
('12345677', 'Marcelo', 'Yabar', 'ma@g.com', '123', '37373732' ),
('72647015', 'Michell', 'Del Pino', 'mi_dp@g.com', '123', '913428693');


create procedure sp_ListarCLI()#--------
select * from Cliente;

create procedure sp_InsertarCLI(#--------
Dni char(8) ,
Nombre varchar(20),
Apellido varchar(20) ,
Correo varchar(30) ,
Contrasena varchar(20),
Celular varchar(10)
)
insert into Cliente values(Dni,Nombre,Apellido,Correo,Contrasena,Celular);

create procedure sp_EliminarCLI(#---------------
Dni char(8))
delete from Cliente where Dni_Cli=Dni;

create procedure sp_ConsultarCLI(
Correo varchar(30),
Pass varchar(20))
select * from Cliente where Correo_Cli = Correo and Contra_Cli = Pass;

create procedure sp_EditarPassCLI(#-----------------------
Dni char(8) ,
Contra varchar(20))
update Cliente set Contra_Cli=Contra where Dni_Cli=Dni;


create procedure sp_ConsultarDniCLI(#-------------------------
Dni char(8))
select * from Cliente where Dni_Cli=Dni;

create procedure sp_ConsultarCorreoCLI(#-------------------------
Correo char(20))
select * from Cliente where Correo_Cli=Correo;

#-------------------------ADMIN--------
create table Admin(
Dni_Adm char(8) primary key,
Nomb_Adm varchar(20) not null,
Ape_Adm varchar(20) not null,
Correo_Adm varchar(30) unique not null,
Contra_Adm varchar(20) not null,
Cel_Admin varchar(15) unique not null
);

insert into admin values
('72673554', 'Milhos', 'Sihuay', 'mi_adm@g.com', '123', '997653086' ),
('70829460', 'Luiggi', 'Rebatta', 'lu_adm@g.com', '123', '969599087' ),
('12345677', 'Marcelo', 'Yabar', 'ma_adm@g.com', '123', '37373732' );

create procedure sp_ConsultarADM(
Correo varchar(30),
Pass varchar(20))
select * from Admin where Correo_Adm = Correo and Contra_Adm = Pass;

create procedure sp_ConsultarDniADM(#-------------------------
Dni char(9))
select * from Admin where Dni_Adm=Dni;

create procedure sp_ConsultarCorreoADM(#-------------------------
Correo char(20))
select * from Admin where Correo_Adm=Correo;


#----------------TABLA FECHA------
create table tb_FECHA(
fecha char(10) primary KEY,
Day_name char(15) default 'N' null,
day_numb int default 0 NOT null,
mes_numb int default 0 NOT null,
anio_numb int default 0 NOT null
);


#------------TABLA RESERVA------------

create table reserva(
id int  auto_increment primary key,
fecha_rsv char(10), #'2023-01-01'
HORA3 boolean default 0 not null,
HORA5 boolean default 0 not null,
HORA7 boolean default 0 not null,
DNI_H3 char(8) ,
DNI_H5 char(8),
DNI_H7 char(8),
foreign key(fecha_rsv) references tb_fecha(fecha)
on update cascade on delete cascade,
foreign key(dni_h3) references cliente(dni_cli)
on update cascade,
foreign key(dni_h5) references cliente(dni_cli)
on update cascade,
foreign key(dni_h7) references cliente(dni_cli)
on update cascade
);
use app_losjardines;
select * from reserva;
#-------TRIGGER CREAR_RESERVA
DELIMITER //
CREATE TRIGGER crear_reserva
AFTER INSERT ON tb_fecha
FOR EACH ROW
BEGIN
	DECLARE valor char(10);
    set valor = new.fecha;
    INSERT INTO reserva (fecha_rsv) VALUES (valor);
END//
DELIMITER ;

drop table tb_fecha;
drop table reserva;
select * from tb_fecha;

#--------------PROCEDURE INSERTAR_FECHAS

DELIMITER //
CREATE PROCEDURE sp_insertar_fechas()
BEGIN
    DECLARE fecha_inicio DATE;
    DECLARE fecha_fin DATE;
    DECLARE fecha_actual DATE;

    SET fecha_inicio = CONCAT(YEAR(CURDATE()), '-01-01');
    SET fecha_fin = CONCAT(YEAR(CURDATE()), '-12-31');
    SET fecha_actual = fecha_inicio;

    WHILE fecha_actual <= fecha_fin DO
        INSERT INTO TB_FECHA  VALUES (fecha_actual, dayname(fecha_actual),day(fecha_actual),month(fecha_actual),year(fecha_actual));
        SET fecha_actual = DATE_ADD(fecha_actual, INTERVAL 1 DAY);
    END WHILE;

    SELECT 'Fechas insertadas correctamente.' AS mensaje;
END //
DELIMITER ;

#---llamar funcion para insertar todas las fechas del año
CALL SP_insertar_fechas();


#----SP TABLA RESERVA--------

DELIMITER //
CREATE PROCEDURE sp_ListarRESERVA_semanal()#---------PROCEDURE
BEGIN
    SELECT *
    FROM RESERVA
    WHERE FECHA_RSV IN (
        DATE_ADD(CURDATE(), INTERVAL 1 DAY),
        DATE_ADD(CURDATE(), INTERVAL 2 DAY),
        DATE_ADD(CURDATE(), INTERVAL 3 DAY),
        DATE_ADD(CURDATE(), INTERVAL 4 DAY),
        DATE_ADD(CURDATE(), INTERVAL 5 DAY),
        DATE_ADD(CURDATE(), INTERVAL 6 DAY)
    );
END//
DELIMITER ;

create procedure sp_ReservarH3(#-----------------------EDIT
dia char(10),
dni char(8))
update Reserva set hora3=1, dni_h3=dni where fecha_rsv=dia;

create procedure sp_ReservarH5(#-----------------------EDIT
dia char(10),
dni char(8))
update Reserva set hora5=1,dni_h5=dni where fecha_rsv=dia;
#call sp_ReservarH5(1,'70829460');

create procedure sp_ReservarH7(#-----------------------EDIT
dia char(10),
dni char(8))
update Reserva set hora7=1, dni_h7=dni where fecha_rsv=dia;

create procedure sp_ConsultarRsvCLI(#-------------------------
Dni char(8))
select * from Reserva where Dni_h3=Dni or dni_h5=Dni or dni_h7=dni;
#call sp_ConsultarRsvCLI('72673554');


create procedure sp_ListarReservasCLI() #--para el admin
select * from Reserva where Dni_h3 is not null or dni_h5 is not null or dni_h7 is not null;

call sp_ListarReservasCLI;

use app_losjardines;
select * from reserva;




