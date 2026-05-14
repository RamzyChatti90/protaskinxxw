package com.example.protaskinxxw.web.rest;

import com.example.protaskinxxw.service.DashboardService;
import com.example.protaskinxxw.service.dto.DashboardStatsDTO;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;


/**
 * REST controller for managing {@link com.example.protaskinxxw.domain.Dashboard}.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardResource {

    private static final Logger LOG = LoggerFactory.getLogger(DashboardResource.class);

    private final DashboardService dashboardService;

    public DashboardResource(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * {@code GET /dashboard/stats} : get stats aggregation.
     */
    @Operation(summary = "Get stats aggregation")
    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getStats() {
        DashboardStatsDTO dto = dashboardService.getStats();
        return ResponseEntity.ok(dto);
    }
}