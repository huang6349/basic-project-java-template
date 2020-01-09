import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, Input } from 'antd';
import { Drawer } from '@/components';
import has from 'lodash/has';

function EditModal({ form, loading, visible, onCancel, onOk, data }) {
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
      title={`${has(data, 'id') ? '编辑' : '新增'}角色信息`}
      onCancel={onCancel}
    >
      <Form layout="vertical" hideRequiredMark={!1}>
        <Row gutter={15}>
          <Col span={12}>
            <Form.Item label="角色名称">
              {getFieldDecorator('name', {
                initialValue: data['name'],
                rules: [{ required: true, message: '角色名称不能为空' }],
              })(<Input placeholder="请输入角色名称" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="唯一标识码">
              {getFieldDecorator('code', {
                initialValue: data['code'],
                rules: [{ required: true, message: '唯一标识码不能为空' }],
              })(<Input placeholder="请输入唯一标识码" />)}
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

EditModal.propTypes = {
  loading: PropTypes.bool,
  visible: PropTypes.bool,
  onCancel: PropTypes.func,
  onOk: PropTypes.func,
  data: PropTypes.object,
};

EditModal.defaultProps = {
  loading: false,
  visible: false,
  data: {},
};

export default Form.create()(EditModal);
