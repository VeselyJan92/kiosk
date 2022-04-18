import { fileURLToPath, URL } from 'url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'


const BASE_URL = '/travel-info/';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },

  css: {
    preprocessorOptions: {
      scss: {
        additionalData: `
          @import "@/assets/scss/mixins.scss";
        `,

      }
    }
  },
  server: {
    base: BASE_URL,
    cors: {
      origin: false
    },
    host: true
  }

})
