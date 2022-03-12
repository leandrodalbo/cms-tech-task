package content.management.dto;

import content.management.model.Node;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderedNodeListDto {
    private String id;

    private String name;

    private List<Node> nodes;
}
