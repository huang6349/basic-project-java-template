import * as React from 'react';
import * as PropTypes from 'prop-types';
import { useGetSet } from 'react-use';
import { Divider, Button, Modal, Typography, Tooltip } from 'antd';
import { connect } from 'dva';
import { SearchTable, Drawer } from '@/components';
import EditModal from './ResourceEditModal';
import has from 'lodash/has';

function ResourceModal({ resource, loading, dispatch, visible, onCancel, data, type }) {
  const [getEditModalType, setEditModalType] = useGetSet(!0);
  const [getEditModalData, setEditModalData] = useGetSet({});
  const [getEditModalVisible, setEditModalVisible] = useGetSet(!1);
  const { current, pageSize, total, list, search, methods } = resource;

  const columns = [
    {
      title: '资源地址',
      width: 200,
      dataIndex: 'pattern',
      key: 'pattern',
      render: (text) => (
        <Tooltip title={text}>
          <Typography.Paragraph ellipsis>{text}</Typography.Paragraph>
        </Tooltip>
      ),
    },
    {
      title: '资源类型',
      width: 120,
      dataIndex: 'method_text',
      key: 'method_text',
    },
    {
      title: '描述',
      dataIndex: 'desc',
      key: 'desc',
      render: (text) => (
        <Tooltip title={text}>
          <Typography.Paragraph ellipsis>{text}</Typography.Paragraph>
        </Tooltip>
      ),
    },
    {
      title: '最后修改时间',
      width: 200,
      dataIndex: 'lastModifiedDate_text',
      key: 'lastModifiedDate_text',
    },
    {
      title: '状态',
      width: 100,
      dataIndex: 'state_text',
      key: 'state_text',
      render: (text = [], { state }) => {
        return <SearchTable.DataStateTag state={state} text={text} />;
      },
    },
    {
      title: '操作',
      width: 165,
      key: 'action',
      render: (text, record) => (
        <React.Fragment>
          <Button type="link" icon="edit" onClick={() => handleEdit(record)}>
            编辑
          </Button>
          <Divider type="vertical" />
          <SearchTable.TableDropdown
            menus={[{ key: 'delete', name: '删除' }]}
            onSelect={(key) => handleTableDropdownSelect(key, record)}
          />
        </React.Fragment>
      ),
    },
  ];

  const pagination = { current, pageSize, total };

  function handleIncrease() {
    setEditModalType(!0);
    setEditModalData({});
    setEditModalVisible(!0);
  }

  function handleEdit(record = {}) {
    setEditModalType(!1);
    setEditModalData(record);
    setEditModalVisible(!0);
  }

  function handleTableDropdownSelect(key, record) {
    key === 'delete' && handleDelete(record);
  }

  function handleDelete({ id, method_text, pattern } = {}) {
    Modal.confirm({
      title: `删除提示`,
      content: `你确定要删除资源[${method_text}, ${pattern}]的吗`,
      onOk: () => {
        dispatch({ type: `resource/deleteResource`, payload: { id } });
      },
    });
  }

  function handleEditModalCancel() {
    setEditModalVisible(!1);
  }

  function handleEditModalOk(data) {
    const type = getEditModalType() ? 'create' : 'update';
    dispatch({
      type: `resource/${type}Resource`,
      payload: data,
    }).then(() => {
      setEditModalVisible(!1);
    });
  }

  function handleParamsChange(search, { current, pageSize }) {
    dispatch({
      type: 'resource/fetchResource',
      payload: { current, pageSize, search },
    });
  }

  function handleAfterVisibleChange(visible) {
    if (!visible) {
      dispatch({ type: 'resource/resetState' });
    } else {
      if (!has(data, 'id')) return;
      dispatch({ type: 'resource/fetchResource', payload: { init: !0, pid: data['id'] } });
      dispatch({ type: `resource/fetchMethods` });
    }
  }

  return (
    <Drawer
      bodyStyle={{ paddingTop: 0 }}
      visible={visible}
      title={`菜单资源列表【${data['name']}】`}
      width={1250}
      onCancel={onCancel}
      afterVisibleChange={handleAfterVisibleChange}
    >
      <SearchTable
        defaultSearch={search}
        searchPlaceholder="请输入资源地址"
        columns={columns}
        dataSource={list}
        loading={loading}
        pagination={pagination}
        onParamsChange={handleParamsChange}
      >
        {type !== 'query' && (
          <Button type="primary" icon="plus" loading={loading} onClick={handleIncrease}>
            新增
          </Button>
        )}
      </SearchTable>
      <EditModal
        loading={loading}
        visible={getEditModalVisible()}
        onCancel={handleEditModalCancel}
        onOk={handleEditModalOk}
        data={getEditModalData()}
        methods={methods}
      />
    </Drawer>
  );
}

ResourceModal.propTypes = {
  visible: PropTypes.bool,
  onCancel: PropTypes.func,
  data: PropTypes.object,
};

ResourceModal.defaultProps = {
  visible: false,
  data: {},
};

function mapStateToProps({ resource, loading: { models } }) {
  return { resource, loading: models['resource'] };
}

export default connect(mapStateToProps)(ResourceModal);
