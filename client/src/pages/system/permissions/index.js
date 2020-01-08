import * as React from 'react';
import { useGetSet } from 'react-use';
import { Divider, Button, Modal, Icon } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { SearchTable } from '@/components';
import { EditModal } from './components';

const IndexPage = ({ permissions, loading, dispatch }) => {
  const [getEditModalType, setEditModalType] = useGetSet(!0);
  const [getEditModalData, setEditModalData] = useGetSet({});
  const [getEditModalVisible, setEditModalVisible] = useGetSet(false);
  const { list } = permissions;

  const columns = [
    {
      title: '菜单名称',
      width: 180,
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '菜单路径',
      width: 180,
      dataIndex: 'path',
      key: 'path',
    },
    {
      title: '菜单图标',
      width: 180,
      dataIndex: 'icon',
      key: 'icon',
      render: (text) => {
        return <Icon type={text} />;
      },
    },
    {
      title: '排序',
      width: 80,
      dataIndex: 'seq',
      key: 'seq',
    },
    {
      title: '描述',
      dataIndex: 'desc',
      key: 'desc',
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

  function handleDelete({ id, name } = {}) {
    Modal.confirm({
      title: `删除提示`,
      content: `你确定要删除菜单[${name}]吗`,
      onOk: () => {
        dispatch({ type: `permissions/deletePermissions`, payload: { id } });
      },
    });
  }

  function handleEditModalCancel() {
    setEditModalVisible(!1);
  }

  function handleEditModalOk(data) {
    const type = getEditModalType() ? 'create' : 'update';
    dispatch({
      type: `permissions/${type}Permissions`,
      payload: data,
    }).then(() => {
      setEditModalVisible(!1);
    });
  }

  return (
    <PageHeaderWrapper title={!1}>
      <SearchTable
        numWidth={100}
        hasSearch={!1}
        columns={columns}
        dataSource={list}
        loading={loading}
        hasPagination={!1}
      >
        <Button type="primary" icon="plus" loading={loading} onClick={handleIncrease}>
          新增
        </Button>
      </SearchTable>
      <EditModal
        loading={loading}
        visible={getEditModalVisible()}
        onCancel={handleEditModalCancel}
        onOk={handleEditModalOk}
        data={getEditModalData()}
        permissions={list}
      />
    </PageHeaderWrapper>
  );
};

function mapStateToProps({ permissions, loading: { models } }) {
  return { permissions, loading: models['permissions'] };
}

export default connect(mapStateToProps)(IndexPage);
