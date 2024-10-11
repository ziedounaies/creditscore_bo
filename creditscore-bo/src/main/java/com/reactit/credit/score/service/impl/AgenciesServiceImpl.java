package com.reactit.credit.score.service.impl;

import com.reactit.credit.score.domain.Agencies;
import com.reactit.credit.score.repository.AgenciesRepository;
import com.reactit.credit.score.service.AgenciesService;
import com.reactit.credit.score.service.dto.AgenciesDTO;
import com.reactit.credit.score.service.mapper.AgenciesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.reactit.credit.score.domain.Agencies}.
 */
@Service
@Transactional
public class AgenciesServiceImpl implements AgenciesService {

    private final Logger log = LoggerFactory.getLogger(AgenciesServiceImpl.class);

    private final AgenciesRepository agenciesRepository;

    private final AgenciesMapper agenciesMapper;

    public AgenciesServiceImpl(AgenciesRepository agenciesRepository, AgenciesMapper agenciesMapper) {
        this.agenciesRepository = agenciesRepository;
        this.agenciesMapper = agenciesMapper;
    }

    @Override
    public AgenciesDTO save(AgenciesDTO agenciesDTO) {
        log.debug("Request to save Agencies : {}", agenciesDTO);
        Agencies agencies = agenciesMapper.toEntity(agenciesDTO);
        agencies = agenciesRepository.save(agencies);
        return agenciesMapper.toDto(agencies);
    }

    @Override
    public AgenciesDTO update(AgenciesDTO agenciesDTO) {
        log.debug("Request to update Agencies : {}", agenciesDTO);
        Agencies agencies = agenciesMapper.toEntity(agenciesDTO);
        agencies = agenciesRepository.save(agencies);
        return agenciesMapper.toDto(agencies);
    }

    @Override
    public Optional<AgenciesDTO> partialUpdate(AgenciesDTO agenciesDTO) {
        log.debug("Request to partially update Agencies : {}", agenciesDTO);

        return agenciesRepository
            .findById(agenciesDTO.getId())
            .map(existingAgencies -> {
                agenciesMapper.partialUpdate(existingAgencies, agenciesDTO);

                return existingAgencies;
            })
            .map(agenciesRepository::save)
            .map(agenciesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AgenciesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Agencies");
        return agenciesRepository.findAll(pageable).map(agenciesMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AgenciesDTO> findOne(Long id) {
        log.debug("Request to get Agencies : {}", id);
        return agenciesRepository.findById(id).map(agenciesMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Agencies : {}", id);
        agenciesRepository.deleteById(id);
    }
}
