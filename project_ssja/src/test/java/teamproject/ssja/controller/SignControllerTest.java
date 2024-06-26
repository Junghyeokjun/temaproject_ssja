package teamproject.ssja.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
class SignControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testIdCheck() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/idCheck?id=testUser1"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}
	
	@Test
	void testNameCheck() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/nickNameCheck?nickName=testUser1"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}

	@Test
	void testEmailCheck() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/emailCheck?email=user@naver.com"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}
	@Test
	void testQuantityCheck() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/quantityCheck?proNo=1&quantity=3"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}
	
	@Test
	void testSignUpBeforePage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/sign_up_before"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}

	@Test
	void testSignUpPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/sign_up"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}

	@Test
	void testTermsModifyPage() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/sign/terms_modify"))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}

	@Test
	void testTermsModify() throws Exception {
		
        String responseJson = "{\"term1\":\"필수약관\",\"term2\":\"선택약관\"}";
		
        mockMvc.perform(MockMvcRequestBuilders.post("/sign/terms_modify")
				.content(responseJson)
				.contentType(MediaType.APPLICATION_JSON)
				.with(csrf())
				.accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk())
			   .andDo(print());	}

//	차후 수정
//	@Test
//	void testSignUpCheck() throws Exception {
//	    Map<String, String> input = new HashMap<>();
//	    input.put("M_NO", "1");
//	    input.put("M_ID", "test12");
//	    input.put("M_PW", "test1");
//	    input.put("M_NAME", "test12");
//	    input.put("M_ADDRESS1", "test1");
//	    input.put("M_ADDRESS2", "test1");
//	    input.put("M_ZIPCODE", "11111");
//	    input.put("M_BIRTH", "010101");
//	    input.put("M_GRADE", "null");
//	    input.put("M_EMAIL", "null");	    
//	    input.put("M_POINT", "null");	    
//	    input.put("M_DATE", "null");	    
//	    input.put("M_PHONE", "01034561234");
//	    input.put("M_NICKNAME", "test21");
//	    input.put("email", "test");
//	    input.put("domain", "google.com");
//	    
//	    ObjectMapper mapper=new ObjectMapper();
//		mockMvc.perform(MockMvcRequestBuilders.post("/sign/signUp")
//						.content(mapper.writeValueAsString(input))
//						.contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON)
//						.with(csrf()))
//			   .andExpect(MockMvcResultMatchers.status().isOk())
//			   .andDo(print());
//	    }



}
