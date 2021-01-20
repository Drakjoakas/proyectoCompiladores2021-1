import java.io.*;

public class Main {
	public static void main(String args[])throws IOException{
		Yylex lexer;
		Parser parser;
		File f = new File(args[0]);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		lexer = new Yylex(br);
		parser = new Parser(lexer);
		
		try {
			parser.programa();
			if(parser.currentToken == 0) {
				System.out.println("Cadena terminada");
				br.close();
				fr.close();
			}else{
				System.out.println("Error");
			}
		}catch(ParserException p){
			System.out.println(p.getMessage() + "~" +Integer.toString(p.getStackTrace()[0].getLineNumber()));
		}
	}
}
