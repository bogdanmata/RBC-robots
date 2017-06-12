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

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * The robot mapping
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Getter
@Setter
@Entity
@Table(name = "t_robot")
public class RobotEntity {

  @Id
  @GeneratedValue(generator = "seq_robot", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(name = "seq_robot", sequenceName = "seq_robot")
  @Column(name = "id")
  private Long       id;

  @Column(name = "name")
  private String     name;

  @Column(name = "description")
  private String     description;

  @Column(name = "created", updatable = false)
  private Date       creationDate;

  @Column(name = "created_by", updatable = false)
  private UserEntity createdBy;
}
