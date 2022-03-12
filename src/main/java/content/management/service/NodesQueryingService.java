package content.management.service;

import content.management.apiObjects.ApiObjectsWrapper;
import content.management.dto.NodeDto;
import content.management.model.NodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static content.management.apiObjects.ApiObjectsWrapper.QueryPayload;

@Component
public class NodesQueryingService {

    private final RestTemplate restTemplate;
    private final String apiHost;

    @Autowired
    public NodesQueryingService(@Value("${sports.book.api.host}") String apiHost, RestTemplate restTemplate) {
        this.apiHost = apiHost;
        this.restTemplate = restTemplate;
    }

    public boolean nodeExists(String nodeType, String sportId) {
        ResponseEntity<ApiObjectsWrapper.APIResponse> responseEntity = this.getNodes(Arrays.asList(nodeType), Optional.of(Arrays.asList(sportId)));
        boolean sportsExists = responseEntity.getBody().getSports() != null;
        boolean competitionsExists = responseEntity.getBody().getCompetitions() != null;

        return (responseEntity.getStatusCode().is2xxSuccessful() && (sportsExists || competitionsExists));
    }

    public List<NodeDto> getAllNodesOfATypeWithoutFilters(String nodeType) {
        ResponseEntity<ApiObjectsWrapper.APIResponse> responseEntity = this.getNodes(Arrays.asList(nodeType), Optional.empty());

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            return Collections.emptyList();
        }

        if (nodeType.equals(NodeType.COMPETITION.getValue())) {
            return toNodeList(responseEntity.getBody().getCompetitions(), nodeType);

        }

        if (nodeType.equals(NodeType.SPORT.getValue())) {
            return toNodeList(responseEntity.getBody().getSports(), nodeType);
        }
        return Collections.emptyList();
    }

    private ResponseEntity<ApiObjectsWrapper.APIResponse> getNodes(List<String> nodeTypes, Optional<List<String>> sportIds) {
        QueryPayload queryPayload = QueryPayload.getPayload(nodeTypes);

        if (sportIds.isPresent()) {
            queryPayload.addFilter(sportIds.get());
        }else {
            queryPayload.addFilter(Collections.emptyList());
        }

        return this.restTemplate.postForEntity(apiHost, queryPayload, ApiObjectsWrapper.APIResponse.class);

    }

    private List<NodeDto> toNodeList(List<ApiObjectsWrapper.APIResponseNode> responseNodes, String nodeType) {
        return responseNodes.stream()
                .map(i -> new NodeDto(i.getId(), i.getName(), nodeType))
                .collect(Collectors.toList());
    }

}
