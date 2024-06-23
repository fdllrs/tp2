package aed;

import java.util.ArrayList;

// cumple las complejidades de un Trie 😎
public class Trie<T> {

    private int tamaño;
    private Nodo raiz = new Nodo();

    private class Nodo {
        T valor;

        String palabra;

        Nodo padre;

        ArrayList<Nodo> hijos = new ArrayList<Nodo>(256);

        public Nodo(Nodo padre) {
            this.padre = padre;
            this.valor = null;

            for (int i = 0; i < 256; i++) { // O(1)
                this.hijos.add(null);
            }

        }

        public Nodo() {
            this.padre = null;
            this.valor = null;
            this.palabra = null;

            for (int i = 0; i < 256; i++) { // O(1)
                this.hijos.add(null);
            }
        }

    }

    public Trie() {
        this.tamaño = 0;
        this.raiz = new Nodo();

    }

    public boolean pertenece(String clave) { // O(|clave|)
        int largoElem = clave.length();
        char[] charList = clave.toCharArray();

        Nodo nodoActual = this.raiz;

        for (int i = 0; i < largoElem; i++) { // O(|clave|)
            if (nodoActual == null) {
                break;
            }

            int charCode = (int) charList[i];
            nodoActual = nodoActual.hijos.get(charCode); // O(1) por ser un Array.
        }

        return (nodoActual != null && nodoActual.valor != null);
    }

    // agregar una clave que ya está simplemente pisa el valor.
    public void agregar(String clave, T valor) {

        int largoElem = clave.length();
        char[] charList = clave.toCharArray();

        Nodo nodoActual = this.raiz;

        for (int i = 0; i < largoElem; i++) { // O(|clave|)
            int charCode = (int) charList[i];

            if (nodoActual.hijos.get(charCode) != null) {
                nodoActual = nodoActual.hijos.get(charCode); // O(1) por ser un Array.
            } else {
                Nodo nuevoNodo = new Nodo(nodoActual);
                nodoActual.hijos.set(charCode, nuevoNodo);
                nodoActual = nodoActual.hijos.get(charCode); // O(1) por ser un Array.
            }
        }

        if (nodoActual.valor == null) {
            tamaño++;
        }

        nodoActual.valor = valor;
        nodoActual.palabra = clave;
    }

    // como |nodos| > |claves| entonces O(|nodos|)
    public String[] toStringArray() { // O(|nodos|)
        int nivel = 0;
        String[] palabras = new String[tamaño];
        ArrayList<String> arrayPalabras = new ArrayList<String>(tamaño);

        stringRecursivo(raiz, nivel, arrayPalabras); // O(|nodos|)

        for (int i = 0; i < tamaño; i++) { // O(|claves|)
            palabras[i] = arrayPalabras.get(i); // O(1) por ser un Array.
        }

        return palabras;

    }

    // recorre los 256 hijos de cada nodo. O(256) + O(|nodos|) = O(|nodos|)
    private void stringRecursivo(Nodo nodo, int nivel, ArrayList<String> listaPalabras) {

        if (nodo.valor != null) {
            listaPalabras.add(nodo.palabra);
        }

        for (int j = 0; j < 256; j++) {

            if (nodo.hijos.get(j) != null) {
                stringRecursivo(nodo.hijos.get(j), nivel + 1, listaPalabras);
            }
        }

    }

    // voy desde la raíz hasta el final de la palabra y luego recorro hacia arriba.
    // O(|clave| + |clave|) = O(|clave|)
    public void eliminar(String clave) {

        int largoElem = clave.length();
        char[] charList = clave.toCharArray();

        Nodo nodoActual = this.raiz;

        for (int i = 0; i < largoElem; i++) { // O(|clave|)
            int charCode = (int) charList[i];

            if (nodoActual.hijos.get(charCode) != null) {
                nodoActual = nodoActual.hijos.get(charCode); // O(1) por ser un Array.
            } else {
                return;
            }
        }
        if (nodoActual == null || nodoActual.valor == null) {
            return;
        }

        tamaño--;
        nodoActual.valor = null;
        nodoActual.palabra = null;

        for (int i = largoElem - 1; i > 0; i--) { // O(|clave|)
            int charCode = (int) charList[i];
            Nodo padre = nodoActual.padre;

            boolean eliminarNodo = true;

            for (int j = 0; j < 256; j++) { // O(1)
                if (padre.hijos.get(j) != null) {
                    eliminarNodo = false;
                }
            }
            if (eliminarNodo) { // si el padre tiene un unico hijo.
                padre.hijos.set(charCode, null); // borro ref padre -> hijo.
                nodoActual.padre = null; // borro ref hijo -> padre.
            } else {
                return;
            }

            nodoActual = padre;
        }

        return;
    }

    public T obtener(String clave) {
        int largoElem = clave.length();
        char[] charList = clave.toCharArray();

        Nodo nodoActual = this.raiz;

        for (int i = 0; i < largoElem; i++) { // O(|clave|)
            int charCode = (int) charList[i];

            if (nodoActual.hijos.get(charCode) != null) {
                nodoActual = nodoActual.hijos.get(charCode); // O(1) por ser un Array.
            } else {
                return null;
            }
        }

        return nodoActual.valor;
    }

    public int tamaño() { // O(1)
        return tamaño;
    }
}