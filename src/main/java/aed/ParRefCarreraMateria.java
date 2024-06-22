package aed;

public class ParRefCarreraMateria {

    private Trie<DatosMateria> refCarrera;
    private String materia;

    public ParRefCarreraMateria(Trie<DatosMateria> refCarrera, String materia) {
        this.refCarrera = refCarrera;
        this.materia = materia;
    }

    public Trie<DatosMateria> getRefCarrera() {

        return refCarrera;
    }

    public String getMateria() {
        return this.materia;
    }

}
