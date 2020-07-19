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
  title: '项目模板',
  locale: {},
  define: {
    TOKEN_NAME: 'basic',
  },
  theme: {
    '@primary-color': 'rgba(81, 130, 228, 1)',
    '@link-color': 'rgba(10, 18, 32, 0.64)',
    '@text-color': 'rgba(10, 18, 32, 0.64)',
    '@text-color-secondary': 'rgba(10, 18, 32, 0.44)',
    '@disabled-color': 'rgba(10, 18, 32, 0.24)',
    '@layout-header-background': 'rgba(44, 56, 76, 1)',
  },
});
