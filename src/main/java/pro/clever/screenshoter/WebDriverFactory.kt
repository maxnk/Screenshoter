package pro.clever.screenshoter

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver

class WebDriverFactory {
    companion object {
        @JvmStatic
        fun createDriver(browser: Browser): RemoteWebDriver {
            return when(browser) {
                Browser.CHROME -> ChromeDriver()
                Browser.OPERA -> {
                    val capabilities = DesiredCapabilities.opera()
                    val options = ChromeOptions()
                    options.setBinary("C:/Program Files/Opera/launcher.exe")
                    //options.setBinary("C:/Program Files/Opera/44.0.2510.857/opera.exe")
                    capabilities.setCapability(ChromeOptions.CAPABILITY, options)

                    return ChromeDriver(capabilities)
                }
                Browser.IE -> {
                    val capabilities = DesiredCapabilities.internetExplorer()
                    capabilities.setCapability("initialBrowserUrl", "about:blank")

                    return InternetExplorerDriver(capabilities)
                }
                Browser.EDGE -> EdgeDriver()
                Browser.FIREFOX -> FirefoxDriver()
                Browser.ANDROID -> {
                    val options = ChromeOptions()
                    options.setExperimentalOption("androidPackage", "com.android.chrome")

                    return ChromeDriver(options)
                }
            }
        }
    }
}