import java.util.Stack;
import java.util.*;

public class Parser {
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
	static final int CADENA = 21;
	static final int AMPERSONN = 22;
	static final int OPASIGNACION = 23;
	static final int MASMAS = 24;
	static final int MENOSMENOS = 49;
	static final int MAYOR = 25;
	static final int MENOR = 48;
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
	static final int RETURN = 47;
	int currentToken;
	Yylex lexer;

	/**
	 * _lexer Analizador léxico
	 */
	public Parser(Yylex _lexer) {
		this.lexer = _lexer;
		try {
			this.currentToken = lexer.yylex().clase;
		} catch (Exception e) {
			System.out.println("error");
		}
	}

	/**
	 * Avanza el "currentToken"
	 */
	public void consumir() {
		try {
			currentToken = lexer.yylex().clase;
		} catch (Exception e) {
			System.out.println("ERROR");
		}

	}

	public void error(String text) throws ParserException {
		throw new ParserException(text + " [" + lexer.yytext() + "]" );
	}

	// PRODUCCIONES
	void programa() throws ParserException {
		declaraciones();
		funciones();
	}

	void declaraciones() throws ParserException {
		if (currentToken == INT || currentToken == FLOAT || currentToken == CHAR || currentToken == DOUBLE || currentToken == VOID) {
			tipo();
			lista_var();
			if (currentToken == PUNTOYCOMA) {
				consumir();
				declaraciones();
			} else {
				error("Declaración inválida");
			}
		}
	}

	void tipo() throws ParserException {
		basico();
		compuesto();
	}

	void basico() throws ParserException {
		if (currentToken == INT || currentToken == FLOAT || currentToken == CHAR || currentToken == DOUBLE || currentToken == VOID) {
			consumir();
		} else {
			error("Tipo de dato inválido");
		}
	}

	void compuesto() throws ParserException {
		if (currentToken == CORIZQ) {
			consumir();
			if (currentToken == ENTERO) {
				consumir();
				if (currentToken == CORDER) {
					consumir();
					compuesto();
				} else {
					error("Esperaba ]");
				}
			} else {
				error("Número inválido");
			}
		}

	}

	void lista_var() throws ParserException {
		if (currentToken == ID) {
			consumir();
			lista_varP();
		} else {
			error("Identificador inválido 1");
		}
	}

	void lista_varP() throws ParserException {
		if (currentToken == COMA) {
			consumir();
			if (currentToken == ID) {
				consumir();
				lista_varP();
			} else {
				error("Identificador inválido 2");
			}
		}
	}

	void funciones() throws ParserException {
		if (currentToken == FUNC) {
			consumir();
			tipo();
			if (currentToken == ID) {
				consumir();
				if (currentToken == PARIZQ) {
					consumir();
					argumentos();
					if (currentToken == PARDER) {
						consumir();
						bloque();
						funciones();
					} else {
						error("Esperaba )");
					}
				} else {
					error("Esperaba (");
				}
			} else {
				error("Identificador inválido 3");
			}
		}else {
			
		}
	}

	void argumentos() throws ParserException {
		if (currentToken == INT || currentToken == FLOAT || currentToken == CHAR || currentToken == DOUBLE || currentToken == VOID) {
			lista_args();
		}
	}

	void lista_args() throws ParserException {
		tipo();
		if (currentToken == ID) {
			consumir();
			lista_argsP();
		} else {
			error("Identificador inválido 4");
		}
	}

	void lista_argsP() throws ParserException {
		if (currentToken == COMA) {
			consumir();
			tipo();
			if (currentToken == ID) {
				consumir();
				lista_argsP();
			} else {
				error("Identificador inválido 5");
			}
		}
	}

	void bloque() throws ParserException {
		
		if (currentToken == LLAIZQ) {
			consumir();
			declaraciones();
			instrucciones();
			if (currentToken == LLADER) {
				consumir();
				
			} else {
				error("Esperaba }");
			}
		} else {
			error("Esperaba {");
		}
	}

	void instrucciones() throws ParserException {
		
		sentencia();
		instruccionesP();
	}

	void instruccionesP() throws ParserException {
		
		switch (currentToken) {
			case ID:
			case IF:
			case WHILE:
			case DO:
			case BREAK:
			case LLAIZQ:
			case RETURN:
			case SWITCH:
			
				sentencia();
				instruccionesP();
				break;
			default:
				
				break;
		}

	}

	void sentencia() throws ParserException {
		
		switch (currentToken) {
			case IF:
				consumir();
				if (currentToken == PARIZQ) {
					consumir();
					bool();
					if (currentToken == PARDER) {
						consumir();
						
						sentencia();
						elseP();
					} else {
						error("Esperaba )");
					}
				} else {
					error("Esperaba (");
				}
				break;

			case WHILE:
				consumir();
				if (currentToken == PARIZQ) {
					consumir();
					bool();
					if (currentToken == PARDER) {
						consumir();
						
						sentencia();
					} else {
						error("Esperaba )");
					}
				} else {
					error("Esperaba (");
				}
				break;

			case DO:
				consumir();
				
				sentencia();
				if (currentToken == WHILE) {
					consumir();
					if (currentToken == PARIZQ) {
						consumir();
						bool();
						if (currentToken == PARDER) {
							consumir();
						} else {
							error("Esperaba )");
						}

					} else {
						error("Esperaba (");
					}

				} else {
					error("Estructura de ciclo inválida");
				}
				break;

			case BREAK:
				consumir();
				if (currentToken == PUNTOYCOMA) {
					consumir();
				} else {
					error("Instrucción incompleta");
				}
				break;

			case SWITCH:
				consumir();
				if (currentToken == PARIZQ) {
					consumir();
					bool();
					if (currentToken == PARDER) {
						consumir();
						if (currentToken == LLAIZQ) {
							consumir();
							casos();
							if (currentToken == LLADER) {
								consumir();
							} else {
								error("Esperaba }");
							}

						} else {
							error("Esperaba {");
						}
					} else {
						error("Esperaba )");
					}
				} else {
					error("Esperaba (");
				}
				break;
			case RETURN:
				
				consumir();
				returnP();
				
				break;
			default:
				if (currentToken == ID) { // Para el caso de localizacion
					localizacion();
					if (currentToken == OPASIGNACION) {
						consumir();
						bool();
						if (currentToken == PUNTOYCOMA) {
							consumir();
						}else{
							error("Se esperaba ;");
						}
					} else {
						error("Caracter inválido 1");
					}
				} else if (currentToken == LLAIZQ) { // para el caso de bloque
					bloque();
				} else {
					error("Caracter inválido 2");

				}
				break;
		}
	}

	private void elseP() throws ParserException {
		if (currentToken == ELSE) {
			consumir();
			
			sentencia();
		}
	}

	private void returnP() throws ParserException {
		
		if (currentToken == PUNTOYCOMA) {
			consumir();
		} else if (currentToken == PARIZQ || currentToken == ID || currentToken == ENTERO || currentToken == CADENA || currentToken == DECIMAL || currentToken == TRUE || currentToken == FALSE || currentToken == NEGADO || currentToken == MENOS) {
			
			exp();
			if(currentToken == PUNTOYCOMA){
				
				consumir();
			} else {
				error("Se esperaba ;");
			}
		}else{
			error("Identificador inválido");
		}
	}

	void casos() throws ParserException {
		if (currentToken == CASE) {
			caso();
			casos();
		} else if (currentToken == DEFAULT) {
			predeterminado();
		} else {
			// ERROR
		}
	}

	void caso() throws ParserException {
		if (currentToken == CASE) {
			consumir();
			switch (currentToken) {
				case ENTERO:
					consumir();
					if (currentToken == DOSPUNTOS) {
						consumir();
					}
					break;
				case DECIMAL:
					consumir();
					if (currentToken == DOSPUNTOS) {
						consumir();
					}
					break;
				default:
					error("Caracter inválido 3");
					break;
			}
		} else {
			error("Caracter inválido 4");
		}
	}

	void predeterminado() throws ParserException {
		if (currentToken == DEFAULT) {
			consumir();
			if (currentToken == DOSPUNTOS) {
				consumir();
				instrucciones();
			} else {
				error("Caracter inválido 5");
			}
		} else {
			error("Caracter inválido 6");
		}
	}

	void bool() throws ParserException {
		comb();
		boolP();
	}

	void boolP() throws ParserException {
		if (currentToken == OR) {
			consumir();
			comb();
			boolP();
		}
	}

	void comb() throws ParserException {
		igualdad();
		combP();
	}

	void combP() throws ParserException {
		if (currentToken == AND) {
			consumir();
			igualdad();
			combP();
		}
	}

	void igualdad() throws ParserException {
		rel();
		igualdadP();
	}

	void igualdadP() throws ParserException {
		if (currentToken == IGUAL || currentToken == DIFERENTE) { // Entran tanto == como !=
			consumir();
			rel();
			igualdadP();
		}
	}

	void rel() throws ParserException {
		exp();
		expPP();
	}

	void expPP() throws ParserException {
		if (currentToken == MAYOR || currentToken == MENOR || currentToken == MAYORIGUAL || currentToken == MENORIGUAL) {
			consumir();
			exp();
		}
	}

	void exp() throws ParserException {
		term();
		expP();
	}

	void expP() throws ParserException {
		if (currentToken == MAS || currentToken == MENOS) {
			termP();
			expP();
		}

	}

	void termP() throws ParserException {
		if (currentToken == MAS || currentToken == MENOS) {
			consumir();
			term();
		} else {
			error("Caracter inválido 7");
		}
	}

	void term() throws ParserException {
		unario();
		termPP();
	}

	void termPP() throws ParserException {
		if (currentToken == MULTIPLICACION || currentToken == DIVISION || currentToken == MODULO) { // AFRONTA EL OJETE
			unarioP();
			termPP();
		}
	}

	void unarioP() throws ParserException {
		if (currentToken == MULTIPLICACION || currentToken == DIVISION || currentToken == MODULO) {
			consumir();
			unario();
		}
	}

	void unario() throws ParserException {
		if (currentToken == NEGADO || currentToken == MENOS) {
			consumir();
			unario();
		} else if (currentToken == PARIZQ || currentToken == ID || currentToken == ENTERO || currentToken == CADENA || currentToken == DECIMAL || currentToken == TRUE || currentToken == FALSE) {
			
			factor();
		} else {
			error("Caracter inválido 8");
		}
	}

	void factor() throws ParserException {
		if (currentToken == PARIZQ) {
			consumir();
			bool();
			if (currentToken == PARDER) {
				consumir();
			} else {
				error("Caracter inválido 9");
			}
		} else if (currentToken == ENTERO || currentToken == DECIMAL || currentToken == CADENA || currentToken == TRUE || currentToken == FALSE) {
			consumir();
		} else if (currentToken == ID) {
			consumir();
			if (currentToken == PARIZQ) {
				consumir();
				parametros();
				if (currentToken == PARDER) {
					consumir();
				} else {
					error("Esperaba )");
				}
			} else {

				localizacionP();
			}
		} else {
			error("Identificador inválido 6");
		}
	}

	void parametros() throws ParserException {
		if (currentToken == ENTERO || currentToken == CADENA || currentToken == TRUE || currentToken == FALSE || currentToken == PARIZQ || currentToken == ID) {
			lista_param();
		}
	}

	void lista_param() throws ParserException {
		bool();
		lista_paramP();
	}

	void lista_paramP() throws ParserException {
		if (currentToken == COMA) {
			consumir();
			bool();
			lista_paramP();
		}
	}

	void localizacion() throws ParserException {
		if (currentToken == ID) {
			consumir();
			localizacionP();
		} else {
			error("Identificador inválido 7");
		}
	}

	void localizacionP() throws ParserException {
		if (currentToken == CORIZQ) {
			consumir();
			bool();
			if (currentToken == CORDER) {
				consumir();
				localizacionP();
			} else {
				error("Esperaba ]");
			}
		}else {
			
		}
	}
}
