package demo.concurrency;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;

// Creamos la clase Runnable
class PokeWorker implements Runnable {

    String pokemon;

    public PokeWorker(String pokemon) {
        this.pokemon = pokemon;
    }

    public void run() {
        System.out.println("Inicializando hilo...");

        // Hacemos el request a la API
        Request request = new Request.Builder().url("https://pokeapi.co/api/v2/pokemon/" + this.pokemon).build();
        String pokeResponse = new String();
        try (Response response = new OkHttpClient().newCall(request).execute()) {
            pokeResponse = response.body().string();
        } catch (IOException e) {
            System.out.println("Error contacting API: " + e.getMessage());
        }

        // Parseamos la respuesta de la API
        Gson gson = new Gson();
        LinkedTreeMap parsed = gson.fromJson(pokeResponse, new LinkedTreeMap().getClass());

        // Obtenemos que tipo de pokemon es
        String parsedTypes = new String();
        for (Object type : (List) parsed.get("types")) {
            Object typeObject = ((LinkedTreeMap) type).get("type");
            parsedTypes += ((LinkedTreeMap) typeObject).get("name") + " ";
        }

        // Mostramos el #, nombre y tipos
        System.out.println("#" + ((Double) parsed.get("order")).intValue() + " - " + parsed.get("name") + " - tipo: " + parsedTypes);
    }

}
