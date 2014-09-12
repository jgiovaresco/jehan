package com.github.jehan.rest;

import java.io.IOException;
import java.lang.annotation.Annotation;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.apache.http.HttpHeaders;

/**
 * Filter use to add cache control header in response.
 */
@Provider
@CacheControl
public class CacheControlFilter implements ContainerResponseFilter
{
   // ------------------------- Membres private -------------------------
   // ------------------------- Constructeur -------------------------
   // ------------------------- Méthodes public -------------------------

   @Override
   public void filter(ContainerRequestContext p_RequestContext, ContainerResponseContext p_ResponseContext) throws IOException
   {
      for (Annotation a : p_ResponseContext.getEntityAnnotations())
      {
         if (a.annotationType() == CacheControl.class)
         {
            String value = ((CacheControl) a).value();
            p_ResponseContext.getHeaders().putSingle(HttpHeaders.CACHE_CONTROL, value);
            break;
         }
      }
   }

   // ------------------------- Méthodes private -------------------------
}
