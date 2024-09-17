//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package pack;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import pack.MProp.Constant;

public class MDB {
    public static final String USERS_COLLECTION = "users";
    public static final String METADATA_COLLECTION = "metadata";
    public static final String USERS_CPF = "cpf";
    public static final String USERS_ACTIVE = "active";
    private static MongoClient client;

    private MDB() {
    }

    public static boolean checkUser(String cpf) {
        MongoCursor<Document> iterator = getDatabase().getCollection("users").find((new Document("cpf", cpf)).append("active", true)).iterator();
        client.close();
        return iterator.hasNext();
    }

    public static void sumBaixadas(int value){
        int baixadas = 0;
        FindIterable<Document> requerentesCursor =  getDatabase().getCollection(METADATA_COLLECTION).find(
                new BasicDBObject().append("_id", new ObjectId("63bc4936811101e1bc443ea1")));
        if (requerentesCursor.first() != null) {
            Document reqt = requerentesCursor.first();
            baixadas = reqt.getInteger("baixadas");

            getDatabase().getCollection(METADATA_COLLECTION).updateOne(Filters.eq("_id", new ObjectId("63bc4936811101e1bc443ea1")), Updates.set("baixadas", baixadas + value));
        }
        client.close();
    }

    public static MongoDatabase getDatabase() {
        try {
            MongoClientURI uri = new MongoClientURI(MProp.getString(Constant.DB_ADMIN));
            client = new MongoClient(uri);
            return client.getDatabase(uri.getDatabase());
        } catch (Exception var1) {
            var1.printStackTrace();
            return null;
        }
    }
}
