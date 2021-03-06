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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The controller for all HTML pages
 * 
 * Created Jun 15, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Controller
public class ViewController {

  /**
   * Mapping for index page
   * 
   * @param model
   *          the model
   * 
   * @return index
   */
  @RequestMapping({ "/", "/robot", "/robot/*", "/login" }) // all declared routes should also be declare here
  public String index(Model model) {
    model.addAttribute("datetime", new Date());
    return "index";
  }
}
