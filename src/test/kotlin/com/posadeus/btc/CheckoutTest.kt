package com.posadeus.btc

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CheckoutTest {

  private val checkout = Checkout()

  @Test
  internal fun `price of empty cart is zero`() {

    assertTrue { checkout.price("") == 0 }
  }

  @Test
  internal fun `price of cart with A product is fifty`() {

    assertTrue { checkout.price("A") == 50 }
  }
}