abstract class Action(val description: String) {
    abstract fun invoke(env: Environment, point: Vector): Action?
    override fun toString() = description
}

fun action(description: String, lambda: (env: Environment, point: Vector) -> Action?): Action = object : Action(description) {
    override fun invoke(env: Environment, point: Vector): Action? {
        return lambda.invoke(env, point)
    }
}

fun actionFinal(description: String, lambda: (env: Environment, point: Vector) -> Unit): Action = object : Action(description) {
    override fun invoke(env: Environment, point: Vector): Action? {
        lambda.invoke(env, point)
        return null
    }
}
