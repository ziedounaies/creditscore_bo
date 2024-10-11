package com.reactit.credit.score.service.impl;

import com.reactit.credit.score.domain.Claim;
import com.reactit.credit.score.repository.ClaimRepository;
import com.reactit.credit.score.service.ClaimService;
import com.reactit.credit.score.service.dto.ClaimDTO;
import com.reactit.credit.score.service.mapper.ClaimMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.reactit.credit.score.domain.Claim}.
 */
@Service
@Transactional
public class ClaimServiceImpl implements ClaimService {

    private final Logger log = LoggerFactory.getLogger(ClaimServiceImpl.class);

    private final ClaimRepository claimRepository;

    private final ClaimMapper claimMapper;

    public ClaimServiceImpl(ClaimRepository claimRepository, ClaimMapper claimMapper) {
        this.claimRepository = claimRepository;
        this.claimMapper = claimMapper;
    }

    @Override
    public ClaimDTO save(ClaimDTO claimDTO) {
        log.debug("Request to save Claim : {}", claimDTO);
        Claim claim = claimMapper.toEntity(claimDTO);
        claim = claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    public ClaimDTO update(ClaimDTO claimDTO) {
        log.debug("Request to update Claim : {}", claimDTO);
        Claim claim = claimMapper.toEntity(claimDTO);
        claim = claimRepository.save(claim);
        return claimMapper.toDto(claim);
    }

    @Override
    public Optional<ClaimDTO> partialUpdate(ClaimDTO claimDTO) {
        log.debug("Request to partially update Claim : {}", claimDTO);

        return claimRepository
            .findById(claimDTO.getId())
            .map(existingClaim -> {
                claimMapper.partialUpdate(existingClaim, claimDTO);

                return existingClaim;
            })
            .map(claimRepository::save)
            .map(claimMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ClaimDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Claims");
        return claimRepository.findAll(pageable).map(claimMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClaimDTO> findOne(Long id) {
        log.debug("Request to get Claim : {}", id);
        return claimRepository.findById(id).map(claimMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Claim : {}", id);
        claimRepository.deleteById(id);
    }
}
