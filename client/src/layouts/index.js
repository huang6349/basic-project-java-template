import * as React from 'react';
import ProLayout from '@ant-design/pro-layout';
import { Link, useModel, history } from 'umi';
import localforage from 'localforage';
import isEmpty from 'lodash/isEmpty';

import { MenuHeader, RightContent } from './components';
import { loopMenu, pathsToRegexp } from './utils';
import styles from './index.less';

const BasicLayout = ({ children }) => {
  const { initialState: { isLogin, username, menuData = [] } = {}, refresh } = useModel('@@initialState');

  const [collapsed, setCollapsed] = React.useState(!1);

  React.useEffect(() => {
    if (isLogin && pathsToRegexp(['/login'], history['location']['pathname'])) {
      history.replace('/');
    }
    if (!isLogin && !pathsToRegexp(['/login'], history['location']['pathname'])) {
      history.replace('/login');
    }
  }, [isLogin]);

  if (pathsToRegexp(['/', '/login'], history['location']['pathname'])) {
    return <React.Fragment>{children}</React.Fragment>;
  }

  function handlePoweroff() {
    localforage.removeItem(TOKEN_NAME).then(() => refresh());
  }

  return (
    <ProLayout
      className={styles['layout']}
      location={history['location']}
      menuHeaderRender={() => {
        return <MenuHeader title="项目模板 - v3.0.0" />;
      }}
      layout="topmenu"
      navTheme="dark"
      collapsed={!0}
      onCollapse={(collapsed) => setCollapsed(collapsed)}
      rightContentRender={() => {
        return <RightContent theme="dark" username={username} onPoweroff={handlePoweroff} />;
      }}
      pageTitleRender={!1}
      disableContentMargin={!0}
    >
      <ProLayout
        location={history['location']}
        contentStyle={{ position: 'absolute', top: 0, right: 0, bottom: 0, left: 0, margin: 0 }}
        layout="sidemenu"
        navTheme="light"
        menuHeaderRender={!1}
        collapsed={collapsed}
        onCollapse={(collapsed) => setCollapsed(collapsed)}
        headerRender={!1}
        collapsedButtonRender={!1}
        pageTitleRender={!1}
        menuItemRender={(itemProps, defaultDom) => {
          if (!isEmpty(itemProps['children']) || !itemProps['path']) {
            return defaultDom;
          }
          return <Link to={{ pathname: itemProps['path'], query: { k: new Date().getTime() } }}>{defaultDom}</Link>;
        }}
        menuDataRender={() => loopMenu(menuData)}
        itemRender={(route, params, routes) => {
          const isLastItem = routes.indexOf(route) === routes['length'] - 1;
          if (!isLastItem) {
            return <Link to={route['path']}>{route['breadcrumbName']}</Link>;
          }
          return <span>{route['breadcrumbName']}</span>;
        }}
      >
        <React.Fragment>{children}</React.Fragment>
      </ProLayout>
    </ProLayout>
  );
};

export default BasicLayout;
