package demo.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {

    public static void main(String args[]) throws InterruptedException {
        // Definimos los pokemones que vamos a buscar
        String[] pokemons = {"articuno", "zapdos", "moltres"};

        // Crea el thread pool indicando la cantidad de hilos que tendrá
        ExecutorService taskExecutor = Executors.newFixedThreadPool(pokemons.length);

        // Por cada pokemon, creamos un Worker y lo mandamos a ejecutar
        for (int i = 0; i < pokemons.length; i++) {
            PokeWorker pokeWorker = new PokeWorker(pokemons[i]);
            taskExecutor.execute(pokeWorker);
        }

        // Finalizamos el ingreso de nuevos hilos y esperamos a que termine cada uno
        taskExecutor.shutdown();
        taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }

}
