package mongo_ejer_xerartenda;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/*
 EJERCICIO "xerartenda" CON MONGODB Y JAVA
 */
//IMPORTAR DRIVER DE MONGO ÚLTIMA V QUE TENGAMOS
public class Mongo_Ejer_xerartenda {

    //INSERCCIÓN
    //1) inserir este  documento : "p4",c1","pro3",5,"02/02/2019"
    public static void insertarDoc() {

        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        //se supone que no nos va a pedir credenciales, así que no los ponemos de momento
        MongoCollection<Document> collection = database.getCollection("pedidos");

        Document document = new Document();

        document.put("_id", "p4");
        document.put("codcli", "c1");
        document.put("codpro", "pro3");
        document.put("cantidade", 5);
        document.put("data", "02/02/2019");

        collection.insertOne(document);

    }

    //ACTUALIZACIÓN
    //2) actualizar un par clave valor por clave do documento : 
    //exemplo actualizar o campo codpro do pedido  p3 a o valor pro4
    public static void actualizarDoc() {

        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");

        BasicDBObject doc = new BasicDBObject();
        doc.put("_id", "p3");
        BasicDBObject newDoc = new BasicDBObject();
        newDoc.put("codpro", "pro4");

        BasicDBObject updateObject = new BasicDBObject();
        updateObject.put("$set", newDoc);

        collection.updateOne(doc, updateObject);

    }

    //INCREMENTAR
    //3)incrementar un par clave valor por clave. exemplo: aumentar en 7 a 
    //cantidade correspondente ao pedido p2 .
    public static void incrementarDoc() {

        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");

        BasicDBObject updateObject = new BasicDBObject();

        BasicDBObject doc = new BasicDBObject();

        doc.put("_id", "p2");

        updateObject.put("$inc", doc);

    }

    public static void main(String[] args) {

        actualizarDoc();

    }

}
