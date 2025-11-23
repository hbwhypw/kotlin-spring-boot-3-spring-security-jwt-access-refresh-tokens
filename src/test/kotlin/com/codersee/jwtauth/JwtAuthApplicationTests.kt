package com.codersee.jwtauth

import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.security.SecureRandom
import java.util.Base64

@SpringBootTest
class JwtAuthApplicationTests {

	@Test
	fun base64Key() {
        val keyBytes = ByteArray(32) // 256位
        SecureRandom().nextBytes(keyBytes)
        println(keyBytes)
        val base64Key = Base64.getEncoder().encodeToString(keyBytes)
        println(base64Key)
    }

    @Test
    fun hmacShaKeyFor() {
        var bytes = Decoders.BASE64.decode("knpVfaUULOjJkQ4q6okwe427nR/gQ4J+i8oW3LbydOA=")
        var hmacShaKeyFor = Keys.hmacShaKeyFor(bytes)
        println(hmacShaKeyFor)
    }

}
