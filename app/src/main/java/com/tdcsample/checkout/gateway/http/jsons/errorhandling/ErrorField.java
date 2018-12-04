package com.tdcsample.checkout.gateway.http.jsons.errorhandling;

import lombok.*;

import java.util.List;

@ToString
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode
public class ErrorField {

    String messageCode;

    List<String> fields;

}
