import * as React from 'react';
import ProLayout from '@ant-design/pro-layout';
import styles from './index.css';

const BasicLayout = ({ children }) => {
  return (
    <ProLayout
      className={styles['layout']}
      menuHeaderRender={!1}
      layout="sidemenu"
      contentWidth="Fluid"
      navTheme="light"
      fixedHeader={!0}
      fixSiderbar={!0}
    >
      <React.Fragment>{children}</React.Fragment>
    </ProLayout>
  );
};

export default BasicLayout;
