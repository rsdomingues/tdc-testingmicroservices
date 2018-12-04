package com.tdcsample.checkout.gateway.http;

import com.tdcsample.checkout.gateway.http.conf.AbstractHttpTest;
import com.tdcsample.checkout.gateway.http.jsons.ErrorResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class CustomExceptionHandlerTest extends AbstractHttpTest {

  private MockMvc mockMvc;

  @Before
  public void setup() {
    final CustomExceptionHandlerTestController controller =
        new CustomExceptionHandlerTestController();
    mockMvc = buildMockMvcWithBusinessExecptionHandler(controller);
  }

  @Test
  public void testMethodNotSupported() throws Exception {
    mockMvc
        .perform(post("/test/method-not-supported"))
        .andExpect(status().isMethodNotAllowed())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_METHOD_NOT_SUPPORTED))
        .andExpect(jsonPath("$.description").value("Request method 'POST' not supported"));
  }

  @Test
  public void testInternalServerError() throws Exception {
    mockMvc
        .perform(get("/test/internal-server-error"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_INTERNAL_SERVER_ERROR))
        .andExpect(jsonPath("$.description").value("Internal server error"));
  }

  @Test
  public void testFeatureDisabled() throws Exception {
    mockMvc
        .perform(get("/test/feature-disabled"))
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_FEATURE))
        .andExpect(jsonPath("$.description").value("Feature disabled: test"));
  }

  @Test
  public void testMethodArgumentNotValid() throws Exception {
    mockMvc
        .perform(
            post("/test/method-argument").content("{}").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_VALIDATION))
        .andExpect(jsonPath("$.errorFields.[0].messageCode").value("NotNull"))
        .andExpect(jsonPath("$.errorFields.[0].fields.[0]").value("test"));
  }

  @Test
  public void testUnprocessableEntity() throws Exception {
    mockMvc
        .perform(get("/test/unprocessable-entity-error"))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_UNPROCESSABLE_ENTITY));
  }

  @Test
  public void testIntegrationAirportError() throws Exception {
    mockMvc
        .perform(get("/test/integration-airport-error"))
        .andExpect(status().isUnprocessableEntity())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_INTEGRATION_AIRPORT_ERROR));
  }

  
  @Test
  public void testHystrixFallBackGenericError() throws Exception {
    mockMvc
        .perform(get("/test/fallback-generic-error"))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value(ErrorResponse.ERR_INTERNAL_SERVER_ERROR))
        .andExpect(jsonPath("$.description").value("Internal server error"));
  }
}
