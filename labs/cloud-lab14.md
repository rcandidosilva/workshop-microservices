# Laboratório 14

## Objetivos
- Utilizando recursos avançados no Netflix Zuul

## Tarefas

### Customize as restrições de cross domain (CORS)
- Utilize os projetos definidos no exercício anterior
- Implemente um `CorsFilter` na classe `ZuulServerApplication` no projeto `zuul-server`
```java
  @EnableZuulProxy
  @SpringBootApplication
  public class ZuulServerApplication {
      //...
	    @Bean
	    public CorsFilter corsFilter() {
	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        final CorsConfiguration config = new CorsConfiguration();
	        config.addAllowedOrigin("http://localhost:8000");
	        source.registerCorsConfiguration("/**", config);
	        return new CorsFilter(source);
	    }			
  }
```
- Execute e teste a aplicação
  - Utilize o site do REST test para testar o comportamento CORS
    - http://resttesttest.com
  - Foi possível realizar a requisição ao Zuul Server com sucesso?
- Modifique o `CorsFilter` para adicionar um novo `allowed-origin` do site REST test
```java
  config.addAllowedOrigin("http://resttesttest.com");
```
- Execute e teste novamente a aplicação

### Implemente um Hystrix fallback padrão via ZuulFallbackProvider
- Utilize os projetos definidos anteriormente
- Implemente uma classe `DefaultFallbackProvider` para definir um Hystrix fallback padrão
```java
  public class DefaultFallbackProvider implements ZuulFallbackProvider {

	    @Override
	    public String getRoute() {
		      return "*";
	    }

	    @Override
	    public ClientHttpResponse fallbackResponse() {
		      return new ClientHttpResponse() {
			      @Override
			      public HttpStatus getStatusCode() throws IOException {
				        return HttpStatus.OK;
			      }

			      @Override
			      public int getRawStatusCode() throws IOException {
				        return 200;
			      }

			      @Override
			      public String getStatusText() throws IOException {
				        return "OK";
			      }

			      @Override
			      public void close() {}

			      @Override
			      public InputStream getBody() throws IOException {
				         return new ByteArrayInputStream("fallback".getBytes());
			      }

			      @Override
			      public HttpHeaders getHeaders() {
				         HttpHeaders headers = new HttpHeaders();
				         headers.setContentType(MediaType.APPLICATION_JSON);
				         return headers;
			      }
		      };
	    }
  }
```
- Configure o `DefaultFallbackProvider` na classe do `ZuulServerApplication`
```java
  @EnableZuulProxy
  @SpringBootApplication
  public class ZuulServerApplication {
      //...
	    @Bean
	    public DefaultFallbackProvider defaultFallbackProvider() {
		      return new DefaultFallbackProvider();
	    }
	}
```
- Execute e teste a aplicação

### Implemente filtros no Zuul Server
- Utilize os projetos definidos anteriormente
- Implemente um classe `LoggingRequestFilter` para filtrar e logar as requisições recebidas
```java
  public class LoggingRequestFilter extends ZuulFilter {

      private static Logger log = LoggerFactory.getLogger(LoggingRequestFilter.class);

      @Override
      public String filterType() {
          return "pre";
      }

      @Override
      public int filterOrder() {
          return 1;
      }

      @Override
      public boolean shouldFilter() {
          return true;
      }

      @Override
      public Object run() {
          RequestContext ctx = RequestContext.getCurrentContext();
          HttpServletRequest request = ctx.getRequest();
          log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
          return null;
      }
  }
```
- Implemente também um classe `AddResponseHeaderFilter` para adicionar um header customizado na resposta
```java
  public class AddResponseHeaderFilter extends ZuulFilter {

	    public String filterType() {
		      return "post";
	    }

	    public int filterOrder() {
		      return 999;
	    }

	    public boolean shouldFilter() {
		      return true;
	    }

	    public Object run() {
		      RequestContext context = RequestContext.getCurrentContext();
		      HttpServletResponse servletResponse = context.getResponse();
		      servletResponse.addHeader("X-Foo", UUID.randomUUID().toString());
		      return null;
	    }
  }
```
- Configure os filtros implementados na classe do `ZuulServerApplication`
```java
  @EnableZuulProxy
  @SpringBootApplication
  public class ZuulServerApplication {
      //...
	    @Bean
	    public LoggingRequestFilter loggingRequestFilter() {
		      return new LoggingRequestFilter();
	    }

	    @Bean
	    public AddResponseHeaderFilter addResponseHeaderFilter() {
		      return new AddResponseHeaderFilter();
	    }
  }
```
- Execute e teste a aplicação
  - Verifique os filtros sendo executados
