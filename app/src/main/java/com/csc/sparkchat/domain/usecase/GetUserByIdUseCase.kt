package com.csc.sparkchat.domain.usecase

import com.csc.sparkchat.domain.model.User
import com.csc.sparkchat.domain.repository.ChatRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: ChatRepository
) {
    suspend operator fun invoke(userId: Long): User? = repository.getUserById(userId)
}