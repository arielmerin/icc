package mx.unam.ciencias.icc;

import java.util.Random;

/**
 * Práctica 3: Recursión.
 */
public class Practica3 {

    public static void main(String[] args) {
        Random random = new Random();
        int total = 10 + random.nextInt(90);
        ListaCadena lista = new ListaCadena();
        int i;

        for (i = 0; i < total/2; i++) {
            if (lista.getLongitud() != i) {
                System.out.println("La longitud de la lista es incorrecta.");
                System.exit(1);
            }
            String r = String.valueOf(random.nextInt(total));
            lista.agregaFinal(r);
            ListaCadena.Nodo ultimo = lista.getRabo();
            if (!ultimo.get().equals(r)) {
                System.out.println("Error al agregar al final.");
                System.exit(1);
            }
        }

        for (i = total/2; i < total; i++) {
            if (lista.getLongitud() != i) {
                System.out.println("La longitud de la lista es incorrecta.");
                System.exit(1);
            }
            String r = String.valueOf(random.nextInt(total));
            lista.agregaInicio(r);
            ListaCadena.Nodo primero = lista.getCabeza();
            if (!primero.get().equals(r)) {
                System.out.println("Error al agregar al inicio.");
                System.exit(1);
            }
        }

        i = 0;
        String[] a = new String[total];
        ListaCadena.Nodo nodo = lista.getCabeza();
        while (nodo != null) {
            System.out.printf("Elemento %d: %s\n", i, nodo.get());
            a[i++] = nodo.get();
            nodo = nodo.getSiguiente();
        }

        System.out.println("Lista: " + lista);

        for (i = 0; i < total; i++) {
            if (!lista.get(i).equals(a[i])) {
                System.out.printf("Error al obtener el %d-ésimo elemento.\n", i);
                System.exit(1);
            }
        }

        while (lista.getLongitud() > 0) {
            int n = random.nextInt(lista.getLongitud());
            String e = lista.get(n);
            System.out.printf("Eliminando %s...\n", e);
            lista.elimina(e);
            System.out.println("Lista resultante: " + lista);
        }
    }
}
