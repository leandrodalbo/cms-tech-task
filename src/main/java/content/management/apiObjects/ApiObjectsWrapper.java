package content.management.apiObjects;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class ApiObjectsWrapper {

    @NoArgsConstructor
    @Setter
    @Getter
    public static class QueryPayload {
        List<String> attachments;
        PayloadFilter filter;

        public static QueryPayload getPayload(List<String> nodeTypes) {
            QueryPayload payload = new QueryPayload();
            payload.setAttachments(nodeTypes);
            return payload;
        }

        public void addFilter(List<String> sportIds) {
            this.filter = new PayloadFilter();
            this.filter.setSportIds(sportIds);
        }
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class APIResponse {
        private List<APIResponseNode> sports;
        private List<APIResponseNode> competitions;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    public static class APIResponseNode {
        private String id;
        private String name;
        private String sportId;
    }

    @NoArgsConstructor
    @Setter
    @Getter
    private static class PayloadFilter {
        private List<String> sportIds;
    }

}
