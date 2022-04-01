package com.posadeus.btc

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CheckoutTest {

  private val rules = listOf(Rule("", 0),
                             Rule("A", 50),
                             Rule("B", 30),
                             Rule("C", 20),
                             Rule("D", 15))

  private val checkout = Checkout(rules)

  @Test
  internal fun `price of empty cart is zero`() {

    assertTrue { checkout.price("") == 0 }
  }

  @Test
  internal fun `price of cart with A product is fifty`() {

    assertTrue { checkout.price("A") == 50 }
  }

  @Test
  internal fun `price of cart with B product is thirty`() {

    assertTrue { checkout.price("B") == 30 }
  }

  @Test
  internal fun `price of cart with A,B product is eighty`() {

    assertTrue { checkout.price("AB") == 80 }
  }

  @Test
  internal fun `price of cart with A,B,C,D product is a hundred fifteen`() {

    assertTrue { checkout.price("CDBA") == 115 }
  }
}