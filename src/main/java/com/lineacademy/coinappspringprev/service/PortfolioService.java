package com.lineacademy.coinappspringprev.service;

import com.lineacademy.coinappspringprev.domain.entity.Portfolio;
import com.lineacademy.coinappspringprev.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = true)
    public List<Portfolio> getMyPortfolios(Long userId) {
        return portfolioRepository.findAllByUserIdWithItems(userId);
    }
}