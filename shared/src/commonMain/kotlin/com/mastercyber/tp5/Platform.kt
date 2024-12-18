package com.mastercyber.tp5

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform