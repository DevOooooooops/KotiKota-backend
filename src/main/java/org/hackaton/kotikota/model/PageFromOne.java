package org.hackaton.kotikota.model;

import lombok.Getter;
import org.hackaton.kotikota.endpoint.rest.exception.BadRequestException;

public class PageFromOne {
  public static final int MIN_PAGE = 1;
  @Getter private final int value;

  public PageFromOne(int value) {
    if (value < MIN_PAGE) {
      throw new BadRequestException("page must be >=1");
    }
    this.value = value;
  }
}
