package aed;

public class SistemaSIU {

    // string es la clave. no deberia ir algo que diga materias, pues es cosa del
    // trie carreras
    Trie<Trie<DatosMateria>> Carreras;
    Trie<Integer> Estudiantes;

    public enum CargoDocente {
        AY2(30, 3),
        AY1(20, 2),
        JTP(100, 1),
        PROF(250, 0);

        private final int Soporte;
        private final int Indexacion;

        private CargoDocente(int Soporte, int Indexacion) {
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

    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        Trie<Trie<DatosMateria>> CarrerasT = new Trie<Trie<DatosMateria>>();
        Trie<Integer> EstudiantesTotal = new Trie<Integer>();

        for (int n = 0; n < infoMaterias.length; n++) {

            // creo un nuevo "DATOSMATERIA" donde esta el valor de la clave de cada carrera
            // y sus otros nombres
            DatosMateria Datos = new DatosMateria();

            ParCarreraMateria[] Materia = infoMaterias[n].getParesCarreraMateria();

            for (int j = 0; j < Materia.length; j++) {

                // pongo "vinculos" para modificar en datos, y agregar un puntero mas de ese
                // nombre alternativo
                ListaEnlazada<Trie<DatosMateria>> Vinculos = Datos.VinculosAMaterias();

                // si ya esta, solo agrego.
                if (CarrerasT.pertenece(Materia[j].getCarrera())) {

                    // Trie de materias, con aliasing
                    Trie<DatosMateria> ClaveMaterias = CarrerasT.obtener(Materia[j].getCarrera());

                    // el trie de Materias que ya estaba
                    Vinculos.agregarAdelante(ClaveMaterias);

                    // en claveMaterias, agrego el nombre y los datos. pero si ya esta, entonces no
                    // debo hacer eso
                    ClaveMaterias.agregar(Materia[j].getNombreMateria(), Datos);

                } else {
                    Trie<DatosMateria> TrieMaterias = new Trie<DatosMateria>();
                    Vinculos.agregarAdelante(TrieMaterias);

                    // pongo los datos, con la direccion del trie. lo que modifique en datos,
                    // deberia modificar en ese trie.
                    TrieMaterias.agregar(Materia[j].getNombreMateria(), Datos);

                    // agrego a carrera, la materia y el trie como valor
                    CarrerasT.agregar(Materia[j].getCarrera(), TrieMaterias);
                }
            }
        }

        for (int C = 0; C < libretasUniversitarias.length; C++) {

            // sin materias donde se inscribio a la hora de crear
            EstudiantesTotal.agregar(libretasUniversitarias[C], 0);
        }
        this.Estudiantes = EstudiantesTotal;
        this.Carreras = CarrerasT;

    }

    public DatosMateria ObtenerValorMateria(String carrera, String materia) {
        Trie<DatosMateria> TrieEstudiantes = Carreras.obtener(carrera);
        DatosMateria Datos = TrieEstudiantes.obtener(materia);
        return Datos;
    }

    public void inscribir(String estudiante, String carrera, String materia) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // ingresar a los datos es O(|n| + |m|)
        Datos.SumarEstudianteYOcuparCupo(estudiante); // O(1)
        int MateriasDeEstudiante = Estudiantes.obtener(estudiante); // LU acotado, O(1)
        Estudiantes.agregar(estudiante, MateriasDeEstudiante + 1); // O(1)

    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);// O(|n| + |m|)
        Datos.SumarDocente(cargo.getIndexacion()); // suma 1 docente buscado desde su indice que lo identifica
        int NuevoMaximo = Datos.ObtenerCantidadDocente(cargo.getIndexacion()) * cargo.getSoporte();

        // en teoria este for recorre cada docente como si estuvieran en un array - O(1)
        for (CargoDocente Docente : CargoDocente.values()) {

            // obtener el total soportable de ese tipo de docentes
            int SoporteTotal = Datos.ObtenerCantidadDocente(Docente.getIndexacion()) * Docente.getSoporte();

            // si tenemos un nuevo "maximo" entonces veremos los cupos de el luego
            // deagregar, y si los demas son 0 se queda como esta el maximo.
            if (SoporteTotal < NuevoMaximo) {
                NuevoMaximo = SoporteTotal;
            }
        }

        // si es el mismo, no cambiará, pues calcula el total y luego resta la cantidad
        // de estudiantes que hay en ese momento.
        Datos.CambiarMaximo(NuevoMaximo);

    }

    public int[] plantelDocente(String materia, String carrera) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // O(|n| + |m|)

        // devolver el array donde primero va el docente, jtp ayudante1 y ayudante 2 en
        // ese orden
        return Datos.CantidadDocente();
    }

    public void cerrarMateria(String materia, String carrera) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // O(|n| + |m|)
        ListaEnlazada<Trie<DatosMateria>> Vinculos = Datos.VinculosAMaterias(); // O(1)
        Iterador<Trie<DatosMateria>> Materia = Vinculos.iterador();

        // hice el it parandome y decir que si hay siguiente sea en el que estoy parado.
        // en otros no funciona, al cambiar tener en cuenta esto.
        while (Materia.haySiguiente()) {
            Trie<DatosMateria> AccesoRapidoAMaterias = Materia.siguiente();

            // "para cada materia, recorrer cada una de la materia y sus nombres
            // alternativos. esto es la sumatoria (todo el while + esto de eliminar)"
            AccesoRapidoAMaterias.eliminar(materia);
        }

        ListaEnlazada<String> EstudiantesEnMateria = Datos.EstudiantesEnMateria();
        Iterador<String> Estudiante = EstudiantesEnMateria.iterador();
        while (Estudiante.haySiguiente()) {
            String LU = Estudiante.siguiente();

            Estudiantes.agregar(LU, (Estudiantes.obtener(LU)) - 1); // O(1).
        }

    }

    public int inscriptos(String materia, String carrera) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // O(|n| + |m|)
        return Datos.CantidadEstudiantes(); // O(1)
    }

    public boolean excedeCupo(String materia, String carrera) {

        DatosMateria Datos = ObtenerValorMateria(carrera, materia);// recorrer carrera y materia O(|n| + |m|)
        int cuposDisponibles = Datos.Maximo(); // O(1)
        return cuposDisponibles < 0; // puede ser 0 y quiere decir que aun no se excedió

    }

    public String[] carreras() {
        return Carreras.toStringArray();
    }

    public String[] materias(String carrera) {
        return Carreras.obtener(carrera).toStringArray();
    }

    public int materiasInscriptas(String estudiante) {
        int CantidadMaterias = Estudiantes.obtener(estudiante); // LU acotado, O(1)
        return CantidadMaterias;
    }
}
