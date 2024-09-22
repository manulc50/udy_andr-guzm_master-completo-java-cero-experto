
-- Datos de prueba para el entorno de Desarrollo
-- Este archivo siempre se tiene que llamar "import.sql" para que Hibernate lo reconozca y pueda realizar los inserts

INSERT INTO clientes (id, nombre, apellido, forma_pago, creado_en, editado_en) VALUES (1,'Andres','Guzman','debito',NULL,NULL),(2,'Jhon','Doe','credito',NULL,NULL),(4,'Pepa','Doe','debito',NULL,NULL),(5,'Lucy','Martinez','paypal',NULL,NULL),(10,'Jhon','Roe','paypal',NULL,NULL),(11,'Lou','Loe','paypal',NULL,NULL),(12,'Lalo','Mena','paypal','2021-10-21 14:24:41','2021-10-21 14:26:43'),(13,'Pia','Perez','debito','2021-10-21 14:42:44','2021-10-21 14:43:31');
INSERT INTO alumnos (id, nombre, apellido) VALUES (1, 'Johana', 'Doe');
INSERT INTO alumnos (id, nombre, apellido) VALUES (2, 'Pepe', 'Gon');
INSERT INTO cursos (id, titulo, profesor) VALUES (1, 'Curso Spring', 'Juan');
INSERT INTO cursos (id, titulo, profesor) VALUES (2, 'Curso React', 'Francisco');
-- No hace fala especificar el id porque se autogenera
INSERT INTO direcciones (calle, numero) VALUES ('Vaticano', 123);
INSERT INTO direcciones (calle, numero) VALUES ('Colon', 456);
INSERT INTO tbl_clientes_direcciones (id_cliente, id_direccion) VALUES (1, 1);
INSERT INTO tbl_clientes_direcciones (id_cliente, id_direccion) VALUES (1, 2);
INSERT INTO clientes_detalles (prime, puntos_acumulados, cliente_detalle_id) VALUES (1, 8000, 1);
INSERT INTO tbl_alumnos_cursos (id_alumno, id_curso) VALUES (1, 1);
INSERT INTO tbl_alumnos_cursos (id_alumno, id_curso) VALUES (1, 2);
INSERT INTO facturas (descripcion, total, id_cliente) VALUES ('Oficina', 4000, 1);
INSERT INTO facturas (descripcion, total, id_cliente) VALUES ('Casa', 2000, 1);
INSERT INTO facturas (descripcion, total, id_cliente) VALUES ('Deporte', 3000, 1);
INSERT INTO facturas (descripcion, total, id_cliente) VALUES ('Computacion', 1000, 2);