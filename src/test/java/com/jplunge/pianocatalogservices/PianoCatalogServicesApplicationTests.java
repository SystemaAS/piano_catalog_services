package com.jplunge.pianocatalogservices;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.aspectj.SpringConfiguredConfiguration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@TestInstance(Lifecycle.PER_CLASS)

public class PianoCatalogServicesApplicationTests {

	@Autowired
    private WebApplicationContext wac;
 
    
    private MockMvc mockMvc;
 
   
	@Test
	public void contextLoads() {
	}
	//
	
	@Test
	public void nonexistentUserCannotGetToken() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
	    String username = "wronguser";
	    String password = "password";

	    String body = "{\"username\":\"" + username + ",\", \"password\":\"" + password + "\"}";

	    mockMvc.perform(MockMvcRequestBuilders.post("/v2/token")
	            .content(body))
	            .andExpect(status().isForbidden()).andReturn();
	}
	
	/*
	@Test
	public void givenNoToken_whenGetSecureRequest_thenUnauthorized() throws Exception {
	    this.mockMvc.perform(get("/pianos"))
	      .andExpect(status().isUnauthorized());
	}
	
	
	@Test
	public void givenToken_whenPostGetSecureRequest_thenOk() throws Exception {
	    String accessToken = obtainAccessToken("admin", "password");
	 
	    mockMvc.perform(post("/pianos")
	      .header("Authorization", "Bearer " + accessToken)
	      .contentType("application/json;charset=UTF-8")
	      .accept("application/json;charset=UTF-8"))
	      .andExpect(status().isOk());
	 
	}
	
	
	private String obtainAccessToken(String username, String password) throws Exception {
		if(this.mockMvc==null) {
			this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
			          .addFilter(springSecurityFilterChain).build();
		}
		
		
	    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
	    params.add("username", username);
	    params.add("password", password);
	    params.add("grant_type", "password");
	    params.add("scope", "read write");
	    
	    ResultActions result 
	      = mockMvc.perform(post("/oauth/token")
	        .params(params)
	        .with(httpBasic("myApp","secret"))
	        .accept("application/json;charset=UTF-8"))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType("application/json;charset=UTF-8"));
	 
	    String resultString = result.andReturn().getResponse().getContentAsString();
	 
	    JacksonJsonParser jsonParser = new JacksonJsonParser();
	    return jsonParser.parseMap(resultString).get("access_token").toString();
	}
	*/
}


/*
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@TestInstance(Lifecycle.PER_CLASS)
@WebAppConfiguration
@AutoConfigureMockMvc
public class PianoCatalogServicesApplicationTests {

	
	@Autowired
    private WebApplicationContext wac;
	
	
    private MockMvc mockMvc;
 
    
	@Test
	public void contextLoads() {
	}
	
	
	
	
	@Test
	public void existentUserCanGetTokenAndAuthentication() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
	    String username = "user";
	    String password = "password";

	    String body = "{ \"username\":\"" + username + ",\"password\":\""  + password + "\"}";

	    MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/signin")
	            .content(body))
	            .andExpect(status().isOk()).andReturn();

	    String response = result.getResponse().getContentAsString();
	    response = response.replace("{\"access_token\": \"", "");
	    String token = response.replace("\"}", "");

	    this.mockMvc.perform(MockMvcRequestBuilders.get("/pianos")
	        .header("Authorization", "Bearer " + token))
	        .andExpect(status().isOk());
	}
	
	@Test
	public void nonexistentUserCannotGetToken() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
	    String username = "wronguser";
	    String password = "password";

	    String body = "{\"username\":\"" + username + ",\", \"password\":\"" + password + "\"}";

	    mockMvc.perform(MockMvcRequestBuilders.post("/v2/token")
	            .content(body))
	            .andExpect(status().isForbidden()).andReturn();
	}

}

*/