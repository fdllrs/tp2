package aed;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class TrieTests {


    @Test
    void pertenece(){
        Trie<Integer> triePrueba = new Trie<Integer>();

        triePrueba.agregar("CS computación", 32);
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


        assertEquals(true, triePrueba.pertenece("CS computación"));
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


    void obtener(){
        Trie<ArrayList<Integer>> triePrueba = new Trie<ArrayList<Integer>>();

        triePrueba.agregar("CS computación", new ArrayList<Integer>(3));
        assertEquals(1, triePrueba.tamaño());
        
        triePrueba.agregar("CS Datos", new ArrayList<Integer>(3));
        assertEquals(2, triePrueba.tamaño());

        triePrueba.agregar("Matemática", new ArrayList<Integer>(3));
        assertEquals(3, triePrueba.tamaño());

        triePrueba.agregar("Biología", new ArrayList<Integer>(3));
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Biología", new ArrayList<Integer>(3));
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("Alimentos", new ArrayList<Integer>(3));
        assertEquals(5, triePrueba.tamaño());


    }



    
}
