package content.management.service;

import content.management.dto.NodeDto;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static content.management.apiObjects.ApiObjectsWrapper.APIResponse;
import static content.management.apiObjects.ApiObjectsWrapper.APIResponseNode;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NodesQueryingServiceTest {

    @Mock
    RestTemplate template;

    private NodesQueryingService service;

    @BeforeEach
    public void setUp() {
        service = new NodesQueryingService("http://localhost:9001", template);
    }

    @Test
    public void willGetAListWillTheSportsFromTheAPI() {

        APIResponse responseBody = new APIResponse();
        responseBody.setSports(Collections.singletonList(new APIResponseNode()));

        when(template.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(
                        responseBody,
                        HttpStatus.OK
                ));

        List<NodeDto> result = service.getAllNodesOfATypeWithoutFilters("SPORT");

        assertFalse(result.isEmpty());
    }

    @Test
    public void willGetAListWillTheCompetitionsFromTheAPI() {

        APIResponse responseBody = new APIResponse();
        responseBody.setCompetitions(Collections.singletonList(new APIResponseNode()));

        when(template.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(
                        responseBody,
                        HttpStatus.OK
                ));

        List<NodeDto> result = service.getAllNodesOfATypeWithoutFilters("COMPETITION");

        assertFalse(result.isEmpty());
    }

    @Test
    public void willBeFalseIfTheNodeDoesNotExist() throws JSONException {
        APIResponse response = new APIResponse();
        response.setCompetitions(null);
        response.setSports(null);

        when(template.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(
                        response,
                        HttpStatus.OK
                ));

        assertFalse(service.nodeExists("SPORT", "NONE"));
    }

    @Test
    public void willBeTrueIfTheSportExists() throws JSONException {
        APIResponse response = new APIResponse();
        response.setCompetitions(null);
        response.setSports(Collections.singletonList(new APIResponseNode()));

        when(template.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(
                        response,
                        HttpStatus.OK
                ));

        assertTrue(service.nodeExists("SPORT", "NONE"));
    }

    @Test
    public void willGetAnEmptyListWithAnInvalidResponse() {
        when(template.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(
                        HttpStatus.BAD_REQUEST
                ));

        List<NodeDto> result = service.getAllNodesOfATypeWithoutFilters("SPORT");

        assertTrue(result.isEmpty());
    }

    @Test
    public void willGetAnEmptyListWithAnInvalidNodeType() {
        when(template.postForEntity(anyString(), any(), any()))
                .thenReturn(new ResponseEntity<>(
                        HttpStatus.OK
                ));

        List<NodeDto> result = service.getAllNodesOfATypeWithoutFilters("INVALID_NODE_TYPE");

        assertTrue(result.isEmpty());
    }

}
