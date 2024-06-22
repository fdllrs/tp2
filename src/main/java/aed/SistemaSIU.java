package aed;

public class SistemaSIU {

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
        Trie<Trie<DatosMateria>> listaCarreras = new Trie<Trie<DatosMateria>>();
        Trie<Integer> listaEstudiantes = new Trie<Integer>();

        for (InfoMateria infoMateria : infoMaterias) {
            DatosMateria Datos = new DatosMateria();

            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria();
            for (ParCarreraMateria parCarreraMateria : paresCarreraMateria) {

                String carrera = parCarreraMateria.getCarrera();
                String materia = parCarreraMateria.getNombreMateria();

                if (listaCarreras.pertenece(carrera)) {
                    Trie<DatosMateria> listaMaterias = listaCarreras.obtener(carrera);
                    ParRefCarreraMateria par = new ParRefCarreraMateria(listaMaterias, materia);
                    Datos.listaNombresMateria().agregarAdelante(par);

                    listaMaterias.agregar(materia, Datos);
                } else {
                    Trie<DatosMateria> nuevaCarrera = new Trie<DatosMateria>();
                    ParRefCarreraMateria par = new ParRefCarreraMateria(nuevaCarrera, materia);
                    Datos.listaNombresMateria().agregarAdelante(par);

                    nuevaCarrera.agregar(materia, Datos);
                    listaCarreras.agregar(carrera, nuevaCarrera);
                }

            }
        }

        for (int i = 0; i < libretasUniversitarias.length; i++) {
            listaEstudiantes.agregar(libretasUniversitarias[i], 0);
        }
        this.Estudiantes = listaEstudiantes;
        this.Carreras = listaCarreras;

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
        Iterador<ParRefCarreraMateria> nombresMateria = Datos.listaNombresMateria().iterador();

        while (nombresMateria.haySiguiente()) {
            ParRefCarreraMateria parRefCarreraMateria = nombresMateria.siguiente();

            Trie<DatosMateria> refCarrera = parRefCarreraMateria.getRefCarrera();
            String nombreMateria = parRefCarreraMateria.getMateria();

            refCarrera.eliminar(nombreMateria);
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
