package content.management.apiObjects;

import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ApiObjectsWrapperTest {

    @Test
    public void shouldGetRequestPayloadWithAttachments() {
        ApiObjectsWrapper.QueryPayload payload = ApiObjectsWrapper.QueryPayload.getPayload(Collections.emptyList());

        assertThat(payload.getAttachments()).isNotNull();

    }

    @Test
    public void shouldBeAbleToAddAFilterObject() {
        ApiObjectsWrapper.QueryPayload payload = ApiObjectsWrapper.QueryPayload.getPayload(Collections.emptyList());

        payload.addFilter(Collections.emptyList());

        assertThat(payload.getAttachments()).isNotNull();
        assertThat(payload.getFilter()).isNotNull();
    }
}
