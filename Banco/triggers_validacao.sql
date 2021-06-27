/*TRIGGERS DE VALIDACAO -- EMAIL, CPF*/

/*VALIDA EMAIL*/
CREATE OR REPLACE TRIGGER EMAIL_TRG
BEFORE INSERT OR UPDATE OF EMAIL ON USUARIO
FOR EACH ROW
BEGIN
	IF VALIDA_EMAIL(:NEW.EMAIL) = 1 THEN 
		RAISE_APPLICATION_ERROR(-20001, 'Email Invalido!');
	END IF;
END;
/

/*VALIDA CPF*/
CREATE OR REPLACE TRIGGER CPF_TRG
BEFORE INSERT OR UPDATE OF CPF ON USUARIO
FOR EACH ROW 
BEGIN
	IF VALIDA_CPF(:NEW.CPF) = 1 THEN
		RAISE_APPLICATION_ERROR(-20001, 'CPF Invalido!');
	END IF;
END;
/