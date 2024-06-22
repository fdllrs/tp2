package aed;

public class DatosMateria {
    private int[] docentesPorCargo = new int[4];
    private int cupoDisponible;
    private int cantEstudiantes;
    private ListaEnlazada<String> EstudiantesEnMateria;
    private ListaEnlazada<ParRefCarreraMateria> listaNombresMateria;

    public DatosMateria() {
        this.cupoDisponible = 0;
        this.cantEstudiantes = 0;
        this.EstudiantesEnMateria = new ListaEnlazada<String>();
        this.listaNombresMateria = new ListaEnlazada<ParRefCarreraMateria>();
    }
    // todo es O(1) pues cada metodo hace operaciones acotadas.

    public int[] CantidadDocente() {
        return this.docentesPorCargo;
    }

    public int Maximo() {
        return this.cupoDisponible;
    }

    // resta al cargo a tener en cuenta y de paso suma la cantidad de estudiantes de
    // esa materia.
    public void SumarEstudianteYOcuparCupo(String LU) {
        this.cantEstudiantes = this.cantEstudiantes + 1;
        this.cupoDisponible = this.cupoDisponible - 1;
        EstudiantesEnMateria.agregarAdelante(LU);
    }

    public void CambiarMaximo(int Maximo) {
        this.cupoDisponible = Maximo - cantEstudiantes;
    }

    public int ObtenerCantidadDocente(int Docente) { // O(1) por indexacion directa
        return docentesPorCargo[Docente];
    }

    public ListaEnlazada<String> EstudiantesEnMateria() {
        return this.EstudiantesEnMateria;

    }

    public void SumarDocente(int Docente) { // O(1) por indexacion directa
        docentesPorCargo[Docente] = docentesPorCargo[Docente] + 1;
    }

    public ListaEnlazada<ParRefCarreraMateria> listaNombresMateria() {
        return this.listaNombresMateria;
    }

    public int CantidadEstudiantes() {
        return this.cantEstudiantes;
    }

}
