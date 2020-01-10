import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, Input, Select } from 'antd';
import has from 'lodash/has';
import { Drawer } from '@/components';

function EditModal({ form, loading, visible, onCancel, onOk, data, authoritys }) {
  const { getFieldDecorator } = form;

  function handleSubmit() {
    form.validateFields((errors, values) => {
      if (errors) return;
      const newData = { ...data, ...values };
      typeof onOk === 'function' && onOk(newData);
    });
  }

  return (
    <Drawer
      confirmLoading={loading}
      visible={visible}
      title={`${has(data, 'id') ? '编辑' : '新增'}用户信息`}
      onCancel={onCancel}
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
      <Drawer.FooterSubmit onSubmit={handleSubmit} />
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
