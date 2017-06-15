/*
 * Copyright 2016-* to the Bogdan MATA.
 *
 * For License read the license attached.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bogdanmata.robots.controllers;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bogdanmata.robots.controllers.configurations.ControllerTestWebConfiguration;
import com.bogdanmata.robots.domains.RobotDetail;
import com.bogdanmata.robots.domains.RobotList;
import com.bogdanmata.robots.domains.enums.UserRole;
import com.bogdanmata.robots.persistence.repositories.UserRepository;
import com.bogdanmata.robots.requests.RobotCreateRequest;
import com.bogdanmata.robots.security.annotations.WithRobotUserDetails;
import com.bogdanmata.robots.services.RobotService;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test class for robot controller
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@RunWith(Suite.class)
@SuiteClasses({
    RobotControllerTest.TestWithAnonymousUser.class,
    RobotControllerTest.TestWithReadOnlyUser.class,
    RobotControllerTest.TestWithWriteReadUser.class
})
@SpringBootTest(classes = ControllerTestWebConfiguration.class)
@WebAppConfiguration
public class RobotControllerTest {

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private FilterChainProxy      springSecurityFilterChain;

  protected MockMvc             mvc;
  protected ObjectMapper        mapper;

  @MockBean
  private UserRepository        userRepository;
  @MockBean
  protected RobotService        robotService;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
    mapper = Jackson2ObjectMapperBuilder.json().build();
    mvc = MockMvcBuilders
        .webAppContextSetup(context)
        .apply(SecurityMockMvcConfigurers.springSecurity(springSecurityFilterChain))
        .build();
  }

  @After
  public void tearDown() throws Exception {
  }

  @WithAnonymousUser
  @RunWith(SpringJUnit4ClassRunner.class)
  public static final class TestWithAnonymousUser extends RobotControllerTest {
    @Test
    public void testReadRobots() throws Exception {
      mvc
          .perform(
              get("/__service/robot")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isUnauthorized())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.errorCode", Matchers.equalTo("authentication.exception.required")));

      verifyZeroInteractions(robotService);
    }

    @Test
    public void testReadRobot() throws Exception {
      mvc
          .perform(
              get("/__service/robot/1")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isUnauthorized())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.errorCode", Matchers.equalTo("authentication.exception.required")));

      verifyZeroInteractions(robotService);
    }

    @Test
    public void testCreateRobot() throws Exception {
      RobotCreateRequest request = new RobotCreateRequest();
      request.setName("Robot's name");
      request.setDescription("Description of the robot");

      mvc
          .perform(
              post("/__service/robot")
                  .accept(MediaType.APPLICATION_JSON_UTF8)
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(mapper.writeValueAsString(request)))
          .andExpect(status().isUnauthorized())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.errorCode", Matchers.equalTo("authentication.exception.required")));

      verifyZeroInteractions(robotService);
    }

    @Test
    public void testDeleteRobot() throws Exception {
      mvc
          .perform(
              delete("/__service/robot/1")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isUnauthorized())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.errorCode", Matchers.equalTo("authentication.exception.required")));

      verifyZeroInteractions(robotService);
    }
  }

  @WithRobotUserDetails(username = "ReadOnlyUser", roles = { UserRole.ROBOT_READ })
  @RunWith(SpringJUnit4ClassRunner.class)
  public static final class TestWithReadOnlyUser extends RobotControllerTest {
    @Test
    public void testReadRobots() throws Exception {
      Date date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017");

      when(robotService.readAll()).thenReturn(
          Arrays.asList(
              RobotList.builder()
                  .id(1L)
                  .name("Robot's name")
                  .addedDate(date)
                  .build(),
              RobotList.builder()
                  .id(2L)
                  .name("Robot's name")
                  .addedDate(date)
                  .build()));

      mvc
          .perform(
              get("/__service/robot")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$", Matchers.hasSize(2)))
          .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
          .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)));

      verify(robotService, times(1)).readAll();
      verifyNoMoreInteractions(robotService);
    }

    @Test
    public void testReadRobot() throws Exception {
      Date date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017");

      when(robotService.read(eq(1L))).thenReturn(RobotDetail.builder()
          .id(1L)
          .name("Robot's name")
          .description("Description of the robot")
          .price(12.5F)
          .addedDate(date)
          .build());

      mvc
          .perform(
              get("/__service/robot/1")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
          .andExpect(jsonPath("$.name", Matchers.equalTo("Robot's name")))
          .andExpect(jsonPath("$.description", Matchers.equalTo("Description of the robot")))
          .andExpect(jsonPath("$.price", Matchers.equalTo(12.5)))
          .andExpect(jsonPath("$.addedDate", Matchers.equalTo(date.getTime())));

      verify(robotService, times(1)).read(eq(1L));
      verifyNoMoreInteractions(robotService);
    }

    @Test
    public void testCreateRobot() throws Exception {
      RobotCreateRequest request = new RobotCreateRequest();
      request.setName("Robot's name");
      request.setDescription("Description of the robot");

      mvc
          .perform(
              post("/__service/robot")
                  .accept(MediaType.APPLICATION_JSON_UTF8)
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(mapper.writeValueAsString(request)))
          .andExpect(status().isForbidden())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.errorCode", Matchers.equalTo("access.denied")));

      verifyZeroInteractions(robotService);
    }

    @Test
    public void testDeleteRobot() throws Exception {
      mvc
          .perform(
              delete("/__service/robot/1")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isForbidden())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.errorCode", Matchers.equalTo("access.denied")));

      verifyZeroInteractions(robotService);
    }
  }

  @WithRobotUserDetails(username = "WriteReadUser", roles = { UserRole.ROBOT_READ, UserRole.ROBOT_EDIT, UserRole.ROBOT_DELETE })
  @RunWith(SpringJUnit4ClassRunner.class)
  public static final class TestWithWriteReadUser extends RobotControllerTest {
    @Test
    public void testReadRobots() throws Exception {
      Date date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017");

      when(robotService.readAll()).thenReturn(
          Arrays.asList(
              RobotList.builder()
                  .id(1L)
                  .name("Robot's name")
                  .addedDate(date)
                  .build(),
              RobotList.builder()
                  .id(2L)
                  .name("Robot's name")
                  .addedDate(date)
                  .build()));

      mvc
          .perform(
              get("/__service/robot")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$", Matchers.hasSize(2)))
          .andExpect(jsonPath("$[0].id", Matchers.equalTo(1)))
          .andExpect(jsonPath("$[1].id", Matchers.equalTo(2)));

      verify(robotService, times(1)).readAll();
      verifyNoMoreInteractions(robotService);
    }

    @Test
    public void testReadRobot() throws Exception {
      Date date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017");

      when(robotService.read(eq(1L))).thenReturn(RobotDetail.builder()
          .id(1L)
          .name("Robot's name")
          .description("Description of the robot")
          .price(12.5F)
          .addedDate(date)
          .build());

      mvc
          .perform(
              get("/__service/robot/1")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
          .andExpect(jsonPath("$.name", Matchers.equalTo("Robot's name")))
          .andExpect(jsonPath("$.description", Matchers.equalTo("Description of the robot")))
          .andExpect(jsonPath("$.price", Matchers.equalTo(12.5)))
          .andExpect(jsonPath("$.addedDate", Matchers.equalTo(date.getTime())));

      verify(robotService, times(1)).read(eq(1L));
      verifyNoMoreInteractions(robotService);
    }

    @Test
    public void testCreateRobot() throws Exception {
      RobotCreateRequest request = new RobotCreateRequest();
      request.setName("Robot's name");
      request.setDescription("Description of the robot");

      when(robotService.create(any(RobotDetail.class), eq("WriteReadUser"))).then(new Answer<RobotDetail>() {
        @Override
        public RobotDetail answer(InvocationOnMock invocation) throws Throwable {
          RobotDetail robotDetail = invocation.getArgumentAt(0, RobotDetail.class);
          robotDetail.setId(1L);
          return robotDetail;
        }
      });

      mvc
          .perform(
              post("/__service/robot")
                  .accept(MediaType.APPLICATION_JSON_UTF8)
                  .contentType(MediaType.APPLICATION_JSON_UTF8)
                  .content(mapper.writeValueAsString(request)))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
          .andExpect(jsonPath("$.name", Matchers.equalTo("Robot's name")))
          .andExpect(jsonPath("$.description", Matchers.equalTo("Description of the robot")));

      verify(robotService, times(1)).create(any(RobotDetail.class), eq("WriteReadUser"));
      verifyNoMoreInteractions(robotService);
    }

    @Test
    public void testDeleteRobot() throws Exception {
      Date date = DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017");

      when(robotService.delete(eq(1L))).thenReturn(RobotDetail.builder()
          .id(1L)
          .name("Robot's name")
          .description("Description of the robot")
          .price(12.5F)
          .addedDate(date)
          .build());

      mvc
          .perform(
              delete("/__service/robot/1")
                  .accept(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(status().isOk())
          .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
          .andExpect(jsonPath("$.id", Matchers.equalTo(1)))
          .andExpect(jsonPath("$.name", Matchers.equalTo("Robot's name")))
          .andExpect(jsonPath("$.description", Matchers.equalTo("Description of the robot")))
          .andExpect(jsonPath("$.price", Matchers.equalTo(12.5)))
          .andExpect(jsonPath("$.addedDate", Matchers.equalTo(date.getTime())));

      verify(robotService, times(1)).delete(eq(1L));
      verifyNoMoreInteractions(robotService);
    }
  }

}
