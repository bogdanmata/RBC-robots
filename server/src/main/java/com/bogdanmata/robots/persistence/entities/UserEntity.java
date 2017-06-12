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

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The user mapping
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class UserEntity {

  @Id
  @Column(name = "username")
  private String               username;

  @Column(name = "first_name")
  private String               firstName;

  @Column(name = "last_name")
  private String               lastName;

  @Column(name = "email_address")
  private String               emailAddress;

  private List<UserRoleEntity> roles;

}
