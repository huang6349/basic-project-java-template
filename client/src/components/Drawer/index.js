import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Drawer, Button } from 'antd';
import styles from './index.css';

export default function DrawerView({ confirmLoading, visible, maskClosable, title, onCancel, onOk, children }) {
  return (
    <Drawer
      className={styles['drawer']}
      closable={!0}
      destroyOnClose={!0}
      maskClosable={maskClosable}
      title={title}
      visible={visible}
      width={720}
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
  onCancel: PropTypes.func,
  onOk: PropTypes.func,
};

DrawerView.defaultProps = {
  confirmLoading: !1,
  visible: !1,
  maskClosable: !1,
};
