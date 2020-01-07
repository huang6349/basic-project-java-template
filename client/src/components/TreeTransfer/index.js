import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Transfer, Tree } from 'antd';
import indexOf from 'lodash/indexOf';

export default function TreeTransfer({ targetKeys, dataSource, ...restProps }) {
  const transferDataSource = [];

  function flatten(list = []) {
    list.forEach((item) => {
      transferDataSource.push(item);
      flatten(item['children']);
    });
  }

  flatten(dataSource);

  return (
    <Transfer {...restProps} dataSource={transferDataSource} showSelectAll={!1} targetKeys={targetKeys}>
      {({ direction, onItemSelect, selectedKeys }) => {
        if (direction === 'right') return;

        const checkedKeys = [...selectedKeys, ...targetKeys];

        function handleCheck(_, { node: { props } = {} }) {
          onItemSelect(props['eventKey'], indexOf(checkedKeys, props['eventKey']) === -1);
        }

        return (
          <Tree
            checkable={!0}
            checkedKeys={checkedKeys}
            checkStrictly={!0}
            defaultExpandAll={!0}
            selectable={!1}
            showLine={!0}
            onCheck={handleCheck}
            treeData={dataSource}
          />
        );
      }}
    </Transfer>
  );
}

TreeTransfer.propTypes = {
  targetKeys: PropTypes.array.isRequired,
  dataSource: PropTypes.array.isRequired,
};

TreeTransfer.defaultProps = {
  targetKeys: [],
  dataSource: [],
};
