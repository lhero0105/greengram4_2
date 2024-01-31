package com.green.greengram4.security;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor // json to object 시
@AllArgsConstructor
public class MyPrincipal {
    private int iuser;

}
