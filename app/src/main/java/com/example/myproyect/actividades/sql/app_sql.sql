#drop database app_losjardines2;
create database if not exists app_losjardines2;
use app_losjardines2;
SELECT NOW() AS fecha_hora_actual;
SET lc_time_names = 'es_ES'; #CAMBIAR A ESPAÑOL EL IDIOMA

create table Cliente(
Dni_Cli char(8) primary key,
Nomb_Cli varchar(20) not null,
Ape_Cli varchar(20) not null,
Correo_Cli varchar(30) unique not null,
Contra_Cli varchar(20) not null,
Cel_Cli varchar(15) unique not null
);

insert into cliente values
('72673554', 'Milhos', 'Sihuay', 'mi@g.com', '123', '997653086' ),
('70829460', 'Luiggi', 'Rebatta', 'lu@g.com', '123', '969599087' ),
('12345677', 'Marcelo', 'Yabar', 'ma@g.com', '123', '986389628' ),
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
('12345677', 'Marcelo', 'Yabar', 'ma_adm@g.com', '123', '986389628' );

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



#------------TABLA RESERVAS------------

create table reserva_losa1(
id int  auto_increment primary key,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
id_losa int,
foreign key(3pm) references cliente(dni_cli),
foreign key(5pm) references cliente(dni_cli),
foreign key(7pm) references cliente(dni_cli)
);


create table reserva_losa2(
id int  auto_increment primary key,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
id_losa int,
foreign key(3pm) references cliente(dni_cli),
foreign key(5pm) references cliente(dni_cli),
foreign key(7pm) references cliente(dni_cli)
);


create table reserva_losa3(
id int  auto_increment primary key,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
id_losa int,
foreign key(3pm) references cliente(dni_cli),
foreign key(5pm) references cliente(dni_cli),
foreign key(7pm) references cliente(dni_cli)
);


create table reserva_losa4(
id int  auto_increment primary key,
fecha_rsv char(10) unique, #'2023-01-01'
3pm char(8),
5pm char(8),
7pm char(8),
id_losa int,
foreign key(3pm) references cliente(dni_cli),
foreign key(5pm) references cliente(dni_cli),
foreign key(7pm) references cliente(dni_cli)
);


#----SP TABLA RESERVA--------
DELIMITER //
CREATE PROCEDURE LLenarTablaReservas(in id_cancha int)
BEGIN
    DECLARE fecha_inicio DATE;
    DECLARE fecha_fin DATE;
    DECLARE fecha_actual DATE;

    SET fecha_inicio = CONCAT(YEAR(CURDATE()), '-01-01');
    SET fecha_fin = CONCAT(YEAR(CURDATE()), '-12-31');
    SET fecha_actual = fecha_inicio;

    WHILE fecha_actual <= fecha_fin DO
		CASE id_cancha
			when 1 then
				INSERT INTO reserva_losa1 (fecha_rsv, id_losa)  VALUES (fecha_actual,id_cancha);
			when 2 then
				INSERT INTO reserva_losa2 (fecha_rsv, id_losa) VALUES (fecha_actual,id_cancha);
			when 3 then
                INSERT INTO reserva_losa3 (fecha_rsv, id_losa)  VALUES (fecha_actual,id_cancha);
			when 4 then
                INSERT INTO reserva_losa4 (fecha_rsv, id_losa)  VALUES (fecha_actual,id_cancha);
		END CASE;
        SET fecha_actual = DATE_ADD(fecha_actual, INTERVAL 1 DAY);
    END WHILE;

    SELECT 'Tablas llenadas correctamente.' AS mensaje;
END //
DELIMITER ;

call LLenarTablaReservas(1);
call LLenarTablaReservas(2);
call LLenarTablaReservas(3);
call LLenarTablaReservas(4);


###		LISTAR RESERVAS SEMANALES O EN UN RANGO DETERMINADO ###
DELIMITER //
CREATE PROCEDURE sp_ListarRsv(IN tabla varchar(50), in dia_min int, in dia_max int)
BEGIN
	SET @query = CONCAT('SELECT * FROM ',tabla, ' WHERE id between ', dia_min, ' and ', dia_max);
	PREPARE stmt FROM @query;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

### REALIZAR UNA RESERVA ### -> CLIENTE COMPRA
DELIMITER //
CREATE PROCEDURE sp_RESERVAR(IN tabla varchar(50), IN dia CHAR(10),IN hora char(5), IN dni_user CHAR(8) )
BEGIN
	SET @query =
		CONCAT('UPDATE ', tabla, ' SET ', hora, '=\'', dni_user, '\' WHERE fecha_rsv=\'', dia, '\'');
	PREPARE stmt FROM @query;
	EXECUTE stmt;
	DEALLOCATE PREPARE stmt;
	SELECT 'Reserva insertada correctamente.' AS mensaje;
END //
DELIMITER ;


### LISTAR RESERVAS INDIVIDUAL POR CLIENTE ###
DELIMITER //
CREATE PROCEDURE sp_ConsultarRsvCLI(IN tabla VARCHAR(30),IN dni INT )
BEGIN
    SET @sql = CONCAT('SELECT *
                       FROM ', tabla, '
                       WHERE 3pm = ? OR 5pm= ? OR 7pm = ?');
    PREPARE stmt FROM @sql;
    EXECUTE stmt USING @dni, @dni, @dni;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;


### LISTAR TODAS LAS RESERVAS COMPRADAS ### -> PARA EL ADMINISTRADOR
DELIMITER //
CREATE PROCEDURE sp_ListarReservasCLI(IN tabla VARCHAR(30))
BEGIN
    SET @sql = CONCAT('SELECT *
                       FROM ', tabla, '
                       WHERE
                       3pm IS NOT NULL OR
                       5pm IS NOT NULL OR
                       7pm IS NOT NULL');
    PREPARE stmt FROM @sql;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
END //
DELIMITER ;

##############<----------------->###############






