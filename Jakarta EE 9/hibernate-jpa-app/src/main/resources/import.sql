
-- Datos de prueba para el entorno de Desarrollo
-- Este archivo siempre se tiene que llamar "import.sql" para que Hibernate lo reconozca y pueda realizar los inserts

INSERT INTO clientes (id, nombre, apellido, forma_pago, creado_en, editado_en) VALUES (1,'Andres','Guzman','debito',NULL,NULL),(2,'Jhon','Doe','credito',NULL,NULL),(4,'Pepa','Doe','debito',NULL,NULL),(5,'Lucy','Martinez','paypal',NULL,NULL),(10,'Jhon','Roe','paypal',NULL,NULL),(11,'Lou','Loe','paypal',NULL,NULL),(12,'Lalo','Mena','paypal','2021-10-21 14:24:41','2021-10-21 14:26:43'),(13,'Pia','Perez','debito','2021-10-21 14:42:44','2021-10-21 14:43:31');