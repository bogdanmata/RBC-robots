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
package com.bogdanmata.robots.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

/**
 * The application start
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.bogdanmata.robots.configurations"
})
@Slf4j
public class Application extends SpringBootServletInitializer {
  
  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(Application.class);
  }

  public static void main(String[] args) {
    log.debug("Application starting");
    SpringApplication.run(Application.class, args); // NOSONAR This is false positive
  }
}
