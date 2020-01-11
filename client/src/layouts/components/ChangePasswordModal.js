import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, Input } from 'antd';
import { Drawer } from '@/components';

function ChangePasswordModal({ form, loading, visible, onCancel, onOk }) {
  const { getFieldDecorator } = form;

  function handleSubmit() {
    form.validateFields((errors, values) => {
      if (errors) return;
      typeof onOk === 'function' && onOk(values);
    });
  }

  return (
    <Drawer confirmLoading={loading} visible={visible} title="更新密码" width={320} onCancel={onCancel}>
      <Form layout="vertical" hideRequiredMark={!0}>
        <Row gutter={15}>
          <Col span={24}>
            <Form.Item label="旧密码">
              {getFieldDecorator('oldPassword', {
                rules: [{ required: true, message: '旧密码不能为空' }],
              })(<Input.Password placeholder="请输入旧密码" />)}
            </Form.Item>
          </Col>
          <Col span={24}>
            <Form.Item label="新密码">
              {getFieldDecorator('newPassword', {
                rules: [{ required: true, message: '新密码不能为空' }],
              })(<Input.Password placeholder="请输入新密码" />)}
            </Form.Item>
          </Col>
          <Col span={24}>
            <Form.Item label="确认密码">
              {getFieldDecorator('confirm', {})(<Input.Password placeholder="请再次输入新密码" />)}
            </Form.Item>
          </Col>
        </Row>
      </Form>
      <Drawer.FooterSubmit onSubmit={handleSubmit} />
    </Drawer>
  );
}

ChangePasswordModal.propTypes = {
  loading: PropTypes.bool,
  visible: PropTypes.bool,
  onCancel: PropTypes.func,
  onOk: PropTypes.func,
};

ChangePasswordModal.defaultProps = {
  loading: false,
  visible: false,
};

export default Form.create()(ChangePasswordModal);
