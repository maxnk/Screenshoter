package pro.clever.screenshoter

import org.openqa.grid.internal.utils.configuration.StandaloneConfiguration
import org.openqa.selenium.remote.server.SeleniumServer

class Main {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val server = SeleniumServer(StandaloneConfiguration());
            try {
                server.boot()

                Screenshoter(ScreenshoterConfiguration(
                        url = "http://sciencedaily.com/",
                        browsers = arrayOf(Browser.IE, Browser.FIREFOX, Browser.EDGE, Browser.CHROME, Browser.OPERA),
                        widths = arrayOf(1920, 1024, 480),
                        imagesDirPath = "screenshots/"))
                    .run()

//                Screenshoter(ScreenshoterConfiguration(
//                        url = "http://github.com/",
//                        browsers = arrayOf(Browser.ANDROID),
//                        widths = arrayOf(1920, 1024, 480),
//                        imagesDirPath = "screenshots/"))
//                    .run()
            } finally {
                server.stop()
            }
        }
    }
}
