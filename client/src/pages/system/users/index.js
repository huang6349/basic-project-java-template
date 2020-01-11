import * as React from 'react';
import { useGetSet } from 'react-use';
import { Divider, Button, Modal, message } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { SearchTable } from '@/components';
import { EditModal } from './components';

const IndexPage = ({ users, loading, dispatch }) => {
  const [getEditModalType, setEditModalType] = useGetSet(!0);
  const [getEditModalData, setEditModalData] = useGetSet({});
  const [getEditModalVisible, setEditModalVisible] = useGetSet(false);
  const { current, pageSize, total, list, search, authoritys, sexs } = users;

  const columns = [
    {
      title: '用户名',
      width: 180,
      dataIndex: 'username',
      key: 'username',
    },
    {
      title: '角色昵称',
      width: 180,
      dataIndex: 'nickname',
      key: 'nickname',
    },
    {
      title: '用户性别',
      width: 100,
      dataIndex: 'sex_text',
      key: 'sex_text',
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
            menus={[
              { key: 'delete', name: '删除' },
              { key: 'enable', name: '启用', hideMenu: record['state'] !== 3 },
              { key: 'disable', name: '禁用', hideMenu: record['state'] === 3 },
              { key: 'reset', name: '重置密码' },
            ]}
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
    key === 'enable' && handleEnable(record);
    key === 'disable' && handleDisable(record);
    key === 'reset' && handleResetPassword(record);
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

  function handleEnable({ id, username } = {}) {
    Modal.confirm({
      title: `启用提示`,
      content: `你确定要启用用户[${username}]吗`,
      onOk: () => {
        dispatch({ type: `users/enableUser`, payload: { id } });
      },
    });
  }

  function handleDisable({ id, username } = {}) {
    Modal.confirm({
      title: `禁用提示`,
      content: `你确定要禁用用户[${username}]吗`,
      onOk: () => {
        dispatch({ type: `users/disableUser`, payload: { id } });
      },
    });
  }

  function handleResetPassword({ id, username }) {
    Modal.confirm({
      title: `重置提示`,
      content: `你确定要重置用户[${username}]的密码吗`,
      onOk: () => {
        dispatch({
          type: `users/resetPassword`,
          payload: { id },
        }).then(() => {
          message.info(`已将用户[${username}]的密码重置为[123456]`);
        });
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
        sexs={sexs}
      />
    </PageHeaderWrapper>
  );
};

function mapStateToProps({ users, loading: { models } }) {
  return { users, loading: models['users'] };
}

export default connect(mapStateToProps)(IndexPage);
