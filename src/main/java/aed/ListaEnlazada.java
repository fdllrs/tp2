package aed;

public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo primerNodo;
    private Nodo ultimoNodo;

    private int size;

    private class Nodo {
        Nodo anterior;
        Nodo siguiente;
        T valor;

        public Nodo(T v) {
            valor = v;
            anterior = null;
            siguiente = null;
        }
    }

    public ListaEnlazada() {
        size = 0;
        primerNodo = null;
        ultimoNodo = null;

    }

    public int longitud() {
        return size;
    }

    public void agregarAdelante(T elem) {
        Nodo nuevoNodo = new Nodo(elem);

        if (size == 0) {
            ultimoNodo = nuevoNodo;
        } else {
            primerNodo.anterior = nuevoNodo;
            nuevoNodo.siguiente = primerNodo;
        }
        primerNodo = nuevoNodo;
        size += 1;
    }

    public void agregarAtras(T elem) {
        Nodo nuevoNodo = new Nodo(elem);

        if (size == 0) {
            primerNodo = nuevoNodo;
        } else {
            ultimoNodo.siguiente = nuevoNodo;
            nuevoNodo.anterior = ultimoNodo;
        }
        ultimoNodo = nuevoNodo;
        size += 1;
    }

    public T obtener(int i) {
        Nodo nodoActual = primerNodo;

        for (int indice = 0; indice < i; indice++) {
            nodoActual = nodoActual.siguiente;
        }
        return (nodoActual.valor);
    }

    public void eliminar(int i) {
        Nodo nodoActual = primerNodo;
        Nodo nodoAnterior;
        Nodo nodoSiguiente;

        if (size == 1) {
            primerNodo = null;
            ultimoNodo = null;
            size -= 1;
            return;
        }
        for (int indice = 0; indice < i; indice++) {
            nodoActual = nodoActual.siguiente;
        }
        if (nodoActual == primerNodo) {
            primerNodo = nodoActual.siguiente;
            primerNodo.anterior = null;
        } else if (nodoActual == ultimoNodo) {
            ultimoNodo = nodoActual.anterior;
            ultimoNodo.siguiente = null;
        } else {
            nodoAnterior = nodoActual.anterior;
            nodoSiguiente = nodoActual.siguiente;
            nodoAnterior.siguiente = nodoSiguiente;
            nodoSiguiente.anterior = nodoAnterior;
        }

        size -= 1;

    }

    public void modificarPosicion(int i, T elem) {

        Nodo nodoActual = primerNodo;

        for (int indice = 0; indice < i; indice++) {
            nodoActual = nodoActual.siguiente;
        }

        nodoActual.valor = elem;

    }

    public ListaEnlazada<T> copiar() {
        ListaEnlazada<T> nuevaLista = new ListaEnlazada<T>();
        Iterador<T> itListaOriginal = this.iterador();

        while (itListaOriginal.haySiguiente()) {
            T siguienteElemento = itListaOriginal.siguiente();
            nuevaLista.agregarAtras(siguienteElemento);
        }

        return nuevaLista;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Iterador<T> itLista = lista.iterador();

        while (itLista.haySiguiente()) {
            T siguienteElemento = itLista.siguiente();
            this.agregarAtras(siguienteElemento);
        }
    }

    @Override
    public String toString() {
        Iterador<T> itLista = this.iterador();
        StringBuffer buffer = new StringBuffer();

        buffer.append("[");

        while (itLista.haySiguiente()) {
            T siguienteElemento = itLista.siguiente();
            buffer.append(siguienteElemento.toString());
            buffer.append(", ");
        }
        buffer.delete(buffer.length() - 2, buffer.length());
        buffer.append("]");

        return buffer.toString();
    }

    private class ListaIterador implements Iterador<T> {
        Nodo actual;

        public ListaIterador() {
            actual = primerNodo;
        }

        public boolean haySiguiente() {
            return actual != null;
        }

        public boolean hayAnterior() {
            return actual != primerNodo;
        }

        public T siguiente() {
            T valorPrevio = actual.valor;
            actual = actual.siguiente;
            return valorPrevio;
        }

        public T anterior() {
            if (actual == null) { // si pasa esto es pq "actual" es el ultimo nodo
                actual = ultimoNodo;
            } else {
                actual = actual.anterior;
            }
            return actual.valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }

}
