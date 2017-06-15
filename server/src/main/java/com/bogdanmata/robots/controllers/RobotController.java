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
package com.bogdanmata.robots.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bogdanmata.robots.domains.RobotDetail;
import com.bogdanmata.robots.domains.RobotList;
import com.bogdanmata.robots.requests.RobotCreateRequest;
import com.bogdanmata.robots.security.domains.RobotUserDetails;
import com.bogdanmata.robots.services.RobotService;

/**
 * The robot controller
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@RestController
@RequestMapping("/__service/robot")
public class RobotController {

  @Autowired
  private RobotService robotService;

  /**
   * Method for mapping the GET /__service/robot
   * 
   * @return all robots
   */
  @Secured("ROLE_ROBOT_READ")
  @RequestMapping(path = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public List<RobotList> readRobots() {
    return robotService.readAll();
  }

  /**
   * Method for mapping the GET /__service/robot/{robotId}
   * 
   * @param robotId
   *          the id of the robot
   * 
   * @return the given robot
   */
  @Secured("ROLE_ROBOT_READ")
  @RequestMapping(path = "/{robotId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public RobotDetail readRobot(
      @PathVariable("robotId") Long robotId) {
    return robotService.read(robotId);
  }

  /**
   * Method for mapping the POST /__service/robot
   * 
   * @param request
   *          the information for the new robot
   * @param userDetails
   *          the authenticated user
   * 
   * @return the new robot
   */
  @Secured("ROLE_ROBOT_EDIT")
  @RequestMapping(path = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public RobotDetail createRobot(
      @RequestBody RobotCreateRequest request,
      @AuthenticationPrincipal RobotUserDetails userDetails) {
    return robotService.create(RobotDetail.builder()
        .name(request.getName())
        .price(request.getPrice())
        .description(request.getDescription())
        .build(), userDetails.getUsername());
  }

  /**
   * Method for mapping the DELETE /__service/robot/{robotId}
   * 
   * @param robotId
   *          the id of the robot
   * 
   * @return the deleted robot
   */
  @Secured("ROLE_ROBOT_DELETE")
  @RequestMapping(path = "/{robotId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public RobotDetail deleteRobot(
      @PathVariable("robotId") Long robotId) {
    return robotService.delete(robotId);
  }
}
