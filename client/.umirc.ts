import { defineConfig } from 'umi';

export default defineConfig({
  extraBabelPlugins: [
    ['import', { libraryName: 'react-use', libraryDirectory: 'lib', camel2DashComponentName: false }],
  ],
  favicon: '/assets/favicon.ico',
  hash: true,
  history: {
    type: 'hash',
  },
  nodeModulesTransform: {
    type: 'none',
  },
  targets: {
    ie: 11,
  },
  title: 'basic-project-java-template',
  dva: {
    immer: true,
  },
  locale: {
    antd: true,
  },
  define: {
    TOKEN_NAME: 'basic',
  },
});
