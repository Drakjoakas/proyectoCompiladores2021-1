public class ParserException extends Exception {
  
  String errorString;
  
  public ParserException(String _error){
    super(_error);
    errorString = _error;
  }
}
