package com.tugbaolcer.clonex.server

import okhttp3.mockwebserver.MockResponse

fun requestTokenSuccess() = MockResponse()
    .setResponseCode(200)
    .setBody("""
        {
          "success": true,
          "request_token": "fake_token_123"
        }
    """)

fun loginSuccess() = MockResponse()
    .setResponseCode(200)
    .setBody("""
        {
          "success": true,
          "request_token": "fake_token_123"
        }
    """)

fun sessionSuccess() = MockResponse()
    .setResponseCode(200)
    .setBody("""
        {
          "success": true,
          "session_id": "session_123"
        }
    """)
