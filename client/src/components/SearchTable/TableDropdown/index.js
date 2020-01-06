import * as React from 'react';
import * as PropTypes from 'prop-types';
import { Dropdown, Menu, Button } from 'antd';

export default function TableDropdown({ menus, text, onSelect }) {
  const filterMenus = menus.filter(({ hideMenu }) => !hideMenu);

  const menu = (
    <Menu onClick={({ key }) => typeof onSelect === 'function' && onSelect(key)}>
      {filterMenus.map(({ key, name }) => (
        <Menu.Item key={key}>{name}</Menu.Item>
      ))}
    </Menu>
  );
  return (
    <Dropdown overlay={menu}>
      <Button type="link">{text}</Button>
    </Dropdown>
  );
}

TableDropdown.propTypes = {
  menus: PropTypes.arrayOf(
    PropTypes.shape({
      key: PropTypes.string.isRequired,
      name: PropTypes.string.isRequired,
      hideMenu: PropTypes.bool,
    })
  ).isRequired,
  text: PropTypes.string.isRequired,
  onSelect: PropTypes.func,
};

TableDropdown.defaultProps = {
  menus: [],
  text: '更多操作',
};
