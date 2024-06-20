package aed;

public class SistemaSIU {
    Trie<Trie<DatosMateria>> Carreras; //string es la clave  //no deberia ir algo que diga materias, pues es cosa del trie carreras
    Trie<Integer> Estudiantes;


    public enum CargoDocente{ //si no pongo public, el metodo agregar dice non public api
        AY2(30,3),
        AY1(20,2),
        JTP(100,1),
        PROF(250,0);

    private final int Soporte;
    private final int Indexacion;

    private CargoDocente(int Soporte, int Indexacion){
        this.Soporte = Soporte;
        this.Indexacion = Indexacion;
    }    
    public int getSoporte() {
            return this.Soporte;
        }
    
    public int getIndexacion() {
            return this.Indexacion;
        }
        
    }

   // el error estaba en que, lo que se pone como T es lo que debes definir, y en este caso era el valor.
    

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias){
        Trie<Trie<DatosMateria>> CarrerasT = new Trie<Trie<DatosMateria>>();
        Trie<Integer> EstudiantesTotal= new Trie<Integer>(); 
        	    for(int n = 0; n < infoMaterias.length; n++){ 
                    DatosMateria Datos = new DatosMateria(); // creo un nuevo "DATOSMATERIA" donde esta el valor de la clave de cada carrera y sus otros nombres
                    ParCarreraMateria[] Materia = infoMaterias[n].getParesCarreraMateria(); //array de pares de materias
                    
                    for(int j = 0; j < Materia.length; j++){
                        ListaEnlazada<Trie<DatosMateria>> Vinculos = Datos.VinculosAMaterias(); // pongo "vinculos" para modificar en datos, y agregar un puntero mas de ese nombre alternativo
                        if(CarrerasT.pertenece(Materia[j].getCarrera())){  //si ya esta, solo agrego. 
                            Trie<DatosMateria> ClaveMaterias = CarrerasT.obtener(Materia[j].getCarrera());  //Trie de materias, con aliasing
                            Vinculos.agregarAdelante(ClaveMaterias);  //el trie de Materias que ya estaba
                            ClaveMaterias.agregar(Materia[j].getNombreMateria(),Datos); //en claveMaterias, agrego el nombre y los datos. //pero si ya esta, entonces no debo hacer eso
                            
                        }
                        else{
                            Trie<DatosMateria> TrieMaterias = new Trie<DatosMateria>(); //creo un trie de Materias,                             
                            Vinculos.agregarAdelante(TrieMaterias);// agrego ese nuevo trie a "vinculo"
                            TrieMaterias.agregar(Materia[j].getNombreMateria(), Datos); //pongo los datos, con la direccion del trie. lo que modifique en datos, deberia modificar en ese trie.                           
                            CarrerasT.agregar(Materia[j].getCarrera(), TrieMaterias); //agrego a carrera, la materia y el trie como valor
                        } 
                    }
                }          
                //-----------------------------------
              for(int C = 0; C < libretasUniversitarias.length; C++ ){
                        EstudiantesTotal.agregar(libretasUniversitarias[C], 0);//sin materias donde se inscribio a la hora de crear
                         }
              this.Estudiantes = EstudiantesTotal;
              this.Carreras = CarrerasT;
           
                }
    
    public DatosMateria ObtenerValorMateria(String carrera, String materia){
        Trie<DatosMateria> TrieEstudiantes = Carreras.obtener(carrera);
        DatosMateria Datos = TrieEstudiantes.obtener(materia);
        return Datos;
    } 
    public void inscribir(String estudiante, String carrera, String materia){
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); //ingresar a los datos es O(\n\ + \m\)
        Datos.SumarEstudianteYOcuparCupo(estudiante); // O(1)
        int MateriasDeEstudiante = Estudiantes.obtener(estudiante); //acotado la LU entonces es O(1)
        Estudiantes.agregar(estudiante, MateriasDeEstudiante+1); // SUmo 1 a la cantidad de materias inscriptas del estudiante.
        //esto puedeser O(1) + O(1),
        
}
    public void agregarDocente(CargoDocente cargo, String carrera, String materia){
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);// O(\n\+\m\)
        Datos.SumarDocente(cargo.getIndexacion());	    //suma 1 docente buscado desde su indice que lo identifica
        int NuevoMaximo = Datos.ObtenerCantidadDocente(cargo.getIndexacion()) * cargo.getSoporte();
        for(CargoDocente Docente : CargoDocente.values()){ //en teoria este for recorre cada docente como si estarian en un array /O(1)
            int SoporteTotal = Datos.ObtenerCantidadDocente(Docente.getIndexacion()) * Docente.getSoporte();   // obtener el total soportable de ese tipo de docentes 
            if(SoporteTotal < NuevoMaximo){   // si tenemos un nuevo "maximo" entonces veremos los cupos de el luego de agregar, y si los demas son 0 se queda como esta el maximo.
                NuevoMaximo = SoporteTotal;  
             }
        }
        Datos.CambiarMaximo(NuevoMaximo); // si es el mismo, no cambiara, pues calcula el total y luego resta la cantidad de estudiantes que hay en ese momento. 
       
    }

    public int[] plantelDocente(String materia, String carrera){
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);// O(\n\ + \m\)
        return Datos.CantidadDocente(); // devolver el array donde primero va el docente, jtp ayudante1 y ayudante 2 en ese orden
    }

    public void cerrarMateria(String materia, String carrera){
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);	    // O(\n\ + \m\)
        ListaEnlazada<Trie<DatosMateria>> Vinculos = Datos.VinculosAMaterias(); //O(1)
        Iterador<Trie<DatosMateria>> Materia = Vinculos.iterador();
        while(Materia.haySiguiente() == true){ // hice el it parandome y decir que si hay siguiente sea en el que estoy parado. en otros no funciona, al cambiar tener en cuenta esto.
            Trie<DatosMateria> AccesoRapidoAMaterias = Materia.siguiente(); 
            AccesoRapidoAMaterias.eliminar(materia);  //"para cada materia, recorrer cada una de la materia y sus nombres alternativos. esto es la sumatoria (todo el while + esto de eliminar)"
        }
        // ---------------------------------------- 
        ListaEnlazada<String> EstudiantesEnMateria = Datos.EstudiantesEnMateria();
        Iterador<String> Estudiante = EstudiantesEnMateria.iterador();
        while(Estudiante.haySiguiente() == true){
            String LU = Estudiante.siguiente();
            Estudiantes.agregar(LU,(Estudiantes.obtener(LU)) - 1 ); // resto 1 a la cantidad de materias. al recorrer el trie, como es acotado sera O(1) siempre.        
        }
        
    }

    public int inscriptos(String materia, String carrera){
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);	    // O(\n\ + \m\)
        return Datos.CantidadEstudiantes();  //     O(1)
    }

    public boolean excedeCupo(String materia, String carrera){
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);// recorrer carrera y materia O(\n\ + \m\)
        int cuposDisponibles = Datos.Maximo(); //O(1)
        return cuposDisponibles < 0; // puede ser 0 y quiere decir que aun no se excedio
            
    }


    public String[] carreras(){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public String[] materias(String carrera){
        throw new UnsupportedOperationException("Método no implementado aún");	    
    }

    public int materiasInscriptas(String estudiante){
        int CantidadMaterias = Estudiantes.obtener(estudiante); //O(1) pues las claves estan acotadas.
        return CantidadMaterias;	    
    }
}
