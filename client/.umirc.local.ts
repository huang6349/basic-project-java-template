import { defineConfig } from 'umi';

export default defineConfig({
  proxy: {
    '/api': { target: 'http://127.0.0.1:9099/', changeOrigin: true, pathRewrite: { '^/api': '/api' } },
  },
});
