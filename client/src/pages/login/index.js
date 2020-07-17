import * as React from 'react';
import { useModel, useRequest } from 'umi';

import authenticate from '@/services/authenticate';
import { LoginFrom } from './components';
import styles from './index.less';

const IndexPage = () => {
  const { refresh } = useModel('@@initialState');

  const { loading, run } = useRequest(authenticate, {
    manual: true,
    onSuccess: () => refresh(),
  });

  return (
    <div className={styles['layout']}>
      <div className={styles['content']}>
        <div className={styles['main']}>
          <LoginFrom loading={loading} onFinish={(values) => run(values)} />
        </div>
      </div>
    </div>
  );
};

export default IndexPage;
