package com.caiofonseca.foundationapi.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CidadeDTO {

    private Long id;

    @NotEmpty
    private String nome;

    @NotEmpty
    private String uf;


}
