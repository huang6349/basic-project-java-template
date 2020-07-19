import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Menu, Modal } from 'antd';
import { PoweroffOutlined } from '@ant-design/icons';

export default function AvatarMenuView({ menus, onClick, onPoweroff }) {
  const filterMenus = menus.filter(({ hideMenu }) => !hideMenu);

  function handleClick(params = {}) {
    if (params['key'] === 'poweroff') {
      Modal.confirm({
        title: '退出提示',
        content: '你确定要进行退出操作吗',
        onOk: () => {
          typeof onPoweroff === 'function' && onPoweroff();
        },
      });
    } else {
      typeof onClick === 'function' && onClick(params);
    }
  }

  return (
    <Menu selectedKeys={[]} onClick={handleClick}>
      {filterMenus.map(({ key, icon, name }) => (
        <Menu.Item key={key}>
          {icon} {name}
        </Menu.Item>
      ))}
      {filterMenus.length > 0 ? <Menu.Divider /> : null}
      <Menu.Item key="poweroff">
        <PoweroffOutlined /> 退出登录
      </Menu.Item>
    </Menu>
  );
}

AvatarMenuView.propTypes = {
  menus: PropTypes.arrayOf(
    PropTypes.shape({
      key: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      icon: PropTypes.element.isRequired,
      hideMenu: PropTypes.bool,
    }),
  ).isRequired,
  onClick: PropTypes.func,
  onPoweroff: PropTypes.func,
};

AvatarMenuView.defaultProps = {
  menus: [],
};
