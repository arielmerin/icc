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
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
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
    public void elimina(String elemento) {
        Nodo nodo = buscaNodo(cabeza, elemento);
        if(nodo == null)
            return;
        eliminaNodo(nodo);
    }

    /** 
     * Regresa el nodo de la lista que contiene el elemento que se le pasa
     * como parámetro.
     * @param elemento el elemento que contiene el nodo.
     * @param nodo el nodo de la lista dónde iniciamos a recorrer la lista.
     * @return el nodo de la lista que contene al elemento.
    */
    private Nodo buscaNodo(Nodo nodo, String elemento) {
        if(nodo == null)
            return null;
        if(nodo.elemento.equals(elemento))
            return nodo;
        return buscaNodo(nodo.siguiente, elemento);
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
        return buscaNodo(cabeza, elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public ListaCadena reversa() {
        ListaCadena lista = new ListaCadena();
        return reversa(lista, rabo);
    }

    /**
     * Regresa la reversa de la lista.
     * @param lista la lista que contendrá la reversa de la lista que manda a 
     * llamar este método.
     * @param nodo el nodo dónde iniciaremos a recorrer la lista que manda a 
     * llamar este método.
     * @return la reversa de la lista.
     */
    private ListaCadena reversa(ListaCadena lista, Nodo nodo) {
        if(nodo == null)
            return lista;
        lista.agregaFinal(nodo.elemento);
        return reversa(lista, nodo.anterior);
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public ListaCadena copia() {
        ListaCadena lista = new ListaCadena();
        return copia(lista, cabeza);
    }

    /**
     * Regresa la copia de la lista.
     * @param lista la lista que contendrá los mismos elementos que la lista
     * que manda a llamar este método.
     * @param nodo el nodo dónde iniciaremos a recorrer la lista que manda a 
     * llamar este método.
     * @return la copia de la lista que manda a llamar este método.
     */
    private ListaCadena copia(ListaCadena lista, Nodo nodo) {
        if(nodo == null)
            return lista;
        lista.agregaFinal(nodo.elemento);
        return copia(lista, nodo.siguiente);
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
        Nodo nodo = getIesimo(cabeza, i, 0);
        return nodo.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(String elemento) {
        return indiceDe(elemento, cabeza, 0);
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento recibido en la lista.
     * @param nodo el nodo dónde iniciamos a recorrer la lista.
     * @param i el contador para recorrer la lista.
     * @return el índice del elemento recibido en la lista.
     */
    private int indiceDe(String elemento, Nodo nodo, int i) {
        if (nodo == null)
            return -1;
        if (nodo.elemento.equals(elemento))
            return i;
        return indiceDe(elemento, nodo.siguiente, ++i);
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    public String toString() {
        if (cabeza == null)
            return "[]";
        return "[" + cabeza.elemento.toString() + toString(cabeza.siguiente);
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @param nodo el nodo donde iniciaremos a recorrer la lista.
     * @return una representación en cadena de la lista.
     */
    private String toString(Nodo nodo) {
        if(nodo == null)
            return "]";
        return ", " + nodo.elemento.toString() + toString(nodo.siguiente);
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
