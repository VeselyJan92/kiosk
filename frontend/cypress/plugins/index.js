const path = require('path')
const { startDevServer } = require('@cypress/vite-dev-server')

module.exports = (on, config) => {
    console.log("xxx")

    on('dev-server:start', (options) => {
        console.log("start")

        return startDevServer({
            options,
            viteConfig: {
                configFile: path.resolve(__dirname, '..', '..', 'vite.config.ts'),
            },
        })
    })
}