package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class TrieTests {


    @Test
    void pertenece(){
        Trie<Integer> triePrueba = new Trie<Integer>();


        triePrueba.agregar("CS Computación", 32);
        assertEquals(1, triePrueba.tamaño());
        
        triePrueba.agregar("CS Datos", 1);
        assertEquals(2, triePrueba.tamaño());

        triePrueba.agregar("Matemática", 2);
        assertEquals(3, triePrueba.tamaño());

        triePrueba.agregar("Biología", 500);
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Biología", 577);
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Alimentos", 0);
        assertEquals(5, triePrueba.tamaño());


        assertEquals(true, triePrueba.pertenece("CS Computación"));
        assertEquals(true, triePrueba.pertenece("CS Datos"));
        assertEquals(true, triePrueba.pertenece("Matemática"));
        assertEquals(true, triePrueba.pertenece("Biología"));
        assertEquals(true, triePrueba.pertenece("Alimentos"));

        assertEquals(false, triePrueba.pertenece("CS computacion"));
        assertEquals(false, triePrueba.pertenece("CS datos"));
        assertEquals(false, triePrueba.pertenece("CS Dato"));
        assertEquals(false, triePrueba.pertenece("CS Datoss"));
        assertEquals(false, triePrueba.pertenece("Alimento"));
        assertEquals(false, triePrueba.pertenece("Matematica"));
    }

    @Test
    void obtener(){
        Trie<ArrayList<Integer>> triePrueba = new Trie<ArrayList<Integer>>();
        

        triePrueba.agregar("CS Computación", new ArrayList<Integer>(Arrays.asList(4,2,6)));
        assertEquals(1, triePrueba.tamaño());
        
        triePrueba.agregar("CS Datos", new ArrayList<Integer>(Arrays.asList(6,1,1)));
        assertEquals(2, triePrueba.tamaño());

        triePrueba.agregar("Matemática", new ArrayList<Integer>(Arrays.asList(1,0,3)));
        assertEquals(3, triePrueba.tamaño());

        triePrueba.agregar("Biología", new ArrayList<Integer>(Arrays.asList(4,122,60)));
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Biología", new ArrayList<Integer>(Arrays.asList(400,2000,60000)));
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Alimentos", new ArrayList<Integer>(Arrays.asList(0,0,0)));
        assertEquals(5, triePrueba.tamaño());

        assertEquals(new ArrayList<Integer>(Arrays.asList(4,2,6)), triePrueba.obtener("CS Computación"));
        assertEquals(new ArrayList<Integer>(Arrays.asList(6,1,1)), triePrueba.obtener("CS Datos"));
        assertEquals(new ArrayList<Integer>(Arrays.asList(1,0,3)), triePrueba.obtener("Matemática"));
        assertEquals(new ArrayList<Integer>(Arrays.asList(400,2000,60000)), triePrueba.obtener("Biología"));
        assertEquals(new ArrayList<Integer>(Arrays.asList(0,0,0)), triePrueba.obtener("Alimentos"));

        assertEquals(null, triePrueba.obtener("CS computacion"));
        assertEquals(null, triePrueba.obtener("CS computació"));
        assertEquals(null, triePrueba.obtener("CS datos"));
        assertEquals(null, triePrueba.obtener("matemática"));
        assertEquals(null, triePrueba.obtener("Biologia"));
        assertEquals(null, triePrueba.obtener("Alimentoss"));
        assertEquals(null, triePrueba.obtener("lernajlkaenrkjnrkjerer"));




    }

    @Test
    void eliminar(){
        Trie<Integer> triePrueba = new Trie<Integer>();

        
        triePrueba.agregar("CS Computación", 32);
        assertEquals(1, triePrueba.tamaño());
        
        triePrueba.agregar("CS Datos", 1);
        assertEquals(2, triePrueba.tamaño());

        triePrueba.agregar("Matemática", 2);
        assertEquals(3, triePrueba.tamaño());

        triePrueba.agregar("Biología", 500);
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Biología", 577);
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Alimentos", 0);
        assertEquals(5, triePrueba.tamaño());





        triePrueba.eliminar("Alimentos");

        assertEquals(4, triePrueba.tamaño());

        triePrueba.eliminar("Alimentos");

        assertEquals(4, triePrueba.tamaño());
        assertEquals(null, triePrueba.obtener("Alimentos"));

        triePrueba.eliminar("Biología");

        assertEquals(3, triePrueba.tamaño());
        assertEquals(null, triePrueba.obtener("Biología"));

        triePrueba.eliminar("CS Computación");

        assertEquals(2, triePrueba.tamaño());
        assertEquals(null, triePrueba.obtener("CS Computación"));





        assertEquals(false, triePrueba.pertenece("CS computación"));
        assertEquals(false, triePrueba.pertenece("Biología"));
        assertEquals(false, triePrueba.pertenece("Alimentos"));

        triePrueba.agregar("Alimentos", 0);
        assertEquals(3, triePrueba.tamaño());

        assertEquals(true, triePrueba.pertenece("Alimentos"));
        assertEquals(true, triePrueba.pertenece("CS Datos"));
        assertEquals(true, triePrueba.pertenece("Matemática"));

    }



    @Test
    void pertenece2(){
        Trie<Integer> triePrueba = new Trie<Integer>();

        triePrueba.agregar("abc", 1);
        triePrueba.agregar("abcd", 2);
        triePrueba.agregar("abcde", 3);
        triePrueba.agregar("abcdefg", 4);


        triePrueba.eliminar("abcde");

        assertEquals(true, triePrueba.pertenece("abc"));

    }



    
}
