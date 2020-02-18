package mongo_ejer_xerartenda;
//https://howtodoinjava.com/mongodb/mongodb-find-documents/

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import com.mongodb.client.model.Projections;
import static com.mongodb.client.model.Projections.include;
import org.bson.Document;

/*
 EJERCICIO "xerartenda" CON MONGODB Y JAVA
 */
//IMPORTAR DRIVER DE MONGO ÚLTIMA V QUE TENGAMOS
//PROBAR A PONERLOS TODOS COMO DOCUMENT, PARECE QUE SALE MEJOR
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

        Document doc = new Document();
        doc.put("_id", "p3");
        Document newDoc = new Document();
        newDoc.put("codpro", "pro4");

        Document updateObject = new Document();
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

        Document doc = new Document();

        Document newdoc = new Document();

        Document updateObject = new Document();

        doc.put("_id", "p2");
        newdoc.put("cantidade", 7);

        updateObject.put("$inc", newdoc);

        collection.updateOne(doc, updateObject);

    }

    //4) amosar o documento correspondente ao pedido p3
    public static void mostrarDoc() {

        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");

        Document buscar = new Document();

        buscar = collection.find(eq("_id", "p3")).first();

        System.out.println(buscar);

    }

    //5) amosar o codigo do cliente, o codigo do producto e a cantidade correspondentes ao pedido p1
    public static void mostrarCampos() {
        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");

        Document buscar = new Document();
        buscar = (Document) collection.find(eq("_id", "p1")).projection(include("codcli", "codpro", "cantidade")).first();

        System.out.println(buscar);

    }

    //6) amosar  o codigo do cliente e  o codigo do producto correspondentes 
    //aos pedidos que teñan no campo cantidade un valor superior a 2
    public static void mostrarCampos2() {
        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");
        //COMO VAMOS A RETORNAR MÁS DE UN RESULTADO, HAY QUE USAR UN ITERABLE !!!!
        FindIterable<Document> buscar = collection.find(gt("cantidade", 2)).projection(include("codcli", "codpro"));

        //RECORREMOS EL ITERABLE
        for (Document z : buscar) {

            String codcli = z.getString("codcli");
            String codpro = z.getString("codpro");

            System.out.println("CODCLI: " + codcli + " CODPRO: " + codpro);

        }

    }

    //7) amosar  o codigo do cliente e  o codigo do producto correspondentes 
    //aos pedidos que teñan no campo cantidade un valor superior a 2 e inferior a 5
    public static void mostrarCampos3() {
        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");

        FindIterable<Document> buscar = collection.find(and(gt("cantidade", 2), lt("cantidade", 5))).projection(include("codcli", "codpro"));

        //RECORREMOS EL ITERABLE
        for (Document z : buscar) {

            String codcli = z.getString("codcli");
            String codpro = z.getString("codpro");

            System.out.println("CODCLI: " + codcli + " CODPRO: " + codpro);

        }

    }

    //8) amosar o codigo do cliente e  o codigo do producto correspondentes 
    //a todos os pedidos.
    public static void mostrarCampos4() {
        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");

        FindIterable<Document> buscar = collection.find().projection(include("codcli", "codpro"));

        //RECORREMOS EL ITERABLE
        for (Document z : buscar) {
            //OJO, EL ID, A NO SER QUE PONGAMOS UN EXCLUDE DEL ID, SIEMPRE LO INCLUYE
            //SERÁ NUESTRA DECISIÓN SI QUEREMOS MOSTRARLO O NO
            String codcli = z.getString("codcli");
            String codpro = z.getString("codpro");

            System.out.println("CODCLI: " + codcli + " CODPRO: " + codpro);

        }

    }

    //9) aumentar  no seu dobre a cantidade correspondente ao pedido p4 .
    public static void aumentarDoble() {
        //NOS CONECTAMOS A MONGO
        MongoClient mongoClient = new MongoClient("localhost", 27017);

        //NOS CONECTAMOS A UNA BD
        MongoDatabase database = mongoClient.getDatabase("tenda");

        MongoCollection<Document> collection = database.getCollection("pedidos");
        //*********PRIMERO LO BUSCAMOS Y LUEGO LO MULTIPLICAMOS

        Document docFind = new Document();
        //ACORDARSE DE PONER EL FIRST !!!
        docFind = (Document) collection.find(eq("_id", "p4")).first();

        int cantidadeDoblada = 2 * docFind.getInteger("cantidade");

        Document doc2 = new Document();

        doc2.put("cantidade", cantidadeDoblada);

        Document doc3 = new Document();

        doc3.put("$set", doc2);

        collection.updateOne(docFind, doc3);

    }

    public static void main(String[] args) {
        //unsertarDoc();
        //actualizarDoc();
        //incrementarDoc();
        //mostrarDoc();
        //mostrarCampos();
        //mostrarCampos2();
        //mostrarCampos3();
        //mostrarCampos4();
        //aumentarDoble();

    }

}
