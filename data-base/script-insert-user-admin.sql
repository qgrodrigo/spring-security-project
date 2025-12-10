
-- insertar el primer usuario admin
use security_db;

INSERT INTO personal (
    nombre,
    apellido_paterno,
    apellido_materno,
    dni,
    celular,
    correo,
    urlimg,
    fecha_creacion
) VALUES (
    'Rodolfo',
    'García',
    'Pérez',
    '12345678',
    '987654321',
    'rodrigo.quispe.gil@gmail.com', -- se tiene que ingresar correo válido para recibir el token
    'https://example.com/img/rodrigo.jpg',
    NOW()
);

INSERT INTO rol (nombre) 
VALUES (
'ADMIN'
);

INSERT INTO rol (nombre) 
VALUES (
'GERENTE'
);

INSERT INTO rol (nombre) 
VALUES (
'ALMACEN'
);

INSERT INTO rol (nombre) 
VALUES (
'BASIC'
);
INSERT INTO usuario (idpersonal, idrol, usuario, contraseña, estado, intentos_fallidos)
VALUES (
    1, -- id de un registro válido en la tabla personal
    1, -- id del rol ADMIN en la tabla rol
    'Rquispe',
    '$2a$12$UN7rgFfNo34bjyWOMs532.2Qf/CzrJ/EjyobSztsVvrWV47CV3uEq', -- BCrypt hash de "Admin123"
    'ACTIVO',
    0
);

SELECT * FROM personal;
SELECT * FROM producto;

SELECT * FROM usuario;
SELECT * FROM log_auth;
SELECT * FROM log_PRODUCTO;
SELECT * FROM PRODUCTO;
-- SELECT * FROM rol
-- Select * from usuario;