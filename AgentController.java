package com.example.agent.controller;

import com.example.agent.service.SupportAgent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoke")
public class AgentController {
    private final SupportAgent agent;

    public AgentController(SupportAgent agent) {
        this.agent = agent;
    }

    @GetMapping
    public String ask(@RequestParam String q) {
        return agent.ask(q);
    }

    @PostMapping
    public String invoke(@RequestBody PromptRequest request) {
        return agent.ask(request.prompt());
    }

    record PromptRequest(String prompt) {}
}
