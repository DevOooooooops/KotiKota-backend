package org.hackaton.kotikota.service.validator;

import java.util.function.Consumer;
import org.hackaton.kotikota.endpoint.rest.exception.BadRequestException;
import org.hackaton.kotikota.endpoint.rest.model.Money;
import org.springframework.stereotype.Component;

@Component
public class BankDepositValidator implements Consumer<Money> {
  @Override
  public void accept(Money money) {
    StringBuilder exceptionMessageBuilder = new StringBuilder();
    if (money == null) {
      exceptionMessageBuilder.append("money is mandatory.");
    } else {
      if (money.getAmount() == null) {
        exceptionMessageBuilder.append("money.amount is mandatory.");
      } else if (money.getAmount() <= 0) {
        exceptionMessageBuilder.append("money.amount must be > 0.");
      }
    }
    if (!exceptionMessageBuilder.isEmpty()) {
      throw new BadRequestException(exceptionMessageBuilder.toString());
    }
  }
}
