import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Button } from 'antd';
import styles from './Footer.Submit.css';

export default function FooterSubmitView({ confirmLoading, onCancel, onSubmit }) {
  return (
    <div className={styles['footer']}>
      <Button onClick={onCancel}>取消</Button>
      <Button type="primary" loading={confirmLoading} onClick={onSubmit}>确定</Button>
    </div>
  );
}

FooterSubmitView.propTypes = {
  confirmLoading: PropTypes.bool.isRequired,
  onCancel: PropTypes.func,
  onSubmit: PropTypes.func,
};

FooterSubmitView.defaultProps = {
  confirmLoading: !1,
};
