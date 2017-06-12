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

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The persistence configuration
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {
    "com.bogdanmata.robots.persistence.repositories"
})
@EntityScan(basePackages = {
    "com.bogdanmata.robots.persistence.entities"
})
public class JPAConfiguration {

}
