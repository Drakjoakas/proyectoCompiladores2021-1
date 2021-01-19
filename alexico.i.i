import java.lang.*;
import java.io.*;
/* 
Arjona Méndez Albarrán Sebastián
Num cuenta:
Gutiérrez Chávez Sergio Daniel  
Num cuenta: 315689763
Sandoval Miramontes Joaquín
Num cuenta: 418049060
Mekibes Meza Malik
Num cuenta: 315206447
*/
%%

%line
%column
%unicode
%int
%eof{ //cerrando el filewriter y el printwriter
	try{
			this.fichero.close();
			this.pw.close();
		}catch (Exception e) {
			System.out.println("Ocurrio un error");	
		}
	
%eof}

%{
	FileWriter fichero;
	PrintWriter pw;
	
	
	public static void main (String args[]){
		String sFichero = "output.tokens";
		try{
			Yylex lexer = new Yylex(new BufferedReader(new FileReader(new File(args[0])))); //args[0]
			lexer.fichero = new FileWriter(sFichero);
			lexer.pw =  new PrintWriter(sFichero);
			lexer.yylex();
		}catch(Exception e){
			System.out.println("Ocurrio un error al intentar abrir el archivo ");
		 }
	}
%}

//Expresiones Regulares
espacio = [ \t\n\r]+
id = [a-zA-Z\_]([a-zA-Z] | "_" | {numero})*
numero = [0-9]
Exponente = [eE] [\+\-]? {numero}+
Entero = {numero}+ | {numero}+("_"{numero}+)* 
Deci1 = {Entero}\. {Entero} {Exponente}?
Deci2 = \.{Entero}{Exponente}?
Deci3 = {Entero}\. {Exponente}*
Deci4 = {Entero} {Exponente}
Decimal = {Deci1} | {Deci2} | {Deci3} | {Deci4}
Simbolos = [!”#$%&/()=?¡@¬|°´+{}"[""]"\-.,;:_\^`~<>'¿\*¨] 
Contenido = ([a-zA-Z] | {numero} | {espacio} | {Simbolos})* 
Cadena = \"{Contenido}\" | \'{Contenido}\' 
OpAritmetico = "++" | "--" 
OpLogicos = < | > 
OpAsignacion = "=" 
ParentesisAbre = "("
ParentesisCierra = ")"
CorcheteAbre = "["
CorcheteCierra = "]"
LlaveAbre = "{"
LlaveCierra= "}"
Amperson = "&"
DosPuntos = ":"
PuntoYComa = ";"
Coma = ","
Or = "||"
And = "&&"
MayorIgual = ">="
MenorIgual = "<="
Diferente = "!="
Igual = "=="
Negado = "!"
Mas = \+
Menos = -
Modulo = %
Division = "/"
Multiplicacion = \*
int1 = "int"
float1 = "float"
char1 = "char"
double1 = "double"
void1 = "void"
func = "func"
if1 = "if"
else1 = "else"
while1 = "while"
do1 = "do"
break1 = "break"
switch1 = "switch"
case1 = "case"
default1 = "default"
true1 = "true"
false1 = "false"

%%
//Estados para el análisis léxico

<YYINITIAL>{  
  {int1}  { 	
						this.pw.println("<int,"+yytext()+">");
												System.out.println("1");
												return 1;
								}
  {float1} { 	
						this.pw.println("<float,"+yytext()+">");
												System.out.println("2");
												return 2;
								}

  {char1} { 	
						this.pw.println("<char,"+yytext()+">");
												System.out.println("3");
												return 3;
								}
								
  {double1} { 	
						this.pw.println("<double,"+yytext()+">");
												System.out.println("4");
												return 4;
								}
								
  {void1} { 	
						this.pw.println("<void,"+yytext()+">");
												System.out.println("5");
												return 5;
								}		
								
  {func} { 	
						this.pw.println("<func,"+yytext()+">");
												System.out.println("6");
												return 6;
								}

  {if1} { 	
						this.pw.println("<if,"+yytext()+">");
												System.out.println("7");
												return 7;
								}

  {else1} { 	
						this.pw.println("<else,"+yytext()+">");
												System.out.println("8");
												return 8;
								}

  {while1} { 	
						this.pw.println("<while,"+yytext()+">");
												System.out.println("9");
												return 9;
								}								

  {do1} { 	
						this.pw.println("<do,"+yytext()+">");
												System.out.println("10");
												return 10;
								}								

  {break1} { 	
						this.pw.println("<break,"+yytext()+">");
												System.out.println("11");
												return 11;
								}					

  {switch1} { 	
						this.pw.println("<switch,"+yytext()+">");
												System.out.println("12");
												return 12;
								}

  {case1} { 	
						this.pw.println("<case,"+yytext()+">");
												System.out.println("13");
												return 13;
								}

  {default1} { 	
						this.pw.println("<default,"+yytext()+">");
												System.out.println("14");
												return 14;
								}	

  {true1} { 	
						this.pw.println("<true,"+yytext()+">");
												System.out.println("15");
												return 15;
								}	

  {false1} { 	
						this.pw.println("<false,"+yytext()+">");
												System.out.println("16");
												return 16;
								}
  {id} { 	
												this.pw.println("<id,"+yytext()+">");
												System.out.println("17");
												return 17;
								}								
//estado que ignora los espacios y tabuladores
{espacio} {}
					
//estado que reconoce los imaginarios
 ({Decimal}"i" | {Entero}"i") {
												this.pw.println("<Imaginario,"+yytext()+">");
												System.out.println("20");
												return 20;
								}
//estado que reconoce los decimales
 {Decimal} {							
												this.pw.println("<Decimal,"+yytext()+">");
												System.out.println("19");
												return 19;
								}
//estado que reconoce los enteros
 {Entero}  {							
								
												this.pw.println("<Entero,"+yytext()+">");
												System.out.println("18");
												return 18;
												 
								}		

//estado que reconoce cadenas
 {Cadena} {
												this.pw.println("<Cadena,"+yytext()+">");
												System.out.println("21");
												return 21;
								}
//estado que reconoce Operadores
 {Amperson} {
												this.pw.println("<Simbolo,"+yytext()+">");
												System.out.println("22");
												return 22;
								}
//estado que reconoce Operadores de Asignación
 {OpAsignacion } {
												this.pw.println("<OpAsignacion,"+yytext()+">");
												System.out.println("23");
												return 23;
								}
//estado que reconoce Operadores Aritméticos
 {OpAritmetico} {
												this.pw.println("<OpAritmetico,"+yytext()+">");
												System.out.println("24");
												return 24;
								}
//estado que reconoce Operadores Lógicos
 {OpLogicos} {
												this.pw.println("<OpLogico,"+yytext()+">");
												System.out.println("25");
												return 25;
												
								}

 {ParentesisAbre} {
												this.pw.println("<PAbre,"+yytext()+">");
												System.out.println("26");
												return 26;
												
								}
 {ParentesisCierra} {
												this.pw.println("<PCierra,"+yytext()+">");
												System.out.println("27");
												return 27;
												
								}
 {CorcheteAbre} {
												this.pw.println("<CAbre,"+yytext()+">");
												System.out.println("28");
												return 28;
												
								}
 {CorcheteCierra} {
												this.pw.println("<CCierra,"+yytext()+">");
												System.out.println("29");
												return 29;
												
								}

 {LlaveAbre} {
												this.pw.println("<LLaveAbre,"+yytext()+">");
												System.out.println("30");
												return 30;
												
								}
 {LlaveCierra} {
												this.pw.println("<LlaveCierra,"+yytext()+">");
												System.out.println("31");
												return 31;
												

											}

 {DosPuntos}	{
												this.pw.println("<DosPuntos,"+yytext()+">");
												System.out.println("32");
												return 32;

												}											
												
 {PuntoYComa}	{
												this.pw.println("<PuntoYComa,"+yytext()+">");
												System.out.println("33");
												return 33;

											}
											
 {Coma}	{
												this.pw.println("<Coma,"+yytext()+">");
												System.out.println("34");
												return 34;

											}
 {Or}	{
												this.pw.println("<Or,"+yytext()+">");
												System.out.println("35");
												return 35;

											}
 {And}	{
												this.pw.println("<And,"+yytext()+">");
												System.out.println("36");
												return 36;

											}	
 {MayorIgual}	{
												this.pw.println("<MayorIgual,"+yytext()+">");
												System.out.println("37");
												return 37;

											}
 {MenorIgual}	{
												this.pw.println("<MenorIgual,"+yytext()+">");
												System.out.println("38");
												return 38;

											}
 {Diferente}	{
												this.pw.println("<Diferente,"+yytext()+">");
												System.out.println("39");
												return 39;

											}
 {Igual}	{
												this.pw.println("<Igual,"+yytext()+">");
												System.out.println("40");
												return 40;

											}	
 {Negado}	{
												this.pw.println("<Negado,"+yytext()+">");
												System.out.println("41");
												return 41;

											}												
 {Mas}	{
												this.pw.println("<Mas,"+yytext()+">");
												System.out.println("42");
												return 42;

											}
 {Menos}	{
												this.pw.println("<Menos,"+yytext()+">");
												System.out.println("43");
												return 43;

											}
 {Multiplicacion}	{
												this.pw.println("<Multiplicacion,"+yytext()+">");
												System.out.println("44");
												return 44;

											}
											
 {Modulo}	{
												this.pw.println("<Modulo,"+yytext()+">");
												System.out.println("45");
												return 45;

											}
											
 {Division}	{
												this.pw.println("<Division,"+yytext()+">");
												System.out.println("46");
												return 46;

											}
 . {System.out.println("La cadena no es reconocida");}
}				