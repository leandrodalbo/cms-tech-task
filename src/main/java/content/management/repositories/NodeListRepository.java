package content.management.repositories;

import content.management.model.NodeList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeListRepository extends MongoRepository<NodeList, String> {
}
