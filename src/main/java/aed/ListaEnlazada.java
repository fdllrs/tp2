package aed;

public class ListaEnlazada<T> {
    Nodo primero;
    Nodo ultimo;
    int size;

    private class Nodo {
        T valor;
        Nodo siguiente;
        Nodo anterior;
    
    private Nodo(T t) {
        valor = t; 
    }
    
     
    
    }

    public ListaEnlazada() {
        primero = null; 
        ultimo = null; 
        size = 0; 
        

    }
    public int longitud() {
        return size;    
           
    }

    public void agregarAdelante(T elem) {
        Nodo nuevo = new Nodo(elem);
        if (size == 1){
            nuevo.siguiente = primero; 
            ultimo = primero;  
            primero = nuevo;        
            ultimo.anterior = primero; 
        }
        else {
            if (size == 0) {
                primero = nuevo;
            }
            else{
                nuevo.siguiente = primero;
            primero.anterior = nuevo;
 
            
            primero = nuevo;
            } 

        } 
     
        size = size +1;
    }

    public void agregarAtras(T elem) {
        Nodo nuevo = new Nodo(elem);
    
        if (primero == null){
            primero = nuevo;
            
      }
         else {
            if (primero.siguiente == null){
                primero.siguiente = nuevo;
                nuevo.anterior = primero; 
                ultimo = nuevo;
            
            }
            else{
            Nodo actual = primero;
            while (actual.siguiente != null) { 
                actual = actual.siguiente; 
            }
            actual.siguiente = nuevo;
            nuevo.anterior = actual;
            ultimo = nuevo;     
         }
        }   
         size = size +1;
    }

    public T obtener(int i) {
        Nodo actual = primero; 
        for (int r = 0; r < i; r++){ 
            actual = actual.siguiente;     
        } 
        return actual.valor;

    }

    public void eliminar(int i) {
        Nodo actual = primero; 
        for (int r = 0; r < i; r++) { 
            actual = actual.siguiente; 
        }
        if ((actual == primero) &&(actual.siguiente != null) ){ 
            primero = actual.siguiente; 
            primero.anterior = null;
        }
        else {
            if ((actual == primero) && (actual.siguiente == null)){
                   primero = null;
            }
            else {
                if (actual == ultimo){ 
                    ultimo = actual.anterior; 
                    ultimo.siguiente = null;
                }
                else{ 
                 Nodo anterior1 = actual.anterior;
                 Nodo siguiente1 = actual.siguiente;
                 anterior1.siguiente = siguiente1;
                 siguiente1.anterior = anterior1; 
                }
            }
             
        }
       size = size - 1;
    }   

    public void modificarPosicion(int indice, T elem) {
        Nodo actual = primero;
        for(int n = 0; n < indice; n++){
             actual = actual.siguiente;
        }
        actual.valor = elem;   
    } 

    public ListaEnlazada<T> copiar() {
        return new ListaEnlazada<>(this);
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        Nodo primer1 = new Nodo(lista.primero.valor);
        primer1.anterior = null;
        primer1.siguiente = lista.primero.siguiente;
        Nodo ultimo1 = new Nodo(lista.ultimo.valor);
        ultimo1.anterior = lista.ultimo.anterior;
        ultimo1.siguiente = null;
        this.primero = primer1;
        this.ultimo = ultimo1;
        this.size = lista.size;
        
        }
        
    

    @Override
    public String toString() {
             StringBuffer dbuffer = new StringBuffer();
             Nodo actual = primero;
             dbuffer.append("[");
             for (int n = 0; n < size-1; n++) { 
                 dbuffer.append(actual.valor);
                 dbuffer.append(", ");
                 actual = actual.siguiente;  
             }
             dbuffer.append(actual.valor);  
             dbuffer.append("]");
             return dbuffer.toString();       
                
                
        }
        
    

    private class ListaIterador implements Iterador<T> {
        int dedito;
        
        
        private ListaIterador(){
            this.dedito = 0;
        }

        public boolean haySiguiente() {
            Nodo actual = primero; 
            for (int n = 0; n<dedito; n++){
                  actual = actual.siguiente;
            }
            if (actual != null){
                return true;
            }
            else {
               return false;
            } 
       
        }

        public boolean hayAnterior() {
            Nodo actual = primero;
            if (dedito == size && size != 0) {
                return true;
            }
            else {
                if (actual == null){
                return false;
            } 
            else {
            for (int n = 0; n<dedito; n++){ 
                  actual = actual.siguiente;
            }
            if (actual.anterior != null){
                return true;
            } 
            else {
               return false;
            } 
        }
          }
  } 
        public T siguiente() {
           Nodo actual = primero;
           for (int n = 0; n < dedito; n++){
            actual = actual.siguiente;
           }
           this.dedito = dedito +1;
           return actual.valor;
        }

        public T anterior() {
            Nodo actual = primero;
            if (dedito == size){
                return ultimo.valor;
            }
           else {
            for (int n = 0; n < dedito; n++){
            actual = actual.siguiente;
           }
           actual = actual.anterior;
           this.dedito = dedito-1;
           return actual.valor;
        }
    }
}
    public Iterador<T> iterador() {
       return new ListaIterador(); 
         
      }

    

}
