package content.management.controller;

import content.management.dto.NodeDto;
import content.management.dto.OrderedNodeListDto;
import content.management.exceptions.InvalidNodesException;
import content.management.exceptions.NodeListNotFoundException;
import content.management.model.NodeList;
import content.management.service.NodeListService;
import content.management.service.NodesQueryingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/")
public class CMSController {

    private final NodesQueryingService nodesQueryingService;
    private final NodeListService listService;

    @Autowired
    public CMSController(NodesQueryingService nodesQueryingService, NodeListService listService) {
        this.listService = listService;
        this.nodesQueryingService = nodesQueryingService;
    }

    @GetMapping("/nodes")
    public List<NodeDto> getAllNodes(@RequestParam("type") String type) {
        return nodesQueryingService.getAllNodesOfATypeWithoutFilters(type);
    }

    @PostMapping("/list")
    @ResponseStatus(HttpStatus.CREATED)
    public NodeList getAllNodes(@RequestBody NodeList nodeList) throws InvalidNodesException {
        return listService.addList(nodeList);
    }

    @GetMapping("/list/{list-id}")
    public OrderedNodeListDto getListWithNodesInOrder(@PathVariable("list-id") String listId) throws NodeListNotFoundException {
        return listService.getListInOrder(listId);
    }

}
