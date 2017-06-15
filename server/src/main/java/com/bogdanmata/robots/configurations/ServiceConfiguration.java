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

/**
 * The service configuration
 * 
 * Created Jun 15, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Configuration
@ComponentScan(basePackages = {
    "com.bogdanmata.robots.services"
})
public class ServiceConfiguration {

}
