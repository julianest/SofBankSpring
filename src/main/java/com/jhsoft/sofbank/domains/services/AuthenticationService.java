package com.jhsoft.sofbank.domains.services;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;

@Service
public class AuthenticationService {

    public String getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth.getName()== null || auth.getName().isEmpty()){
            throw new AccessDeniedException("Usuario no autenticado. No se puede realizar esta operación.");
        }
        return auth.getName();

    }

    public boolean isAuthenticated(){
        return getCurrentUser() != null;
    }

    public <T>Function<T, T> addCreatedByAuditInfo() {
        return obj -> {
            String authUser = getCurrentUser();
            try {
                Method setCreatedByMethod = obj.getClass().getMethod("setCreatedBy", String.class);
                setCreatedByMethod.invoke(obj, authUser);
                return obj;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("El objeto no tiene el método setCreatedBy o no se puede invocar", e);
            }
        };
    }

}
