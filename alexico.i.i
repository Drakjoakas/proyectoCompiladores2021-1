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
%type Token
%eofval{ //cerrando el filewriter y el printwriter
	return new Token(0);
%eofval}

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
MasMas = "++"
MenosMenos = "--"
Mayor = >
Menor = < 
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
return1 = "return"

%%
//'Estados para el análisis léxico

<YYINITIAL>{  
	{int1}  { 	
		System.out.println("1");
		return new Token(1);
	}
 	{float1} { 	
		System.out.println("2");
		return new Token(2);
	}

   	{char1} { 	
		System.out.println("3");
		return new Token(3);
	}
								
   	{double1} { 	
		System.out.println("4");
		return new Token(4);
	}
								
    {void1} { 	
		System.out.println("5");
		return new Token(5);
	}		
								
    {func} { 	
		System.out.println("6");
		return new Token(6);
	}

    {if1} { 	
		System.out.println("7");
		return new Token(7);
	}

    {else1} { 	
		System.out.println("8");
		return new Token(8);
	}

    {while1} { 	
		System.out.println("9");
		return new Token(9);
	}									

    {do1} { 	
		System.out.println("10");
		return new Token(10);
	}								

    {break1} { 	
		System.out.println("11");
		return new Token(11);
	}					

    {switch1} { 	
		System.out.println("12");
		return new Token(12);
	}

    {case1} { 	
		System.out.println("13");
		return new Token(13);
	}

    {default1} { 	
		System.out.println("14");
		return new Token(14);
	}	

    {true1} { 	
		System.out.println("15");
		return new Token(15);
	}	

    {false1} { 	
		System.out.println("16");
		return new Token(16);
	}
	
	{return1}	{
		System.out.println("47");
		return new Token(47);
	}
	
	{id} { 	
		System.out.println("17");
		return new Token(17);
	}								
	
	//estado que ignora los espacios y tabuladores
	{espacio} {}
					
	//estado que reconoce los decimales
 	{Decimal} {							
		System.out.println("19");
		return new Token(1,yytext(),19);
	}
	
	//estado que reconoce los enteros
 	{Entero}  {														
		System.out.println("18");
		return new Token(0,yytext(),18);			
	}		
		
	//estado que reconoce cadenas
 	{Cadena} {
		System.out.println("21");
		return new Token(2,yytext(),21);
	}
	
	//estado que reconoce Operadores
 	{Amperson} {
		System.out.println("22");
		return new Token(22);
	}
	
	//estado que reconoce Operadores de Asignación
 	{OpAsignacion } {
		System.out.println("23");
		return new Token(23);
	}
	
	//estado que reconoce Operadores Aritméticos
 	{MasMas} {
		System.out.println("24");
		return new Token(24);
	}
	
	//estado que reconoce Operadores Lógicos
 	{Mayor} {
		System.out.println("25");
		return new Token(25);
		
	}

 	{ParentesisAbre} {
		System.out.println("26");
		return new Token(26);				
	}

 	{ParentesisCierra} {	
		System.out.println("27");
		return new Token(27);											
	}

 	{CorcheteAbre} {
		System.out.println("28");
		return new Token(28);		
	}

 	{CorcheteCierra} {
		System.out.println("29");
		return new Token(29);		
	}

 	{LlaveAbre} {
		System.out.println("30");
		return new Token(30);
	}

 	{LlaveCierra} {
		System.out.println("31");
		return new Token(31);
	}

	{DosPuntos}	{
		System.out.println("32");
		return new Token(32);

	}											
												
 	{PuntoYComa}	{
		System.out.println("33");
		return new Token(33);
	}
											
 	{Coma}	{
		System.out.println("34");
		return new Token(34);
	}

 	{Or}	{
		System.out.println("35");
		return new Token(35);
	}

 	{And}	{
		System.out.println("36");
		return new Token(36);
	}	

 	{MayorIgual}	{
		System.out.println("37");
		return new Token(37);
	}

 	{MenorIgual}	{
		System.out.println("38");
		return new Token(38);
	}

 	{Diferente}	{
		System.out.println("39");
		return new Token(39);
	}

	{Igual}	{
		System.out.println("40");
		return new Token(40);
	}

 	{Negado}	{
		System.out.println("41");
		return new Token(41);
	}	

 	{Mas}	{
		System.out.println("42");
		return new Token(42);
	}

 	{Menos}	{
		System.out.println("43");
		return new Token(43);
	}

 	{Multiplicacion}	{
		System.out.println("44");
		return new Token(44);
	}
											
 	{Modulo}	{
		System.out.println("45");
		return new Token(45);
	}
											
 	{Division}	{
		System.out.println("46");
		return new Token(46);
	}

 	{Menor} {
		System.out.println("48");
		return new Token(48);				
	}	

 	{MenosMenos} {
		System.out.println("49");
		return new Token(49);
	}																											
 . {System.out.println("La cadena no es reconocida");}
}
