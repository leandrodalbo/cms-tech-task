package content.management.service;

import content.management.dto.OrderedNodeListDto;
import content.management.exceptions.InvalidNodesException;
import content.management.exceptions.NodeListNotFoundException;
import content.management.model.Node;
import content.management.model.NodeList;
import content.management.repositories.NodeListRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NodeListServiceTest {

    @Mock
    NodesQueryingService nodesQueryingService;

    @Mock
    NodeListRepository repository;

    @InjectMocks
    NodeListService listService;

    @Test
    public void willGetAnOrderedNodeList() throws NodeListNotFoundException {
        NodeList list = new NodeList("abc", "none", Arrays.asList(new Node("1001", "SPORT", 1), new Node("1001", "SPORT", 0)));

        when(repository.findById(anyString()))
                .thenReturn(Optional.of(list));

        OrderedNodeListDto dto = listService.getListInOrder("abc");

        assertThat(dto.getNodes().get(0).getPosition()).isEqualTo(0);
        assertThat(dto.getNodes().get(1).getPosition()).isEqualTo(1);
    }

    @Test
    public void willThrowExceptionWithAnInvalidListId() throws NodeListNotFoundException {
        when(repository.findById(anyString()))
                .thenReturn(Optional.empty());

        NodeListNotFoundException exception = assertThrows(NodeListNotFoundException.class, () -> listService.getListInOrder("invalidId"));

        assertThat(exception.getMessage()).isEqualTo("List Not Found");
    }

    @Test
    public void addListFailsIfANodeDoesNotExist() {
        NodeList list = new NodeList("", "none", Arrays.asList(new Node("1001", "SPORT", 0)));

        when(nodesQueryingService.nodeExists(anyString(), anyString()))
                .thenReturn(false);

        InvalidNodesException exception = assertThrows(InvalidNodesException.class, () -> listService.addList(list));

        assertThat(exception.getMessage()).isEqualTo("Invalid Nodes In The List");
    }

    @Test
    public void willAddANewList() throws InvalidNodesException {
        NodeList list = new NodeList("axz", "none", Arrays.asList(new Node("1001", "SPORT", 0)));

        when(nodesQueryingService.nodeExists(anyString(), anyString()))
                .thenReturn(true);
        when(repository.save(any()))
                .thenReturn(list);

        assertThat(listService.addList(list)).isEqualTo(list);
    }

}
