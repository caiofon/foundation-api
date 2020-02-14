package com.caiofonseca.foundationapi.util;

import com.caiofonseca.foundationapi.api.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataTools {


    public int getAnosEntreDuasDatas(String dataInicial, String dataFinal) throws ParseException {

        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");


        try {
            Date data1 = sdf.parse(dataInicial);
            Date data2 = sdf.parse(dataFinal);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(data1);

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(data2);

            long valor = 0;

            if (data1.getTime() > data2.getTime()) {
                if (cal1.get(Calendar.MONTH) < cal2.get(Calendar.MONTH) &&
                        cal1.get(Calendar.DAY_OF_MONTH) < cal2.get(Calendar.DAY_OF_MONTH) ) {
                    valor = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR) - 1;
                } else { valor = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
                }            }
            else {
                if (cal2.get(Calendar.MONTH) < cal1.get(Calendar.MONTH) &&
                        cal2.get(Calendar.DAY_OF_MONTH) < cal1.get(Calendar.DAY_OF_MONTH) ) {
                    valor = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) - 1;
                } else { valor = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
                }
            }

            int numAnos = (int) (valor);

            return numAnos;

        } catch (ParseException pe) {

            throw new BusinessException("Data para calculo de numero de anos invalida. Formato DD/MM/YYYY");

            }
    }

}
