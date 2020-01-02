package mx.unam.ciencias.icc;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas implementan la interfaz {@link Iterable}, y por lo tanto se
 * pueden recorrer usando la estructura de control <em>for-each</em>. Las listas
 * no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Iterable<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {            
            this.elemento = elemento;
        }
    }

    /* Clase Iterador privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
            anterior = null;
            siguiente = cabeza;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if(siguiente == null)
                throw new NoSuchElementException("El siguiente del iterador " +
                                                 "es vacío.");
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if(anterior == null)
                throw new NoSuchElementException("El anterior del iterador " +
                                                 "es vacío.");
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            anterior = null;
            siguiente = cabeza;   
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            siguiente = null;
            anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {        
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacia() {
        return cabeza == null;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException("El elemento es null.");

        Nodo nodo = new Nodo(elemento);
        longitud++;

        if(esVacia())
            cabeza = rabo = nodo;
        else {
            rabo.siguiente = nodo;
            nodo.anterior = rabo;
            rabo = nodo;
        }
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException("El elemento es null.");
        
        Nodo nodo = new Nodo(elemento);
        longitud++;

        if(esVacia())
            cabeza = rabo = nodo;
        else {
            cabeza.anterior = nodo;
            nodo.siguiente = cabeza;
            cabeza = nodo;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        if(elemento == null)
            throw new IllegalArgumentException("El elemento es null.");
        
        if(i <= 0)
            agregaInicio(elemento);
        else if(i >= longitud)
            agregaFinal(elemento);
        else {
            longitud++;
            Nodo auxiliar = getIesimo(cabeza, i, 1);
            Nodo nodo = new Nodo(elemento);
            nodo.anterior = auxiliar;
	        nodo.siguiente = auxiliar.siguiente;
	        nodo.siguiente.anterior = nodo;
	        nodo.anterior.siguiente = nodo;
        }
    }
    
    /**
     * Regresa el i-ésimo nodo de la lista.
     * @param nodo el nodo desde dónde iniciamos a recorrer la lista.
     * @param i el índice dónde insertar el elemento.
     * @param j el contador auxiliar para recorrer la lista.
     * @return el i-ésimo nodo de la lista.
     */
    private Nodo getIesimo(Nodo nodo, int i, int j) {
        if (i == j)
           return nodo;
        return getIesimo(nodo.siguiente, i, ++j);
    }
    
    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(T elemento) {
        Nodo nodo = buscaNodo(elemento);
        if(nodo == null)
            return;
        eliminaNodo(nodo);
    }

    /** 
     * Regresa el nodo de la lista que contiene el elemento que se le pasa
     * como parámetro.
     * @param elemento el elemento que contiene el nodo.
     * @return el nodo de la lista que contiene el elemento que se le pasa 
     * como parámetro.
    */
    private Nodo buscaNodo(T elemento) {
        Nodo nodo = cabeza;

        while(nodo != null) {
            if(nodo.elemento.equals(elemento))
                break;
            nodo = nodo.siguiente;
        }

        return nodo;
    }

    /**
     * Elimina un nodo de la lista.
     * @param nodo el nodo a eliminar.
     */
    private void eliminaNodo(Nodo nodo) {
        longitud--;

        if(cabeza == rabo) {
            cabeza = rabo = null;
        } else if(nodo == cabeza) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } else if(nodo == rabo) {
            rabo = rabo.anterior;
            rabo.siguiente = null;
        } else {
            nodo.anterior.siguiente = nodo.siguiente;
            nodo.siguiente.anterior = nodo.anterior;
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        T s = cabeza.elemento;
        eliminaNodo(cabeza);
        return s;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
       if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        T s = rabo.elemento;
        eliminaNodo(rabo);
        return s;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(T elemento) {
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> lista = new Lista<T>();
        Nodo nodo = rabo;

        while(nodo != null) {
            lista.agregaFinal(nodo.elemento);
            nodo = nodo.anterior;
        }

        return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> lista = new Lista<T>();
        Nodo nodo = cabeza;

        while(nodo != null) {
            lista.agregaFinal(nodo.elemento);
            nodo = nodo.siguiente;
        }

        return lista;
    }

    /**
     * Limpia la lista de elementos. El llamar este método es equivalente a
     * eliminar todos los elementos de la lista.
     */
    public void limpia() {
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if(esVacia())
            throw new NoSuchElementException("La lista es vacía.");
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if(i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido("El índice es inválido.");;
        Nodo nodo = getIesimo(cabeza, i, 0);
        return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        Nodo nodo = cabeza;
        for(int i = 0; nodo != null; i++) {
            if(nodo.elemento.equals(elemento))
                return i;
            nodo = nodo.siguiente;
        }

        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        String s = "[";
        Nodo nodo = cabeza;

        while(nodo != null) {
            s += nodo.elemento;
            nodo = nodo.siguiente;

            if(nodo != null)
                s += ", ";
        }

        s += "]";
        return s;
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param o el objeto con el que hay que comparar.
     * @return <tt>true</tt> si la lista es igual al objeto recibido;
     *         <tt>false</tt> en otro caso.
     */
    @Override public boolean equals(Object o) {
        if(o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)o;

        if(lista.getLongitud() != longitud)
            return false;
        Nodo n = lista.cabeza;
        Nodo m = cabeza;
        while(n != null) {
            if(n.elemento.equals(m.elemento)) {
                n = n.siguiente;
                m = m.siguiente;
            } else 
                return false;
        }

        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
