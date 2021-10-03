package com.example.authfeature.utils


import com.example.entity.Product
import com.example.entity.Profile

/**
 * Dummy data generator for tests
 */
class TestDataGenerator {

    companion object {
        fun generateProfile(): Profile {
            return Profile(
                username = "ahmedshaaban",
                image = "https://i.picsum.photos/id/1062/5092/3395.jpg?hmac=o9m7qeU51uOLfXvepXcTrk2ZPiSBJEkiiOp-Qvxja-k",
                name = "معز نديم",
                email = "z5yrjx4dxfxkyd5@gmail.com",
                phone = "01910894829"
            )
        }
    }

}