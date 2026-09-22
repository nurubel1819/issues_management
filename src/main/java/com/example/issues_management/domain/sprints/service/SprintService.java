package com.example.issues_management.domain.sprints.service;

import com.example.issues_management.common.exception.ResourceNotFoundException;
import com.example.issues_management.domain.sprints.dtos.SprintRequest;
import com.example.issues_management.domain.sprints.dtos.SprintResponse;
import com.example.issues_management.domain.sprints.entitys.Sprint;
import com.example.issues_management.domain.sprints.mapper.SprintMapper;
import com.example.issues_management.domain.sprints.repo.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SprintService {

    private final SprintRepository sprintRepository;
    private final SprintMapper sprintMapper;

    @Transactional
    public SprintResponse createSprint(SprintRequest request) {
        Sprint saved = sprintRepository.save(sprintMapper.toEntity(request));
        return sprintMapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SprintResponse> getAllSprints() {
        return sprintRepository.findAll().stream()
                .map(sprintMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<SprintResponse> getActiveSprints() {
        return sprintRepository.findByActiveTrue().stream()
                .map(sprintMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SprintResponse getSprintById(Long id) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + id));
        return sprintMapper.toResponse(sprint);
    }

    @Transactional
    public SprintResponse updateSprint(Long id, SprintRequest request) {
        Sprint sprint = sprintRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sprint not found with id: " + id));

        sprintMapper.updateEntity(sprint, request);
        return sprintMapper.toResponse(sprintRepository.save(sprint));
    }

    @Transactional
    public void deleteSprint(Long id) {
        if (!sprintRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sprint not found with id: " + id);
        }
        sprintRepository.deleteById(id);
    }
}
