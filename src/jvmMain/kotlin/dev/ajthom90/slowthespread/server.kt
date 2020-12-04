package dev.ajthom90.slowthespread

import com.soywiz.klock.*
import com.soywiz.klock.locale.spanish
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.html.*

val locale = KlockLocale.english

val startDate = DateTime(2020, 3, 16, 12, 0, 0)

fun getDays(today: DateTime): Int {
	println(today.format(ISO8601.DATETIME_COMPLETE))
	val range = (startDate until today)
	println(range)
	val value = range.duration.days
	println(value)
	return value.toInt()
}

fun getTimes(days: Int): Double {
	return days.toDouble() / 15.toDouble()
}

fun HTML.index() {
	val today = DateTime.now()
	val days = getDays(today)
	head {
		title("Hello from Ktor!")
		meta(name = "viewport", content = "width=device-width, initial-scale=1, shrink-to-fit=no")
		link {
			rel = "stylesheet"
			href = "https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
			integrity = "sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
			attributes["crossorigin"] = "anonymous"
		}
	}
	body {
		div(classes = "container") {
			row {
				col {
					h1(classes = "display-1") {
						+"15* days"
						small(classes = "text-muted") {
							+" to slow the spread"
						}
					}
				}
			}
			row {
				col {
					+"On ${locale.formatDateLong.format(startDate)}, we were told to lock down for \"fifteen days to slow the spread.\" Today, ${locale.formatDateLong.format(today)}, is day $days of fifteen days to slow the spread, or ${getTimes(days).format(2)}x what was originally stated."
				}
			}
		}
		script(src = "/static/output.js") {}
	}
}

fun DIV.row(block: DIV.() -> Unit) = div(classes = "row", block = block)

fun DIV.col(block: DIV.() -> Unit) = div(classes = "row", block = block)

fun Double.format(digits: Int) = "%.${digits}f".format(this)

fun main() {
	embeddedServer(Netty, port = 8080, host = "127.0.0.1") {
		install(ContentNegotiation) {
			json()
		}
		routing {
			get("/") {
				call.respondHtml(HttpStatusCode.OK, HTML::index)
			}
			get("/days") {
				call.respond(getDays(DateTime.now()))
			}
			static("/static") {
				resources()
			}
		}
	}.start(wait = true)
}
