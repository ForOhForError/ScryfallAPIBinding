package forohfor.scryfall.api;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class RequestResponseLoggingInterceptor implements ClientHttpRequestInterceptor
{

  private static final Logger LOG
          = Logger.getLogger(RequestResponseLoggingInterceptor.class.getName());

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body,
          ClientHttpRequestExecution execution) throws IOException
  {
    logRequest(request, body);
    ClientHttpResponse response = execution.execute(request, body);
    logResponse(response);
    return response;
  }

  private void logRequest(HttpRequest request, byte[] body) throws IOException
  {
    if (LOG.isLoggable(Level.FINE))
    {
      LOG.fine("===========================request begin================================================");
      LOG.log(Level.FINE, "URI         : {0}", request.getURI());
      LOG.log(Level.FINE, "Method      : {0}", request.getMethod());
      LOG.log(Level.FINE, "Headers     : {0}", request.getHeaders());
      LOG.log(Level.FINE, "Request body: {0}", new String(body, "UTF-8"));
      LOG.fine("==========================request end================================================");
    }
  }

  private void logResponse(ClientHttpResponse response) throws IOException
  {
    if (LOG.isLoggable(Level.FINE))
    {
      LOG.fine("============================response begin==========================================");
      LOG.log(Level.FINE, "Status code  : {0}", response.getStatusCode());
      LOG.log(Level.FINE, "Status text  : {0}", response.getStatusText());
      LOG.log(Level.FINE, "Headers      : {0}", response.getHeaders());
      LOG.log(Level.FINE, "Response body: {0}", StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
      LOG.fine("=======================response end=================================================");
    }
  }
}
