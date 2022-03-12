package content.management.service;

import content.management.dto.OrderedNodeListDto;
import content.management.exceptions.InvalidNodesException;
import content.management.exceptions.NodeListNotFoundException;
import content.management.model.Node;
import content.management.model.NodeList;
import content.management.repositories.NodeListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class NodeListService {

    private final NodeListRepository nodeListRepository;
    private final NodesQueryingService nodesQueryingService;

    @Autowired
    public NodeListService(NodeListRepository nodeListRepository, NodesQueryingService nodesQueryingService) {
        this.nodeListRepository = nodeListRepository;
        this.nodesQueryingService = nodesQueryingService;
    }

    public OrderedNodeListDto addList(NodeList nodeList) throws InvalidNodesException {

        if (!allNodesExist(nodeList.getNodes())) {
            throw new InvalidNodesException();
        }

        NodeList savedList = this.nodeListRepository.save(nodeList);

        return toOrderedNodeListDto(savedList);
    }


    public OrderedNodeListDto getListInOrder(String listId) throws NodeListNotFoundException {
        Optional<NodeList> nodeList = nodeListRepository.findById(listId);

        if (nodeList.isEmpty()) {
            throw new NodeListNotFoundException();
        }

        return toOrderedNodeListDto(nodeList.get());
    }

    private boolean allNodesExist(List<Node> nodes) {
        List<Node> invalidNodes = nodes.stream()
                .filter(node -> !nodesQueryingService.nodeExists(node.getType(), node.getId()))
                .collect(Collectors.toList());

        return invalidNodes.isEmpty();
    }

    private OrderedNodeListDto toOrderedNodeListDto(NodeList nodeList) {
        Comparator<Node> nodeComparator = Comparator.comparing(Node::getPosition);
        OrderedNodeListDto dto = new OrderedNodeListDto();

        dto.setId(nodeList.getId());
        dto.setName(nodeList.getName());
        dto.setNodes(nodeList.getNodes());
        dto.getNodes().sort(nodeComparator);

        return dto;
    }
}
