package aed;

public class DatosMateria {
     private int[] CantidadDeDocentes = new int[4]; 
     private int CargoMaximo;
     private int CantidadDeEstudiantes;
     private ListaEnlazada<String> EstudiantesEnMateria;
     private ListaEnlazada<Trie<DatosMateria>> VinculosAMaterias; //vinculos a materias de los demas Carreras
     
    

    public DatosMateria(){
        this.CargoMaximo = 0;
        this.CantidadDeEstudiantes = 0;
        this.EstudiantesEnMateria = new ListaEnlazada<String>();
        this.VinculosAMaterias = new ListaEnlazada<Trie<DatosMateria>>();
    }
    //TODO ES O(1) pues cada metodo hace operaciones acotadas.

    public int[] CantidadDocente(){
        return this.CantidadDeDocentes;
    }
    public int Maximo(){
        return this.CargoMaximo;
    }
    public void SumarEstudianteYOcuparCupo(String LU){ //resta al cargo a tener en cuenta y depaso suma la cantidad de estudiantes de esa materia. 
        this.CantidadDeEstudiantes = this.CantidadDeEstudiantes +1;
        this.CargoMaximo = this.CargoMaximo -1;
        EstudiantesEnMateria.agregarAdelante(LU);
    }
    public void CambiarMaximo(int Maximo){ //cambiar el maximo. 
        this.CargoMaximo = Maximo - CantidadDeEstudiantes; 
    } 
    public int ObtenerCantidadDocente(int Docente){ //obtener la cantidad de tal docente, O(1) por indexacion directa
        return CantidadDeDocentes[Docente];
    }
    public ListaEnlazada<String> EstudiantesEnMateria(){
        return this.EstudiantesEnMateria;

    }
    
    public void SumarDocente(int Docente){
        CantidadDeDocentes[Docente] = CantidadDeDocentes[Docente] +1; //aumenta al docente pasado en 1. //indexacion directa y sumo 1, O(1)
    }
    public ListaEnlazada<Trie<DatosMateria>> VinculosAMaterias(){
        return this.VinculosAMaterias;
    }
    public int CantidadEstudiantes (){
        return this.CantidadDeEstudiantes; //this por si, no da aliasing sin eso
    }
    
}
// el objetivo es poder tener una estructura para poder tener un vinculo.
