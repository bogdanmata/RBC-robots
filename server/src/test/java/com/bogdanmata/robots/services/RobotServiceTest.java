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
package com.bogdanmata.robots.services;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bogdanmata.robots.configurations.ServiceConfiguration;
import com.bogdanmata.robots.domains.RobotDetail;
import com.bogdanmata.robots.domains.RobotList;
import com.bogdanmata.robots.exceptions.NotFoundException;
import com.bogdanmata.robots.persistence.entities.RobotEntity;
import com.bogdanmata.robots.persistence.entities.UserEntity;
import com.bogdanmata.robots.persistence.repositories.RobotRepository;
import com.bogdanmata.robots.persistence.repositories.UserRepository;

/**
 * Test class for robot service
 * 
 * Created Jun 15, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
public class RobotServiceTest {

  @MockBean
  private RobotRepository robotRepository;

  @MockBean
  private UserRepository  userRepository;

  @Autowired
  private RobotService    robotService;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  private UserEntity createUserEntity(String username) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(username);

    return userEntity;
  }

  private RobotEntity createRobotEntity(Long id, String name, String description, Float price, Date creationDate, UserEntity createdBy) {
    RobotEntity robotEntity = new RobotEntity();
    robotEntity.setId(id);
    ;
    robotEntity.setName(name);
    robotEntity.setDescription(description);
    robotEntity.setPrice(price);
    robotEntity.setCreationDate(creationDate);
    robotEntity.setCreatedBy(createdBy);

    return robotEntity;
  }

  @Test
  public void testReadAll() throws Exception {
    when(robotRepository.findAll()).thenReturn(Arrays.asList(
        createRobotEntity(1L, "name1", "description1", 10F, DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"),
            createUserEntity("createdBy")),
        createRobotEntity(1L, "name2", "description2", 12F, DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"),
            createUserEntity("createdBy"))));
    List<RobotList> robotList = robotService.readAll();

    assertNotNull(robotList);
    assertEquals(2, robotList.size());
    assertEquals(Long.valueOf(1L), robotList.get(0).getId());
    assertEquals("name1", robotList.get(0).getName());
    assertEquals(Float.valueOf(10F), robotList.get(0).getPrice());
    assertEquals(DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"), robotList.get(0).getAddedDate());

    verify(robotRepository, times(1)).findAll();
    verifyNoMoreInteractions(userRepository, robotRepository);
  }

  @Test
  public void testRead() throws Exception {
    when(robotRepository.findOne(eq(1L))).thenReturn(
        createRobotEntity(1L, "name1", "description1", 10F, DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"),
            createUserEntity("createdBy")));

    RobotDetail robot = robotService.read(1L);

    assertNotNull(robot);
    assertEquals(Long.valueOf(1L), robot.getId());
    assertEquals("name1", robot.getName());
    assertEquals("description1", robot.getDescription());
    assertEquals(Float.valueOf(10F), robot.getPrice());
    assertEquals(DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"), robot.getAddedDate());

    verify(robotRepository, times(1)).findOne(eq(1L));
    verifyNoMoreInteractions(userRepository, robotRepository);
  }

  @Test(expected = NotFoundException.class)
  public void testReadNotFound() throws Exception {
    when(robotRepository.findOne(any(Long.class))).thenReturn(null);
    robotService.read(1L);
  }

  @Test
  public void testCreate() throws Exception {
    UserEntity userEntity = createUserEntity("createdBy");
    when(userRepository.findOne("createdBy")).thenReturn(userEntity);
    when(robotRepository.save(any(RobotEntity.class))).thenAnswer(invocation -> {
      RobotEntity entity = invocation.getArgumentAt(0, RobotEntity.class);
      entity.setId(1L);
      return entity;
    });

    RobotDetail robot = robotService.create(RobotDetail.builder()
        .name("name1")
        .description("description1")
        .price(10F)
        .build(), "createdBy");

    assertNotNull(robot);
    assertEquals(Long.valueOf(1L), robot.getId());
    assertEquals("name1", robot.getName());
    assertEquals("description1", robot.getDescription());
    assertEquals(Float.valueOf(10F), robot.getPrice());
    assertEquals(DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(new Date()),
        DateFormat.getDateInstance(DateFormat.LONG, Locale.US).format(robot.getAddedDate()));

    verify(userRepository, times(1)).getOne(eq("createdBy"));
    verify(robotRepository, times(1)).save(any(RobotEntity.class));
    verifyNoMoreInteractions(userRepository, robotRepository);
  }

  @Test
  public void testDelete() throws Exception {
    when(robotRepository.findOne(eq(1L))).thenReturn(
        createRobotEntity(1L, "name1", "description1", 10F, DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"),
            createUserEntity("createdBy")));

    RobotDetail robot = robotService.delete(1L);
    assertNotNull(robot);
    assertEquals(Long.valueOf(1L), robot.getId());
    assertEquals("name1", robot.getName());
    assertEquals("description1", robot.getDescription());
    assertEquals(Float.valueOf(10F), robot.getPrice());
    assertEquals(DateFormat.getDateInstance(DateFormat.SHORT, Locale.US).parse("6/12/2017"), robot.getAddedDate());

    verify(robotRepository, times(1)).findOne(eq(1L));
    verify(robotRepository, times(1)).delete(eq(1L));
    verifyNoMoreInteractions(userRepository, robotRepository);
  }
  
  @Test(expected = NotFoundException.class)
  public void testDeleteNotFound() throws Exception {
    when(robotRepository.findOne(any(Long.class))).thenReturn(null);
    robotService.delete(1L);
    
  }

}
