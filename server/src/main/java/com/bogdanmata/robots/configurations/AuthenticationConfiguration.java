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

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bogdanmata.robots.persistence.entities.UserEntity;
import com.bogdanmata.robots.persistence.repositories.UserRepository;
import com.bogdanmata.robots.security.domains.RobotUserDetails;

/**
 * TODO add description
 * 
 * Created Jun 15, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Configuration
public class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

  @Autowired
  private UserRepository userRepository;
  
  @Bean
  public UserDetailsService getUserDetailsService() {
    return username -> {
      UserEntity userEntity = userRepository.findOne(username);
      if (userEntity == null) {
        throw new UsernameNotFoundException("The user was not found.");
      }
      return RobotUserDetails.builder()
          .username(userEntity.getUsername())
          .firstName(userEntity.getFirstName())
          .lastName(userEntity.getLastName())
          .password(userEntity.getPassword())
          .accountNonExpired(true)
          .accountNonLocked(true)
          .credentialsNonExpired(true)
          .enabled(true)
          .authorities(
              AuthorityUtils.createAuthorityList(
                  userEntity.getRoles()
                    .stream()
                    .map((role) -> {return "ROLE_" + role.getId().getRole().name();})
                    .collect(Collectors.toList())
                    .toArray(new String[]{})
              )
          )
          .build();
    };
  }
}
