import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Menu, Icon, Modal } from 'antd';

export default function AvatarMenuView({ menus, onSelect, onPoweroff }) {
  const filterMenus = menus.filter(({ hideMenu }) => !hideMenu);

  function handleClick(params) {
    const { key } = params;
    if (key === 'poweroff') {
      Modal.confirm({
        title: `退出提示`,
        content: `你确定要进行退出操作吗`,
        onOk: () => {
          typeof onPoweroff === 'function' && onPoweroff();
        },
      });
    } else {
      typeof onSelect === 'function' && onSelect(params);
    }
  }

  return (
    <Menu selectedKeys={[]} onClick={handleClick}>
      {filterMenus.map(({ key, icon, name }) => (
        <Menu.Item key={key}>
          <Icon type={icon} /> {name}
        </Menu.Item>
      ))}
      {filterMenus.length > 0 ? <Menu.Divider /> : null}
      <Menu.Item key="poweroff">
        <Icon type="poweroff" /> 退出登录
      </Menu.Item>
    </Menu>
  );
}

AvatarMenuView.propTypes = {
  menus: PropTypes.arrayOf(
    PropTypes.shape({
      key: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      icon: PropTypes.string.isRequired,
      hideMenu: PropTypes.bool,
    })
  ).isRequired,
  onSelect: PropTypes.func,
  onPoweroff: PropTypes.func,
};

AvatarMenuView.defaultProps = {
  menus: [],
};
