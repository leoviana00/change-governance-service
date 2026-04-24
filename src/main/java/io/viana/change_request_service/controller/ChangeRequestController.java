package io.viana.change_governance_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.viana.change_governance_service.dto.ApproveChangeRequestDTO;
import io.viana.change_governance_service.dto.CreateChangeRequestDTO;
import io.viana.change_governance_service.dto.DeployRequestDTO;
import io.viana.change_governance_service.entity.ChangeRequest;
import io.viana.change_governance_service.service.ChangeRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/change-requests")
@Tag(name = "Change Requests", description = "API de governança de mudanças para CI/CD")
public class ChangeRequestController {

    private final ChangeRequestService service;

    public ChangeRequestController(ChangeRequestService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Criar nova solicitação de mudança")
    public ChangeRequest create(@RequestBody CreateChangeRequestDTO dto) {

        ChangeRequest request = new ChangeRequest();
        request.setTitle(dto.getTitle());
        request.setDescription(dto.getDescription());
        request.setRequester(dto.getRequester());
        request.setSystemName(dto.getSystemName());
        request.setRiskLevel(dto.getRiskLevel());

        return service.create(request);
    }

    @GetMapping
    @Operation(summary = "Listar todas as mudanças")
    public List<ChangeRequest> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar mudança por ID")
    public ChangeRequest findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PutMapping("/{id}/approve")
    @Operation(summary = "Aprovar mudança")
    public ChangeRequest approve(
            @PathVariable UUID id,
            @RequestBody ApproveChangeRequestDTO dto) {

        return service.approve(id, dto.getApprovedBy());
    }

    @PutMapping("/{id}/reject")
    @Operation(summary = "Rejeitar mudança")
    public ChangeRequest reject(@PathVariable UUID id) {
        return service.reject(id);
    }

    @PutMapping("/{id}/deploy")
    @Operation(summary = "Executar deploy ou redeploy da mudança")
    public ChangeRequest deploy(
            @PathVariable UUID id,
            @RequestBody(required = false) DeployRequestDTO dto) {

        return service.deploy(id, dto);
    }
}