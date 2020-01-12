package mx.unam.ciencias.icc;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Proyecto1: Excepciones, entrada/salida y enumeraciones.
 */
public class Proyecto1 {

    /* Hace búsquedas por nombre y artista en la base de datos. */
    private static void busquedas(BaseDeDatosCanciones bdd) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");

        System.out.printf("Entra un nombre de cancion para buscar: ");
        String nombre = sc.next();

        Lista r = bdd.buscaRegistros(CampoCancion.NOMBRE, nombre);
        if (r.esVacia()) {
            System.out.printf("\nNo se hallaron canciones " +
                              "con el nombre \"%s\".\n",
                              nombre);
        } else {
            System.out.printf("\nSe hallaron las siguientes " +
                              "canciones con el nombre \"%s\":\n\n",
                              nombre);
            Lista.Nodo nodo = r.getCabeza();
            while (nodo != null) {
                System.out.println(nodo.get().toString() + "\n");
                nodo = nodo.getSiguiente();
            }
        }

        System.out.printf("Entra el nombre de un artista para buscar: ");
        String artista = "";
        try {
            artista = sc.next();
        } catch (InputMismatchException ime) {
            System.out.printf("Se entró el nombre de un artista inválido. " +
                              "Se interpretará como cadena vacía.\n");
        }

        r = bdd.buscaRegistros(CampoCancion.ARTISTA, artista);
        if (r.esVacia()) {
            System.out.printf("\nNo se hallaron canciones " +
                              "con el artista \"%s\".\n",
                              artista);
        } else {
            System.out.printf("\nSe hallaron las siguientes " +
                              "canciones con el artista \"%s\":\n\n",
                              artista);
            Lista.Nodo nodo = r.getCabeza();
            while (nodo != null) {
                System.out.println(nodo.get().toString() + "\n");
                nodo = nodo.getSiguiente();
            }
        }
    }

    /* Crea una base de datos y la llena a partir de los datos que el usuario
       escriba a través del teclado. Después la guarda en disco duro y la
       regresa. */
    private static BaseDeDatosCanciones escritura(String nombreArchivo) {
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");

        File archivo = new File(nombreArchivo);

        if (archivo.exists()) {
            System.out.printf("El archivo \"%s\" ya existe.\n" +
                              "Presiona Ctrl-C si no quieres reescribirlo, " +
                              "o Enter para continuar...\n", nombreArchivo);
            sc.nextLine();
        }

        System.out.println("Entra canciones a la base de datos.\n" +
                           "Cuando desees terminar, deja el nombre en blanco.\n");

        BaseDeDatosCanciones bdd = new BaseDeDatosCanciones();

        do {
            String nombre;
            String artista;
            String album;
            double duracion = 0.0;
            int reproducciones = 0;

            System.out.printf("Nombre   : ");
            nombre = sc.next();
            if (nombre.equals(""))
                break;
            try {
                System.out.printf("Artista  : ");
                artista = sc.next();
                System.out.printf("Album    : ");
                album = sc.next();
                System.out.printf("Duracion en segundos : ");
                duracion = sc.nextDouble();
                System.out.printf("Número de reproducciones     : ");
                reproducciones = sc.nextInt();
            } catch (InputMismatchException ime) {
                System.out.println("\nNúmero inválido: se descartará " +
                                   "esta cancion.\n");
                continue;
            }
            Cancion e = new Cancion(nombre,
                                    artista,
                                    album,
                                    duracion,
                                    reproducciones);
            bdd.agregaRegistro(e);
            System.out.println();
        } while (true);

        int n = bdd.getNumRegistros();
        if (n == 1)
            System.out.printf("\nSe agregó 1 cancion.\n");
        else
            System.out.printf("\nSe agregaron %d canciones.\n", n);

        try {
            FileOutputStream fileOut = new FileOutputStream(nombreArchivo);
            OutputStreamWriter osOut = new OutputStreamWriter(fileOut);
            BufferedWriter out = new BufferedWriter(osOut);
            bdd.guarda(out);
            out.close();
        } catch (IOException ioe) {
            System.out.printf("No pude guardar en el archivo \"%s\".\n",
                              nombreArchivo);
            System.exit(1);
        }

        System.out.printf("\nBase de datos guardada exitosamente en \"%s\".\n",
                          nombreArchivo);

        return bdd;
    }

    /* Crea una base de datos y la llena cargándola del disco duro. Después la
       regresa. */
    private static BaseDeDatosCanciones lectura(String nombreArchivo) {
        BaseDeDatosCanciones bdd = new BaseDeDatosCanciones();

        try {
            FileInputStream fileIn = new FileInputStream(nombreArchivo);
            InputStreamReader isIn = new InputStreamReader(fileIn);
            BufferedReader in = new BufferedReader(isIn);
            bdd.carga(in);
            in.close();
        } catch (IOException ioe) {
            System.out.printf("No pude cargar del archivo \"%s\".\n",
                              nombreArchivo);
            System.exit(1);
        }

        System.out.printf("Base de datos cargada exitosamente de \"%s\".\n\n",
                          nombreArchivo);

        Lista r = bdd.getRegistros();
        Lista.Nodo nodo = r.getCabeza();
        while (nodo != null) {
            System.out.println(nodo.get().toString() + "\n");
            nodo = nodo.getSiguiente();
        }

        return bdd;
    }

    /* Imprime en pantalla cómo debe usarse el programa y lo termina. */
    private static void uso() {
        System.out.println("Uso: java -jar proyecto1.jar [-g|-c] <archivo>");
        System.exit(1);
    }

    public static void main(String[] args) {
        if (args.length != 2)
            uso();

        String bandera = args[0];
        String nombreArchivo = args[1];

        if (!bandera.equals("-g") && !bandera.equals("-c"))
            uso();

        BaseDeDatosCanciones bdd;

        if (bandera.equals("-g"))
            bdd = escritura(nombreArchivo);
        else
            bdd = lectura(nombreArchivo);

        busquedas(bdd);
    }
}
