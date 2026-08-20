package com.lineacademy.coinappspringprev.service;

import com.lineacademy.coinappspringprev.domain.entity.Portfolio;
import com.lineacademy.coinappspringprev.domain.entity.PortfolioItem;
import com.lineacademy.coinappspringprev.domain.entity.User;
import com.lineacademy.coinappspringprev.dto.portfolio.request.CreatePortfolioItemRequest;
import com.lineacademy.coinappspringprev.dto.portfolio.request.CreatePortfolioRequest;
import com.lineacademy.coinappspringprev.repository.PortfolioRepository;
import com.lineacademy.coinappspringprev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    @Transactional(readOnly = true)
    public List<Portfolio> getMyPortfolios(Long userId) {
        return portfolioRepository.findAllByUserIdWithItems(userId);
    }

    @Transactional
    public Portfolio createPortfolio(Long userId, CreatePortfolioRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("USER_NOT_FOUND"));

        Portfolio portfolio = Portfolio.builder()
                .user(user)
                .title(request.getTitle())
                .totalSeedMoney(request.getTotalSeedMoney())
                .build();

        if (request.getItems() != null && !request.getItems().isEmpty()) {
            for (CreatePortfolioItemRequest itemRequest : request.getItems()) {
                PortfolioItem item = PortfolioItem.builder()
                        .portfolio(portfolio)
                        .market(itemRequest.getMarket())
                        .targetRatio(itemRequest.getTargetRatio())
                        .buyPrice(itemRequest.getBuyPrice())
                        .quantity(itemRequest.getQuantity())
                        .build();

                portfolio.getPortfolioItems().add(item);
            }
        }

        return portfolioRepository.save(portfolio);
    }
}