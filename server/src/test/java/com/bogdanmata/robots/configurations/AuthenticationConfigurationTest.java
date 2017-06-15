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
package com.bogdanmata.robots.configurations;

import static org.junit.Assert.*;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bogdanmata.robots.domains.enums.UserRole;
import com.bogdanmata.robots.persistence.entities.UserEntity;
import com.bogdanmata.robots.persistence.entities.UserRoleEntity;
import com.bogdanmata.robots.persistence.entities.ids.UserRoleId;
import com.bogdanmata.robots.persistence.repositories.UserRepository;
import com.bogdanmata.robots.security.domains.RobotUserDetails;

/**
 * Test unit for AuthenticationConfiguration
 * 
 * Created Jun 15, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AuthenticationConfiguration.class)
public class AuthenticationConfigurationTest {

  @MockBean
  private UserRepository              userRepository;

  @Autowired
  private AuthenticationConfiguration authenticationConfiguration;

  @Test
  public void testGetUserDetailsService() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername("username");
    userEntity.setFirstName("First");
    userEntity.setLastName("Last");
    userEntity.setPassword("password");
    userEntity.setEmailAddress("first.last@test.com");
    userEntity.setRoles(Arrays.asList(create(userEntity, UserRole.ROBOT_READ)));

    when(userRepository.findOne(eq("username"))).thenReturn(userEntity);

    UserDetails userDetails = authenticationConfiguration.getUserDetailsService().loadUserByUsername("username");
    assertNotNull(userDetails);
    assertEquals(RobotUserDetails.class, userDetails.getClass());
    RobotUserDetails robotUserDetails = (RobotUserDetails) userDetails;
    assertEquals("username", robotUserDetails.getUsername());
    assertEquals("First", robotUserDetails.getFirstName());
    assertEquals("Last", robotUserDetails.getLastName());
    assertEquals("password", robotUserDetails.getPassword());
    assertEquals(1, robotUserDetails.getAuthorities().size());
    assertEquals("ROLE_ROBOT_READ", robotUserDetails.getAuthorities().toArray(new GrantedAuthority[] {})[0].getAuthority());

    verify(userRepository, times(1)).findOne(eq("username"));
    verifyNoMoreInteractions(userRepository);
  }

  @Test(expected = UsernameNotFoundException.class)
  public void testGetUserDetailsServiceNoFound() {
    when(userRepository.findOne(any(String.class))).thenReturn(null);
    authenticationConfiguration.getUserDetailsService().loadUserByUsername("username");
  }

  private UserRoleEntity create(UserEntity userEntity, UserRole robotRead) {
    UserRoleEntity userRoleEntity = new UserRoleEntity();
    UserRoleId id = new UserRoleId();
    id.setRole(robotRead);
    id.setUsername(userEntity.getUsername());
    userRoleEntity.setId(id);
    userRoleEntity.setUser(userEntity);

    return userRoleEntity;
  }

}
