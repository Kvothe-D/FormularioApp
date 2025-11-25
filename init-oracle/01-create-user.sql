-- Este script se ejecuta automáticamente cuando Oracle se inicia por primera vez
-- Conectar al PDB
ALTER SESSION SET CONTAINER=FREEPDB1;

-- Crear el usuario daniel si no existe 
DECLARE
  user_exists NUMBER;
BEGIN
  SELECT COUNT(*) INTO user_exists
  FROM ALL_USERS
  WHERE USERNAME = 'DANIEL';
  
  IF user_exists = 0 THEN
    EXECUTE IMMEDIATE 'CREATE USER daniel IDENTIFIED BY daniel12';
    DBMS_OUTPUT.PUT_LINE('Usuario daniel creado exitosamente');
  ELSE
    DBMS_OUTPUT.PUT_LINE('Usuario daniel ya existe');
  END IF;
END;
/

-- Permisos necesarios al usuario
GRANT CONNECT, RESOURCE TO daniel;
GRANT UNLIMITED TABLESPACE TO daniel;
GRANT CREATE SESSION TO daniel;
GRANT CREATE TABLE TO daniel;
GRANT CREATE SEQUENCE TO daniel;

-- Confirmar que el usuario fue creado
SELECT 'Usuario daniel configurado exitosamente' AS mensaje FROM DUAL;