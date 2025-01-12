package content.management.model;

import lombok.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Value
@Document(collection = "nodelists")
public class NodeList {

    @Id
    String id;
    String name;
    List<Node> nodes;
}
