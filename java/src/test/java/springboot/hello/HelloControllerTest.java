package springboot.hello;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HelloControllerTest
{

	@Autowired
	private MockMvc mvc;

	
	@Test
   public void getHelloTest() throws Exception 
	{
		// Arrange
		//
		String        msg = "SpringBoot: Hello Moto!";
		MediaType      mt = MediaType.APPLICATION_JSON;
		RequestBuilder  b = MockMvcRequestBuilders.get("/").accept(mt);
		ResultMatcher  rm = status().isOk();

		// Act & Assert
		//
      mvc.perform(b)
           .andExpect(rm)
           .andExpect(content().string(equalTo(msg)));
  }
	
}