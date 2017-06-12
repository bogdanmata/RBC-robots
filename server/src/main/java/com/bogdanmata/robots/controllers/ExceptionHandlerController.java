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

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bogdanmata.robots.domains.errors.ErrorMessage;

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

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> onException(Exception exception, HttpServletResponse response) {
    ErrorMessage errorMessage = ErrorMessage.builder().errorCode("server.error").errorMessage(exception.getMessage()).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new ResponseEntity<ErrorMessage>(errorMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
  }
  
  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ErrorMessage> exception(AccessDeniedException exception) {
    ErrorMessage errorMessage = ErrorMessage.builder().errorCode("access.denied").errorMessage(exception.getMessage()).build();
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new ResponseEntity<ErrorMessage>(errorMessage, headers, HttpStatus.FORBIDDEN);
  }
}
