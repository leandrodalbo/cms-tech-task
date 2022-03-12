package content.management.controller;


import content.management.dto.NodeDto;
import content.management.dto.OrderedNodeListDto;
import content.management.exceptions.InvalidNodesException;
import content.management.exceptions.NodeListNotFoundException;
import content.management.model.Node;
import content.management.model.NodeList;
import content.management.service.NodeListService;
import content.management.service.NodesQueryingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@AutoConfigureJsonTesters
@WebMvcTest(CMSController.class)
public class CMSControllerTest {

    @MockBean
    private NodesQueryingService nodesQueryingService;

    @MockBean
    private NodeListService nodeListService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester jsonData;

    public CMSControllerTest() {
    }

    @Test
    public void wilLQueryAllSports() throws Exception {
        List<NodeDto> result = Collections.singletonList(new NodeDto("node0", "nodeName", "nodeType"));

        given(nodesQueryingService.getAllNodesOfATypeWithoutFilters(anyString())).
                willReturn(result);

        MockHttpServletResponse res = mvc.perform(
                get("/nodes?type=SPORT").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(res.getContentAsString()).isEqualTo(jsonData.write(result).getJson());

    }

    @Test
    public void willAddANewList() throws Exception {
        NodeList list = new NodeList("dddssf", "none", Arrays.asList(new Node("1001", "SPORT", 0)));

        given(nodeListService.addList(any())).
                willReturn(list);

        MockHttpServletResponse res = mvc.perform(
                post("/list").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData.write(list).getJson()))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        then(res.getContentAsString()).isEqualTo(jsonData.write(list).getJson());

    }

    @Test
    public void willGetBadRequestWithInvalidNodes() throws Exception {
        NodeList list = new NodeList("dddssf", "none", Arrays.asList(new Node("1001", "SPORT", 0)));

        given(nodeListService.addList(any())).
                willThrow(new InvalidNodesException());

        MockHttpServletResponse res = mvc.perform(
                post("/list").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData.write(list).getJson()))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void willGetBadRequestWithInvalidListId() throws Exception {

        given(nodeListService.getListInOrder(any())).
                willThrow(new NodeListNotFoundException());

        MockHttpServletResponse res = mvc.perform(
                get("/list/{list-id}", "invalidId").contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void willGetTheListInOrder() throws Exception {
        OrderedNodeListDto dto = new OrderedNodeListDto("abcds", "noName", Arrays.asList(new Node("1001", "SPORT", 0), new Node("1002", "SPORT", 1)));

        given(nodeListService.getListInOrder(any())).
                willReturn(dto);

        MockHttpServletResponse res = mvc.perform(
                get("/list/{list-id}", "abcds").contentType(MediaType.APPLICATION_JSON)
                        .content(jsonData.write(dto).getJson()))
                .andReturn().getResponse();

        then(res.getStatus()).isEqualTo(HttpStatus.OK.value());
        then(res.getContentAsString()).isEqualTo(jsonData.write(dto).getJson());

    }
}
