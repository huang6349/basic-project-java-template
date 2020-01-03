import * as React from 'react';
import { useAsync } from 'react-use';
import ProLayout from '@ant-design/pro-layout';
import Link from 'umi/link';
import { connect } from 'dva';
import router from 'umi/router';
import pathToRegexp from 'path-to-regexp';
import localforage from 'localforage';
import { TOKEN } from '@/constant';
import styles from './index.css';

const BasicLayout = ({ global, location, dispatch, children }) => {
  const { value: hasToken, loading } = useAsync(async () => {
    return !!(await localforage.getItem(TOKEN['name']));
  }, []);

  React.useEffect(() => {
    if (loading) return;
    dispatch({ type: 'global/keep', payload: { hasToken } });
  }, [hasToken]);

  const { isLogin, menuData } = global;

  React.useEffect(() => {
    if (loading || !isLogin || isLogin === null) return;
    dispatch({ type: 'global/fetchUser' });
  }, [isLogin]);

  if (loading || isLogin === null) {
    return <React.Fragment>用户验证身份中...</React.Fragment>;
  }

  const { pathname } = location;

  if (isLogin && pathToRegexp('/login').exec(pathname)) {
    setTimeout(() => {
      router.replace({ pathname: '/', query: { k: new Date().getTime() } });
    }, 0);
    return <React.Fragment />;
  }

  if (!isLogin && !pathToRegexp('/login').exec(pathname)) {
    setTimeout(() => {
      router.replace({ pathname: '/login', query: { k: new Date().getTime() } });
    }, 0);
    return <React.Fragment />;
  }

  if (pathToRegexp('/login').exec(pathname)) {
    return <React.Fragment>{children}</React.Fragment>;
  }

  return (
    <ProLayout
      className={styles['layout']}
      location={location}
      title="basic"
      menuHeaderRender={!1}
      layout="sidemenu"
      contentWidth="Fluid"
      navTheme="light"
      fixedHeader={!0}
      fixSiderbar={!1}
      menuItemRender={(menuItemProps, defaultDom) => {
        if (menuItemProps['children'] || !menuItemProps['path']) {
          return defaultDom;
        }
        return <Link to={{ pathname: menuItemProps['path'], query: { k: new Date().getTime() } }}>{defaultDom}</Link>;
      }}
      breadcrumbRender={(routes) => {
        return routes.map(({ path, ...route }) => ({ path: `#${path}`, ...route }));
      }}
      menuDataRender={() => menuData}
    >
      <React.Fragment>{children}</React.Fragment>
    </ProLayout>
  );
};

function mapStateToProps({ global, loading: { global: loading } }) {
  return { global, loading };
}

export default connect(mapStateToProps)(BasicLayout);
