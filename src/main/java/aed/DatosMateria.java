package aed;

public class DatosMateria {
    private int[] CantidadDeDocentes = new int[4];
    private int CargoMaximo;
    private int CantidadDeEstudiantes;
    private ListaEnlazada<String> EstudiantesEnMateria;
    private ListaEnlazada<Trie<DatosMateria>> VinculosAMaterias; // vinculos a materias de los demas Carreras

    public DatosMateria() {
        this.CargoMaximo = 0;
        this.CantidadDeEstudiantes = 0;
        this.EstudiantesEnMateria = new ListaEnlazada<String>();
        this.VinculosAMaterias = new ListaEnlazada<Trie<DatosMateria>>();
    }
    // todo es O(1) pues cada metodo hace operaciones acotadas.

    public int[] CantidadDocente() {
        return this.CantidadDeDocentes;
    }

    public int Maximo() {
        return this.CargoMaximo;
    }

    // resta al cargo a tener en cuenta y de paso suma la cantidad de estudiantes de
    // esa materia.
    public void SumarEstudianteYOcuparCupo(String LU) {
        this.CantidadDeEstudiantes = this.CantidadDeEstudiantes + 1;
        this.CargoMaximo = this.CargoMaximo - 1;
        EstudiantesEnMateria.agregarAdelante(LU);
    }

    public void CambiarMaximo(int Maximo) {
        this.CargoMaximo = Maximo - CantidadDeEstudiantes;
    }

    public int ObtenerCantidadDocente(int Docente) { // O(1) por indexacion directa
        return CantidadDeDocentes[Docente];
    }

    public ListaEnlazada<String> EstudiantesEnMateria() {
        return this.EstudiantesEnMateria;

    }

    public void SumarDocente(int Docente) { // O(1) por indexacion directa
        CantidadDeDocentes[Docente] = CantidadDeDocentes[Docente] + 1;
    }

    public ListaEnlazada<Trie<DatosMateria>> VinculosAMaterias() {
        return this.VinculosAMaterias;
    }

    public int CantidadEstudiantes() {
        return this.CantidadDeEstudiantes;
    }

}
