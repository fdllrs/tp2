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

    // la multiplicación representa la longitud de cada palabra para cada
    // iteración.
    // por ejemplo |materias| < = > sumatoria de la longitud de cada materia.

    // O(|materias|*|nombresMateria|*(|materia| + |carrera|) + |estudiantes|)
    public SistemaSIU(InfoMateria[] infoMaterias, String[] libretasUniversitarias) {
        Trie<Trie<DatosMateria>> listaCarreras = new Trie<Trie<DatosMateria>>();
        Trie<Integer> listaEstudiantes = new Trie<Integer>();

        // O( |materias|*|nombresMateria|*(|m| + |c|) )

        for (InfoMateria infoMateria : infoMaterias) {
            DatosMateria Datos = new DatosMateria();

            ParCarreraMateria[] paresCarreraMateria = infoMateria.getParesCarreraMateria();

            // O( |nombresMateria|*(|m| + |c|) )
            for (ParCarreraMateria parCarreraMateria : paresCarreraMateria) {

                String carrera = parCarreraMateria.getCarrera();
                String materia = parCarreraMateria.getNombreMateria();

                if (listaCarreras.pertenece(carrera)) { // O(|m| + |c|)
                    Trie<DatosMateria> listaMaterias = listaCarreras.obtener(carrera); // O(|c|)

                    ParRefCarreraMateria par = new ParRefCarreraMateria(listaMaterias, materia);
                    Datos.listaNombresMateria().agregarAdelante(par);

                    listaMaterias.agregar(materia, Datos); // O(|m|)

                } else { // O(|m| + |c|)
                    Trie<DatosMateria> nuevaCarrera = new Trie<DatosMateria>();

                    ParRefCarreraMateria par = new ParRefCarreraMateria(nuevaCarrera, materia);
                    Datos.listaNombresMateria().agregarAdelante(par);

                    nuevaCarrera.agregar(materia, Datos); // O(|m|)
                    listaCarreras.agregar(carrera, nuevaCarrera); // O(|c|)
                }

            }
        }
        // O(|E|)
        for (int i = 0; i < libretasUniversitarias.length; i++) {
            listaEstudiantes.agregar(libretasUniversitarias[i], 0);
        }
        this.Estudiantes = listaEstudiantes;
        this.Carreras = listaCarreras;

    }

    public DatosMateria ObtenerValorMateria(String carrera, String materia) { // O(|c| + |m|)
        Trie<DatosMateria> TrieEstudiantes = Carreras.obtener(carrera);
        DatosMateria Datos = TrieEstudiantes.obtener(materia);
        return Datos;
    }

    public void inscribir(String estudiante, String carrera, String materia) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // O(|c| + |m|)

        // LU acotado, O(1)
        Datos.SumarEstudianteYOcuparCupo(estudiante);
        int MateriasDeEstudiante = Estudiantes.obtener(estudiante);
        Estudiantes.agregar(estudiante, MateriasDeEstudiante + 1);

    }

    public void agregarDocente(CargoDocente cargo, String carrera, String materia) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);// O(|c| + |m|)
        Datos.SumarDocente(cargo.getIndexacion());
        int NuevoCupoMaximo = Datos.ObtenerCantidadDocente(cargo.getIndexacion()) * cargo.getSoporte();

        for (CargoDocente Docente : CargoDocente.values()) {// O(1)

            int CupoPorDocente = Datos.ObtenerCantidadDocente(Docente.getIndexacion()) * Docente.getSoporte();

            if (CupoPorDocente < NuevoCupoMaximo) {
                NuevoCupoMaximo = CupoPorDocente;
            }
        }

        Datos.ActualizarCupoDisponible(NuevoCupoMaximo);

    }

    public int[] plantelDocente(String materia, String carrera) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // O(|c| + |m|)

        return Datos.CantidadDocente();
    }

    // O(|c| + |nombresMateria|*|m| + |Em|)
    // |nombresMateria|*|m| se refiere a recorrer la longitud de la materia para
    // cada nombre alternativo.
    public void cerrarMateria(String materia, String carrera) {
        DatosMateria Datos = ObtenerValorMateria(carrera, materia); // O(|c| + |m|)
        Iterador<ParRefCarreraMateria> nombresMateria = Datos.listaNombresMateria().iterador();

        while (nombresMateria.haySiguiente()) { // O(|nombresMateria|)
            ParRefCarreraMateria parRefCarreraMateria = nombresMateria.siguiente();

            Trie<DatosMateria> refCarrera = parRefCarreraMateria.getRefCarrera();
            String nombreMateria = parRefCarreraMateria.getMateria();

            refCarrera.eliminar(nombreMateria); // O(|m|)
        }

        ListaEnlazada<String> EstudiantesEnMateria = Datos.EstudiantesEnMateria();
        Iterador<String> Estudiante = EstudiantesEnMateria.iterador();

        while (Estudiante.haySiguiente()) { // O(|Em|)
            String LU = Estudiante.siguiente();

            Estudiantes.agregar(LU, (Estudiantes.obtener(LU)) - 1); // O(1).
        }

    }

    public int inscriptos(String materia, String carrera) { // O(|c| + |m|)
        DatosMateria Datos = ObtenerValorMateria(carrera, materia);
        return Datos.CantidadEstudiantes();
    }

    public boolean excedeCupo(String materia, String carrera) { // O(|c| + |m|)

        DatosMateria Datos = ObtenerValorMateria(carrera, materia);
        int cupoDisponible = Datos.CupoDisponible();
        return cupoDisponible < 0;

    }

    public String[] carreras() { // O(|c|)
        return Carreras.toStringArray();
    }

    public String[] materias(String carrera) { // O(|m|)
        return Carreras.obtener(carrera).toStringArray();
    }

    public int materiasInscriptas(String estudiante) { // LU acotado, O(1)
        int CantidadMaterias = Estudiantes.obtener(estudiante);
        return CantidadMaterias;
    }
}
