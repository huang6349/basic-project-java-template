import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Drawer, Button } from 'antd';
import styles from './index.css';

export default function DrawerView({ confirmLoading, visible, maskClosable, title, width, onCancel, onOk, children }) {
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
      className={styles['drawer']}
      closable={!0}
      destroyOnClose={!0}
      maskClosable={maskClosable}
      title={title}
      visible={myVisible}
      width={width}
      placement="right"
      onClose={onCancel}
    >
      <React.Fragment>{children}</React.Fragment>
      <div className={styles['footer']}>
        <Button onClick={onCancel}>取消</Button>
        <Button type="primary" loading={confirmLoading} onClick={onOk}>确定</Button>
      </div>
    </Drawer>
  );
}

DrawerView.propTypes = {
  confirmLoading: PropTypes.bool.isRequired,
  visible: PropTypes.bool.isRequired,
  maskClosable: PropTypes.bool,
  title: PropTypes.string,
  width: PropTypes.number,
  onCancel: PropTypes.func,
  onOk: PropTypes.func,
};

DrawerView.defaultProps = {
  confirmLoading: !1,
  visible: !1,
  width: 720,
  maskClosable: !1,
};
