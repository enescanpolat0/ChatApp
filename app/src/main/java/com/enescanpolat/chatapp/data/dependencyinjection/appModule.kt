package com.enescanpolat.chatapp.data.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.enescanpolat.chatapp.data.localdatabase.UserDao
import com.enescanpolat.chatapp.data.localdatabase.UserDatabase
import com.enescanpolat.chatapp.data.repository.AuthRepositoryImpl
import com.enescanpolat.chatapp.data.repository.UserRepositoryImpl
import com.enescanpolat.chatapp.domain.repository.AuthRepository
import com.enescanpolat.chatapp.domain.repository.UserRepository
import com.enescanpolat.chatapp.domain.use_case.AutheticationUseCase
import com.enescanpolat.chatapp.domain.use_case.ChatUseCase
import com.enescanpolat.chatapp.domain.use_case.ContactUseCase
import com.enescanpolat.chatapp.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object appModule {


    @Singleton
    @Provides
    fun injectFirebaseAuth():FirebaseAuth{
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun injectFirebaseFirestore():FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun injectAuthRepository(firebaseAuth: FirebaseAuth,fireBaseFirestore: FirebaseFirestore):AuthRepository{
        return AuthRepositoryImpl(firebaseAuth,fireBaseFirestore)
    }

    @Singleton
    @Provides
    fun injectAuheticationUseCase(authRepository: AuthRepository):AutheticationUseCase{
        return AutheticationUseCase(authRepository)
    }


    @Singleton
    @Provides
    fun injectContactRepository(firestore: FirebaseFirestore,userDao: UserDao):UserRepository{
        return UserRepositoryImpl(firestore,userDao)
    }

    @Singleton
    @Provides
    fun injectContactUseCase(userRepository: UserRepository):ContactUseCase{
        return ContactUseCase(userRepository)
    }


    @Singleton
    @Provides
    fun injectUserRoomDatabase(@ApplicationContext context : Context) : UserDatabase{

        return Room.databaseBuilder(context,UserDatabase::class.java,Constants.userRoomDatabase).build()

    }

    @Singleton
    @Provides
    fun injectUserDao(database: UserDatabase) : UserDao{
        return database.userDao()
    }

    @Singleton
    @Provides
    fun injectChatUseCase(userRepository: UserRepository):ChatUseCase{
        return ChatUseCase(userRepository)
    }





}