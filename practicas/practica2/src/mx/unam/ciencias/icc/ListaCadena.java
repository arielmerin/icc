package mx.unam.ciencias.icc;

/**
 * <p>Clase para listas doblemente ligadas de cadenas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas son iterables utilizando sus nodos.</p>
 */
public class ListaCadena {

    /**
     * Clase para nodos de uso interno a la clase Lista.
     */
    public class Nodo {

        /* El elemento del nodo. */
        private String elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         * @param elemento el elemento del nodo.
         */
        public Nodo(String elemento) {
            this.elemento = elemento;
        }

        /**
         * Regresa el nodo anterior del nodo.
         * @return el nodo anterior del nodo.
         */
        public Nodo getAnterior() {
            return anterior;
        }

        /**
         * Regresa el nodo siguiente del nodo.
         * @return el nodo siguiente del nodo.
         */
        public Nodo getSiguiente() {
            return siguiente;
        }

        /**
         * Regresa el elemento del nodo.
         * @return el elemento del nodo.
         */
        public String get() {
            return elemento;
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
     */
    public void agregaFinal(String elemento) {
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
     */
    public void agregaInicio(String elemento) {
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
     * la lista, el elemento se agrega al final de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     */
    public void inserta(int i, String elemento) {
        if(i <= 0)
            agregaInicio(elemento);
        else if(i >= longitud)
            agregaFinal(elemento);
        else {
            longitud++;
            Nodo auxiliar = cabeza;
            for(int j = 0; j != i-1; j++)
                auxiliar = auxiliar.siguiente;

            Nodo nodo = new Nodo(elemento);
            nodo.anterior = auxiliar;
	        nodo.siguiente = auxiliar.siguiente;
	        nodo.siguiente.anterior = nodo;
	        nodo.anterior.siguiente = nodo;
        }
    }

    /** 
     * Regresa el nodo de la lista que contiene el elemento que se le pasa
     * como parámetro.
     * @param elemento el elemento que contiene el nodo.
     * @return el nodo de la lista que contene al elemento.
    */
    private Nodo buscaNodo(String elemento) {
        Nodo nodo = cabeza;

        while(nodo != null) {
            if(nodo.elemento.equals(elemento))
                break;
            nodo = nodo.siguiente;
        }

        return nodo;
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    public void elimina(String elemento) {
        Nodo nodo = buscaNodo(elemento);
        if(nodo == null)
            return;
        eliminaNodo(nodo);
    }

    /**
     * Elimina un nodo de la lista.
     * @param nodo el nodo a eliminar
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
     * @return el primer elemento de la lista antes de eliminarlo, o
     *         <code>null</code> si la lista está vacía.
     */
    public String eliminaPrimero() {
        if(esVacia())
            return null;
        String s = cabeza.elemento;
        eliminaNodo(cabeza);
        return s;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo, o
     *         <code>null</code> si la lista está vacía.
     */
    public String eliminaUltimo() {
        if(esVacia())
            return null;
        String s = rabo.elemento;
        eliminaNodo(rabo);
        return s;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <tt>true</tt> si <tt>elemento</tt> está en la lista,
     *         <tt>false</tt> en otro caso.
     */
    public boolean contiene(String elemento) {
        return buscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public ListaCadena reversa() {
        ListaCadena lista = new ListaCadena();
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
    public ListaCadena copia() {
        ListaCadena lista = new ListaCadena();
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
     * @return el primer elemento de la lista, o <code>null</code> si la lista
     *         es vacía.
     */
    public String getPrimero() {
        if(esVacia())
            return null;
        else 
            return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista, o <code>null</code> si la lista
     *         es vacía.
     */
    public String getUltimo() {
        if(esVacia())
            return null;
        else 
            return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista. Si el índice es menor
     * que cero o mayor o igual que el número de elementos de la lista, el
     * método regresa <tt>null</tt>.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista, si <em>i</em> es mayor
     *         o igual que cero y menor que el número de elementos en la lista;
     *         <tt>null</tt> en otro caso.
     */
    public String get(int i) {
        if(i < 0 || i >= longitud)
            return null;
        
        Nodo nodo = cabeza;
        for(int j = 0; nodo != null; j++) {
            if(i == j)
                break;
            nodo = nodo.siguiente;
        }

        return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(String elemento) {
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
    public String toString() {
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
     * Regresa el nodo cabeza de la lista.
     * @return el nodo cabeza de la lista.
     */
    public Nodo getCabeza() {
        return cabeza;
    }

    /**
     * Regresa el nodo rabo de la lista.
     * @return el nodo rabo de la lista.
     */
    public Nodo getRabo() {
        return rabo;
    }
}
