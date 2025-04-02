package com.example.mockserver.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@RestController
@RequestMapping("/mock-config")
public class MockController {

    private final WireMockServer wireMockServer;

    public MockController(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
    }

    @PostMapping("/stub")
    public String createStub(@RequestBody Map<String, String> config) {
        wireMockServer.stubFor(get(urlEqualTo(config.get("path")))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(config.get("response"))));
        return "Stub created at " + config.get("path");
    }
}
