package com.example.agent.controller;

import com.example.agent.service.SupportAgent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agent")
public class AgentController {
    private final SupportAgent agent;

    public AgentController(SupportAgent agent) {
        this.agent = agent;
    }

    @GetMapping
    public String ask(@RequestParam String q) {
        return agent.ask(q);
    }
}
