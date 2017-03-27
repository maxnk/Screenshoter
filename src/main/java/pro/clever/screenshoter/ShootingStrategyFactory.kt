package pro.clever.screenshoter

import ru.yandex.qatools.ashot.shooting.ShootingStrategies
import ru.yandex.qatools.ashot.shooting.ShootingStrategy

class ShootingStrategyFactory {
    companion object {
        @JvmStatic
        fun createStrategy(browser: Browser): ShootingStrategy {
            if (browser == Browser.IE) {
                return ShootingStrategies.simple()
            }

            if (browser == Browser.ANDROID) {
                return ShootingStrategies.viewportRetina(100, 0, 0, 3.5f);
            }

            return ShootingStrategies.viewportPasting(100)
        }
    }
}