package sample.meter

import io.micrometer.core.instrument.Metrics
import java.util.concurrent.atomic.AtomicInteger

val countInSystem: AtomicInteger = Metrics.gauge("lvalue", AtomicInteger(0))!!