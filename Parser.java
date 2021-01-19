import java.util.Stack;
import java.util.*;


public class Parser{
	static final int INT = 1;
	static final int FLOAT = 2;
	static final int CHAR = 3;
	static final int DOUBLE = 4;
	static final int VOID = 5;
	static final int FUNC = 6;
	static final int IF = 7;
	static final int ELSE = 8;
	static final int WHILE = 9;
	static final int DO = 10;
	static final int BREAK = 11;
	static final int SWITCH = 12;
	static final int CASE = 13;
	static final int DEFAULT = 14;
	static final int TRUE = 15;
	static final int FALSE = 16;
	static final int ID = 17;
	static final int ENTERO = 18;
	static final int DECIMAL = 19;
	static final int IMAGINARIO = 20;
	static final int CADENA = 21;
	static final int OPERADORES = 22;
	static final int OPASIGNACION = 23;
	static final int OPARITMETICO = 24;
	static final int OPLOGICOS = 25;
	static final int PARIZQ = 26;
	static final int PARDER = 27;
	static final int CORIZQ = 28;
	static final int CORDER = 29;
	static final int LLAIZQ = 30;
	static final int LLADER = 31;
	static final int DOSPUNTOS = 32;
	static final int PUNTOYCOMA = 33;
	static final int COMA = 34;
	static final int OR = 35;
	static final int AND = 36;
	static final int MAYORIGUAL = 37;
	static final int MENORIGUAL = 38;
	static final int DIFERENTE = 39;
	static final int IGUAL = 40;
	static final int NEGADO = 41;
	static final int MAS = 42;
	static final int MENOS = 43;
	static final int MULTIPLICACION = 44;
	static final int MODULO = 45;
	static final int DIVISION = 46;
	int currentToken;
	Yylex lexer;

	/**
	 * _lexer Analizador léxico
	 */
	public Parser(Yylex _lexer){
		this.lexer = _lexer;
	}
	
	/**
	 * Avanza el "currentToken"
	 */
	public void consumir()  {
		try{
			currentToken = lexer.yylex()
		}catch(Exception e){
			System.out.println("ERROR");
		}
		
	}

	public void error() throws ParserException {
		throw new ParserException("La cadena no es aceptada por la gramática");
	}


	//PRODUCCIONES
	void programa() throws ParserException{
		declaraciones();
		funciones();
	}
	
	void declaraciones(){
		tipo();
		lista_var();
		if(currentToken == PUNTOYCOMA){
			consumir();
			declaraciones();
		}else{
			//error
		}
	}
	
	void tipo(){
		basico();
		compuesto();
	}
	
	void basico(){
		switch(currentToken){
			case INT:
				consumir();
				break;
			case FLOAT:
				consumir();
				break;
			case CHAR:
				consumir();
				break;
			case DOUBLE:
				consumir();
				break;
			case VOID:
				consumir();
				break;
			default:
				break;
				//error
		}
	}
	
	void compuesto(){
		if(currentToken == CORIZQ){
			consumir();
			if (currentToken == ENTERO){
				consumir();
				if (currentToken == CORDER){
					consumir();
					compuesto(); 
				}else{
					//error
				}
			}else{
				//error
			}			
		}else{
			//error
		}
		
	}
	
	void lista_var(){
		if(currentToken == ID){
			consumir();
			lista_varprim();
		}else{
			//error
		}
	}
	
	void lista_varprim(){
		if(currentToken == COMA){
			consumir();
			if(currentToken == ID){
				consumir();
				lista_varprim();
			}else{
				//error
			}
		}
	}
	
	void funciones(){
		if(currentToken == FUNC) {
			consumir();
			if (currentToken == ID) {
				consumir();
				if (currentToken == PARIZQ){
					consumir();
					argumentos();
					if (currentToken == PARDER){
						consumir();
						bloque();
						funciones();
					}else{
						//error
					}
				}else{
						//error
				}
			}else{
				//error
			}
		}else{
			//error
		}
	}
	
	void argumentos(){
		lista_args();
	}
	
	void lista_args() {
		
		tipo();
		if (currentToken == ID){
			consumir();
			lista_argsP();
				
		}else {
			//error
			
		}
		
	}
	
	void lista_argsP() {
		if (currentToken == COMA){
			tipo();
			consumir();
			if (currentToken == ID){
				lista_argsP();
				consumir();
			}else{				
				//error
				
			}
			
		}
			
			
	}
	
	void bloque() {
		if (currentToken == LLAIZQ){
			declaraciones();
			instrucciones();
			consumir();
			if (currentToken == LLADER){
				consumir();
			}else{
				//error
			}
			
		}else{
			
			//error
			
		}
	} 
	
	void instrucciones() {
		
		sentencia();
		instruccionesP();
		
	}
	
	void instruccionesP() {
		
		sentencia();
		instruccionesP();
	}
	
	void sentencia() {
		
		switch(currentToken){
			case IF:
				consumir();
				if (currentToken == PARIZQ){
					bool();
					consumir();
					if (currentToken == PARDER){
						sentencia();
						consumir();
						if (currentToken == ELSE){
							sentencia();
							consumir();
						}else{
							//error
						}
					}else{
						//error
					}
				}else{
						//error
				}
				break;
				
			case WHILE:
				if (currentToken == PARIZQ){
					bool();
					consumir();
					
					if (currentToken == PARDER){
						sentencia();
						consumir();
					}else{
						//error
					}
				}else{
						//error
				}
				break;
				
			case DO:
				sentencia();
				consumir();
				if (currentToken == WHILE){
					consumir();
					if (currentToken == PARIZQ){
						bool();
						consumir();
						if(currentToken == PARDER){
							consumir();
							
						}else{
							//error
						}
						
						
					}else{
						//error
					}
					
				}else{
					//error
				}
				break;
				
			case BREAK:
					consumir();
					if(currentToken == PUNTOYCOMA){
						consumir();
					}else{
						//error
					}
					break;
			
			case SWITCH:
					consumir();
					
					if(currentToken == PARIZQ){
						consumir();
						bool();
						
						if (currentToken == PARDER){
							consumir();
							
							if(currentToken == LLAIZQ){
								consumir();
								casos();
								
								if(currentToken == LLADER){
									consumir();
									
								}else{
									//error
								
								}
								
							}else{
								//error
								
							}
							
						}else{
							//error
						}
					}else{
						//error
					}
					break;
					
			default:
					if (currentToken == ID){		//Para el caso de localizacion
						localizacion();
						consumir();
						if(currentToken == OPASIGNACION){
							bool();
						}else{
							//error
						}
					}else if (currentToken == INT || FLOAT || CHAR || DOUBLE || VOID){	//para el caso de bloque
						
						bloque();
						
					}else{
						//error
						
					}
					break;
		}
	}
	
	void casos(){
		caso();
		casos();
		predeterminado();
	}
	
	void caso(){
		if (currentToken == CASE){
			consumir();
			switch(currentToken){
				case ENTERO:
					consumir();
					if (currentToken == OPERADORES){
						consumir();
					}
					break;
				case DECIMAL:
					consumir();
					if (currentToken == OPERADORES){
						consumir();
					}
					break;				
				default:
					//error
					break;
			}
		}else{
			//error
		}
	}
	
	void predeterminado(){
		if (currentToken == DEFAULT){
			consumir();
			if (currentToken == DOSPUNTOS){
				consumir();
				instrucciones();
			}else{
				//error
			}
		}else{
			//error
		}
	}
	
	void bool(){
		comb();
		boolprim();
	}
	
	void boolprim(){
		if (currentToken == OR)
			consumir();
			comb();
			boolprim();
	}
	
	void comb(){
		igualdad();
		combprim();
	}
	
	void combprim(){
		if (currentToken == AND){
			consumir();
			igualdad();
			combprim();
		}
	}


	void igualdad(){
		rel();
		igualdadprim();
	}

	void igualdadprim() {
		if (currentToken ==  (IGUAL || DIFERENTE)) { //Entran tanto == como !=
			consumir();
			rel();
			igualdadprim();
		}
	}

	void rel() {
		exp();
		expprimprim();
	}
	
	void expprimprim() {
		if(currentToken == (OPLOGICOS || MAYORIGUAL || MENORIGUAL)) {
			termprim();
			expprimprim();
		}
	}
	
	void exp() {
		term();
		expprim();
	}

	void expprim(){
			termprim();
			expprim();
		
	}

	void termprim(){
		if( currentToken == (MAS || MENOS)) {
			consumir();
			term();
		}else{
			//error
		}
	}

	void term() {
		unario();
		termprimprim();
	}

	void termprimprim() {
		if( currentToken == (MULTIPLICACION || DIVISION || MODULO)){ //AFRONTA EL OJETE
			unarioprim();
			termprimprim();
		}
	}

	void unarioprim() {
		if( currentToken == MULTIPLICACION || currentToken == DIVISION || currentToken == MODULO) {
			consumir();
			unario();
		}
	}

	void unario() {
		if (currentToken == NEGADO || currentToken == MENOS)) {
			consumir();
			unario();
		} else if (currentToken == PARIZQ || currentToken == ID || currentToken == ENTERO || currentToken == CADENA ||  currentToken == DECIMAL || currentToken == TRUE || currentToken == FALSE){
			factor();
		}else{
			//error
		}
	}

	void factor() {
		if(currentToken == PARIZQ) {
			consumir();
			bool();
			if(currentToken == PARDER){
				consumir();
			}else{
				//error
			}
		} else if (currentToken == ENTERO || currentToken == CADENA || currentToken == TRUE || currentToken == FALSE) {
			consumir();
		} else if (currentToken == ID ){
				consumir();
				if(currentToken == PARIZQ) {
					consumir();
					parametros();
					if (currentToken == PARDER){
						consumir();
					} else {
						//error
					}
				} else {
					localizacion();
				}
		} else {
			//error
		}
	}

	void parametros() {
		if (currentToken == ENTERO || currentToken == CADENA || currentToken == TRUE || currentToken == FALSE || currentToken == PARIZQ || currentToken == ID) {
			lista_param();
		}
	}

	void lista_param() {
		bool();
		lista_paramprim();
	}

	void lista_paramprim() {
		if (currentToken == COMA) {
			consumir();
			bool();
			lista_paramprim();
		}
	}

	void localizacion() {
		if(currentToken == ID) {
			consumir();
			localizacionprim();
		} else {
			//error
		}
	}

	void localizacionprim() {
		if(currentToken == CORIZQ) {
			consumir();
			bool();
			if(currentToken == CORDER) {
				consumir();
				localizacionprim();
			}else{
				//error
			}
		}else{
			//error
		}
	}
}
