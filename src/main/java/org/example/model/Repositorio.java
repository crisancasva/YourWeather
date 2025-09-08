package org.example.model;

import com.mongodb.MongoException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Repositorio {

    private static final Logger logger = LoggerFactory.getLogger(Repositorio.class);
    private MongoCollection<Document> collection;
    private MongoClient mongoClient;

    public Repositorio() {
        try {
            mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("climaDB");
            collection = database.getCollection("consultas");
            logger.info("Conexión establecida con MongoDB");
        } catch (MongoTimeoutException e) {
            logger.error("Tiempo de espera excedido al conectar a MongoDB: ",  e);
            collection = null;
        } catch (MongoException e) {
            logger.error("Error de conexión a MongoDB: ", e);
            collection = null;
        } catch (Exception e) {
            logger.error("Error general al conectar con la base de datos: ", e);
            collection = null;
        }
    }

    public boolean isDisponible() {
        return collection != null;
    }

    public void guardarConsulta(JSONObject clima) {
        if (collection == null) {
            logger.warn("No se logró guardar en la base de datos: conexión no disponible");
            return;
        }
        try {
            Document doc = Document.parse(clima.toString());
            doc.put("fecha", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            collection.insertOne(doc);
            logger.info("Consulta guardada en MongoDB");
        } catch (Exception e) {
            logger.error("Error al guardar en base de datos: ", e);
            e.printStackTrace();
        }
    }

    public Iterable<Document> obtenerHistorial() {
        if (collection == null) {
            logger.warn("No se pudo obtener el historial: conexión no disponible.");
            return Collections.emptyList();
        }
        logger.debug("Obteniendo historial de consultas" );
        return collection.find();
    }

    public void cerrar() {
        if (mongoClient != null) {
            mongoClient.close();
            logger.info("Conexión con MongoDB cerrada.");
        }
    }
}
