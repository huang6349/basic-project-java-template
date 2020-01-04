import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, Input, Select } from 'antd';
import { Drawer } from '@/components';
import has from 'lodash/has';

function EditModal({ form, loading, visible, onCancel, onOk, data, authoritys }) {
  const { getFieldDecorator } = form;

  return (
    <Drawer
      confirmLoading={loading}
      visible={visible}
      title={`${has(data, 'name') ? '编辑' : '新增'}用户信息`}
      onCancel={onCancel}
      onOk={() => {
        form.validateFields((errors, values) => {
          if (errors) return;
          const newData = { ...data, ...values };
          typeof onOk === 'function' && onOk(newData);
        });
      }}
    >
      <Form layout="vertical" hideRequiredMark={!1}>
        <Row gutter={15}>
          <Col span={12}>
            <Form.Item label="用户名">
              {getFieldDecorator('username', {
                initialValue: data['username'],
                rules: [{ required: true, message: '用户名不能为空' }],
              })(<Input placeholder="请输入用户名" disabled={has(data, 'id')} />)}
            </Form.Item>
          </Col>
          {!has(data, 'id') && (
            <Col span={12}>
              <Form.Item label="密码">
                {getFieldDecorator('password', {
                  initialValue: data['password'] || '123456',
                  rules: [{ required: true, message: '用户密码不能为空' }],
                })(<Input placeholder="请输入密码" />)}
              </Form.Item>
            </Col>
          )}
          <Col span={12}>
            <Form.Item label="选择角色">
              {getFieldDecorator('authorities', {
                initialValue: data['authorities'],
                rules: [{ required: true, message: '用户角色不能为空' }],
              })(
                <Select mode="multiple" placeholder="请选角色" showSearch={true} optionFilterProp="children">
                  {authoritys.map((authority) => (
                    <Select.Option key={authority['code']} value={authority['id']}>
                      {authority['name']}
                    </Select.Option>
                  ))}
                </Select>
              )}
            </Form.Item>
          </Col>
        </Row>
      </Form>
    </Drawer>
  );
}

EditModal.propTypes = {
  loading: PropTypes.bool,
  visible: PropTypes.bool,
  onCancel: PropTypes.func,
  onOk: PropTypes.func,
  data: PropTypes.object,
  authoritys: PropTypes.array,
};

EditModal.defaultProps = {
  loading: false,
  visible: false,
  data: {},
  authoritys: [],
};

export default Form.create()(EditModal);
