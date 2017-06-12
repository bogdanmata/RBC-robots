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
package com.bogdanmata.robots.security.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.test.context.support.WithSecurityContext;

import com.bogdanmata.robots.domains.enums.UserRole;
import com.bogdanmata.robots.security.factories.WithRobotUserDetailsSecurityContextFactory;

/**
 * Annotation for the test user details
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@WithSecurityContext(factory = WithRobotUserDetailsSecurityContextFactory.class)
public @interface WithRobotUserDetails {
  /**
   * Alias for user name
   * 
   * @see WithRobotUserDetails#username()
   * */
  @AliasFor("username")
  String value() default "user";
  
  /**
   * The user name
   * */
  @AliasFor("value")
  String username() default "user";
  
  /**
   * The roles of the user
   * */
  UserRole[] roles() default {};
}
