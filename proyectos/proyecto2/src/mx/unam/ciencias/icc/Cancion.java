package mx.unam.ciencias.icc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Clase para representar canciones. Una canción tiene nombre, artista, álbum,
 * duración y posición en el ranking top. La clase implementa {@link Registro}, 
 * por lo que puede cargarse y guardarse utilizando objetos de las clases 
 * {@link BufferedReader} y {@link BufferedWriter} como entrada y salida
 * respectivamente.
 */
public class Cancion implements Registro {

    /* Nombre de la canción. */
    private StringProperty nombre;
    /* Número de posición en el ranking. */
    private IntegerProperty posicion;
    /* Duración de la canción. */
    private DoubleProperty duracion;
    /* Nombre del artista. */
    private StringProperty artista;
    /* Nombre del álbum. */
    private StringProperty album;

    /**
     * Construye una canción con todas sus propiedades.
     * @param nombre el nombre de la canción.
     * @param posicion la posición de la canción en el ranking.
     * @param duracion la duración de la canción.
     * @param artista el nombre del artista.
     * @param album el nombre del álbum.
     */
    public Cancion(String nombre,
                   int    posicion,
                   double duracion,
                   String artista,
                   String album) {
        this.nombre = new SimpleStringProperty(nombre);
        this.posicion = new SimpleIntegerProperty(posicion);
        this.duracion = new SimpleDoubleProperty(duracion);
        this.artista = new SimpleStringProperty(artista);
        this.album = new SimpleStringProperty(album);
    }

    /**
     * Regresa el nombre de la canción.
     * @return el nombre de la canción.
     */
    public String getNombre() {
        return nombre.get();
    }

    /**
     * Define el nombre de la canción.
     * @param nombre el nuevo nombre de la canción.
     */
    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    /**
     * Regresa la propiedad del nombre.
     * @return la propiedad del nombre.
     */
    public StringProperty nombreProperty() {
        return this.nombre;
    }

    /**
     * Regresa la posicion en el ranking de la canción.
     * @return la posicion en el ranking de la canción.
     */
    public int getPosicion() {
        return posicion.get();
    }

    /**
     * Define la posicion en el ranking de la canción.
     * @param posicion la posicion en el ranking de la canción.
     */
    public void setPosicion(int posicion) {
        this.posicion.set(posicion);
    }

    /**
     * Regresa la propiedad la posicion en el ranking de la canción.
     * @return la propiedad la posicion en el ranking de la canción.
     */
    public IntegerProperty posicionProperty() {
        return this.posicion;
    }

    /**
     * Regresa la duración de la canción.
     * @return la duración de la canción.
     */
    public double getDuracion() {
        return duracion.get();
    }

    /**
     * Define la duración de la canción.
     * @param duracion la duración de la canción.
     */
    public void setDuracion(double duracion) {
        this.duracion.set(duracion);
    }

    /**
     * Regresa la propiedad de la duración de la canción.
     * @return la propiedad de la duración de la canción.
     */
    public DoubleProperty duracionProperty() {
        return this.duracion;
    }

    /**
     * Regresa el nombre del artista.
     * @return el nombre del artista.
     */
    public String getArtista() {
        return artista.get();
    }

    /**
     * Define el nombre del artista.
     * @param artista el nombre del artista.
     */
    public void setArtista(String artista) {
        this.artista.set(artista);
    }

    /**
     * Regresa la propiedad de el nombre del artista.
     * @return la propiedad de el nombre del artista.
     */
    public StringProperty artistaProperty() {
        return this.artista;
    }

    /**
     * Regresa el nombre del álbum.
     * @return el nombre del álbum.
     */
    public String getAlbum() {
        return album.get();
    }

    /**
     * Define el nombre del álbum.
     * @param album el nombre del álbum.
     */
    public void setAlbum(String album) {
        this.album.set(album);
    }

    /**
     * Regresa la propiedad de el nombre del álbum.
     * @return la propiedad de el nombre del álbum.
     */
    public StringProperty albumProperty() {
        return this.album;
    }

    /**
     * Nos dice si el objeto recibido es una canción igual al que manda llamar
     * el método.
     * @param o el objeto con el que la canción se comparará.
     * @return <tt>true</tt> si el objeto o es una canción con las mismas
     *         propiedades que el objeto que manda llamar al método,
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if(!(o instanceof Cancion))
            return false;
        Cancion c = (Cancion)o;
        if((c.getNombre().equals(getNombre())) && 
            (c.getPosicion() == getPosicion()) && 
                (c.getDuracion() == getDuracion()) && 
                    (c.getArtista().equals(getArtista()) &&
                        (c.getAlbum().equals(getAlbum()))))
                return true;
        return false;
    }

    /**
     * Regresa una representación en cadena de la canción.
     * @return una representación en cadena de la canción.
     */
    @Override public String toString() {
        return String.format("Nombre                 : %s\n"+
                             "Posición               : %d\n"+
                             "Duración               : %2.2f\n"+
                             "Artista                : %s\n"+
                             "Album                  : %s",
                             nombre.get(), posicion.get(), duracion.get(),
                             artista.get(), album.get());
    }

    /**
     * Guarda la canción en la salida recibida.
     * @param out la salida dónde hay que guardar la canción
     * @throws IOException si un error de entrada/salida ocurre.
     */
    @Override public void guarda(BufferedWriter out) throws IOException {
        out.write(String.format("%s\t"+"%d\t"+"%2.2f\t"+"%s\t"+"%s\n",
                                nombre.get(), posicion.get(), duracion.get(),
                                artista.get(), album.get()));
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
        String s = in.readLine();
        if(s == null)
            return false;
        s = s.trim();
        if(s.equals(""))
            return false;

        String[] datos = s.split("\t");
        if(datos.length != 5)
            throw new IOException("Error de entrada/salida.");
        nombre.setValue(datos[0]);

        try {
          this.posicion.setValue(Integer.parseInt(datos[1]));
          this.duracion.setValue(Double.parseDouble(datos[2]));
          this.duracion.setValue(Double.parseDouble(String.format("%2.2f", duracion.get())));
          this.artista.setValue(datos[3]);
          this.album.setValue(datos[4]);
          return true;
        }
        catch(NumberFormatException nfe) {
            throw new IOException("Cadena invalida");
        }
    }

    /**
     * Actualiza los valores de la canción con los del registro recibido.
     * @param registro la canción con el cual actualizar los valores.
     * @throws ClassCastException si el registro recibido no es instancia de la
     *         clase {@link Cancion}.
     */
    @Override public void actualiza(Registro registro) throws ClassCastException{
        if(!(registro instanceof Cancion))
          throw new ClassCastException("El registro recibido no es instancia " +
                                       "de la clase Cancion.");
        Cancion e = (Cancion)registro;
        this.nombre.set(e.getNombre());
        this.posicion.set(e.getPosicion());
        this.duracion.set(e.getDuracion());
        this.artista.set(e.getArtista());
        this.album.set(e.getAlbum());
    }
}
