package com.panini.support.data.remote.model

data class LoginRequestDto(
    val email: String,
    val password: String
)

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val role: String
)