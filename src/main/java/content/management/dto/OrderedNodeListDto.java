package content.management.dto;

import content.management.model.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderedNodeListDto {
    private String id;
    private String name;
    private List<Node> nodes;
}
