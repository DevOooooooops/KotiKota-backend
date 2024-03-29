package org.hackaton.kotikota.service;

import lombok.AllArgsConstructor;
import org.hackaton.kotikota.endpoint.rest.exception.BadRequestException;
import org.hackaton.kotikota.endpoint.rest.model.Money;
import org.hackaton.kotikota.repository.model.User;
import org.hackaton.kotikota.service.validator.BankDepositValidator;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BankInfoService {
  private final UserService userService;
  private final BankDepositValidator bankDepositValidator;

  public User deposit(String userId, Money money) {
    bankDepositValidator.accept(money);
    User persistedUser = userService.getById(userId);
    persistedUser.setBalance(persistedUser.getBalance() + money.getAmount());
    return userService.save(persistedUser);
  }

  public User withdrawal(String userId, Money money) {
    bankDepositValidator.accept(money);
    User persistedUser = userService.getById(userId);
    if (persistedUser.getBalance() < money.getAmount()) {
      throw new BadRequestException("Not enough balance in your bank account");
    }
    persistedUser.setBalance(persistedUser.getBalance() - money.getAmount());
    return userService.save(persistedUser);
  }
}
