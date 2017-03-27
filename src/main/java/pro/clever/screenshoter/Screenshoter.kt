package pro.clever.screenshoter

import io.github.bonigarcia.wdm.ChromeDriverManager
import io.github.bonigarcia.wdm.EdgeDriverManager
import io.github.bonigarcia.wdm.FirefoxDriverManager
import io.github.bonigarcia.wdm.InternetExplorerDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.remote.RemoteWebDriver
import ru.yandex.qatools.ashot.AShot
import ru.yandex.qatools.ashot.coordinates.WebDriverCoordsProvider
import java.net.URI
import java.net.URISyntaxException
import java.nio.file.Paths
import java.text.SimpleDateFormat
import java.util.*
import javax.imageio.ImageIO

class Screenshoter(val config: ScreenshoterConfiguration) {
    init {
        ChromeDriverManager.getInstance().setup()
        InternetExplorerDriverManager.getInstance().setup()
        EdgeDriverManager.getInstance().setup()
        FirefoxDriverManager.getInstance().setup()
    }

    fun run() {
        val time = SimpleDateFormat("yyyy-MM-dd_HH-mm").format(Date())
        for (browser in config.browsers) {
            val driver = WebDriverFactory.createDriver(browser)

            if (browser == Browser.ANDROID) {
                take(driver, browser, null, time)
            } else {
                for (width in config.widths) {
                    take(driver, browser, width, time)
                }
            }

            driver.close()
            driver.quit()
        }
    }

    private fun take(driver: RemoteWebDriver, browser: Browser, width: Int?, time: String) {
        driver.get(config.url)

        if (width != null) {
            driver.manage().window().size = Dimension(width, 1000)
        }

        val realWidth = width ?: driver.executeScript("return document.body.clientWidth;")

        val htmlElement = driver.findElement(By.tagName("html"))
        val image = AShot()
                .coordsProvider(WebDriverCoordsProvider())
                .shootingStrategy(ShootingStrategyFactory.createStrategy(browser))
                .takeScreenshot(driver, htmlElement)
                .image

        val dir = Paths.get(config.imagesDirPath, getDomainName(config.url) + "_" + time).toFile()
        if (!dir.exists()) {
            dir.mkdirs()
        }

        ImageIO.write(image, "png", Paths.get(dir.absolutePath, "${browser.toString().toLowerCase()}_${realWidth}x${image.height}.png").toFile())
    }

    @Throws(URISyntaxException::class)
    private fun getDomainName(url: String): String {
        val uri = URI(url)
        val domain = uri.host
        return if (domain.startsWith("www.")) domain.substring(4) else domain
    }
}