import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, Input, Select } from 'antd';
import { Drawer } from '@/components';
import has from 'lodash/has';

function ResourceEditModal({ form, loading, visible, onCancel, onOk, data, methods }) {
  const { getFieldDecorator } = form;

  function handleSubmit() {
    form.validateFields((errors, { ...values }) => {
      if (errors) return;
      const newData = { ...data, ...values };
      typeof onOk === 'function' && onOk(newData);
    });
  }

  return (
    <Drawer
      confirmLoading={loading}
      visible={visible}
      title={`${has(data, 'id') ? '编辑' : '新增'}菜单资源信息`}
      onCancel={onCancel}
    >
      <Form layout="vertical" hideRequiredMark={!1}>
        <Row gutter={15}>
          <Col span={12}>
            <Form.Item label="资源地址">
              {getFieldDecorator('pattern', {
                initialValue: data['pattern'],
                rules: [{ required: true, message: '资源地址不能为空' }],
              })(<Input placeholder="请输入资源地址" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="选择资源类型">
              {getFieldDecorator('methodId', {
                initialValue: data['methodId'],
                rules: [{ required: true, message: '资源类型不能为空' }],
              })(
                <Select placeholder="请选择资源类型" showSearch={true} optionFilterProp="children">
                  {methods.map((method) => (
                    <Select.Option key={`method-${method['id']}`} value={method['id']}>
                      {method['name']}
                    </Select.Option>
                  ))}
                </Select>
              )}
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={15}>
          <Col span={24}>
            <Form.Item label="描述">
              {getFieldDecorator('desc', {
                initialValue: data['desc'],
              })(<Input.TextArea placeholder="请输入描述" autosize={{ minRows: 3, maxRows: 6 }} />)}
            </Form.Item>
          </Col>
        </Row>
      </Form>
      <Drawer.FooterSubmit onSubmit={handleSubmit} />
    </Drawer>
  );
}

ResourceEditModal.propTypes = {
  loading: PropTypes.bool,
  visible: PropTypes.bool,
  onCancel: PropTypes.func,
  onOk: PropTypes.func,
  data: PropTypes.object,
  methods: PropTypes.array,
};

ResourceEditModal.defaultProps = {
  loading: false,
  visible: false,
  data: {},
  methods: [],
};

export default Form.create()(ResourceEditModal);
