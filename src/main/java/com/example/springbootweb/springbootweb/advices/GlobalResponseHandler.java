package com.example.springbootweb.springbootweb.advices;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
/*
ResponseBodyAdvice<Object>

*ResponseBodyAdvice<T> is an interface helps us to modify the message that is to be written in the HttpMessageConverter
*which is sent from the @ResponseBody.

*In simple it helps us to modify the message i.e response object before it is written to the Response body.

*Here we need to implement a class that annotates with @RestControllerAdvice and implements the ResponseBodyAdvice<T>.

It consists of two main methods:

supports: This method determines whether a given response type or controller method should be intercepted and processed.
beforeBodyWrite: This method allows you to manipulate the actual response body before it is sent out.

*In beforeBodyWrite Methods we have the parameter body, this helps us to update the incoming response with the new Response body and it is returned as a new requestBody to the RestController.

 */

@RestControllerAdvice
public class GlobalResponseHandler implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse<?>){
            return body;
        }
        return new ApiResponse<>(body);
    }
}
