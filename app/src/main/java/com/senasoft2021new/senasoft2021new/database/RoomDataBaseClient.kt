package com.senasoft2021new.senasoft2021new.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.senasoft2021new.senasoft2021new.models.AdminRegister
import com.senasoft2021new.senasoft2021new.models.DenunciaRegister
import com.senasoft2021new.senasoft2021new.models.EventRegister
import com.senasoft2021new.senasoft2021new.models.UserRegister
import kotlinx.coroutines.runBlocking

@Database(
    version = 1,
    entities = [UserRegister::class, AdminRegister::class, EventRegister::class, DenunciaRegister::class]
)
abstract class RoomDataBaseClient : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun adminDap(): AdminDao
    abstract fun eventDao(): EventDao
    abstract fun denunciaDao(): DenunciaDao


    companion object {

        private const val DATABASE_NAME = "SENASOFT"

        /**
         * obtener una instancia de la base de datos
         */
        fun getInstance(context: Context) =
            Room.databaseBuilder(context, RoomDataBaseClient::class.java, DATABASE_NAME)
                .allowMainThreadQueries().build()


        //operaciones para los usuarios//---------------------------------

        /**
         * registrar un nuevo usuario en la base da datos
         * @return true si se registro correctamente - false en caso contrario
         */
        fun registerUser(userRegister: UserRegister, context: Context): Boolean {

            val bd = getInstance(context).userDao()
            var retorno = false

            runBlocking {
                bd.insertUser(userRegister)
                retorno = true
            }
            return retorno

        }

        /**
         * loguear un usuario con una cuenta previamente registrada
         * @return true si se logueo correctamente - false en caso contrario
         */
        fun loginUser(name: String, pass: String, context: Context): Boolean {

            val bd = getInstance(context).userDao()
            var retorno = false

            runBlocking {

                bd.listAllUsers().forEach { user ->
                    if (user.name.lowercase() == name.lowercase() && user.password.lowercase() == pass.lowercase())
                        retorno = true
                }

            }
            return retorno
        }


        /**
         * verificar si el nombre de usuario exite
         * @return true si el nombre existe - false en caso contrario
         */
        fun nameUserExits(name: String, context: Context): Boolean {

            val bd = getInstance(context).userDao()
            var retorno = false

            runBlocking {
                bd.selectUserByName(name)?.let {
                    if (it.name.lowercase() == name.lowercase())
                        retorno = true
                }

            }
            return retorno
        }

        /**
         * verificar si el email de usuario exite
         * @return true si el email existe - false en caso contrario
         */
        fun emailUserExits(email: String, context: Context): Boolean {
            val bd = getInstance(context).userDao()
            var retorno = false

            runBlocking {
                bd.selectUserByEmail(email)?.let {
                    if (it.email.lowercase() == email.lowercase())
                        retorno = true
                }

            }
            return retorno
        }


        //operaciones para los admins//-----------------------------------
        /**
         * loguear un admin con una cuenta previamente registrada
         * @return true si se logueo correctamente - false en caso contrario
         */
        fun loginAdmin(name: String, pass: String, context: Context): Boolean {

            val bd = getInstance(context).adminDap()
            var retorno = false

            runBlocking {
                bd.selectAdminByDni(name)?.let {
                    if (it.dni.lowercase() == name.lowercase() && it.dni == pass.lowercase())
                        retorno = true
                }

            }
            return retorno
        }

        //operaciones para los eventos

        /**
         * registrar un nuevo evento en la base de datos
         * @return true si el evento se registro correctamente - false en caso contrario
         */
        fun registerEvent(eventRegister: EventRegister, context: Context): Boolean {

            val bd = getInstance(context).eventDao()
            var retorno = false

            runBlocking {
                bd.insertEvent(eventRegister)
                retorno = true
            }

            return retorno
        }

        /**
         * listar todos los eventos almacenado en la bd
         * @return lista con todos los eventos registrado
         */
        fun listAllEvents(context: Context) = getInstance(context).eventDao().listAllEvents()

        //operaciones para las denuncias

        /**
         * registrar una nueva denuncia en la base de datos
         * @return true si el denuncia se registro correctamente - false en caso contrario
         */
        fun registerDenuncia(denunciaRegister: DenunciaRegister, context: Context): Boolean {


            var retorno = false
            val bd = getInstance(context).denunciaDao()

            runBlocking {
                bd.insertDenuncia(denunciaRegister)
                retorno = true
            }

            return retorno

        }

        /**
         * listar todas las denuncias almacenado en la bd
         * @return lista con todas las denuncia registrado
         */
        fun lisAllDenuncias(context: Context) =
            getInstance(context).denunciaDao().listAllDenuncias()

    }//endComp


}