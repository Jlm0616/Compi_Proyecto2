import java.io.*;

public class GeneradorCodigo {

    private static int contadorTemp = 0;
    private static int contadorEtiq = 0;
    private static PrintWriter writer = null;

    // Abre el archivo donde se escribirá el código intermedio
    public static void iniciar(String archivo) {
        try {
            writer = new PrintWriter(new FileWriter(archivo));
            writer.println("# ===== CODIGO INTERMEDIO =====");
        } catch (IOException e) {
            System.err.println("Error al crear archivo de codigo intermedio: " + e.getMessage());
        }
    }

    // Cierra el archivo
    public static void cerrar() {
        if (writer != null) {
            writer.println("# ===== FIN DEL CODIGO INTERMEDIO =====");
            writer.flush();
            writer.close();
        }
    }

    // Genera un nuevo temporal: t0, t1, t2,...,tn
    public static String nuevoTemp() {
        return "t" + (contadorTemp++);
    }

    // Genera una nueva etiqueta: L0, L1, L2,...,Ln
    public static String nuevaEtiqueta() {
        return "L" + (contadorEtiq++);
    }

    // Escribe una línea al archivo
    public static void emitir(String instruccion) {
        if (writer != null) writer.println(instruccion);
    }
}