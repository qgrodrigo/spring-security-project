
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
    estado,
    fecha_creacion
) VALUES (
    'Rodrigo',
    'García',
    'Pérez',
    '12345678',
    '987654321',
    'rodrigo@example.com',
    'https://example.com/img/rodrigo.jpg',
    'ACTIVO',
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
INSERT INTO usuario (idpersonal, idrol, usuario, contraseña, estado)
VALUES (
    1, -- id de un registro válido en la tabla personal
    1, -- id del rol ADMIN en la tabla rol
    'Rquispe',
    '$2a$12$UN7rgFfNo34bjyWOMs532.2Qf/CzrJ/EjyobSztsVvrWV47CV3uEq', -- BCrypt hash de "Admin123"
    'ACTIVO'
);

SELECT * FROM personal;

SELECT * FROM usuario
Select * from usuario;