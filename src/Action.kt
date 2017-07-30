abstract class Action(val description: String) {
    abstract fun invoke(env: Environment, area: AreaGroup, point: Vector): Action?
    override fun toString() = description
}

fun action(description: String, lambda: (env: Environment, area: AreaGroup, point: Vector) -> Action?): Action = object : Action(description) {
    override fun invoke(env: Environment, area: AreaGroup, point: Vector): Action? {
        return lambda.invoke(env, area, point)
    }
}

fun actionFinal(description: String, lambda: (env: Environment, area: AreaGroup, point: Vector) -> Unit): Action = object : Action(description) {
    override fun invoke(env: Environment, area: AreaGroup, point: Vector): Action? {
        lambda.invoke(env, area, point)
        return null
    }
}
