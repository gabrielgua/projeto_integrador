/*VALIDA EMAIL*/

/*[:alnum:_.-]+@[:alnum:]+\.[:alnum:]{2,}*/

CREATE OR REPLACE FUNCTION VALIDA_EMAIL(EMAIL VARCHAR)
RETURN NUMBER IS 

BEGIN
	IF REGEXP_INSTR(EMAIL, '^[a-zA-Z0-9_.]+@[a-zA-Z0-9_]+.[a-zA-Z]{2,}$') = 1 THEN
		RETURN 0;
	END IF;
RETURN 1;
END;
/