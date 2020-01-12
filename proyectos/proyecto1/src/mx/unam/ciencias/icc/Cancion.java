package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 * Clase para representar canciones. Una canción tiene nombre, artista, álbum,
 * duración y número de reproducciones. La clase implementa {@link Registro}, 
 * por lo que puede cargarse y guardarse utilizando objetos de las clases 
 * {@link BufferedReader} y {@link BufferedWriter} como entrada y salida, 
 * respectivamente.
 */
public class Cancion implements Registro {

    /* Nombre de la canción. */
    private String nombre;
    /* Nombre del artista. */
    private String artista;
    /* Álbum al que pertenece la canción. */
    private String album;
    /* Duración de la canción. */
    private double duracion;
    /* Cantidad de reproducciones. */
    private int reproducciones;

    /**
     * Construye una cancion con todas sus propiedades.
     * @param nombre el nombre de la canción.
     * @param artista el nombre del artista.
     * @param album el álbum al que pertenece la canción.
     * @param segundos la duración de la canción.
     * @param reproducciones la cantidad de reproducciones.
     */
    public Cancion(String nombre,
                   String artista,
                   String album,
                   double duracion,
                   int    reproducciones) {
        this.nombre = nombre;
        this.artista = artista;
        this.album = album;
        this.duracion = duracion;
        this.reproducciones = reproducciones;
    }

    /**
     * Regresa el nombre de la canción.
     * @return el nombre de la canción.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Define el nombre de la canción.
     * @param nombre el nuevo nombre de la canción.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Regresa el nombre del artista de la canción.
     * @return el nombre del artista de la canción.
     */
    public String getArtista() {
        return artista;
    }

    /**
     * Define el nombre del artista de la canción.
     * @param artista el nuevo nombre del artista de la canción.
     */
    public void setArtista(String artista) {
        this.artista = artista;
    }

    /**
     * Regresa el álbum al que pertenece la canción.
     * @return el álbum al que pertenece la canción.
     */
    public String getAlbum() {
        return album;
    }

    /**
     * Define el álbum al que pertenece la canción.
     * @param album el nuevo álbum al que pertenece la canción.
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * Regresa la duración de la canción.
     * @return la duración de la canción.
     */
    public double getDuracion() {
        return duracion;
    }

    /**
     * Define la duración de la canción.
     * @param duracion la nueva duración de la canción.
     */
    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    /**
     * Regresa la cantidad de reproducciones de la canción.
     * @return la cantidad de reproducciones de la canción.
     */
    public int getReproducciones() {
        return reproducciones;
    }

    /**
     * Define la cantidad de reproducciones de la canción.
     * @param reproducciones la nueva cantidad de reproducciones de la canción.
     */
    public void setReproducciones(int reproducciones) {
        this.reproducciones = reproducciones;
    }    

    /**
     * Nos dice si el objeto recibido es una canción igual a la que manda llamar
     * el método.
     * @param o el objeto con el que la canción se comparará.
     * @return <tt>true</tt> si el objeto o es una canción con las mismas
     *         propiedades que el objeto que manda llamar al método,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if (!(o instanceof Cancion))
            return false;
        Cancion cancion = (Cancion)o;
        if((cancion.nombre.equals(nombre)) && (cancion.artista.equals(artista)) && 
            (cancion.album.equals(album)) && (cancion.duracion == duracion) && 
              (cancion.reproducciones == reproducciones))
                  return true;
        return false;
    }

    /**
     * Regresa una representación en cadena de la canción.
     * @return una representación en cadena de la canción.
     */
    @Override public String toString() {
        return String.format("Nombre         : %s\n" +
                             "Artista        : %s\n" +
                             "Álbum          : %s\n" +
                             "Duración       : %2.2f\n" +
                             "Reproducciones : %d",
                             nombre, artista, album, duracion, reproducciones);
    }

    /**
     * Guarda la canción en la salida recibida.
     * @param out la salida dónde hay que guardar la canción.
     * @throws IOException si un error de entrada/salida ocurre.
     */
    @Override public void guarda(BufferedWriter out) throws IOException {
        try {
            out.write(String.format("%s\t%s\t%s\t%2.2f\t%d\n", 
                      nombre, artista, album, duracion, reproducciones));
        } catch(IOException ioe) {
            throw new IOException("Error de entrada/salida.");
        }
    }

    /**
     * Carga la canción de la entrada recibida.
     * @param in la entrada de dónde hay que cargar la canción.
     * @return <tt>true</tt> si el método carga una canción válida,
     *         <tt>false</tt> en otro caso.
     * @throws IOException si un error de entrada/salida ocurre, o si la entrada
     *         recibida no contiene a una canción.
     */
    @Override public boolean carga(BufferedReader in) throws IOException {
        String cancion = null;
        cancion = in.readLine();
     
        if(cancion == null)
            return false;
        
        String[] datos = cancion.split("\t");
        if(datos.length != 5)
            throw new IOException ("Cadena inválida.");
        
        try{
            this.nombre = datos[0];
            this.artista = datos[1];
            this.album = datos[2];
            this.duracion = Double.parseDouble(datos[3]);
            String.format("%2.2f", duracion);
            this.reproducciones = Integer.parseInt(datos[4]);
            return true;
        } catch (NumberFormatException nfe){
            throw new IOException("Cadena inválida.");
        }
    }
}
