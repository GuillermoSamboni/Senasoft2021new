package com.senasoft2021new.senasoft2021new.database

import android.content.Context
import com.senasoft2021new.senasoft2021new.R
import com.senasoft2021new.senasoft2021new.models.UserRegister

/**
 * realizar todas las operaciones de las operaciones relacionada a las sharedPrefences
 */
class SharedPreferencesClient {

    companion object {

        private const val KEY_ID_USER = "ID_USER"
        private const val FIRST_TIME_HOME = "F_HOME"
        private const val FIRST_TIME_EVENTS = "F_EVENTS"
        private const val FIRST_TIME_ADMIN = "F_ADMIN"
        private const val FIRST_TIME_COMPETENCIA= "F_COMPETENCIA"

        fun getInstance(context: Context) =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)


        /**
         * guardar el id del user en sharedPrefences para mantener su seccion abierta
         */
        fun setSeccionUser(userRegister: UserRegister, context: Context) {
            val shp = getInstance(context)
            shp.edit().putInt(KEY_ID_USER, userRegister.id).apply()
        }

        /**
         * verificar si es la primera vez que se accede al apartado de home
         * @return true si es la promera vez en acceder - false en caso contrario
         */
        fun firstTimeHome(context: Context):Boolean{

            val shp= getInstance(context)
            val fTime=shp.getBoolean(FIRST_TIME_HOME, true)
            if(fTime)
                shp.edit().putBoolean(FIRST_TIME_HOME,true)

            return fTime
        }

        /**
         * verificar si es la primera vez que se accede al apartado de Eventos
         * @return true si es la promera vez en acceder - false en caso contrario
         */
        fun firstTimeEvents(context: Context):Boolean{

            val shp= getInstance(context)
            val fTime=shp.getBoolean(FIRST_TIME_EVENTS, true)
            if(fTime)
                shp.edit().putBoolean(FIRST_TIME_EVENTS,true)

            return fTime
        }

        /**
         * verificar si es la primera vez que se accede al apartado de admin
         * @return true si es la promera vez en acceder - false en caso contrario
         */
        fun firstTimeAdmim(context: Context):Boolean{

            val shp= getInstance(context)
            val fTime=shp.getBoolean(FIRST_TIME_ADMIN, true)
            if(fTime)
                shp.edit().putBoolean(FIRST_TIME_ADMIN,true)

            return fTime
        }

        /**
         * verificar si es la primera vez que se accede al apartado de competencia
         * @return true si es la promera vez en acceder - false en caso contrario
         */
        fun firstTimeCompetencia(context: Context):Boolean{

            val shp= getInstance(context)
            val fTime=shp.getBoolean(FIRST_TIME_COMPETENCIA, true)
            if(fTime)
                shp.edit().putBoolean(FIRST_TIME_COMPETENCIA,true)

            return fTime
        }



    }//endComp

}