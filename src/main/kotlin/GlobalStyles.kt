import kotlinx.css.*
import styled.*

object GlobalStyles {
    fun inject() {
        val styles = CSSBuilder(allowClasses = false).apply {
            html{
                height = 100.pct
            }
            body {
                overflowY = Overflow.scroll
                fontFamily = "-apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Fira Sans\", \"Droid Sans\", \"Helvetica Neue\", sans-serif"
                height = 100.pct
                margin(0.px)
                padding(0.px)
            }
        }

        injectGlobal(styles.toString())
    }
}