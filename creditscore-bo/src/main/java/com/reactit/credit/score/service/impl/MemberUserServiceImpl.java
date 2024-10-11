package com.reactit.credit.score.service.impl;

import com.reactit.credit.score.domain.MemberUser;
import com.reactit.credit.score.repository.MemberUserRepository;
import com.reactit.credit.score.service.MemberUserService;
import com.reactit.credit.score.service.dto.MemberUserDTO;
import com.reactit.credit.score.service.mapper.MemberUserMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.reactit.credit.score.domain.MemberUser}.
 */
@Service
@Transactional
public class MemberUserServiceImpl implements MemberUserService {

    private final Logger log = LoggerFactory.getLogger(MemberUserServiceImpl.class);

    private final MemberUserRepository memberUserRepository;

    private final MemberUserMapper memberUserMapper;

    public MemberUserServiceImpl(MemberUserRepository memberUserRepository, MemberUserMapper memberUserMapper) {
        this.memberUserRepository = memberUserRepository;
        this.memberUserMapper = memberUserMapper;
    }

    @Override
    public MemberUserDTO save(MemberUserDTO memberUserDTO) {
        log.debug("Request to save MemberUser : {}", memberUserDTO);
        MemberUser memberUser = memberUserMapper.toEntity(memberUserDTO);
        memberUser = memberUserRepository.save(memberUser);
        return memberUserMapper.toDto(memberUser);
    }

    @Override
    public MemberUserDTO update(MemberUserDTO memberUserDTO) {
        log.debug("Request to update MemberUser : {}", memberUserDTO);
        MemberUser memberUser = memberUserMapper.toEntity(memberUserDTO);
        memberUser = memberUserRepository.save(memberUser);
        return memberUserMapper.toDto(memberUser);
    }

    @Override
    public Optional<MemberUserDTO> partialUpdate(MemberUserDTO memberUserDTO) {
        log.debug("Request to partially update MemberUser : {}", memberUserDTO);

        return memberUserRepository
            .findById(memberUserDTO.getId())
            .map(existingMemberUser -> {
                memberUserMapper.partialUpdate(existingMemberUser, memberUserDTO);

                return existingMemberUser;
            })
            .map(memberUserRepository::save)
            .map(memberUserMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MemberUserDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MemberUsers");
        return memberUserRepository.findAll(pageable).map(memberUserMapper::toDto);
    }

    /**
     *  Get all the memberUsers where CreditRapport is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MemberUserDTO> findAllWhereCreditRapportIsNull() {
        log.debug("Request to get all memberUsers where CreditRapport is null");
        return StreamSupport
            .stream(memberUserRepository.findAll().spliterator(), false)
            .filter(memberUser -> memberUser.getCreditRapport() == null)
            .map(memberUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the memberUsers where Invoice is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<MemberUserDTO> findAllWhereInvoiceIsNull() {
        log.debug("Request to get all memberUsers where Invoice is null");
        return StreamSupport
            .stream(memberUserRepository.findAll().spliterator(), false)
            .filter(memberUser -> memberUser.getInvoice() == null)
            .map(memberUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MemberUserDTO> findOne(Long id) {
        log.debug("Request to get MemberUser : {}", id);
        return memberUserRepository.findById(id).map(memberUserMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete MemberUser : {}", id);
        memberUserRepository.deleteById(id);
    }
}
