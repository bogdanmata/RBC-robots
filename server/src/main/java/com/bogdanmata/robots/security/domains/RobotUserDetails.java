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
package com.bogdanmata.robots.security.domains;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The wrapper for the security details
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Builder
@Getter
@RequiredArgsConstructor
public class RobotUserDetails implements UserDetails {

  /**
   * Serial version UID
   */
  private static final long                            serialVersionUID = -8186498920709379553L;

  private final String                                 username;
  private final String                                 password;
  private final String                                 firstName;
  private final String                                 lastName;
  private final boolean                                accountNonExpired;
  private final boolean                                accountNonLocked;
  private final boolean                                credentialsNonExpired;
  private final boolean                                enabled;

  private final Collection<? extends GrantedAuthority> authorities;

}
