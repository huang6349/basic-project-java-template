import * as React from 'react';
import router from 'umi/router';
import { connect } from 'dva';
import find from 'lodash/find';
import head from 'lodash/head';
import pathToRegexp from 'path-to-regexp';

function IndexPage({ global, location: { pathname } }) {
  const { menuData = [] } = global;
  React.useEffect(() => {
    if (!menuData) return;
    const { path } = head((find(menuData, ({ path }) => pathToRegexp(path).exec(pathname)) || {})['children']) || {};
    if (!path) return;
    router.replace({ pathname: path, query: { k: new Date().getTime() } });
  }, [menuData]);

  return <React.Fragment />;
}

function mapStateToProps({ global }) {
  return { global };
}

export default connect(mapStateToProps)(IndexPage);
