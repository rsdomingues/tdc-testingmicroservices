package com.tdcsample.checkout.conf.ff4j;

import lombok.Getter;

@Getter
public enum Features {

  // @formatter:off
  BILLET_PAYMENT(
      "billet-payment", "Payment", "Payment by billet", false);
  // @formatter:on

  @Getter private final String key;
  private final String description;
  private final String groupName;
  private final boolean defaultValue;

  Features(
      final String key, final String group, final String description, final boolean defaultValue) {
    this.key = key;
    this.description = description;
    this.defaultValue = defaultValue;
    this.groupName = group;
  }
}
