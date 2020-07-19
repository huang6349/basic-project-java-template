import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Input, Button } from 'antd';
import { UserOutlined, LockOutlined } from '@ant-design/icons';

export default function LoginFromView({ loading, onFinish }) {
  return (
    <Form hideRequiredMark={!0} layout="vertical" name="login" size="large" onFinish={onFinish} style={{ width: 400 }}>
      <Form.Item name="username" rules={[{ required: !0, message: '请输入您的账号' }]}>
        <Input
          prefix={<UserOutlined style={{ marginTop: 1, marginRight: 5, color: 'rgba(0, 0, 0, 0.25)' }} />}
          placeholder="请输入账号"
        />
      </Form.Item>
      <Form.Item name="password" rules={[{ required: !0, message: '请输入您的密码' }]}>
        <Input.Password
          prefix={<LockOutlined style={{ marginTop: 1, marginRight: 5, color: 'rgba(0, 0, 0, 0.25)' }} />}
          placeholder="请输入密码"
        />
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" loading={loading} block={!0}>
          登录
        </Button>
      </Form.Item>
    </Form>
  );
}

LoginFromView.propTypes = {
  loading: PropTypes.bool.isRequired,
  onFinish: PropTypes.func,
};

LoginFromView.defaultProps = {
  loading: !1,
};
