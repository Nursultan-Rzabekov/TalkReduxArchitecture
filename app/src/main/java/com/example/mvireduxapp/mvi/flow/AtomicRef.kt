package com.example.mvireduxapp.mvi.flow

import java.util.concurrent.atomic.AtomicReference

class AtomicRef<T>(value: T) {
  private val delegate = AtomicReference<T>(value)
  fun set(value: T) {
    delegate.set(value)
  }
  fun get(): T {
    return delegate.get()
  }
}
