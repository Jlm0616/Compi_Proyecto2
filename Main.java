import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Uso: java Main <archivo>");
            return;
        }

        // ===== PRIMER LEXER - Para generar tokens.txt (SILENCIADO) =====
        Lexer.silenciarErrores = true;
        Lexer lexerTokens = new Lexer(new FileReader(args[0]));
        PrintWriter tokenWriter = new PrintWriter(new FileWriter("tokens.txt"));
        tokenWriter.println("=== TOKENS ENCONTRADOS ===\n");
        tokenWriter.println(String.format("%-25s %-20s %s", "Token", "Lexema", "Linea"));
        tokenWriter.println(String.format("%-25s %-20s %s", "-----", "------", "-----"));

        java_cup.runtime.Symbol token;
        while ((token = lexerTokens.next_token()).sym != sym.EOF) {
            String nombreToken = sym.terminalNames[token.sym];
            String valor = token.value != null ? token.value.toString() : "";
            int linea = token.left;
            tokenWriter.println(String.format("%-25s %-20s %d", nombreToken, valor, linea));
        }

        tokenWriter.println("\n=== FIN DEL ANALISIS ===");
        tokenWriter.close();
        System.out.println("Tokens guardados en: tokens.txt");

        // ===== RESETEAR CONTADORES Y REACTIVAR ERRORES =====
        Lexer.erroresLexicos = 0;
        Lexer.silenciarErrores = false;

        // ===== SEGUNDO LEXER - Para el parser (MUESTRA ERRORES) =====
        Lexer lexerParser = new Lexer(new FileReader(args[0]));
        
        @SuppressWarnings("deprecation")
        parser p = new parser(lexerParser);
        p.parse();
        
        if (parser.erroresSintacticos == 0 && Lexer.erroresLexicos == 0) {
            System.out.println("Analisis sintactico: EXITOSO");
        } else {
            System.out.println("Analisis sintactico: FALLIDO (" + parser.erroresSintacticos + " error(es) sintactico(s), " + Lexer.erroresLexicos + " error(es) lexico(s))");
        }
    }
}