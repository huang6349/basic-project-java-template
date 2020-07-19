import * as React from 'react';
import { useModel, history } from 'umi';

import { findMenu } from '@/layouts/utils';

export default function useDefaultPage(pathname) {
  const { initialState: { menuData = [] } = {} } = useModel('@@initialState');

  React.useEffect(() => {
    const { path } = findMenu(menuData, pathname) || {};
    if (!path) return;
    history.replace(path);
  }, [menuData]);
}
