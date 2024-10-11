package com.reactit.credit.score.service.impl;

import com.reactit.credit.score.domain.CreditRapport;
import com.reactit.credit.score.repository.CreditRapportRepository;
import com.reactit.credit.score.service.CreditRapportService;
import com.reactit.credit.score.service.dto.CreditRapportDTO;
import com.reactit.credit.score.service.mapper.CreditRapportMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.reactit.credit.score.domain.CreditRapport}.
 */
@Service
@Transactional
public class CreditRapportServiceImpl implements CreditRapportService {

    private final Logger log = LoggerFactory.getLogger(CreditRapportServiceImpl.class);

    private final CreditRapportRepository creditRapportRepository;

    private final CreditRapportMapper creditRapportMapper;

    public CreditRapportServiceImpl(CreditRapportRepository creditRapportRepository, CreditRapportMapper creditRapportMapper) {
        this.creditRapportRepository = creditRapportRepository;
        this.creditRapportMapper = creditRapportMapper;
    }

    @Override
    public CreditRapportDTO save(CreditRapportDTO creditRapportDTO) {
        log.debug("Request to save CreditRapport : {}", creditRapportDTO);
        CreditRapport creditRapport = creditRapportMapper.toEntity(creditRapportDTO);
        creditRapport = creditRapportRepository.save(creditRapport);
        return creditRapportMapper.toDto(creditRapport);
    }

    @Override
    public CreditRapportDTO update(CreditRapportDTO creditRapportDTO) {
        log.debug("Request to update CreditRapport : {}", creditRapportDTO);
        CreditRapport creditRapport = creditRapportMapper.toEntity(creditRapportDTO);
        creditRapport = creditRapportRepository.save(creditRapport);
        return creditRapportMapper.toDto(creditRapport);
    }

    @Override
    public Optional<CreditRapportDTO> partialUpdate(CreditRapportDTO creditRapportDTO) {
        log.debug("Request to partially update CreditRapport : {}", creditRapportDTO);

        return creditRapportRepository
            .findById(creditRapportDTO.getId())
            .map(existingCreditRapport -> {
                creditRapportMapper.partialUpdate(existingCreditRapport, creditRapportDTO);

                return existingCreditRapport;
            })
            .map(creditRapportRepository::save)
            .map(creditRapportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CreditRapportDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CreditRapports");
        return creditRapportRepository.findAll(pageable).map(creditRapportMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditRapportDTO> findOne(Long id) {
        log.debug("Request to get CreditRapport : {}", id);
        return creditRapportRepository.findById(id).map(creditRapportMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CreditRapport : {}", id);
        creditRapportRepository.deleteById(id);
    }
}
