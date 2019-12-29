import * as React from 'react';
import { Form, Input, Icon, Button } from 'antd';

function FormView({ form, loading, onSubmit }) {
  const { getFieldDecorator } = form;

  function handleSubmit(e) {
    e.preventDefault();
    form.validateFields((err, values) => {
      if (err) return;
      typeof onSubmit === 'function' && onSubmit(values);
    });
  }

  return (
    <Form hideRequiredMark={true} layout="horizontal" onSubmit={handleSubmit} style={{ marginTop: '24px' }}>
      <Form.Item>
        {getFieldDecorator('username', {
          rules: [{ required: true, message: '请输入您的账号' }],
        })(
          <Input
            placeholder="请输入账号"
            prefix={<Icon type="user" style={{ color: 'rgba(0, 0, 0, 0.25)' }} />}
            size="large"
          />
        )}
      </Form.Item>
      <Form.Item>
        {getFieldDecorator('password', {
          rules: [{ required: true, message: '请输入您的密码' }],
        })(
          <Input.Password
            placeholder="请输入密码"
            prefix={<Icon type="lock" style={{ color: 'rgba(0, 0, 0, 0.25)' }} />}
            size="large"
          />
        )}
      </Form.Item>
      <Form.Item>
        <Button type="primary" htmlType="submit" loading={loading} size="large" block={!0}>
          登录
        </Button>
      </Form.Item>
    </Form>
  );
}

export default Form.create()(FormView);
