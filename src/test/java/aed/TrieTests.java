package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class TrieTests {


    @Test
    void agregarYPertenece(){
        Trie triePrueba = new Trie();
        triePrueba.agregar("papu");
        assertEquals(1, triePrueba.tamaño());
        
        triePrueba.agregar("papurri");
        assertEquals(2, triePrueba.tamaño());

        triePrueba.agregar("papurrris");
        assertEquals(3, triePrueba.tamaño());

        triePrueba.agregar("eduardo");
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("eduardo");
        assertEquals(4, triePrueba.tamaño());

        triePrueba.agregar("ajklsdhnajksdhkjASDHAJKSDHKJASDHKJASD");
        assertEquals(5, triePrueba.tamaño());


        assertEquals(true, triePrueba.pertenece("papu"));
        assertEquals(true, triePrueba.pertenece("papurri"));
        assertEquals(true, triePrueba.pertenece("papurrris"));
        assertEquals(true, triePrueba.pertenece("eduardo"));
        assertEquals(true, triePrueba.pertenece("ajklsdhnajksdhkjASDHAJKSDHKJASDHKJASD"));

        assertEquals(false, triePrueba.pertenece("papurris"));
        assertEquals(false, triePrueba.pertenece("edu"));
        assertEquals(false, triePrueba.pertenece("eduard"));
        assertEquals(false, triePrueba.pertenece("eduardoe"));
        assertEquals(false, triePrueba.pertenece("ajklsdhnajksdhkjASDHAJKSDHKJASDHKJAS"));
        assertEquals(false, triePrueba.pertenece("2343242423"));
    }


    
}
