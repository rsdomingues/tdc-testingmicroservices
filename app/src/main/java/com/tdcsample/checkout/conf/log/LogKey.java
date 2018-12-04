package com.tdcsample.checkout.conf.log;

import com.google.common.base.CaseFormat;

public enum LogKey {
  FEATURE_KEY,
  FEATURE_VALUE,
  AMOUNT_ITEMS,
  PAYMENT_METHOD,
  CAUSE;


  @Override
  public String toString() {
    return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, this.name().toLowerCase());
  }
}
