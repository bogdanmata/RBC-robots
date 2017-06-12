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
package com.bogdanmata.robots.persistence.entities.ids;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.bogdanmata.robots.domains.enums.UserRole;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * The user role id
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Embeddable
@Access(AccessType.FIELD)
@Getter
@Setter
@EqualsAndHashCode
public class UserRoleId implements Serializable {

  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 5927634862971507320L;

  @Column(name = "username", updatable = false)
  private String            username;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", updatable = false)
  private UserRole          role;
}
