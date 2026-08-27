package ltd.jconet.lunchbox.module

interface Module {
    val id: String
    val name: String

    fun enable()
    fun disable()
}