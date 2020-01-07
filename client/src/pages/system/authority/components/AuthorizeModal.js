import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Drawer, TreeTransfer } from '@/components';
import styles from './AuthorizeModal.css';
import has from 'lodash/has';

function EditModal({ loading, visible, onCancel, onOk, data, permissions }) {
  const [targetKeys, setTargetKeys] = React.useState();

  React.useEffect(() => {
    setTargetKeys((data['permissions'] || []).map((id) => `${id}`));
  }, [data]);

  function format(list = []) {
    return list.map(({ id, name, children }) => ({
      title: name,
      key: `${id}`,
      children: format(children),
    }));
  }

  return (
    <Drawer
      confirmLoading={loading}
      visible={visible}
      title={`角色权限配置【${data['name']}】`}
      width={590}
      onCancel={onCancel}
      onOk={() => {
        if (!has(data, 'id')) return;
        const newData = { id: data['id'], permissions: targetKeys };
        typeof onOk === 'function' && onOk(newData);
      }}
    >
      <TreeTransfer
        className={styles['transfer']}
        dataSource={format(permissions)}
        render={({ title }) => title}
        listStyle={{ width: 250, height: 450 }}
        targetKeys={targetKeys}
        titles={['未拥有的权限', '已拥有的权限']}
        onChange={(targetKeys) => setTargetKeys(targetKeys)}
      />
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

export default EditModal;
