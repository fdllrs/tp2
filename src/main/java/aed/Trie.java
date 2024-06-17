package aed;

import java.util.ArrayList;

@SuppressWarnings("unused") // TEMPORAL
public class Trie<T> {

    private int tamaño;
    private Nodo raiz = new Nodo();


    private class Nodo{
        T valor; // <- REVISAR DESPUES

        Nodo padre;

        ArrayList<Nodo> hijos = new ArrayList<Nodo>(256);


        //inicializar con un nodo padre    
        public Nodo(Nodo padre){
            this.padre = padre;
            this.valor = null;

            for(int i = 0; i < 256; i++){
                this.hijos.add(null);
            }

        }
        
        //inicializar vacío
        public Nodo(){
            this.padre = null;
            this.valor = null;

            for(int i = 0; i < 256; i++){
                this.hijos.add(null);
            }
        }

    }

    //inicializar vacío
    public Trie(){
        this.tamaño = 0;
        this.raiz = new Nodo();
                    
        
    }



    public boolean pertenece(String clave){
        int largoElem = clave.length();
        char[] charList = clave.toCharArray();
        
        Nodo nodoActual = this.raiz;

        for(int i= 0; i < largoElem; i++){
            if (nodoActual == null) {break;}

            int charCode = (int) charList[i];
            nodoActual = nodoActual.hijos.get(charCode);
        }
        
        
        
        return (nodoActual != null && nodoActual.valor != null);
    }

    public void agregar(String clave, T valor){
        boolean agregoNodo = false;

        int largoElem = clave.length();
        char[] charList = clave.toCharArray();
        
        Nodo nodoActual = this.raiz;

        for(int i= 0; i < largoElem; i++){
            int charCode = (int) charList[i];

            if (nodoActual.hijos.get(charCode) != null) {
                nodoActual = nodoActual.hijos.get(charCode);
            }
            else{
                Nodo nuevNodo = new Nodo(nodoActual);
                nodoActual.hijos.set(charCode, nuevNodo);
                nodoActual = nodoActual.hijos.get(charCode);
                agregoNodo = true;
            }
        }
        if(agregoNodo){tamaño++;}
            
        nodoActual.valor = valor;
        
    }

    public void eliminar(String clave){

        int largoElem = clave.length();
        char[] charList = clave.toCharArray();
        
        Nodo nodoActual = this.raiz;
        

        for(int i=0; i < largoElem; i++){




        }
        return;
    }

    public T obtener(String clave){
        int largoElem = clave.length();
        char[] charList = clave.toCharArray();
        
        Nodo nodoActual = this.raiz;

        for(int i= 0; i < largoElem; i++){
            int charCode = (int) charList[i];

            if (nodoActual.hijos.get(charCode) != null) {
                nodoActual = nodoActual.hijos.get(charCode);
            }
            else{
                return null;
            }
        }

        return nodoActual.valor;
    }


    
    public int tamaño(){
        return tamaño;
    }


}
