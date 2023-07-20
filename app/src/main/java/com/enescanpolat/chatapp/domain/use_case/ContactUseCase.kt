package com.enescanpolat.chatapp.domain.use_case

import com.enescanpolat.chatapp.domain.repository.UserRepository
import javax.inject.Inject

class ContactUseCase @Inject constructor(
    private var userRepository: UserRepository
) {

    fun getAllUsers (deviceContacts : List<String>) = userRepository.getAllUsers(deviceContacts)

}