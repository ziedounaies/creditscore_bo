package com.reactit.credit.score.service.impl;

import com.reactit.credit.score.domain.Banks;
import com.reactit.credit.score.repository.BanksRepository;
import com.reactit.credit.score.service.BanksService;
import com.reactit.credit.score.service.dto.BanksDTO;
import com.reactit.credit.score.service.mapper.BanksMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.reactit.credit.score.domain.Banks}.
 */
@Service
@Transactional
public class BanksServiceImpl implements BanksService {

    private final Logger log = LoggerFactory.getLogger(BanksServiceImpl.class);

    private final BanksRepository banksRepository;

    private final BanksMapper banksMapper;

    public BanksServiceImpl(BanksRepository banksRepository, BanksMapper banksMapper) {
        this.banksRepository = banksRepository;
        this.banksMapper = banksMapper;
    }

    @Override
    public BanksDTO save(BanksDTO banksDTO) {
        log.debug("Request to save Banks : {}", banksDTO);
        Banks banks = banksMapper.toEntity(banksDTO);
        banks = banksRepository.save(banks);
        return banksMapper.toDto(banks);
    }

    @Override
    public BanksDTO update(BanksDTO banksDTO) {
        log.debug("Request to update Banks : {}", banksDTO);
        Banks banks = banksMapper.toEntity(banksDTO);
        banks = banksRepository.save(banks);
        return banksMapper.toDto(banks);
    }

    @Override
    public Optional<BanksDTO> partialUpdate(BanksDTO banksDTO) {
        log.debug("Request to partially update Banks : {}", banksDTO);

        return banksRepository
            .findById(banksDTO.getId())
            .map(existingBanks -> {
                banksMapper.partialUpdate(existingBanks, banksDTO);

                return existingBanks;
            })
            .map(banksRepository::save)
            .map(banksMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BanksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Banks");
        return banksRepository.findAll(pageable).map(banksMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BanksDTO> findOne(Long id) {
        log.debug("Request to get Banks : {}", id);
        return banksRepository.findById(id).map(banksMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Banks : {}", id);
        banksRepository.deleteById(id);
    }
}
