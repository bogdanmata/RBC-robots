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
package com.bogdanmata.robots.persistence.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bogdanmata.robots.persistence.entities.ids.UserRoleId;

import lombok.Getter;
import lombok.Setter;

/**
 * The user roles mapping
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "t_user_role")
public class UserRoleEntity {

  @EmbeddedId
  private UserRoleId id;

  @AttributeOverride(name = "id.username", column = @Column(name = "username"))
  @ManyToOne
  @JoinColumn(name = "username", insertable = false, updatable = false)
  private UserEntity user;
}
