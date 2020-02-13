package com.caiofonseca.foundationapi.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class DataToolsTest {

    @Test
    @DisplayName("Retorna numero de Anos entre duas datas")
    public void getNumAnosTest() throws Exception {

      String dataInicial = "24/04/1974";
      String dataFinal = "01/08/2019";

        DataTools dataTools = new DataTools();

        int numAnos = dataTools.getAnosEntreDuasDatas(dataInicial, dataFinal);

        Assertions.assertThat(numAnos).isEqualTo(45);

    }
}



