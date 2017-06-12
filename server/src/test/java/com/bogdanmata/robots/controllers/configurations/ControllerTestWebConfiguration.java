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
package com.bogdanmata.robots.controllers.configurations;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

import com.bogdanmata.robots.configurations.JPAConfiguration;

/**
 * The configuration for the controller tests
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = {
    "com.bogdanmata.robots.configurations",
    "com.bogdanmata.robots.security.annotations"
}, excludeFilters = {
    @Filter(type = FilterType.ASSIGNABLE_TYPE, value = JPAConfiguration.class)
})
public class ControllerTestWebConfiguration {
}
