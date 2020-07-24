package sample.meter

import org.slf4j.LoggerFactory

inline fun <reified T> loggerFor() =  LoggerFactory.getLogger(T::class.java)