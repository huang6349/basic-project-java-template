import * as React from 'react';
import { useGetSet } from 'react-use';
import { Divider, Button, Modal } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { SearchTable, DataStateTag } from '@/components';
import { EditModal } from './components';

const IndexPage = ({ users, loading, dispatch }) => {
  const [getEditModalType, setEditModalType] = useGetSet(!0);
  const [getEditModalData, setEditModalData] = useGetSet({});
  const [getEditModalVisible, setEditModalVisible] = useGetSet(false);
  const { current, pageSize, total, list, search, authoritys } = users;

  const columns = [
    {
      title: '用户名',
      width: 180,
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: '角色信息',
      dataIndex: 'authorities_text',
      key: 'authorities_text',
      render: (text = []) => text.join('，'),
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
        return <DataStateTag state={state} text={text} />;
      },
    },
    {
      title: '操作',
      width: 250,
      key: 'action',
      render: (text, record) => (
        <React.Fragment>
          <span onClick={() => handleEdit(record)}>编辑</span>
          <Divider type="vertical" />
          <span onClick={() => handleDelete(record)}>删除</span>
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

  function handleDelete({ id, username } = {}) {
    Modal.confirm({
      title: `删除提示`,
      content: `你确定要删除用户[${username}]吗`,
      onOk: () => {
        dispatch({ type: `users/deleteUser`, payload: { id } });
      },
    });
  }

  function handleEditModalCancel() {
    setEditModalVisible(!1);
  }

  function handleEditModalOk(data) {
    const type = getEditModalType() ? 'create' : 'update';
    dispatch({
      type: `users/${type}User`,
      payload: data,
    }).then(() => {
      setEditModalVisible(!1);
    });
  }

  function handleParamsChange(search, { current, pageSize }) {
    dispatch({
      type: 'users/fetchUsers',
      payload: { current, pageSize, search },
    });
  }

  return (
    <PageHeaderWrapper title={!1}>
      <SearchTable
        defaultSearch={search}
        searchPlaceholder="请输入用户名"
        columns={columns}
        dataSource={list}
        loading={loading}
        pagination={pagination}
        onParamsChange={handleParamsChange}
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
        authoritys={authoritys}
      />
    </PageHeaderWrapper>
  );
};

function mapStateToProps({ users, loading: { global } }) {
  return { users, loading: global };
}

export default connect(mapStateToProps)(IndexPage);
