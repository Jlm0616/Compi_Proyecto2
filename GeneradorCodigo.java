// ===============================================================
// GENERADOR DE CODIGO INTERMEDIO
// ===============================================================
// Clase responsable de generar codigo intermedio de tres direcciones
// El codigo se escribe en un archivo de texto para su posterior uso
// ===============================================================

import java.io.*;

public class GeneradorCodigo {

    // ===============================================================
    // ATRIBUTOS ESTATICOS
    // ===============================================================
    
    private static int contadorTemp = 0;    // Contador para temporales: t0, t1, t2...
    private static int contadorEtiq = 0;    // Contador para etiquetas: L0, L1, L2...
    private static PrintWriter writer = null;  // Archivo de salida

    // ===============================================================
    // INICIAR
    // ===============================================================
    // Abre el archivo donde se escribira el codigo intermedio
    // Recibe: archivo - nombre del archivo de salida
    // ===============================================================
    public static void iniciar(String archivo) {
        try {
            writer = new PrintWriter(new FileWriter(archivo));
            writer.println("# ===== CODIGO INTERMEDIO =====");
        } catch (IOException e) {
            System.err.println("Error al crear archivo de codigo intermedio: " + e.getMessage());
        }
    }

    // ===============================================================
    // EMITIR ETIQUETA
    // ===============================================================
    // Escribe una etiqueta en el codigo intermedio (ej: "L0:")
    // Recibe: etiqueta - nombre de la etiqueta a emitir
    // ===============================================================
    public static void emitirEtiqueta(String etiqueta) {
        emitir(etiqueta + ":");
    }

    // ===============================================================
    // CERRAR
    // ===============================================================
    // Cierra el archivo de codigo intermedio
    // ===============================================================
    public static void cerrar() {
        if (writer != null) {
            writer.println("# ===== FIN DEL CODIGO INTERMEDIO =====");
            writer.flush();
            writer.close();
        }
    }

    // ===============================================================
    // NUEVO TEMPORAL
    // ===============================================================
    // Genera un nuevo temporal unico: t0, t1, t2,..., tn
    // Retorna: String - temporal con formato "tX"
    // ===============================================================
    public static String nuevoTemp() {
        return "t" + (contadorTemp++);
    }

    // ===============================================================
    // NUEVA ETIQUETA
    // ===============================================================
    // Genera una nueva etiqueta unica: L0, L1, L2,..., Ln
    // Retorna: String - etiqueta con formato "LX"
    // ===============================================================
    public static String nuevaEtiqueta() {
        return "L" + (contadorEtiq++);
    }

    // ===============================================================
    // EMITIR
    // ===============================================================
    // Escribe una linea de codigo intermedio en el archivo
    // Recibe: instruccion - linea de codigo a emitir
    // ===============================================================
    public static void emitir(String instruccion) {
        if (writer != null) writer.println(instruccion);
    }
}