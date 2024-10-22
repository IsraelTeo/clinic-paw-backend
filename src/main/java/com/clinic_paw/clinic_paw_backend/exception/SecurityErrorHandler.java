package com.clinic_paw.clinic_paw_backend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.method.MethodAuthorizationDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class SecurityErrorHandler implements MethodAuthorizationDeniedHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityErrorHandler.class);

    @Override
    public Object handleDeniedInvocation(MethodInvocation methodInvocation, AuthorizationResult authorizationResult) {
        LOGGER .info("\n\n\n");
        LOGGER.info("Method info -> {}", methodInvocation.toString());
        LOGGER.info("Is authorized? {}", authorizationResult.isGranted());

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonNode = mapper.createObjectNode();
        jsonNode.put("status", 401);
        jsonNode.put("message", "Not authorized");
        try {
            return mapper.writeValueAsString(jsonNode);
        }catch (JsonProcessingException e) {
            throw new PawException(ApiError.JSON_PROCESSING_FAILED);
        }
    }
}