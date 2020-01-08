import * as React from 'react';
import * as PropTypes from 'prop-types';
import { useGetSet } from 'react-use';
import { Divider, Button, Modal } from 'antd';
import { connect } from 'dva';
import { SearchTable, Drawer } from '@/components';
import EditModal from './ConfigEditModal';
import has from 'lodash/has';

function ConfigModal({ dict, loading, dispatch, visible, onCancel, data }) {
  const [getEditModalType, setEditModalType] = useGetSet(!0);
  const [getEditModalData, setEditModalData] = useGetSet({});
  const [getEditModalVisible, setEditModalVisible] = useGetSet(!1);
  const { current, pageSize, total, list, search } = dict;

  const columns = [
    {
      title: '字典名称',
      width: 180,
      dataIndex: 'name',
      key: 'name',
    },
    {
      title: '字典数据',
      width: 180,
      dataIndex: 'data',
      key: 'data',
    },
    {
      title: '描述',
      dataIndex: 'desc',
      key: 'desc',
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

  function handleDelete({ id, name } = {}) {
    Modal.confirm({
      title: `删除提示`,
      content: `你确定要删除字典[${name}]吗`,
      onOk: () => {
        dispatch({ type: `dictConfig/deleteDict`, payload: { id } });
      },
    });
  }

  function handleEditModalCancel() {
    setEditModalVisible(!1);
  }

  function handleEditModalOk(data) {
    const type = getEditModalType() ? 'create' : 'update';
    dispatch({
      type: `dictConfig/${type}Dict`,
      payload: data,
    }).then(() => {
      setEditModalVisible(!1);
    });
  }

  function handleParamsChange(search, { current, pageSize }) {
    dispatch({
      type: 'dictConfig/fetchDict',
      payload: { current, pageSize, search },
    });
  }

  function handleAfterVisibleChange(visible) {
    if (!visible) {
      dispatch({ type: 'dictConfig/resetState' });
    } else {
      if (!has(data, 'id')) return;
      dispatch({ type: 'dictConfig/fetchDict', payload: { init: !0, pid: data['id'] } });
    }
  }

  return (
    <Drawer
      bodyStyle={{ paddingTop: 0 }}
      visible={visible}
      title={`字典列表【${data['name']}】`}
      width={1250}
      onCancel={onCancel}
      afterVisibleChange={handleAfterVisibleChange}
    >
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
    </Drawer>
  );
}

ConfigModal.propTypes = {
  visible: PropTypes.bool,
  onCancel: PropTypes.func,
  data: PropTypes.object,
};

ConfigModal.defaultProps = {
  visible: false,
  data: {},
};

function mapStateToProps({ dictConfig, loading: { models } }) {
  return { dict: dictConfig, loading: models['dictConfig'] };
}

export default connect(mapStateToProps)(ConfigModal);
