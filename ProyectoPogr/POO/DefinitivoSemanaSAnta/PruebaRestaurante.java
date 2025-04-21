import java.util.*;

public class Restaurante {
    private Map<String, IFastFood> mapaRecetasRestaurante;
    private Menu menu;
    private static final int TIEMPO_MAXIMO_MENU = 30;

    public Restaurante() {
        this.mapaRecetasRestaurante = new HashMap<>();
        this.menu = new Menu();
    }

    public void crearReceta(String nombre, ArrayList<String> ingredientes, ArrayList<String> pasosPreparacion, String tipo) {
        IFastFood receta;
        if (tipo.equalsIgnoreCase("pizza")) {
            receta = new Pizza(nombre, ingredientes, pasosPreparacion);
        } else if (tipo.equalsIgnoreCase("sandwich")) {
            receta = new Sandwich(nombre, ingredientes, pasosPreparacion);
        } else {
            System.out.println("Tipo de receta no válido.");
            return;
        }
        mapaRecetasRestaurante.put(nombre, receta);
        System.out.println("Receta '" + nombre + "' creada y almacenada.");
    }

    public void anadirRecetaAlMenu(IFastFood receta) throws TiempoException {
        int tiempoTotalActual = 0;
        for (IFastFood item : menu.getElementosMenu()) {
            tiempoTotalActual += item.tiempoDePreparacion();
        }
        if (tiempoTotalActual + receta.tiempoDePreparacion() > TIEMPO_MAXIMO_MENU) {
            throw new TiempoException("No se puede añadir '" + ((Receta) receta).getNombre() + "'. El tiempo total del menú excedería los " + TIEMPO_MAXIMO_MENU + " minutos.");
        }
        menu.getElementosMenu().add(receta);
        System.out.println("Receta '" + ((Receta) receta).getNombre() + "' añadida al menú.");
    }

    public void anadirRecetaAlMenu() throws TiempoException {
        if (mapaRecetasRestaurante.isEmpty()) {
            System.out.println("No hay recetas disponibles en el restaurante.");
            return;
        }

        System.out.println("Recetas disponibles:");
        int i = 1;
        List<String> nombresRecetas = new ArrayList<>(mapaRecetasRestaurante.keySet());
        for (String nombre : nombresRecetas) {
            System.out.println(i + ". " + nombre);
            i++;
        }

        Scanner scanner = new Scanner(System.in);
        int opcion;
        try {
            System.out.print("Elige el número de la receta para añadir al menú: ");
            opcion = scanner.nextInt();
            if (opcion > 0 && opcion <= nombresRecetas.size()) {
                IFastFood recetaSeleccionada = mapaRecetasRestaurante.get(nombresRecetas.get(opcion - 1));
                anadirRecetaAlMenu(recetaSeleccionada);
            } else {
                System.out.println("Opción no válida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Entrada no válida. Debes ingresar un número.");
        }
    }

    public double tiempoMedio() {
        if (menu.getElementosMenu().isEmpty()) {
            return -1;
        }
        int tiempoTotal = 0;
        for (IFastFood receta : menu.getElementosMenu()) {
            tiempoTotal += receta.tiempoDePreparacion();
        }
        return (double) tiempoTotal / menu.getElementosMenu().size();
    }

    public double porcentajeVegetariano() {
        if (menu.getElementosMenu().isEmpty()) {
            return 0;
        }
        int contadorVegetariano = 0;
        for (IFastFood receta : menu.getElementosMenu()) {
            if (receta.isVegetariano()) {
                contadorVegetariano++;
            }
        }
        return (double) contadorVegetariano / menu.getElementosMenu().size() * 100;
    }

    public static void main(String[] args) {
        Restaurante miRestaurante = new Restaurante();

        ArrayList<String> ingredientesPizza = new ArrayList<>();
        ingredientesPizza.add("Masa");
        ingredientesPizza.add("Tomate");
        ingredientesPizza.add("Queso");
        ingredientesPizza.add("Pepperoni");

        ArrayList<String> pasosPizza = new ArrayList<>();
        pasosPizza.add("Extender la masa");
        pasosPizza.add("Añadir tomate");
        pasosPizza.add("Añadir queso y pepperoni");
        pasosPizza.add("Hornear");

        miRestaurante.crearReceta("Pizza Pepperoni", ingredientesPizza, pasosPizza, "pizza");

        ArrayList<String> ingredientesSandwich = new ArrayList<>();
        ingredientesSandwich.add("Pan");
        ingredientesSandwich.add("Lechuga");
        ingredientesSandwich.add("Tomate");
        ingredientesSandwich.add("Cebolla");
        ingredientesSandwich.add("Queso");

        ArrayList<String> pasosSandwich = new ArrayList<>();
        pasosSandwich.add("Cortar el pan");
        pasosSandwich.add("Añadir ingredientes");
        pasosSandwich.add("Cerrar el sandwich");
        pasosSandwich.add("Planchar");

        miRestaurante.crearReceta("Sandwich Vegetal", ingredientesSandwich, pasosSandwich, "sandwich");

        ArrayList<String> ingredientesPizzaVeggie = new ArrayList<>();
        ingredientesPizzaVeggie.add("Masa");
        ingredientesPizzaVeggie.add("Tomate");
        ingredientesPizzaVeggie.add("Queso");
        ingredientesPizzaVeggie.add("Champiñones");
        ingredientesPizzaVeggie.add("Pimiento");

        ArrayList<String> pasosPizzaVeggie = new ArrayList<>();
        pasosPizzaVeggie.add("Extender la masa");
        pasosPizzaVeggie.add("Añadir tomate");
        pasosPizzaVeggie.add("Añadir queso y champiñones y pimiento");
        pasosPizzaVeggie.add("Hornear");

        miRestaurante.crearReceta("Pizza Vegetariana", ingredientesPizzaVeggie, pasosPizzaVeggie, "pizza");

        System.out.println("\nAñadiendo recetas al menú:");
        try {
            miRestaurante.anadirRecetaAlMenu();
            miRestaurante.anadirRecetaAlMenu();
        } catch (TiempoException e) {
            System.err.println("Error al añadir receta al menú: " + e.getMessage());
        }

        double tiempoMedio = miRestaurante.tiempoMedio();
        if (tiempoMedio != -1) {
            System.out.println("\nTiempo medio de preparación del menú: " + tiempoMedio + " minutos.");
        } else {
            System.out.println("\nEl menú está vacío.");
        }

        double porcentajeVegetariano = miRestaurante.porcentajeVegetariano();
        System.out.println("Porcentaje de recetas vegetarianas en el menú: " + porcentajeVegetariano + "%.");
    }
}