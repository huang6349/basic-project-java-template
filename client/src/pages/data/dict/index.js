import * as React from 'react';
import { useGetSet } from 'react-use';
import { Divider, Button, Modal } from 'antd';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { connect } from 'dva';
import { SearchTable } from '@/components';
import { EditModal, ConfigModal } from './components';

const IndexPage = ({ dict, loading, dispatch }) => {
  const [getEditModalType, setEditModalType] = useGetSet(!0);
  const [getEditModalData, setEditModalData] = useGetSet({});
  const [getEditModalVisible, setEditModalVisible] = useGetSet(!1);
  const [getConfigModalData, setConfigModalData] = useGetSet({});
  const [getConfigModalVisible, setConfigModalVisible] = useGetSet(!1);
  const { current, pageSize, total, list, search } = dict;

  const columns = [
    {
      title: '字典名称',
      width: 180,
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '唯一标识码',
      width: 180,
      dataIndex: 'code',
      key: 'code',
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
      width: 230,
      key: 'action',
      render: (text, record) => (
        <React.Fragment>
          <Button type="link" icon="edit" onClick={() => handleEdit(record)}>
            编辑
          </Button>
          <Divider type="vertical" />
          <Button type="link" icon="setting" onClick={() => handleConfig(record)}>
            配置
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

  function handleConfig(record = {}) {
    setConfigModalData(record);
    setConfigModalVisible(!0);
  }

  function handleTableDropdownSelect(key, record) {
    key === 'delete' && handleDelete(record);
  }

  function handleDelete({ id, name } = {}) {
    Modal.confirm({
      title: `删除提示`,
      content: `你确定要删除字典[${name}]吗`,
      onOk: () => {
        dispatch({ type: `dict/deleteDict`, payload: { id } });
      },
    });
  }

  function handleEditModalCancel() {
    setEditModalVisible(!1);
  }

  function handleEditModalOk(data) {
    const type = getEditModalType() ? 'create' : 'update';
    dispatch({
      type: `dict/${type}Dict`,
      payload: data,
    }).then(() => {
      setEditModalVisible(!1);
    });
  }

  function handleConfigModalCancel() {
    setConfigModalVisible(!1);
  }

  function handleParamsChange(search, { current, pageSize }) {
    dispatch({
      type: 'dict/fetchDict',
      payload: { current, pageSize, search },
    });
  }

  return (
    <PageHeaderWrapper title={!1}>
      <SearchTable
        defaultSearch={search}
        searchPlaceholder="请输入字典名称"
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
      />
      <ConfigModal visible={getConfigModalVisible()} onCancel={handleConfigModalCancel} data={getConfigModalData()} />
    </PageHeaderWrapper>
  );
};

function mapStateToProps({ dict, loading: { models } }) {
  return { dict, loading: models['dict'] };
}

export default connect(mapStateToProps)(IndexPage);
