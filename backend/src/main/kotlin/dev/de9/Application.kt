package dev.de9

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("dev.de9")
            .start()
}

