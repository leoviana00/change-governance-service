package io.viana.change_governance_service.repository;

import io.viana.change_governance_service.entity.ChangeRequest;
import io.viana.change_governance_service.enums.ChangeStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ChangeRequestRepository extends JpaRepository<ChangeRequest, UUID> {

    List<ChangeRequest> findByStatus(ChangeStatus status);

}