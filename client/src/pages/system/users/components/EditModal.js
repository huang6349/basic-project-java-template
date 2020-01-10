import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, Input, Select, DatePicker } from 'antd';
import has from 'lodash/has';
import moment from 'moment';
import { Drawer } from '@/components';

function EditModal({ form, loading, visible, onCancel, onOk, data, authoritys, sexs }) {
  const { getFieldDecorator } = form;

  function handleSubmit() {
    form.validateFields((errors, { birthday, ...values }) => {
      if (errors) return;
      const newData = { ...data, ...values };
      if (birthday) newData['birthday'] = birthday.format('YYYY-MM-DD HH:mm:ss');
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
                <Select mode="multiple" placeholder="请选择角色" showSearch={true} optionFilterProp="children">
                  {authoritys.map((authority) => (
                    <Select.Option key={`authority-${authority['code']}`} value={authority['id']}>
                      {authority['name']}
                    </Select.Option>
                  ))}
                </Select>
              )}
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={15}>
          <Col span={12}>
            <Form.Item label="用户昵称">
              {getFieldDecorator('nickname', {
                initialValue: data['nickname'],
                rules: [{ required: true, message: '用户昵称不能为空' }],
              })(<Input placeholder="请输入用户昵称" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="选择性别">
              {getFieldDecorator('sexId', {
                initialValue: data['sexId'],
                rules: [{ required: true, message: '性别不能为空' }],
              })(
                <Select placeholder="请选择性别" showSearch={true} optionFilterProp="children">
                  {sexs.map((sex) => (
                    <Select.Option key={`sex-${sex['id']}`} value={sex['id']}>
                      {sex['name']}
                    </Select.Option>
                  ))}
                </Select>
              )}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="手机号">
              {getFieldDecorator('mobilePhone', {
                initialValue: data['mobilePhone'],
              })(<Input placeholder="请输入手机号" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="邮箱">
              {getFieldDecorator('email', {
                initialValue: data['email'],
              })(<Input placeholder="请输入邮箱" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="生日">
              {getFieldDecorator('birthday', {
                initialValue: data['birthday'] && moment(data['birthday']),
              })(<DatePicker placeholder="请选择生日" style={{ width: '100%' }} />)}
            </Form.Item>
          </Col>
        </Row>
        <Row gutter={15}>
          <Col span={12}>
            <Form.Item label="真实姓名">
              {getFieldDecorator('realname', {
                initialValue: data['realname'],
              })(<Input placeholder="请输入真实姓名" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="身份证">
              {getFieldDecorator('idCard', {
                initialValue: data['idCard'],
              })(<Input placeholder="请输入身份证" />)}
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
