package com.caiofonseca.foundationapi.api.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotEmpty
    private String nome;

    private String dataNascimento;

    private Integer idade;

    private Long cidadeId;

    private CidadeDTO cidade;


}
