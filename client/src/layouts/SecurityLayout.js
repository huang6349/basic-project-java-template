import * as React from 'react';
import { history, useModel } from 'umi';
import pathToRegexp from 'path-to-regexp';

const SecurityLayout = ({ children }) => {
  const { initialState: { isLogin } = {} } = useModel('@@initialState');

  React.useEffect(() => {
    if (isLogin && pathToRegexp('/login').exec(history.location.pathname)) {
      history.replace({ pathname: '/', query: { k: new Date().getTime() } });
    }
    if (!isLogin && !pathToRegexp('/login').exec(history.location.pathname)) {
      history.replace({ pathname: '/login', query: { k: new Date().getTime() } });
    }
  }, [isLogin]);

  return <React.Fragment>{children}</React.Fragment>;
};

export default SecurityLayout;
