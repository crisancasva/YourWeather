package org.example.model;

import com.mongodb.MongoTimeoutException;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import org.json.JSONObject;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class Repositorio {

    private MongoCollection<Document> collection;
    private MongoClient mongoClient;

    public Repositorio() {
        try {
            // Conexión al servidor MongoDB local
            mongoClient = MongoClients.create("mongodb://localhost:27017");

            // Acceder a la base de datos y colección
            MongoDatabase database = mongoClient.getDatabase("climaDB");
            collection = database.getCollection("consultas");
        }catch(MongoTimeoutException e){
            System.out.println("No hay conexion a la base de datos "+ e.getMessage());
            collection = null;
        } catch (Exception e) {
            System.out.println("Error general al conectar con la base de datos "+ e.getMessage());
            collection = null;
        }
    }
    public boolean isDisponible() {
        return collection != null;
    }

    public void guardarConsulta(JSONObject clima) {
        if(collection==null){
            System.out.println("No se logro guardar en la base de datos, conexion no disponible");
            return;
        }
        try {
            Document doc = Document.parse(clima.toString());
            doc.put("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            collection.insertOne(doc);
        } catch (Exception e) {
            System.out.println("Error al guardar en base de datos: " + e.getMessage());
        }
    }

    public FindIterable<Document> obtenerHistorial() {
        if (collection == null) {
            System.out.println("No se pudo obtener el historial: conexión no disponible.");

        }

        return collection.find();
    }
}
