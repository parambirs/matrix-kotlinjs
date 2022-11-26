import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.math.floor
import kotlin.random.Random

fun main() {
    val c = document.getElementById("c") as HTMLCanvasElement
    val ctx = c.getContext("2d") as CanvasRenderingContext2D

    // make canvas full screen
    c.height = window.innerHeight
    c.width = window.innerWidth

    // the characters
    val hanzi = "田由甲申甴电甶男甸甹町画甼甽甾甿畀畁畂畃畄畅畆畇畈畉畊畋界畍畎畏畐畑呂"

    // convert the string into an array of single characters
    val characters = hanzi.split("")
    val fontSize = 24
    val columns = c.width / fontSize    // no. of columns for the rain

    // a list of drops - one per column
    val drops = mutableListOf<Int>()

    // x below is the x coordinate
    // 1 = y-coordinate of the drop (same for every drop initially)
    for (x in 0 until columns) {
        drops.add(1)
    }

    // draw the characters
    fun draw() {
        // translucent BG to show trail
        ctx.fillStyle = "rgba(0, 0, 0, 0.05)"
        ctx.fillRect(0.0, 0.0, c.width.toDouble(), c.height.toDouble())

        ctx.fillStyle = "#03A062"   // green text
        ctx.font = "${fontSize}px arial"

        // loop over drops
        for (i in drops.indices) {
            // a random character to print
            val text = characters[floor(Random.nextDouble() * characters.size).toInt()]

            // x = i * fontSize, y = value of drops[i] * fontSize
            ctx.fillText(text, i * fontSize.toDouble(), drops[i] * fontSize.toDouble())

            // sending the drop back to the top randomly after it has crossed the screen
            // adding randomness to the reset to make the drops scattered on the Y axis
            if (drops[i] * fontSize > c.height && Random.nextDouble() > 0.975) {
                drops[i] = 0
            }

            // Increment Y coordinate
            drops[i]++
        }
    }

    window.setInterval(::draw, 35)
}
