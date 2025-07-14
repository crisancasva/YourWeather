package org.example.model;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.FindIterable;


import java.time.LocalDateTime;

public class Repositorio {

    private final MongoCollection<Document> collection;

    public Repositorio() {
        // Conexión al servidor MongoDB local
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        // Acceder a la base de datos y colección
        MongoDatabase database = mongoClient.getDatabase("climaDB");
        collection = database.getCollection("consultas");
    }

    public void guardarConsulta(String ciudad, double temperatura, int humedad) {
        Document doc = new Document("ciudad", ciudad)
                .append("temperatura", temperatura)
                .append("humedad", humedad)
                .append("fecha", LocalDateTime.now().toString());

        collection.insertOne(doc);
    }


    public FindIterable<Document> obtenerHistorial() {
        return collection.find();
    }

}
