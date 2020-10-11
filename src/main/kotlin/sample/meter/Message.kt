package sample.meter

data class Message<T>(
        val headers: Map<String, Any>,
        val payload: T
) {
    constructor(payload: T) : this(mapOf(Constants.CREATED_TIMESTAMP to System.nanoTime()), payload)
}

