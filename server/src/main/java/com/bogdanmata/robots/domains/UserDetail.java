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
package com.bogdanmata.robots.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * User detail
 * 
 * Created Jun 12, 2017
 * @author Bogdan MATA
 * @since 1.0
 * */
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDetail {
  private String username;
  private String firstName;
  private String lastName;
}
