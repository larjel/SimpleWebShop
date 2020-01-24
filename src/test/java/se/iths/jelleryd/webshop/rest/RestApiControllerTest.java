package se.iths.jelleryd.webshop.rest;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class RestApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void getAllOrders() throws Exception {

    // Create a get request with an accept header for application\json
    RequestBuilder requestBuilder =
        MockMvcRequestBuilders.get("/admin/allorders").contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    // Assert that the return status is OK
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void getDispatchedOrders() throws Exception {

    // Create a get request with an accept header for application\json
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/dispatchedorders")
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    // Assert that the return status is OK
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void getNotDispatchedOrders() throws Exception {

    // Create a get request with an accept header for application\json
    RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/admin/notdispatchedorders")
        .contentType(MediaType.APPLICATION_JSON);

    MvcResult result = mockMvc.perform(requestBuilder).andReturn();
    MockHttpServletResponse response = result.getResponse();

    // Assert that the return status is OK
    assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

}
