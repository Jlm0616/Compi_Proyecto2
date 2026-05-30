// ===============================================================
// MAIN - CLASE PRINCIPAL
// ===============================================================
// Orquesta el proceso completo de compilacion:
// 1. Primer pase lexico (solo genera tokens.txt, errores silenciados)
// 2. Segundo pase lexico + parser (muestra errores y genera codigo)
// ===============================================================

import java.io.*;

public class Main {

    // Colores para la salida en consola
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_DARK_BLUE = "\u001B[34;2m";
    public static final String ANSI_RED_BRIGHT = "\u001B[91m";

    // ===============================================================
    // METODO PRINCIPAL
    // ===============================================================
    public static void main(String[] args) throws Exception {
        
        // Verificar que se haya pasado un archivo como argumento
        if (args.length < 1) {
            System.out.println("Uso: java Main <archivo>");
            return;
        }

        // ===============================================================
        // PRIMER PASO: ANALISIS LEXICO SILENCIADO
        // Solo para generar el archivo tokens.txt
        // ===============================================================
        
        Lexer.silenciarErrores = true;           // No mostrar errores en consola
        Lexer lexerTokens = new Lexer(new FileReader(args[0]));
        PrintWriter tokenWriter = new PrintWriter(new FileWriter("tokens.txt"));
        
        // Escribir encabezado del archivo de tokens
        tokenWriter.println("=== TOKENS ENCONTRADOS ===\n");
        tokenWriter.println(String.format("%-25s %-20s %s", "Token", "Lexema", "Linea"));
        tokenWriter.println(String.format("%-25s %-20s %s", "-----", "------", "-----"));

        // Leer todos los tokens y guardarlos
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

        // ===============================================================
        // SEGUNDO PASO: RESETEAR Y PREPARAR PARA EL PARSER
        // ===============================================================
        
        Lexer.erroresLexicos = 0;               // Reiniciar contador de errores
        Lexer.silenciarErrores = false;          // Activar visualizacion de errores

        // ===============================================================
        // TERCER PASO: ANALISIS SINTACTICO Y SEMANTICO
        // Genera codigo intermedio y muestra errores en consola
        // ===============================================================
        
        Lexer lexerParser = new Lexer(new FileReader(args[0]));  // Nuevo lexer
        GeneradorCodigo.iniciar("codigo_intermedio.txt");       // Preparar archivo de salida

        @SuppressWarnings("deprecation")
        parser p = new parser(lexerParser);
        p.parse();                                               // Ejecutar parser

        GeneradorCodigo.cerrar();                                // Cerrar archivo de codigo

        // ===============================================================
        // CUARTO PASO: MOSTRAR RESUMEN DE RESULTADOS
        // ===============================================================
        
        System.out.println();
        System.out.println("\n=== ANALISIS DE COMPILACION ===" + " (Archivo: " + args[0] + ")");

        // Resumen de errores lexicos
        String lexicoMsg = (Lexer.erroresLexicos == 0) 
            ? ANSI_GREEN + "Analisis lexico:     EXITOSO" 
            : ANSI_RED + "Analisis lexico:     FALLIDO (" + Lexer.erroresLexicos + " error(es) lexico(s))";
        System.out.println(lexicoMsg + ANSI_RESET);

        // Resumen de errores sintacticos
        String sintacticoMsg = (parser.erroresSintacticos == 0) 
            ? ANSI_BLUE + "Analisis sintactico: EXITOSO" 
            : ANSI_RED + "Analisis sintactico: FALLIDO (" + parser.erroresSintacticos + " error(es) sintactico(s))";
        System.out.println(sintacticoMsg + ANSI_RESET);

        // Resumen de errores semanticos
        String semanticoMsg = (parser.erroresSemanticos == 0) 
            ? ANSI_RED_BRIGHT + "Analisis semantico:  EXITOSO" 
            : ANSI_RED + "Analisis semantico:  FALLIDO (" + parser.erroresSemanticos + " error(es) semantico(s))";
        System.out.println(semanticoMsg + ANSI_RESET);
    }
}