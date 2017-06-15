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
package com.bogdanmata.robots.exceptions;

import lombok.Getter;

/**
 * The exceptions when a resource is not found
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Getter
public class NotFoundException extends BusinessException {

  /**
   * Serial version UID
   */
  private static final long serialVersionUID = 1236673549909428284L;

  /**
   * Constructor
   * 
   * @param code
   *          the code for exception
   * @param message
   *          the message if the exception
   * 
   * @see {@link BusinessException#BusinessException(String, String)}
   */
  public NotFoundException(String code, String message) {
    super(code, message);
  }

  /**
   * @param code
   *          the code for exception
   * @param message
   *          the message if the exception
   * @param cause
   *          the cause
   * 
   * @see {@link BusinessException#BusinessException(String, String, Throwable)}
   */
  public NotFoundException(String code, String message, Throwable cause) {
    super(code, message, cause);
  }
}
