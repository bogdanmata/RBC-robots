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

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.bogdanmata.robots.domains.UserDetail;
import com.bogdanmata.robots.domains.errors.ErrorMessage;
import com.bogdanmata.robots.security.domains.RobotUserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * The web security configuration
 * 
 * Created Jun 12, 2017
 * 
 * @author Bogdan MATA
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests().antMatchers("/", "/index.html", "/static/**", "/js/**", "/css/**", "/partials/**", "/login").permitAll()
        .and().authorizeRequests().anyRequest().fullyAuthenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new AuthenticationEntryPoint() {

          private final ObjectMapper objectMapper = new ObjectMapper();

          @Override
          public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
              throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ErrorMessage errorMessage =
                ErrorMessage.builder().errorCode("authentication.exception.required").errorMessage(authenticationException.getMessage()).build();
            response.getWriter().append(objectMapper.writeValueAsString(errorMessage));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().flush();
          }
        })
        .and()
        .formLogin()
        .loginProcessingUrl("/__service/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .successHandler(new AuthenticationSuccessHandler() {
          
          private final ObjectMapper objectMapper = new ObjectMapper();
          
          @Override
          public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
              throws IOException, ServletException {
            
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            RobotUserDetails userDetails = (RobotUserDetails) authentication.getPrincipal();
            response.getWriter().append(objectMapper.writeValueAsString(
                UserDetail.builder()
                  .username(userDetails.getUsername())
                  .firstName(userDetails.getFirstName())
                  .lastName(userDetails.getLastName())
                  .build()));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().flush();
          }
        })
        .failureHandler(new AuthenticationFailureHandler() {

          private final ObjectMapper objectMapper = new ObjectMapper();

          @Override
          public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException)
              throws IOException, ServletException {
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            ErrorMessage errorMessage =
                ErrorMessage.builder().errorCode("authentication.exception.wrong").errorMessage(authenticationException.getMessage()).build();
            response.getWriter().append(objectMapper.writeValueAsString(errorMessage));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().flush();
          }
        })
        .and().logout().logoutUrl("/__service/logout")
        .and().csrf().disable();
  }

}
