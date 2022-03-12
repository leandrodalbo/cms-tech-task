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
import java.util.Collections;
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
    NodeListRepository nodeListRepository;

    @InjectMocks
    NodeListService nodeListService;

    @Test
    public void willGetAnOrderedNodeList() throws NodeListNotFoundException {
        NodeList list = new NodeList("abc", "none", Arrays.asList(new Node("1001", "SPORT", 1), new Node("1002", "SPORT", 0)));

        when(nodeListRepository.findById(anyString()))
                .thenReturn(Optional.of(list));

        OrderedNodeListDto dto = nodeListService.getListInOrder("abc");

        assertThat(dto.getNodes().get(0).getPosition()).isEqualTo(0);
        assertThat(dto.getNodes().get(1).getPosition()).isEqualTo(1);
    }

    @Test
    public void willThrowExceptionWithAnInvalidListId() {
        when(nodeListRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        NodeListNotFoundException exception = assertThrows(NodeListNotFoundException.class, () -> nodeListService.getListInOrder("invalidId"));

        assertThat(exception.getMessage()).isEqualTo("List Not Found");
    }

    @Test
    public void addListFailsIfANodeDoesNotExist() {
        NodeList list = new NodeList("", "none", Collections.singletonList(new Node("1001", "SPORT", 0)));

        when(nodesQueryingService.nodeExists(anyString(), anyString()))
                .thenReturn(false);

        InvalidNodesException exception = assertThrows(InvalidNodesException.class, () -> nodeListService.addList(list));

        assertThat(exception.getMessage()).isEqualTo("Invalid Nodes In The List");
    }

    @Test
    public void willAddANewList() throws InvalidNodesException {
        NodeList list = new NodeList("axz", "none", Collections.singletonList(new Node("1001", "SPORT", 0)));

        when(nodesQueryingService.nodeExists(anyString(), anyString()))
                .thenReturn(true);
        when(nodeListRepository.save(any()))
                .thenReturn(list);

        assertThat(nodeListService.addList(list)).isInstanceOf(OrderedNodeListDto.class);
    }
}
