import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.function.Consumer;

public class Main {
    public static void main(String[] args) {
        try (var mongoClient = MongoClients.create()) {

            var database = mongoClient.getDatabase("syn");

            var todoCollection = database.getCollection("todo");

            todoCollection.find(new Document("some change", new Document("$regex", "coffee")))
                    .forEach((Consumer<Document>) System.out::println);

            todoCollection.insertOne(new Document(Map.of(
                    "_id", new ObjectId(),
                    "task", "Drink some coffee",
                    "dateCreated", LocalDateTime.now(),
                    "done", false
            )));

            todoCollection.updateOne(
                    new Document("_id", new ObjectId("6294e6108b613d76ee74b59b")),
                new Document(Map.of(
                        "$set", new Document("done", false),
                        "$currentDate", new Document("dateDone", true),
                        "$unset", new Document("dateCreated", true)
                ))
            );

            todoCollection.deleteOne(new Document("_id", new ObjectId("6294e6108b613d76ee74b59b")));

        }
    }
}
