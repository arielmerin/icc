package mx.unam.ciencias.icc;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }
    
    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        for(int i = 0; i < arreglo.length - 1; ++i) {
            int minimo = i;
        for(int j = i + 1; j < arreglo.length; ++j) {
            if(comparador.compare(arreglo[j], arreglo[minimo]) < 0)
                minimo = j;
        }

        intercambia(arreglo, i, minimo);
        }
    }

    /**
     * Intercambia los valores que se encuentran en la i-ésima y j-ésima
     * posición de un arreglo.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo en el cual se van a intercambiar los valores.
     * @param i la i-ésima posición del arreglo.
     * @param j la j-ésima posición del arreglo.
     */
    private static<T> void intercambia(T[] arreglo, int i, int j) {
        T temp = arreglo[i];
        arreglo[i] = arreglo[j];
        arreglo[j] = temp;
    }

    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }
    
    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void quickSort(T[] arreglo, Comparator<T> comparador) {
        quickSort(arreglo, 0, arreglo.length-1, comparador);
    }
    
    /**
     * Ordena el arreglo recibido usando QuickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param izq la posición inicial del arreglo.
     * @param der la posición final del arreglo.
     * @param comparador el comparador para ordenar el arreglo.
     */
    private static <T> void quickSort(T[] arreglo, int izq, int der, 
                                      Comparator<T> comparador) {
        if(izq >= der)
            return;
        int i = izq;
        int j = der;

        if(izq != der) {
            while(izq != der) {
                while(comparador.compare(arreglo[der], arreglo[izq]) >= 0 && izq < der)
                    der--;
                while(comparador.compare(arreglo[izq], arreglo[der]) < 0 && izq < der)
                    izq++;
                if(der != izq) 
                    intercambia(arreglo, der, izq);
                if(izq == der) {
                    quickSort(arreglo, i, izq - 1, comparador);
                    quickSort(arreglo, izq + 1, j, comparador);
                }
            }
        }
	}
    
    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        int izq = 0;
        int der = arreglo.length-1;

        while (izq < der) {
            int mitad = ((izq + der)/2);
            int comp = comparador.compare(elemento, arreglo[mitad]);
            if(comp == 0)
                return mitad;
            else if(comp == 1)
                izq = mitad + 1;
            else
                der = mitad - 1;
        }

        if(der == izq)
            if(comparador.compare(elemento, arreglo[izq]) == 0)
                return izq;
        return -1;
    }
}
