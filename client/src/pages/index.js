import * as React from 'react';
import router from 'umi/router';
import { connect } from 'dva';
import head from 'lodash/head';

function IndexPage({ global }) {
  const { menuData = [] } = global;
  React.useEffect(() => {
    if (!menuData) return;
    const { path } = head(menuData) || {};
    if (!path) return;
    router.replace({ pathname: path, query: { k: new Date().getTime() } });
  }, [menuData]);

  return <React.Fragment />;
}

function mapStateToProps({ global }) {
  return { global };
}

export default connect(mapStateToProps)(IndexPage);
