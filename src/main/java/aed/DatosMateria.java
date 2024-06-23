package aed;

public class DatosMateria {
    private int[] docentesPorCargo = new int[4];
    private int cupoDisponible;
    private int cantEstudiantes;
    private ListaEnlazada<String> listaLibretas;
    private ListaEnlazada<ParRefCarreraMateria> listaNombresMateria;

    public DatosMateria() {
        this.cupoDisponible = 0;
        this.cantEstudiantes = 0;
        this.listaLibretas = new ListaEnlazada<String>();
        this.listaNombresMateria = new ListaEnlazada<ParRefCarreraMateria>();
    }
    // todo es O(1) pues cada metodo o bien hace operaciones acotadas o bien
    // devuelve variables.

    public int[] CantidadDocente() {
        return this.docentesPorCargo;
    }

    public int CupoDisponible() {
        return this.cupoDisponible;
    }

    public void SumarEstudianteYOcuparCupo(String LU) {
        this.cantEstudiantes = this.cantEstudiantes + 1;
        this.cupoDisponible = this.cupoDisponible - 1;
        listaLibretas.agregarAdelante(LU);
    }

    public void ActualizarCupoDisponible(int cupoActual) {
        this.cupoDisponible = cupoActual - cantEstudiantes;
    }

    public int ObtenerCantidadDocente(int Docente) {
        return docentesPorCargo[Docente];
    }

    public ListaEnlazada<String> EstudiantesEnMateria() {
        return this.listaLibretas;

    }

    public void SumarDocente(int Docente) {
        docentesPorCargo[Docente] = docentesPorCargo[Docente] + 1;
    }

    public ListaEnlazada<ParRefCarreraMateria> listaNombresMateria() {
        return this.listaNombresMateria;
    }

    public int CantidadEstudiantes() {
        return this.cantEstudiantes;
    }

}
