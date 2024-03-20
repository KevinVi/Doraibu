package com.kevinvi.common.extension

val String.Companion.empty: String
	get() = ""

fun CharSequence?.isNotNullOrBlank() = !this.isNullOrBlank()

fun CharSequence?.isNotNullOrEmpty() = !this.isNullOrEmpty()

fun <T : CharSequence> T.takeIfNotEmpty() = this.takeIf { it.isNotEmpty() }

fun <T : CharSequence> T?.takeIfNotNullOrEmpty() = this.takeIf { it.isNotNullOrEmpty() }

fun <T : CharSequence> T.takeIfNotBlank() = this.takeIf { it.isNotBlank() }

fun <T : CharSequence> T?.takeIfNotNullOrBlank() = this.takeIf { it.isNotNullOrBlank() }
