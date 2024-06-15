package aed;

import java.util.ArrayList;

@SuppressWarnings("unused") // TEMPORAL
public class Trie {

    private int tamaño;
    private Nodo raiz = new Nodo();


    private class Nodo{
        int valor; // <- REVISAR DESPUES

        Nodo padre;

        ArrayList<Nodo> hijos = new ArrayList<Nodo>(256);


        //inicializar con un nodo padre    
        public Nodo(Nodo padre){
            this.padre = padre;
            this.valor = 0;

            for(int i = 0; i < 256; i++){
                this.hijos.add(null);
            }

        }
        
        //inicializar vacío
        public Nodo(){
            this.padre = null;
            this.valor = 0;

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



    public boolean pertenece(String element){
        int largoElem = element.length();
        char[] charList = element.toCharArray();
        
        Nodo nodoActual = this.raiz;

        for(int i= 0; i < largoElem; i++){
            if (nodoActual == null) {break;}

            int charCode = (int) charList[i];
            nodoActual = nodoActual.hijos.get(charCode);
        }
        
        
        
        return (nodoActual != null && nodoActual.valor != 0);
    }

    public void agregar(String element){
        int largoElem = element.length();
        char[] charList = element.toCharArray();
        
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
                
            }
        }
        nodoActual.valor = 1;
        
    }

    public void eliminar(String element){
        return;
    }

    public String obtener(String element){
        return null;
    }


    
    public int tamaño(){
        return tamaño;
    }


}
