import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Drawer } from 'antd';
import FooterSubmit from './Footer.Submit';
import styles from './index.css';

export default function DrawerView({ confirmLoading, visible, onCancel, children, ...restProps }) {
  const [myVisible, setMyVisible] = React.useState(visible);

  React.useEffect(() => {
    if (!visible) {
      setTimeout(() => setMyVisible(visible), 10);
    } else {
      setMyVisible(visible);
    }
  }, [visible]);

  return (
    <Drawer
      closable={!0}
      destroyOnClose={!0}
      maskClosable={!1}
      width={720}
      placement="right"
      zIndex={998}
      {...restProps}
      className={styles['drawer']}
      visible={myVisible}
      onClose={onCancel}
    >
      {React.Children.map(children, (child) => {
        if (child.type === FooterSubmit) {
          return React.cloneElement(child, { confirmLoading, onCancel });
        }
        return child;
      })}
    </Drawer>
  );
}

DrawerView.propTypes = {
  confirmLoading: PropTypes.bool.isRequired,
  visible: PropTypes.bool.isRequired,
  onCancel: PropTypes.func,
};

DrawerView.defaultProps = {
  confirmLoading: !1,
  visible: !1,
};

DrawerView.FooterSubmit = FooterSubmit;
