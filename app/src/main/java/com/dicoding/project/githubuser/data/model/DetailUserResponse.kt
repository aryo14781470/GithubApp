package com.dicoding.project.githubuser.data.model

data class DetailUserResponse (
    val login: String,
    val id: Int,
    val avatar_url: String,
    val html_url: String,
    val name: String,
    val followers: Int,
    val following: Int,
    val public_repos: Int,
    val public_gists: Int,
    val location: String,
    val company: String
    )