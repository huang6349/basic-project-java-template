import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Form, Row, Col, TreeSelect, Input, InputNumber } from 'antd';
import has from 'lodash/has';
import { Drawer } from '@/components';

function EditModal({ form, loading, visible, onCancel, onOk, data, permissions }) {
  const [treeData, setTreeData] = React.useState([]);

  React.useEffect(() => {
    function formatPermissions(permissions = []) {
      return permissions.map(({ id, pid, name, children }) => ({
        title: name,
        value: id,
        key: `permissions-${pid}-${id}`,
        children: formatPermissions(children),
      }));
    }
    setTreeData(formatPermissions(permissions));
  }, [permissions]);

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
      title={`${has(data, 'id') ? '编辑' : '新增'}菜单信息`}
      onCancel={onCancel}
    >
      <Form layout="vertical" hideRequiredMark={!1}>
        <Row gutter={15}>
          <Col span={12}>
            <Form.Item label="菜单名称">
              {getFieldDecorator('name', {
                initialValue: data['name'],
                rules: [{ required: true, message: '菜单名称不能为空' }],
              })(<Input placeholder="请输入菜单名称" />)}
            </Form.Item>
          </Col>
          {data['pid'] !== 0 && (
            <Col span={12}>
              <Form.Item label="上级菜单">
                {getFieldDecorator('pid', {
                  initialValue: data['pid'],
                })(<TreeSelect allowClear={!0} placeholder="请选择上级菜单" treeData={treeData} />)}
              </Form.Item>
            </Col>
          )}
          <Col span={12}>
            <Form.Item label="菜单路径">
              {getFieldDecorator('path', {
                initialValue: data['path'],
                rules: [{ required: true, message: '菜单路径不能为空' }],
              })(<Input placeholder="请输入菜单路径" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="菜单图标">
              {getFieldDecorator('icon', {
                initialValue: data['icon'],
              })(<Input placeholder="请输入菜单图标" />)}
            </Form.Item>
          </Col>
          <Col span={12}>
            <Form.Item label="排序">
              {getFieldDecorator('seq', {
                initialValue: data['seq'],
              })(<InputNumber placeholder="请输入排序" min={0} max={999} style={{ width: 180 }} />)}
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
  permissions: PropTypes.array,
};

EditModal.defaultProps = {
  loading: false,
  visible: false,
  data: {},
  permissions: [],
};

export default Form.create()(EditModal);
