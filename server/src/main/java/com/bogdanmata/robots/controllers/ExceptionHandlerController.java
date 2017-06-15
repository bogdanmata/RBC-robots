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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bogdanmata.robots.domains.errors.ErrorMessage;
import com.bogdanmata.robots.exceptions.BusinessException;
import com.bogdanmata.robots.exceptions.NotFoundException;
import com.bogdanmata.robots.security.domains.RobotUserDetails;

/**
 * The exceptions controller
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@ControllerAdvice
public class ExceptionHandlerController {

  /**
   * Method for handling all exception (the most general method)
   * 
   * @param exception
   *          the exception
   * 
   * @return a response containing the JSON format of the exception
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> onException(Exception exception) {
    ErrorMessage errorMessage = ErrorMessage.builder().errorCode("server.error").errorMessageDetails(exception.getMessage()).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new ResponseEntity<>(errorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Method for handling the access denied exceptions. When the user is unauthorized the code is 401, otherwise is 403
   * 
   * @param exception
   *          access denied exception
   * @param userDetails
   *          the authenticated user
   * 
   * @return a response containing the JSON format of the exception
   */
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorMessage> onException(AccessDeniedException exception, @AuthenticationPrincipal RobotUserDetails userDetails) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

    // in the case if the user is not authenticate
    if (userDetails == null) {
      ErrorMessage errorMessage = ErrorMessage.builder().errorCode("authentication.exception.required").errorMessageDetails(exception.getMessage()).build();
      return new ResponseEntity<>(errorMessage, headers, HttpStatus.UNAUTHORIZED);
    }

    ErrorMessage errorMessage = ErrorMessage.builder().errorCode("access.denied").errorMessageDetails(exception.getMessage()).build();
    return new ResponseEntity<>(errorMessage, headers, HttpStatus.FORBIDDEN);
  }

  /**
   * Method for handling the business exceptions. Code id 500. (this is generic - a different code should be used)
   * 
   * @param exception
   *          business exception
   * 
   * @return a response containing the JSON format of the exception
   */
  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<ErrorMessage> onException(BusinessException exception) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

    ErrorMessage errorMessage = ErrorMessage.builder().errorCode(exception.getCode()).errorMessageDetails(exception.getMessage()).build();
    return new ResponseEntity<>(errorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  /**
   * Method for handling the not found exceptions. Code id 404.
   * 
   * @param exception
   *          not found exception
   * 
   * @return a response containing the JSON format of the exception
   */
  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorMessage> onException(NotFoundException exception) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

    ErrorMessage errorMessage = ErrorMessage.builder().errorCode(exception.getCode()).errorMessageDetails(exception.getMessage()).build();
    return new ResponseEntity<>(errorMessage, headers, HttpStatus.NOT_FOUND);
  }
}
