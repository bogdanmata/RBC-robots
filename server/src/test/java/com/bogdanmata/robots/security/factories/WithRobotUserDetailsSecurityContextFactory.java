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
package com.bogdanmata.robots.security.factories;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.springframework.util.Assert;

import com.bogdanmata.robots.domains.enums.UserRole;
import com.bogdanmata.robots.security.annotations.WithRobotUserDetails;
import com.bogdanmata.robots.security.domains.RobotUserDetails;

/**
 * Helper class for authentication
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
public class WithRobotUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithRobotUserDetails> {

  public UserDetails createUserDetails(WithRobotUserDetails withRobotUserDetails) throws UsernameNotFoundException {
    Assert.hasLength(withRobotUserDetails.username(), "The username cannot be empty");
    return RobotUserDetails.builder()
        .username(withRobotUserDetails.username())
        .password("")
        .accountNonExpired(true)
        .accountNonLocked(true)
        .credentialsNonExpired(true)
        .enabled(true)
        .authorities(AuthorityUtils.createAuthorityList(Arrays.asList(withRobotUserDetails.roles()).stream().map(new Function<UserRole, String>() {
          @Override
          public String apply(UserRole userRole) {
            return "ROLE_" + userRole.name();
          }
        }).collect(Collectors.toList()).toArray(new String[] {})))
        .build();
  }

  @Override
  public SecurityContext createSecurityContext(WithRobotUserDetails withRobotUserDetails) {
    UserDetails principal = createUserDetails(withRobotUserDetails);
    Authentication authentication = new UsernamePasswordAuthenticationToken(principal, "", principal.getAuthorities());
    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(authentication);
    return context;
  }

}
