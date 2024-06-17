package aed;

import java.util.ArrayList;

public class Trie<T> {

    private int tamaño;
    private Nodo raiz = new Nodo();


    private class Nodo{
        T valor;

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
                Nodo nuevoNodo = new Nodo(nodoActual);
                nodoActual.hijos.set(charCode, nuevoNodo);
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
            int charCode = (int) charList[i];

            if (nodoActual.hijos.get(charCode) != null) {
                nodoActual = nodoActual.hijos.get(charCode);
            }
            else{
                return;
            }
        }
        if (nodoActual == null || nodoActual.valor == null){return;}


        tamaño--;
        nodoActual.valor = null;
        
        for(int i=largoElem - 1; i > 0; i--){
            int charCode = (int) charList[i];
            Nodo padre = nodoActual.padre;

            Nodo nodoConSoloUnHijo = new Nodo();
            nodoConSoloUnHijo.hijos.set(charCode, nodoActual);

            nodoActual.padre = null;                    // borro ref hijo -> padre
            
            
            if(padre.hijos.equals(nodoConSoloUnHijo.hijos)){     // si el padre tiene un unico hijo
                padre.hijos.set(charCode, null); // borro ref padre -> hijo
            }
            else{return;}

            nodoActual = padre;
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
