package warehouse

typealias Rollback = () -> Unit

sealed interface Reservation

class OK(
    val rollback: Rollback,
) : Reservation

object Failure : Reservation
