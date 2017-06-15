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

import java.util.Date;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bogdanmata.robots.security.domains.RobotUserDetails;

/**
 * TODO add description
 * 
 * Created Jun 15, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Controller
public class ViewController {
  
  @RequestMapping({"/", "/robot", "/robot/*", "/login"})
  public String index(
      Model model, 
      @AuthenticationPrincipal RobotUserDetails userDetails) {
    model.addAttribute("user", userDetails == null ? RobotUserDetails.builder().username("anonymous").build() : userDetails);
    model.addAttribute("datetime", new Date());

    return "index";
  }
}
