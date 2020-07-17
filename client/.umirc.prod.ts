import { defineConfig } from 'umi';

export default defineConfig({
  nodeModulesTransform: {
    type: 'all',
  },
  outputPath: '../service/src/main/resources/static',
});
