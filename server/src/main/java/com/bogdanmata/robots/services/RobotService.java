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

import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bogdanmata.robots.domains.RobotDetail;
import com.bogdanmata.robots.domains.RobotList;
import com.bogdanmata.robots.exceptions.NotFoundException;
import com.bogdanmata.robots.persistence.entities.RobotEntity;
import com.bogdanmata.robots.persistence.entities.UserEntity;
import com.bogdanmata.robots.persistence.repositories.RobotRepository;
import com.bogdanmata.robots.persistence.repositories.UserRepository;

/**
 * The robot service
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Service
public class RobotService {

  @Autowired
  private RobotRepository robotRepository;

  @Autowired
  private UserRepository  userRepository;

  @Transactional(readOnly = true)
  public List<RobotList> readAll() {
    return robotRepository.findAll().stream().map(new Function<RobotEntity, RobotList>() {
      public RobotList apply(RobotEntity entity) {
        return RobotList.builder()
            .id(entity.getId())
            .name(entity.getName())
            .price(entity.getPrice())
            .adddedDate(entity.getCreationDate())
            .build();
      };
    }).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public RobotDetail read(Long robotId) {
    RobotEntity entity = robotRepository.findOne(robotId);
    if (entity == null) {
      throw new NotFoundException("robot.not.found", "The robot was not found!");
    }
    return getRobotDetail(entity);
  }

  @Transactional
  public RobotDetail create(RobotDetail robotDetail, String username) {
    UserEntity userEntity = userRepository.getOne(username);

    RobotEntity robotEntity = new RobotEntity();
    robotEntity.setName(robotDetail.getName());
    robotEntity.setPrice(robotDetail.getPrice());
    robotEntity.setDescription(robotDetail.getDescription());
    robotEntity.setCreationDate(new Date());
    robotEntity.setCreatedBy(userEntity);

    robotEntity = robotRepository.save(robotEntity);

    return getRobotDetail(robotEntity);
  }

  @Transactional
  public RobotDetail delete(Long robotId) {
    RobotEntity robotEntity = robotRepository.findOne(robotId);
    if (robotEntity == null) {
      throw new NotFoundException("robot.not.found", "The robot was not found!");
    }
    robotRepository.delete(robotId);

    return getRobotDetail(robotEntity);
  }

  private RobotDetail getRobotDetail(@NotNull RobotEntity entity) {
    return RobotDetail.builder()
        .id(entity.getId())
        .name(entity.getName())
        .price(entity.getPrice())
        .description(entity.getDescription())
        .adddedDate(entity.getCreationDate())
        .build();
  }
}
