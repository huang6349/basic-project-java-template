import * as React from 'react';
import * as PropTypes from 'prop-types';
import { useSetState } from 'react-use';
import { Table, Input } from 'antd';
import styles from './index.css';

export default function SearchTableView({
  rowKey,
  hasSearch,
  defaultSearch,
  searchPlaceholder,
  columns,
  dataSource,
  loading,
  pagination,
  onParamsChange,
  children,
}) {
  const [state, setState] = useSetState({ pagination: {} });

  const paging = {
    showQuickJumper: true,
    showSizeChanger: true,
    ...pagination,
  };
  const num = {
    title: '#序号',
    width: 80,
    dataIndex: '_num',
    render: (text, record, index) => {
      const { current, pageSize } = pagination;
      if (Number(current) && Number(pageSize)) {
        return (current - 1) * pageSize + index + 1;
      }
      return index + 1;
    },
  };
  const cols = [num, ...columns];

  const handleSearch = (search) => {
    if (typeof onParamsChange === 'function') {
      onParamsChange.apply(null, prepareParamsArguments({ ...state, search }));
    }
    setState({ search });
  };

  const handleChange = (pagination) => {
    if (typeof onParamsChange === 'function') {
      onParamsChange.apply(null, prepareParamsArguments({ ...state, pagination }));
    }
    setState({ pagination });
  };

  const prepareParamsArguments = ({ search, pagination }) => {
    return [search, pagination];
  };

  return (
    <div className={styles['layout']}>
      <div className={styles['header']}>
        {children && <div>{children}</div>}
        {hasSearch && (
          <Input.Search defaultValue={defaultSearch} placeholder={searchPlaceholder} onSearch={handleSearch} />
        )}
      </div>
      <Table
        bordered={true}
        columns={cols}
        dataSource={dataSource}
        loading={loading}
        pagination={paging}
        rowKey={rowKey}
        onChange={handleChange}
      />
    </div>
  );
}

SearchTableView.propTypes = {
  rowKey: PropTypes.string.isRequired,
  hasSearch: PropTypes.bool,
  defaultSearch: PropTypes.string,
  searchPlaceholder: PropTypes.string,
  columns: PropTypes.array,
  dataSource: PropTypes.array,
  loading: PropTypes.bool,
  pagination: PropTypes.object,
  onParamsChange: PropTypes.func,
};

SearchTableView.defaultProps = {
  rowKey: 'id',
  hasSearch: !0,
  searchPlaceholder: '请输入关键词',
  columns: [],
  dataSource: [],
  pagination: {},
};
