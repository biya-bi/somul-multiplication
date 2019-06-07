/**
 * 
 */
package microservices.book.multiplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import microservices.book.multiplication.service.AdminService;

/**
 * @author biya-bi
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(AdminController.class)
public class AdminControllerDisabledTest {
	@MockBean
	private AdminService adminService;
	@Autowired
	private MockMvc mvc;

	/**
	 * This test checks that the controller is NOT ACCESSIBLE when profile is not
	 * set to test
	 *
	 * @throws Exception if any error occurs
	 */
	@Test
	public void deleteDatabase_ProfileIsNotTest_DatabaseContentsNotDeleted() throws Exception {
		// when
		MockHttpServletResponse response = mvc
				.perform(post("/multiplication/admin/delete-db").accept(MediaType.APPLICATION_JSON)).andReturn()
				.getResponse();
		// then
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
		verifyZeroInteractions(adminService);
	}
}
