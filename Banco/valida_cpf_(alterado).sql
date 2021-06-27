/*VALIDA CPF*/

CREATE OR REPLACE FUNCTION VALIDA_CPF ( CPF NUMBER )
RETURN NUMBER IS
  CPF_TRATADO CHAR(11);
  SOMA        NUMBER(5) := 0;
  RESTO       NUMBER(2) := 0;
BEGIN

    IF LENGTH(CPF) < 9 OR LENGTH(CPF) > 11 THEN
        RETURN 1;
    END IF;
	
    CPF_TRATADO := LPAD( CPF, 11, 0 );
    
    FOR I IN 1 .. 9 LOOP
        SOMA := SOMA + ( SUBSTR(CPF_TRATADO, I, 1) * I );
	END LOOP;
	
    RESTO := MOD(SOMA,11);

    IF RESTO = 10
      THEN RESTO := 0;
    END IF;
	
    IF RESTO != SUBSTR(CPF_TRATADO,10,1) THEN
	 	RETURN 1;
    END IF;

	SOMA := 0; 
    FOR I IN 2 .. 10 LOOP
        SOMA := SOMA + ( SUBSTR(CPF_TRATADO, I, 1) * (I-1) );
	END LOOP;
	
    RESTO := MOD(SOMA,11);

    IF RESTO = 10
      THEN RESTO := 0;
    END IF;
	
    IF RESTO != SUBSTR(CPF_TRATADO,11,1) THEN
	 	RETURN 1;
    END IF;
	
  RETURN 0;
END;
/

