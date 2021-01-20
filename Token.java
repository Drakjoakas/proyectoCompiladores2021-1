public class Token {
//INT 0
//FLOAT 1
//STRING 2
//CHAR 3
//VOID 4

/***
* int tipo : Indica el tipo de dato del token.
* String lexema : La cadena en sí del lexema
* int clase : El identificador único de cada token.
***/
  int tipo;
  String lexema;
  int clase;

//Valores fijos para cada tipo de dato manejado.
  public static int INT = 0;
  public static int FLOAT = 1;
  public static int STRING = 2;
  public static int CHAR = 3;
  public static int VOID = 4;

  public Token (int _tipo, String _lexema, int _clase){
    tipo = _tipo;
    lexema = _lexema;
    clase = _clase;
  }

  public Token (int _tipo, String _lexema) {
    tipo = _tipo;
    lexema = _lexema;
  }

  public Token (int _clase) {
    clase = _clase;
  }
}
