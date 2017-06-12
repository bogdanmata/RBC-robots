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
package com.bogdanmata.robots.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * The web configuration
 * 
 * Created Oct 29, 2016
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
    "com.bogdanmata.robots.controllers",
    "com.bogdanmata.robots.services"
})
public class WebConfiguration extends WebMvcConfigurerAdapter {
}
