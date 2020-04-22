package com.aaroncarlson.controllers;

import com.aaroncarlson.models.Session;
import com.aaroncarlson.repositories.SessionRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/sessions")
public class SessionController {

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping
    public List<Session> getSessions() {
        return sessionRepository.findAll();
    }

    @GetMapping("/{sessionId}")
    public Session getSessionById(@PathVariable Long sessionId) {
        return sessionRepository.getOne(sessionId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Session createSession(@Valid @RequestBody final Session session) {
        return sessionRepository.saveAndFlush(session);
    }

    @PutMapping("/{sessionId}")
    public Session updateSessionById(@PathVariable Long sessionId,
                                  @Valid @RequestBody final Session session) {
        // Because this is a PUT, we expect all attributes to be passed in. A PATCH would only update attributes provided
        // TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Session existingSession = sessionRepository.getOne(sessionId);
        BeanUtils.copyProperties(session, existingSession, "session_id");
        return sessionRepository.saveAndFlush(existingSession);
    }

    @DeleteMapping("/{sessionId}")
    public void deleteSessionById(@PathVariable Long sessionId) {
        // Also need to check for children records before deleting
        sessionRepository.deleteById(sessionId);
    }

}
