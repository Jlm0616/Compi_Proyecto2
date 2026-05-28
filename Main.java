import java.io.*;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_DARK_BLUE = "\u001B[34;2m";
    public static final String ANSI_RED_BRIGHT = "\u001B[91m";

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

        GeneradorCodigo.iniciar("codigo_intermedio.txt");

        @SuppressWarnings("deprecation")
        parser p = new parser(lexerParser);
        p.parse();

        GeneradorCodigo.cerrar();

        System.out.println();
        System.out.println("\n=== ANALISIS DE COMPILACION ===" + " (Archivo: " + args[0] + ")");

        String lexicoMsg = (Lexer.erroresLexicos == 0) ? ANSI_GREEN + "Analisis lexico:     EXITOSO" : ANSI_RED + "Analisis lexico:     FALLIDO (" + Lexer.erroresLexicos + " error(es) lexico(s))";
        System.out.println(lexicoMsg + ANSI_RESET);

        String sintacticoMsg = (parser.erroresSintacticos == 0) ? ANSI_BLUE + "Analisis sintactico: EXITOSO" : ANSI_RED + "Analisis sintactico: FALLIDO (" + parser.erroresSintacticos + " error(es) sintactico(s))";
        System.out.println(sintacticoMsg + ANSI_RESET);

        String semanticoMsg = (parser.erroresSemanticos == 0) ? ANSI_RED_BRIGHT + "Analisis semantico:  EXITOSO" : ANSI_RED + "Analisis semantico:  FALLIDO (" + parser.erroresSemanticos + " error(es) semantico(s))";
        System.out.println(semanticoMsg + ANSI_RESET);
    }
}