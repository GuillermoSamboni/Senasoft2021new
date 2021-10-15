package com.senasoft2021new.senasoft2021new.validations

import android.widget.EditText

/**
 * realizar todas la validaciones de campos de registros
 */
class Validations {

    companion object{

        /**
         * validar um editText
         * @return true si el editText esta vacio o es nulo - false en caso contrario
         */
        fun validteEditText(editText: EditText)=editText.text.isNullOrEmpty()

    }//endComp

}