package com.example.demo.utils;

import com.example.demo.enums.UserRole;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;


@Component
public class RoleChecker {


    public void verifierConnexion(HttpSession session) {
        Long userId = (Long) session.getAttribute("userID");
        if (userId == null) {
            throw new UnauthorizedException("Vous devez être connecté pour accéder à cette ressource");
        }
    }


    public void verifierAdmin(HttpSession session) {
        verifierConnexion(session);
        UserRole role = (UserRole) session.getAttribute("role");
        if (role != UserRole.ADMIN) {
            throw new ForbiddenException("Seul un ADMIN peut effectuer cette action");
        }
    }


    public void verifierClient(HttpSession session) {
        verifierConnexion(session);
        UserRole role = (UserRole) session.getAttribute("role");
        if (role != UserRole.CLIENT) {
            throw new ForbiddenException("Seul un CLIENT peut effectuer cette action");
        }
    }


    public boolean estAdmin(HttpSession session) {
        UserRole role = (UserRole) session.getAttribute("role");
        return role != null && role == UserRole.ADMIN;
    }


    public boolean estClient(HttpSession session) {
        UserRole role = (UserRole) session.getAttribute("role");
        return role != null && role == UserRole.CLIENT;
    }


    public Long getUserId(HttpSession session) {
        return (Long) session.getAttribute("userID");
    }


    public Long getClientId(HttpSession session) {
        return (Long) session.getAttribute("clientID");
    }
}