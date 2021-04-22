import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import view.App


@ExperimentalJsExport
fun main() {
    
    GlobalStyles.inject()
    window.onload = {
        GlobalStyles.inject()
        render(document.getElementById("root")) {
            child(App::class) {}
        }
    }
}

