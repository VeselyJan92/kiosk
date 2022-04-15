//import { startDevServer } from '@cypress/vite-dev-server'


const x = require("@cypress/vite-dev-server")


module.exports = (on, config) => {
    on('dev-server:start', async (options) => x.startDevServer({ options }))

    return config
}