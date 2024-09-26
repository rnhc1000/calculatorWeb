package br.dev.ferreiras.calculatorweb.filter;

public class CsrfCookieFilter {

//public class CsrfCookieFilter extends OncePerRequestFilter {
//  @Override
//  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//          throws ServletException, IOException {
//
//    CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//    //Render the token value to a cookie by causing the deferred token to be loaded
//    csrfToken.getToken();
//
//    filterChain.doFilter(request, response);
//  }
}
