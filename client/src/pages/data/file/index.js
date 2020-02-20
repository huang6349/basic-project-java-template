import * as React from 'react';
import { PageHeaderWrapper } from '@ant-design/pro-layout';
import { Typography, Tooltip, Button } from 'antd';
import { connect } from 'dva';
import { SearchTable } from '@/components';

const IndexPage = ({ file, loading, dispatch }) => {
  const { current, pageSize, total, list, search } = file;

  const columns = [
    {
      title: '文件名称',
      width: 300,
      dataIndex: 'name',
      key: 'name',
      render: (text) => (
        <Tooltip title={text}>
          <Typography.Paragraph ellipsis>{text}</Typography.Paragraph>
        </Tooltip>
      ),
    },
    {
      title: '文件地址',
      dataIndex: 'url',
      key: 'url',
      render: (text) => (
        <Tooltip title={text}>
          <Typography.Paragraph ellipsis>{text}</Typography.Paragraph>
        </Tooltip>
      ),
    },
    {
      title: '文件类型',
      width: 120,
      dataIndex: 'type',
      key: 'type',
    },
    {
      title: '文件大小',
      width: 120,
      dataIndex: 'size',
      key: 'size',
    },
    {
      title: '下载次数',
      width: 120,
      dataIndex: 'num',
      key: 'num',
    },

    {
      title: '文件创建时间',
      width: 200,
      dataIndex: 'createdBy_text',
      key: 'createdBy_text',
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
          <Button type="link" icon="download" onClick={() => handleDownload(record)}>
            下载
          </Button>
        </React.Fragment>
      ),
    },
  ];

  function handleDownload({ download } = {}) {
    dispatch({ type: `global/localDownload`, payload: { url: download } }).then(() => {
      handleParamsChange(search, { current, pageSize });
    });
  }

  const pagination = { current, pageSize, total };

  function handleParamsChange(search, { current, pageSize }) {
    dispatch({
      type: 'file/fetchFile',
      payload: { current, pageSize, search },
    });
  }

  return (
    <PageHeaderWrapper title={!1}>
      <SearchTable
        defaultSearch={search}
        searchPlaceholder="请输入文件名称"
        columns={columns}
        dataSource={list}
        loading={loading}
        pagination={pagination}
        onParamsChange={handleParamsChange}
      ></SearchTable>
    </PageHeaderWrapper>
  );
};

function mapStateToProps({ file, loading: { models } }) {
  return { file, loading: models['file'] };
}

export default connect(mapStateToProps)(IndexPage);
